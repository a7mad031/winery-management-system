package bg.tu_varna.sit.winefactory.data.repositories;

import bg.tu_varna.sit.winefactory.data.access.Connection;
import bg.tu_varna.sit.winefactory.data.entities.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class UserRepository implements DAORepository<User> {

    private UserRepository() {
    }

    public static UserRepository getInstance() {
        return UserRepositoryHolder.INSTANCE;
    }

    private static class UserRepositoryHolder {
        private static final UserRepository INSTANCE = new UserRepository();
    }

    @Override
    public void save(User obj) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.persist(obj);
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

    @Override
    public void update(User obj) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.merge(obj);
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

    @Override
    public void delete(User obj) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.remove(session.contains(obj) ? obj : session.merge(obj));
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

    @Override
    public Optional<User> getById(Long id) {
        Session session = Connection.openSession();
        try {
            return Optional.ofNullable(session.get(User.class, id));
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAll() {
        Session session = Connection.openSession();
        try {
            return session.createQuery("from User", User.class).list();
        } finally {
            session.close();
        }
    }

    public User findByUsernameAndPassword(String username, String password) {
        Session session = Connection.openSession();
        try {
            return session.createQuery(
                            "from User where username = :username and password = :password",
                            User.class
                    )
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .uniqueResult();
        } finally {
            session.close();
        }
    }
}