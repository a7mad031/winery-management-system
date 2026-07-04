package bg.tu_varna.sit.winefactory.business.services;

import bg.tu_varna.sit.winefactory.data.entities.GrapeVariety;
import bg.tu_varna.sit.winefactory.data.entities.WineBatch;
import bg.tu_varna.sit.winefactory.data.entities.WineRecipeItem;
import bg.tu_varna.sit.winefactory.data.entities.WineType;

import java.time.LocalDate;

public class ProductionService {

    private ProductionService() {
    }

    public static ProductionService getInstance() {
        return ProductionServiceHolder.INSTANCE;
    }

    private static class ProductionServiceHolder {
        private static final ProductionService INSTANCE = new ProductionService();
    }

    public boolean canProduceWine(WineType wineType) {
        for (WineRecipeItem item : wineType.getRecipeItems()) {
            GrapeVariety grape = item.getGrapeVariety();
            if (grape.getQuantityKg() < item.getRequiredQuantityKg()) {
                return false;
            }
        }
        return true;
    }

    public WineBatch produceWine(Long batchId, WineType wineType) {
        if (!canProduceWine(wineType)) {
            throw new IllegalArgumentException("Not enough grape quantity to produce this wine.");
        }

        double totalLiters = 0;

        for (WineRecipeItem item : wineType.getRecipeItems()) {
            GrapeVariety grape = item.getGrapeVariety();
            grape.setQuantityKg(grape.getQuantityKg() - item.getRequiredQuantityKg());
            totalLiters += item.getRequiredQuantityKg() * grape.getWineYieldPerKg();
        }

        return new WineBatch(batchId, wineType, totalLiters, LocalDate.now());
    }
}