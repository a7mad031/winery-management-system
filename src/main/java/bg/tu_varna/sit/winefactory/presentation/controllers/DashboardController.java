package bg.tu_varna.sit.winefactory.presentation.controllers;

import bg.tu_varna.sit.winefactory.application.UserSession;
import bg.tu_varna.sit.winefactory.business.services.GrapeVarietyService;
import bg.tu_varna.sit.winefactory.data.entities.GrapeVariety;
import bg.tu_varna.sit.winefactory.data.entities.Role;
import bg.tu_varna.sit.winefactory.data.entities.User;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

public class DashboardController {

    @FXML
    private Button manageUsersButton;

    @FXML
    private Button manageWineTypesButton;

    @FXML
    private Button produceWineButton;

    @FXML
    private Button manageProductionBatchesButton;

    @FXML
    private Button manageBottleTypesButton;

    @FXML
    private Button manageGrapesButton;

    @FXML
    private Button viewNotificationsButton;

    @FXML
    private Button generateReportsButton;

    @FXML
    private Label messageLabel;

    @FXML
    private ListView<String> grapeListView;

    @FXML
    private void initialize() {
        List<GrapeVariety> grapes = GrapeVarietyService.getInstance().getAllGrapeVarieties();

        List<String> grapeNames = grapes.stream()
                .map(g -> g.getName() + " - " + g.getQuantityKg() + " kg")
                .collect(Collectors.toList());

        grapeListView.setItems(FXCollections.observableArrayList(grapeNames));

        applyRoleAccess();
    }

    private void applyRoleAccess() {
        User loggedInUser = UserSession.getCurrentUser();

        if (loggedInUser == null) {
            messageLabel.setText("No active user session.");
            return;
        }

        Role role = loggedInUser.getRole();

        messageLabel.setText("Logged in as: " + loggedInUser.getUsername() + " (" + role + ")");

        boolean isAdmin = role == Role.ADMIN;
        boolean isOperator = role == Role.OPERATOR;
        boolean isWarehouse = role == Role.WAREHOUSE_KEEPER;

        showButton(manageUsersButton, isAdmin);

        showButton(manageGrapesButton, isAdmin || isOperator || isWarehouse);

        showButton(manageBottleTypesButton, isAdmin || isOperator || isWarehouse);

        showButton(manageWineTypesButton, isAdmin || isOperator);

        showButton(produceWineButton, isAdmin || isOperator);

        showButton(manageProductionBatchesButton, isAdmin || isOperator);

        showButton(viewNotificationsButton, true);

        showButton(generateReportsButton, true);
    }

    private void showButton(Button button, boolean visible) {
        if (button != null) {
            button.setVisible(visible);
            button.setManaged(visible);
        }
    }

    @FXML
    private void onManageUsers() {
        if (!hasAccess(Role.ADMIN)) {
            messageLabel.setText("Access denied.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/user-management-view.fxml"));
            Scene scene = new Scene(loader.load(), 700, 500);

            Stage stage = (Stage) messageLabel.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Manage Users");
            stage.show();
        } catch (Exception e) {
            messageLabel.setText("Cannot open user management.");
            e.printStackTrace();
        }
    }

    @FXML
    private void onManageGrapes() {
        if (!hasAccess(Role.ADMIN, Role.OPERATOR, Role.WAREHOUSE_KEEPER)) {
            messageLabel.setText("Access denied.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/grape-view.fxml"));
            Scene scene = new Scene(loader.load(), 600, 600);

            Stage stage = (Stage) grapeListView.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Manage Grape Varieties");
            stage.show();
        } catch (Exception e) {
            messageLabel.setText("Error loading grape screen.");
            e.printStackTrace();
        }
    }

    @FXML
    private void onManageWineTypes() {
        if (!hasAccess(Role.ADMIN, Role.OPERATOR)) {
            messageLabel.setText("Access denied.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/wine-recipe-view.fxml"));
            Scene scene = new Scene(loader.load(), 1000, 650);

            Stage stage = (Stage) messageLabel.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Manage Wine Types and Recipes");
            stage.show();
        } catch (Exception e) {
            messageLabel.setText("Cannot open wine type management.");
            e.printStackTrace();
        }
    }

    @FXML
    private void onManageBottleTypes() {
        if (!hasAccess(Role.ADMIN, Role.OPERATOR, Role.WAREHOUSE_KEEPER)) {
            messageLabel.setText("Access denied.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/bottle-type-view.fxml"));
            Scene scene = new Scene(loader.load(), 700, 500);

            Stage stage = (Stage) messageLabel.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Manage Bottle Types");
            stage.show();
        } catch (Exception e) {
            messageLabel.setText("Cannot open bottle type management.");
            e.printStackTrace();
        }
    }

    @FXML
    private void onManageProductionBatches() {
        if (!hasAccess(Role.ADMIN, Role.OPERATOR)) {
            messageLabel.setText("Access denied.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/production-batch-view.fxml"));
            Scene scene = new Scene(loader.load(), 800, 560);

            Stage stage = (Stage) messageLabel.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Manage Production Batches");
            stage.show();
        } catch (Exception e) {
            messageLabel.setText("Cannot open production batch management.");
            e.printStackTrace();
        }
    }

    @FXML
    private void onProduceWine() {
        if (!hasAccess(Role.ADMIN, Role.OPERATOR)) {
            messageLabel.setText("Access denied.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/produce-wine-view.fxml"));
            Scene scene = new Scene(loader.load(), 850, 550);

            Stage stage = (Stage) messageLabel.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Produce Wine");
            stage.show();
        } catch (Exception e) {
            messageLabel.setText("Cannot open produce wine screen.");
            e.printStackTrace();
        }
    }

    @FXML
    private void onGenerateReports() {
        if (!hasAccess(Role.ADMIN, Role.OPERATOR, Role.WAREHOUSE_KEEPER)) {
            messageLabel.setText("Access denied.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/report-view.fxml"));
            Scene scene = new Scene(loader.load(), 1100, 700);

            Stage stage = (Stage) messageLabel.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Reports");
            stage.show();
        } catch (Exception e) {
            messageLabel.setText("Cannot open reports.");
            e.printStackTrace();
        }
    }

    @FXML
    private void onViewNotifications() {
        if (!hasAccess(Role.ADMIN, Role.OPERATOR, Role.WAREHOUSE_KEEPER)) {
            messageLabel.setText("Access denied.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/notification-view.fxml"));
            Scene scene = new Scene(loader.load(), 900, 600);

            Stage stage = (Stage) messageLabel.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Notifications");
            stage.show();
        } catch (Exception e) {
            messageLabel.setText("Cannot open notifications.");
            e.printStackTrace();
        }
    }

    private boolean hasAccess(Role... allowedRoles) {
        User currentUser = UserSession.getCurrentUser();

        if (currentUser == null || currentUser.getRole() == null) {
            return false;
        }

        for (Role role : allowedRoles) {
            if (currentUser.getRole() == role) {
                return true;
            }
        }

        return false;
    }
}