package bg.tu_varna.sit.winefactory.data.repositories;

import bg.tu_varna.sit.winefactory.data.access.Connection;
import bg.tu_varna.sit.winefactory.data.entities.BottleType;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class BottleTypeRepository {

    private static BottleTypeRepository instance;

    private BottleTypeRepository() {
    }

    public static BottleTypeRepository getInstance() {
        if (instance == null) {
            instance = new BottleTypeRepository();
        }
        return instance;
    }

    public void save(BottleType bottleType) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.persist(bottleType);
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

    public void update(BottleType bottleType) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.merge(bottleType);
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

    public void delete(BottleType bottleType) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.remove(session.contains(bottleType) ? bottleType : session.merge(bottleType));
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

    public List<BottleType> getAll() {
        Session session = Connection.openSession();
        try {
            return session.createQuery("from BottleType", BottleType.class).list();
        } finally {
            session.close();
        }
    }
}