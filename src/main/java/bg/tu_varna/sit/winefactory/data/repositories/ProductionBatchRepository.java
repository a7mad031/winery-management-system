package bg.tu_varna.sit.winefactory.data.repositories;

import bg.tu_varna.sit.winefactory.data.access.Connection;
import bg.tu_varna.sit.winefactory.data.entities.ProductionBatch;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ProductionBatchRepository {

    private static ProductionBatchRepository instance;

    private ProductionBatchRepository() {
    }

    public static ProductionBatchRepository getInstance() {
        if (instance == null) {
            instance = new ProductionBatchRepository();
        }
        return instance;
    }

    public void save(ProductionBatch batch) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.persist(batch);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }

    public void update(ProductionBatch batch) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.merge(batch);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }

    public void delete(ProductionBatch batch) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.remove(session.contains(batch) ? batch : session.merge(batch));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }

    public List<ProductionBatch> getAll() {
        Session session = Connection.openSession();
        try {
            return session.createQuery("from ProductionBatch", ProductionBatch.class).list();
        } finally {
            session.close();
        }
    }
}