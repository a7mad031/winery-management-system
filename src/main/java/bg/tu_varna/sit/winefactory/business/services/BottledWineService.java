package bg.tu_varna.sit.winefactory.business.services;

import bg.tu_varna.sit.winefactory.data.entities.BottledWine;
import bg.tu_varna.sit.winefactory.data.repositories.BottledWineRepository;

import java.util.List;

public class BottledWineService {

    private static BottledWineService instance;
    private final BottledWineRepository bottledWineRepository;

    private BottledWineService() {
        bottledWineRepository = BottledWineRepository.getInstance();
    }

    public static BottledWineService getInstance() {
        if (instance == null) {
            instance = new BottledWineService();
        }
        return instance;
    }

    public void addBottledWine(BottledWine bottledWine) {
        bottledWineRepository.save(bottledWine);
    }

    public List<BottledWine> getAllBottledWines() {
        return bottledWineRepository.getAll();
    }
}