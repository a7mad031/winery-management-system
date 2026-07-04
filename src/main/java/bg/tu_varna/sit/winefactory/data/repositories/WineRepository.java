package bg.tu_varna.sit.winefactory.data.repositories;

import bg.tu_varna.sit.winefactory.data.access.Connection;
import bg.tu_varna.sit.winefactory.data.entities.Wine;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class WineRepository {

    private static WineRepository instance;

    private WineRepository() {
    }

    public static WineRepository getInstance() {
        if (instance == null) {
            instance = new WineRepository();
        }
        return instance;
    }

    public void save(Wine wine) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.persist(wine);
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

    public void update(Wine wine) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.merge(wine);
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

    public void delete(Wine wine) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.remove(session.contains(wine) ? wine : session.merge(wine));
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

    public List<Wine> getAll() {
        Session session = Connection.openSession();
        try {
            return session.createQuery("from Wine", Wine.class).list();
        } finally {
            session.close();
        }
    }
}