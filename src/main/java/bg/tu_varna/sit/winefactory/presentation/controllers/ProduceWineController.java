package bg.tu_varna.sit.winefactory.presentation.controllers;

import bg.tu_varna.sit.winefactory.application.MainApplication;
import bg.tu_varna.sit.winefactory.business.services.BottleTypeService;
import bg.tu_varna.sit.winefactory.business.services.BottledWineService;
import bg.tu_varna.sit.winefactory.business.services.WineTypeService;
import bg.tu_varna.sit.winefactory.data.entities.BottleType;
import bg.tu_varna.sit.winefactory.data.entities.BottledWine;
import bg.tu_varna.sit.winefactory.data.entities.WineType;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.List;

public class ProduceWineController {

    @FXML
    private ComboBox<WineType> wineTypeComboBox;

    @FXML
    private ComboBox<BottleType> bottleTypeComboBox;

    @FXML
    private TextField litersField;

    @FXML
    private Label messageLabel;

    @FXML
    private ListView<BottledWine> bottledWineListView;

    private final WineTypeService wineTypeService = WineTypeService.getInstance();
    private final BottleTypeService bottleTypeService = BottleTypeService.getInstance();
    private final BottledWineService bottledWineService = BottledWineService.getInstance();

    @FXML
    private void initialize() {
        wineTypeComboBox.setItems(FXCollections.observableArrayList(wineTypeService.getAllWineTypes()));
        bottleTypeComboBox.setItems(FXCollections.observableArrayList(bottleTypeService.getAllBottleTypes()));
        refreshList();
    }

    @FXML
    private void onBottledWine() {
        WineType wineType = wineTypeComboBox.getValue();
        BottleType bottleType = bottleTypeComboBox.getValue();

        if (wineType == null || bottleType == null) {
            messageLabel.setText("Please select wine type and bottle type.");
            return;
        }

        double liters;
        try {
            liters = Double.parseDouble(litersField.getText().trim());
        } catch (Exception e) {
            messageLabel.setText("Liters must be a valid number.");
            return;
        }

        if (liters <= 0) {
            messageLabel.setText("Liters must be greater than 0.");
            return;
        }

        int bottleCount = (int) Math.floor((liters * 1000.0) / bottleType.getSizeMl());

        if (bottleCount <= 0) {
            messageLabel.setText("Not enough wine for one bottle.");
            return;
        }

        if (bottleType.getQuantity() < bottleCount) {
            messageLabel.setText("Not enough bottles in stock.");
            return;
        }

        bottleType.setQuantity(bottleType.getQuantity() - bottleCount);

        BottledWine bottledWine = new BottledWine();
        bottledWine.setWineType(wineType);
        bottledWine.setBottleType(bottleType);
        bottledWine.setBottledLiters(liters);
        bottledWine.setBottleCount(bottleCount);
        bottledWine.setBottledAt(LocalDateTime.now());

        try {
            bottleTypeService.updateBottleType(bottleType);
            bottledWineService.addBottledWine(bottledWine);
            refreshList();
            clearFields();
            messageLabel.setText("Wine bottled successfully.");
        } catch (Exception e) {
            messageLabel.setText("Error while bottling wine.");
            e.printStackTrace();
        }
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

    private void refreshList() {
        List<BottledWine> bottledWines = bottledWineService.getAllBottledWines();
        bottledWineListView.setItems(FXCollections.observableArrayList(bottledWines));
    }

    private void clearFields() {
        wineTypeComboBox.setValue(null);
        bottleTypeComboBox.setValue(null);
        litersField.clear();
    }
}
