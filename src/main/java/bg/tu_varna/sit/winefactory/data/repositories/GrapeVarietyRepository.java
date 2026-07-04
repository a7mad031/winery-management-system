package bg.tu_varna.sit.winefactory.data.repositories;

import bg.tu_varna.sit.winefactory.data.access.Connection;
import bg.tu_varna.sit.winefactory.data.entities.GrapeVariety;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GrapeVarietyRepository implements DAORepository<GrapeVariety> {

    private GrapeVarietyRepository() {
    }

    public static GrapeVarietyRepository getInstance() {
        return GrapeVarietyRepositoryHolder.INSTANCE;
    }

    private static class GrapeVarietyRepositoryHolder {
        private static final GrapeVarietyRepository INSTANCE = new GrapeVarietyRepository();
    }

    @Override
    public void save(GrapeVariety obj) {
        Transaction transaction = null;

        try (Session session = Connection.openSession()) {
            transaction = session.beginTransaction();
            session.persist(obj);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void update(GrapeVariety obj) {
        Transaction transaction = null;

        try (Session session = Connection.openSession()) {
            transaction = session.beginTransaction();
            session.merge(obj);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void delete(GrapeVariety obj) {
        Transaction transaction = null;

        try (Session session = Connection.openSession()) {
            transaction = session.beginTransaction();
            GrapeVariety managed = session.get(GrapeVariety.class, obj.getId());
            if (managed != null) {
                session.remove(managed);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public Optional<GrapeVariety> getById(Long id) {
        try (Session session = Connection.openSession()) {
            GrapeVariety grapeVariety = session.get(GrapeVariety.class, id);
            return Optional.ofNullable(grapeVariety);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<GrapeVariety> getAll() {
        try (Session session = Connection.openSession()) {
            return session.createQuery("FROM GrapeVariety", GrapeVariety.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}