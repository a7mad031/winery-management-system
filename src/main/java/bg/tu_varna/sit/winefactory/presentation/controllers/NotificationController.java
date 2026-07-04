package bg.tu_varna.sit.winefactory.presentation.controllers;

import bg.tu_varna.sit.winefactory.application.MainApplication;
import bg.tu_varna.sit.winefactory.business.services.BottleTypeService;
import bg.tu_varna.sit.winefactory.business.services.GrapeVarietyService;
import bg.tu_varna.sit.winefactory.data.entities.BottleType;
import bg.tu_varna.sit.winefactory.data.entities.GrapeVariety;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

public class NotificationController {

    @FXML
    private ListView<String> grapeNotificationsListView;

    @FXML
    private ListView<String> bottleNotificationsListView;

    @FXML
    private Label messageLabel;

    private final GrapeVarietyService grapeVarietyService = GrapeVarietyService.getInstance();
    private final BottleTypeService bottleTypeService = BottleTypeService.getInstance();

    @FXML
    private void initialize() {
        refreshNotifications();
    }

    @FXML
    private void onRefresh() {
        refreshNotifications();
        messageLabel.setText("Notifications refreshed.");
    }

    @FXML
    private void onBackToDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("/fxml/dashboard-view.fxml"));
            Scene scene = new Scene(loader.load(), 700, 500);

            Stage stage = (Stage) messageLabel.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Dashboard");
            stage.show();
        } catch (Exception e) {
            messageLabel.setText("Cannot return to dashboard.");
            e.printStackTrace();
        }
    }

    private void refreshNotifications() {
        List<String> grapeAlerts = grapeVarietyService.getAllGrapeVarieties().stream()
                .filter(g -> g.getQuantityKg() <= g.getCriticalMinimumKg())
                .map(g -> g.getName() + " is at critical minimum or missing: " + g.getQuantityKg() + " kg")
                .collect(Collectors.toList());

        List<String> bottleAlerts = bottleTypeService.getAllBottleTypes().stream()
                .filter(b -> b.getQuantity() <= b.getCriticalMinimum())
                .map(b -> b.getSizeMl() + " ml bottles are at critical minimum or missing: " + b.getQuantity())
                .collect(Collectors.toList());

        grapeNotificationsListView.setItems(FXCollections.observableArrayList(grapeAlerts));
        bottleNotificationsListView.setItems(FXCollections.observableArrayList(bottleAlerts));

        if (grapeAlerts.isEmpty() && bottleAlerts.isEmpty()) {
            messageLabel.setText("No critical notifications.");
        }
    }
}