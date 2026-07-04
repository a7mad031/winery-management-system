package bg.tu_varna.sit.winefactory.business.services;

import bg.tu_varna.sit.winefactory.data.entities.WineRecipeItem;
import bg.tu_varna.sit.winefactory.data.entities.WineType;
import bg.tu_varna.sit.winefactory.data.repositories.WineTypeRepository;

import java.util.List;

public class WineTypeService {

    private final WineTypeRepository wineTypeRepository = WineTypeRepository.getInstance();

    private WineTypeService() {
    }

    public static WineTypeService getInstance() {
        return WineTypeServiceHolder.INSTANCE;
    }

    private static class WineTypeServiceHolder {
        private static final WineTypeService INSTANCE = new WineTypeService();
    }

    public void addWineType(WineType wineType) {
        wineTypeRepository.save(wineType);
    }

    public void updateWineType(WineType wineType) {
        wineTypeRepository.update(wineType);
    }

    public void deleteWineType(WineType wineType) {
        wineTypeRepository.delete(wineType);
    }

    public void addRecipeItemToWine(WineType wineType, WineRecipeItem item) {
        wineType.addRecipeItem(item);
        wineTypeRepository.update(wineType);
    }

    public List<WineType> getAllWineTypes() {
        return wineTypeRepository.getAll();
    }
}