package bg.tu_varna.sit.winefactory.data.repositories;

import bg.tu_varna.sit.winefactory.data.access.Connection;
import bg.tu_varna.sit.winefactory.data.entities.BottledWine;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class BottledWineRepository {

    private static BottledWineRepository instance;

    private BottledWineRepository() {
    }

    public static BottledWineRepository getInstance() {
        if (instance == null) {
            instance = new BottledWineRepository();
        }
        return instance;
    }

    public void save(BottledWine bottledWine) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.persist(bottledWine);
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

    public List<BottledWine> getAll() {
        Session session = Connection.openSession();
        try {
            return session.createQuery("from BottledWine", BottledWine.class).list();
        } finally {
            session.close();
        }
    }
}
