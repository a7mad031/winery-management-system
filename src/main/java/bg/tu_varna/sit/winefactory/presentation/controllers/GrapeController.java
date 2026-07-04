package bg.tu_varna.sit.winefactory.presentation.controllers;

import bg.tu_varna.sit.winefactory.application.MainApplication;
import bg.tu_varna.sit.winefactory.business.services.GrapeVarietyService;
import bg.tu_varna.sit.winefactory.data.entities.GrapeCategory;
import bg.tu_varna.sit.winefactory.data.entities.GrapeVariety;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;

public class GrapeController {

    @FXML
    private TextField nameField;

    @FXML
    private ComboBox<GrapeCategory> categoryComboBox;

    @FXML
    private TextField quantityField;

    @FXML
    private TextField yieldField;

    @FXML
    private TextField criticalMinimumField;

    @FXML
    private ListView<GrapeVariety> grapeListView;

    @FXML
    private Label messageLabel;

    private final GrapeVarietyService grapeVarietyService = GrapeVarietyService.getInstance();

    @FXML
    private void initialize() {
        categoryComboBox.setItems(FXCollections.observableArrayList(GrapeCategory.values()));
        refreshList();

        grapeListView.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, selectedGrape) -> {
            if (selectedGrape != null) {
                nameField.setText(selectedGrape.getName());
                categoryComboBox.setValue(selectedGrape.getCategory());
                quantityField.setText(String.valueOf(selectedGrape.getQuantityKg()));
                yieldField.setText(String.valueOf(selectedGrape.getWineYieldPerKg()));
                criticalMinimumField.setText(String.valueOf(selectedGrape.getCriticalMinimumKg()));
            }
        });
    }

    @FXML
    private void onAddGrape() {
        try {
            String validationError = validateInput();
            if (validationError != null) {
                messageLabel.setText(validationError);
                return;
            }

            String name = nameField.getText().trim();
            GrapeCategory category = categoryComboBox.getValue();
            double quantity = Double.parseDouble(quantityField.getText().trim());
            double yield = Double.parseDouble(yieldField.getText().trim());
            double criticalMinimum = Double.parseDouble(criticalMinimumField.getText().trim());

            GrapeVariety grape = new GrapeVariety(null, name, category, quantity, yield, criticalMinimum);
            grapeVarietyService.addGrapeVariety(grape);

            refreshList();
            clearFields();
            messageLabel.setText("Grape variety added successfully.");
        } catch (Exception e) {
            messageLabel.setText("Error while adding grape: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void onUpdateGrape() {
        GrapeVariety selected = grapeListView.getSelectionModel().getSelectedItem();

        if (selected == null) {
            messageLabel.setText("Please select a grape variety to update.");
            return;
        }

        try {
            String validationError = validateInput();
            if (validationError != null) {
                messageLabel.setText(validationError);
                return;
            }

            selected.setName(nameField.getText().trim());
            selected.setCategory(categoryComboBox.getValue());
            selected.setQuantityKg(Double.parseDouble(quantityField.getText().trim()));
            selected.setWineYieldPerKg(Double.parseDouble(yieldField.getText().trim()));
            selected.setCriticalMinimumKg(Double.parseDouble(criticalMinimumField.getText().trim()));

            grapeVarietyService.updateGrapeVariety(selected);
            refreshList();
            clearFields();
            messageLabel.setText("Grape variety updated successfully.");
        } catch (Exception e) {
            messageLabel.setText("Error while updating grape: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String validateInput() {
        String name = nameField.getText();
        String quantityText = quantityField.getText();
        String yieldText = yieldField.getText();
        String criticalMinimumText = criticalMinimumField.getText();
        GrapeCategory category = categoryComboBox.getValue();

        if (name == null || name.trim().isEmpty()) {
            return "Name is required.";
        }

        if (category == null) {
            return "Please select a grape category.";
        }

        double quantity;
        double yield;
        double criticalMinimum;

        try {
            quantity = Double.parseDouble(quantityText.trim());
        } catch (Exception e) {
            return "Quantity must be a valid number.";
        }

        try {
            yield = Double.parseDouble(yieldText.trim());
        } catch (Exception e) {
            return "Yield must be a valid number.";
        }

        try {
            criticalMinimum = Double.parseDouble(criticalMinimumText.trim());
        } catch (Exception e) {
            return "Critical minimum must be a valid number.";
        }

        if (quantity < 0) {
            return "Quantity cannot be negative.";
        }

        if (yield < 0) {
            return "Yield cannot be negative.";
        }

        if (criticalMinimum < 0) {
            return "Critical minimum cannot be negative.";
        }

        return null;
    }

    @FXML
    private void onDeleteGrape() {
        GrapeVariety selected = grapeListView.getSelectionModel().getSelectedItem();

        if (selected == null) {
            messageLabel.setText("Please select a grape variety to delete.");
            return;
        }

        grapeVarietyService.deleteGrapeVariety(selected);
        refreshList();
        clearFields();
        messageLabel.setText("Grape variety deleted successfully.");
    }

    @FXML
    private void onClearFields() {
        clearFields();
        messageLabel.setText("Fields cleared.");
    }

    @FXML
    private void onBackToDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("/fxml/dashboard-view.fxml"));
            Scene scene = new Scene(loader.load(), 600, 500);

            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Dashboard");
            stage.show();
        } catch (Exception e) {
            messageLabel.setText("Cannot return to dashboard.");
            e.printStackTrace();
        }
    }

    private void refreshList() {
        List<GrapeVariety> grapes = grapeVarietyService.getAllGrapeVarieties();
        grapeListView.setItems(FXCollections.observableArrayList(grapes));
    }

    private void clearFields() {
        nameField.clear();
        categoryComboBox.setValue(null);
        quantityField.clear();
        yieldField.clear();
        criticalMinimumField.clear();
    }
}