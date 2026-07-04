package bg.tu_varna.sit.winefactory.presentation.controllers;

import bg.tu_varna.sit.winefactory.application.MainApplication;
import bg.tu_varna.sit.winefactory.business.services.BottleTypeService;
import bg.tu_varna.sit.winefactory.data.entities.BottleType;
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

public class BottleTypeController {

    @FXML
    private ComboBox<Integer> sizeComboBox;

    @FXML
    private TextField quantityField;

    @FXML
    private TextField criticalMinimumField;

    @FXML
    private ListView<BottleType> bottleTypeListView;

    @FXML
    private Label messageLabel;

    private final BottleTypeService bottleTypeService = BottleTypeService.getInstance();

    @FXML
    private void initialize() {
        sizeComboBox.setItems(FXCollections.observableArrayList(750, 375, 200, 187));
        sizeComboBox.setPromptText("Select bottle size");

        refreshList();

        bottleTypeListView.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, selectedBottleType) -> {
            if (selectedBottleType != null) {
                sizeComboBox.setValue(selectedBottleType.getSizeMl());
                quantityField.setText(String.valueOf(selectedBottleType.getQuantity()));
                criticalMinimumField.setText(String.valueOf(selectedBottleType.getCriticalMinimum()));
            }
        });
    }

    @FXML
    private void onAddBottleType() {
        try {
            String validationError = validateInput();
            if (validationError != null) {
                messageLabel.setText(validationError);
                return;
            }

            BottleType bottleType = new BottleType(
                    null,
                    sizeComboBox.getValue(),
                    Integer.parseInt(quantityField.getText().trim()),
                    Integer.parseInt(criticalMinimumField.getText().trim())
            );

            bottleTypeService.addBottleType(bottleType);
            refreshList();
            clearFields();
            messageLabel.setText("Bottle type added successfully.");
        } catch (Exception e) {
            messageLabel.setText("Error while adding bottle type: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void onUpdateBottleType() {
        BottleType selected = bottleTypeListView.getSelectionModel().getSelectedItem();

        if (selected == null) {
            messageLabel.setText("Please select a bottle type to update.");
            return;
        }

        try {
            String validationError = validateInput();
            if (validationError != null) {
                messageLabel.setText(validationError);
                return;
            }

            selected.setSizeMl(sizeComboBox.getValue());
            selected.setQuantity(Integer.parseInt(quantityField.getText().trim()));
            selected.setCriticalMinimum(Integer.parseInt(criticalMinimumField.getText().trim()));

            bottleTypeService.updateBottleType(selected);
            refreshList();
            clearFields();
            messageLabel.setText("Bottle type updated successfully.");
        } catch (Exception e) {
            messageLabel.setText("Error while updating bottle type: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void onDeleteBottleType() {
        BottleType selected = bottleTypeListView.getSelectionModel().getSelectedItem();

        if (selected == null) {
            messageLabel.setText("Please select a bottle type to delete.");
            return;
        }

        try {
            bottleTypeService.deleteBottleType(selected);
            refreshList();
            clearFields();
            messageLabel.setText("Bottle type deleted successfully.");
        } catch (Exception e) {
            messageLabel.setText("Error while deleting bottle type: " + e.getMessage());
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
        List<BottleType> bottleTypes = bottleTypeService.getAllBottleTypes();
        bottleTypeListView.setItems(FXCollections.observableArrayList(bottleTypes));
    }

    private void clearFields() {
        sizeComboBox.setValue(null);
        quantityField.clear();
        criticalMinimumField.clear();
    }

    private String validateInput() {
        if (sizeComboBox.getValue() == null) {
            return "Please select a bottle size.";
        }

        int quantity;
        int criticalMinimum;

        try {
            quantity = Integer.parseInt(quantityField.getText().trim());
        } catch (Exception e) {
            return "Quantity must be a valid integer.";
        }

        try {
            criticalMinimum = Integer.parseInt(criticalMinimumField.getText().trim());
        } catch (Exception e) {
            return "Critical minimum must be a valid integer.";
        }

        if (quantity < 0) {
            return "Quantity cannot be negative.";
        }

        if (criticalMinimum < 0) {
            return "Critical minimum cannot be negative.";
        }

        return null;
    }
}