package bg.tu_varna.sit.winefactory.business.services;

import bg.tu_varna.sit.winefactory.data.entities.Role;
import bg.tu_varna.sit.winefactory.data.entities.User;
import bg.tu_varna.sit.winefactory.data.repositories.UserRepository;

import java.util.List;

public class UserService {

    private final UserRepository userRepository = UserRepository.getInstance();

    private UserService() {
    }

    public static UserService getInstance() {
        return UserServiceHolder.INSTANCE;
    }

    private static class UserServiceHolder {
        private static final UserService INSTANCE = new UserService();
    }

    public void createUser(User user) {
        userRepository.save(user);
    }

    public void createOperator(Long id, String username, String password) {
        userRepository.save(new User(id, username, password, Role.OPERATOR));
    }

    public void createWarehouseKeeper(Long id, String username, String password) {
        userRepository.save(new User(id, username, password, Role.WAREHOUSE_KEEPER));
    }

    public void createAdmin(Long id, String username, String password) {
        userRepository.save(new User(id, username, password, Role.ADMIN));
    }

    public List<User> getAllUsers() {
        return userRepository.getAll();
    }

    public User login(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }
}