package bg.tu_varna.sit.winefactory.business.services;

import bg.tu_varna.sit.winefactory.data.entities.Wine;
import bg.tu_varna.sit.winefactory.data.repositories.WineRepository;

import java.util.List;

public class WineService {

    private static WineService instance;
    private final WineRepository wineRepository;

    private WineService() {
        wineRepository = WineRepository.getInstance();
    }

    public static WineService getInstance() {
        if (instance == null) {
            instance = new WineService();
        }
        return instance;
    }

    public void addWine(Wine wine) {
        wineRepository.save(wine);
    }

    public void updateWine(Wine wine) {
        wineRepository.update(wine);
    }

    public void deleteWine(Wine wine) {
        wineRepository.delete(wine);
    }

    public List<Wine> getAllWines() {
        return wineRepository.getAll();
    }
}