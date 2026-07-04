package bg.tu_varna.sit.winefactory.business.services;

import bg.tu_varna.sit.winefactory.data.entities.ProductionBatch;
import bg.tu_varna.sit.winefactory.data.repositories.ProductionBatchRepository;

import java.util.List;

public class ProductionBatchService {

    private static ProductionBatchService instance;
    private final ProductionBatchRepository productionBatchRepository;

    private ProductionBatchService() {
        productionBatchRepository = ProductionBatchRepository.getInstance();
    }

    public static ProductionBatchService getInstance() {
        if (instance == null) {
            instance = new ProductionBatchService();
        }
        return instance;
    }

    public void addBatch(ProductionBatch batch) {
        productionBatchRepository.save(batch);
    }

    public void updateBatch(ProductionBatch batch) {
        productionBatchRepository.update(batch);
    }

    public void deleteBatch(ProductionBatch batch) {
        productionBatchRepository.delete(batch);
    }

    public List<ProductionBatch> getAllBatches() {
        return productionBatchRepository.getAll();
    }
}
