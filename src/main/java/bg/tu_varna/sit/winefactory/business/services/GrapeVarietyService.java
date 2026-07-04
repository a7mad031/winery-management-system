package bg.tu_varna.sit.winefactory.business.services;

import bg.tu_varna.sit.winefactory.data.entities.GrapeVariety;
import bg.tu_varna.sit.winefactory.data.repositories.GrapeVarietyRepository;

import java.util.List;

public class GrapeVarietyService {

    private final GrapeVarietyRepository grapeVarietyRepository = GrapeVarietyRepository.getInstance();

    private GrapeVarietyService() {
    }

    public static GrapeVarietyService getInstance() {
        return GrapeVarietyServiceHolder.INSTANCE;
    }

    private static class GrapeVarietyServiceHolder {
        private static final GrapeVarietyService INSTANCE = new GrapeVarietyService();
    }

    public void addGrapeVariety(GrapeVariety grapeVariety) {
        grapeVarietyRepository.save(grapeVariety);
    }

    public void updateGrapeVariety(GrapeVariety grapeVariety) {
        grapeVarietyRepository.update(grapeVariety);
    }

    public void deleteGrapeVariety(GrapeVariety grapeVariety) {
        grapeVarietyRepository.delete(grapeVariety);
    }

    public List<GrapeVariety> getAllGrapeVarieties() {
        return grapeVarietyRepository.getAll();
    }

    public boolean isBelowMinimum(GrapeVariety grapeVariety) {
        return grapeVariety.getQuantityKg() <= grapeVariety.getCriticalMinimumKg();
    }

    public boolean isOutOfStock(GrapeVariety grapeVariety) {
        return grapeVariety.getQuantityKg() <= 0;
    }
}