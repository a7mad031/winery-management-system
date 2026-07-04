package bg.tu_varna.sit.winefactory.data.repositories;

import bg.tu_varna.sit.winefactory.data.access.Connection;
import bg.tu_varna.sit.winefactory.data.entities.WineType;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WineTypeRepository implements DAORepository<WineType> {

    private WineTypeRepository() {
    }

    public static WineTypeRepository getInstance() {
        return WineTypeRepositoryHolder.INSTANCE;
    }

    private static class WineTypeRepositoryHolder {
        private static final WineTypeRepository INSTANCE = new WineTypeRepository();
    }

    @Override
    public void save(WineType obj) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.save(obj);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void update(WineType obj) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.merge(obj);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void delete(WineType obj) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            WineType managed = session.get(WineType.class, obj.getId());
            if (managed != null) {
                session.remove(managed);
            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public Optional<WineType> getById(Long id) {
        Session session = Connection.openSession();
        try {
            return Optional.ofNullable(session.get(WineType.class, id));
        } finally {
            session.close();
        }
    }

    @Override
    public List<WineType> getAll() {
        Session session = Connection.openSession();
        try {
            return session.createQuery("FROM WineType", WineType.class).getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        } finally {
            session.close();
        }
    }
}