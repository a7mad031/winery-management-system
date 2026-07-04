package bg.tu_varna.sit.winefactory.business.services;

import bg.tu_varna.sit.winefactory.data.entities.BottleType;
import bg.tu_varna.sit.winefactory.data.repositories.BottleTypeRepository;

import java.util.List;

public class BottleTypeService {

    private static BottleTypeService instance;
    private final BottleTypeRepository bottleTypeRepository;

    private BottleTypeService() {
        bottleTypeRepository = BottleTypeRepository.getInstance();
    }

    public static BottleTypeService getInstance() {
        if (instance == null) {
            instance = new BottleTypeService();
        }
        return instance;
    }

    public void addBottleType(BottleType bottleType) {
        bottleTypeRepository.save(bottleType);
    }

    public void updateBottleType(BottleType bottleType) {
        bottleTypeRepository.update(bottleType);
    }

    public void deleteBottleType(BottleType bottleType) {
        bottleTypeRepository.delete(bottleType);
    }

    public List<BottleType> getAllBottleTypes() {
        return bottleTypeRepository.getAll();
    }
}