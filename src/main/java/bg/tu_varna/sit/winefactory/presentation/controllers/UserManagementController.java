package bg.tu_varna.sit.winefactory.presentation.controllers;

import bg.tu_varna.sit.winefactory.business.services.UserService;
import bg.tu_varna.sit.winefactory.data.entities.Role;
import bg.tu_varna.sit.winefactory.data.entities.User;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;

public class UserManagementController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ComboBox<Role> roleComboBox;

    @FXML
    private ListView<User> userListView;

    @FXML
    private Label messageLabel;

    private final UserService userService = UserService.getInstance();

    @FXML
    private void initialize() {
        roleComboBox.setItems(FXCollections.observableArrayList(Role.ADMIN, Role.OPERATOR, Role.WAREHOUSE_KEEPER));
        refreshList();
    }

    @FXML
    private void onAddUser() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        Role role = roleComboBox.getValue();

        if (username.isEmpty() || password.isEmpty() || role == null) {
            messageLabel.setText("Please fill all fields.");
            return;
        }

        try {
            User user = new User(null, username, password, role);
            userService.createUser(user);
            refreshList();
            clearFields();
            messageLabel.setText("User added successfully.");
        } catch (Exception e) {
            messageLabel.setText("Error adding user.");
            e.printStackTrace();
        }
    }

    @FXML
    private void onDeleteUser() {
        User selectedUser = userListView.getSelectionModel().getSelectedItem();

        if (selectedUser == null) {
            messageLabel.setText("Please select a user.");
            return;
        }

        try {
            userService.deleteUser(selectedUser);
            refreshList();
            clearFields();
            messageLabel.setText("User deleted successfully.");
        } catch (Exception e) {
            messageLabel.setText("Error deleting user.");
            e.printStackTrace();
        }
    }

    @FXML
    private void onClearFields() {
        clearFields();
        messageLabel.setText("Fields cleared.");
    }

    @FXML
    private void onBackToDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboard-view.fxml"));
            Scene scene = new Scene(loader.load(), 600, 400);

            Stage stage = (Stage) messageLabel.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Dashboard");
            stage.show();
        } catch (Exception e) {
            messageLabel.setText("Error loading dashboard.");
            e.printStackTrace();
        }
    }

    private void refreshList() {
        List<User> users = userService.getAllUsers();
        userListView.setItems(FXCollections.observableArrayList(users));
    }

    private void clearFields() {
        usernameField.clear();
        passwordField.clear();
        roleComboBox.setValue(null);
    }
}