package bg.tu_varna.sit.winefactory.presentation.controllers;


import bg.tu_varna.sit.winefactory.application.MainApplication;
import bg.tu_varna.sit.winefactory.business.services.ProductionBatchService;
import bg.tu_varna.sit.winefactory.business.services.WineService;
import bg.tu_varna.sit.winefactory.data.entities.ProductionBatch;
import bg.tu_varna.sit.winefactory.data.entities.Wine;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.util.List;

public class ProductionBatchController {

    @FXML
    private TextField batchCodeField;

    @FXML
    private DatePicker productionDatePicker;

    @FXML
    private TextField litersProducedField;

    @FXML
    private TextField statusField;

    @FXML
    private TextField notesField;

    @FXML
    private ComboBox<Wine> wineComboBox;

    @FXML
    private ListView<ProductionBatch> batchListView;

    @FXML
    private Label messageLabel;

    private final ProductionBatchService productionBatchService = ProductionBatchService.getInstance();
    private final WineService wineService = WineService.getInstance();

    @FXML
    private void initialize() {
        List<Wine> wines = wineService.getAllWines();
        wineComboBox.setItems(FXCollections.observableArrayList(wines));
        wineComboBox.setPromptText("Select wine");

        wineComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Wine wine) {
                return wine != null ? wine.getName() : "";
            }

            @Override
            public Wine fromString(String string) {
                return null;
            }
        });

        refreshList();

        batchListView.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, selectedBatch) -> {
            if (selectedBatch != null) {
                batchCodeField.setText(selectedBatch.getBatchCode());
                productionDatePicker.setValue(selectedBatch.getProductionDate());
                litersProducedField.setText(String.valueOf(selectedBatch.getLitersProduced()));
                statusField.setText(selectedBatch.getStatus());
                notesField.setText(selectedBatch.getNotes());
                wineComboBox.setValue(selectedBatch.getWine());
            }
        });
    }

    @FXML
    private void onAddBatch() {
        try {
            String validationError = validateInput();
            if (validationError != null) {
                messageLabel.setText(validationError);
                return;
            }

            ProductionBatch batch = new ProductionBatch(
                    null,
                    batchCodeField.getText().trim(),
                    productionDatePicker.getValue(),
                    Double.parseDouble(litersProducedField.getText().trim()),
                    statusField.getText().trim(),
                    notesField.getText() != null ? notesField.getText().trim() : "",
                    wineComboBox.getValue()
            );

            productionBatchService.addBatch(batch);
            refreshList();
            clearFields();
            messageLabel.setText("Production batch added successfully.");
        } catch (Exception e) {
            messageLabel.setText("Error while adding batch: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void onUpdateBatch() {
        ProductionBatch selected = batchListView.getSelectionModel().getSelectedItem();

        if (selected == null) {
            messageLabel.setText("Please select a batch to update.");
            return;
        }

        try {
            String validationError = validateInput();
            if (validationError != null) {
                messageLabel.setText(validationError);
                return;
            }

            selected.setBatchCode(batchCodeField.getText().trim());
            selected.setProductionDate(productionDatePicker.getValue());
            selected.setLitersProduced(Double.parseDouble(litersProducedField.getText().trim()));
            selected.setStatus(statusField.getText().trim());
            selected.setNotes(notesField.getText() != null ? notesField.getText().trim() : "");
            selected.setWine(wineComboBox.getValue());

            productionBatchService.updateBatch(selected);
            refreshList();
            clearFields();
            messageLabel.setText("Production batch updated successfully.");
        } catch (Exception e) {
            messageLabel.setText("Error while updating batch: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void onDeleteBatch() {
        ProductionBatch selected = batchListView.getSelectionModel().getSelectedItem();

        if (selected == null) {
            messageLabel.setText("Please select a batch to delete.");
            return;
        }

        try {
            productionBatchService.deleteBatch(selected);
            refreshList();
            clearFields();
            messageLabel.setText("Production batch deleted successfully.");
        } catch (Exception e) {
            messageLabel.setText("Error while deleting batch: " + e.getMessage());
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

            Stage stage = (Stage) batchCodeField.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Dashboard");
            stage.show();
        } catch (Exception e) {
            messageLabel.setText("Cannot return to dashboard.");
            e.printStackTrace();
        }
    }

    private void refreshList() {
        List<ProductionBatch> batches = productionBatchService.getAllBatches();
        batchListView.setItems(FXCollections.observableArrayList(batches));
    }

    private void clearFields() {
        batchCodeField.clear();
        productionDatePicker.setValue(null);
        litersProducedField.clear();
        statusField.clear();
        notesField.clear();
        wineComboBox.setValue(null);
    }

    private String validateInput() {
        if (batchCodeField.getText() == null || batchCodeField.getText().trim().isEmpty()) {
            return "Batch code is required.";
        }

        if (productionDatePicker.getValue() == null) {
            return "Production date is required.";
        }

        if (statusField.getText() == null || statusField.getText().trim().isEmpty()) {
            return "Status is required.";
        }

        if (wineComboBox.getValue() == null) {
            return "Please select a wine.";
        }

        double litersProduced;

        try {
            litersProduced = Double.parseDouble(litersProducedField.getText().trim());
        } catch (Exception e) {
            return "Liters produced must be a valid number.";
        }

        if (litersProduced < 0) {
            return "Liters produced cannot be negative.";
        }

        return null;
    }
}