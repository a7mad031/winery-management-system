package bg.tu_varna.sit.winefactory.presentation.controllers;

import bg.tu_varna.sit.winefactory.application.MainApplication;
import bg.tu_varna.sit.winefactory.business.services.GrapeVarietyService;
import bg.tu_varna.sit.winefactory.business.services.WineService;
import bg.tu_varna.sit.winefactory.data.entities.GrapeVariety;
import bg.tu_varna.sit.winefactory.data.entities.Wine;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.util.List;

public class WineController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField typeField;

    @FXML
    private TextField quantityField;

    @FXML
    private TextField priceField;

    @FXML
    private ComboBox<GrapeVariety> grapeComboBox;

    @FXML
    private ListView<Wine> wineListView;

    @FXML
    private Label messageLabel;

    private final WineService wineService = WineService.getInstance();
    private final GrapeVarietyService grapeVarietyService = GrapeVarietyService.getInstance();

    @FXML
    private void initialize() {
        List<GrapeVariety> grapes = grapeVarietyService.getAllGrapeVarieties();
        grapeComboBox.setItems(FXCollections.observableArrayList(grapes));

        grapeComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(GrapeVariety grape) {
                return grape != null ? grape.getName() : "";
            }

            @Override
            public GrapeVariety fromString(String string) {
                return null;
            }
        });

        refreshList();

        wineListView.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, selectedWine) -> {
            if (selectedWine != null) {
                nameField.setText(selectedWine.getName());
                typeField.setText(selectedWine.getType());
                quantityField.setText(String.valueOf(selectedWine.getQuantityLiters()));
                priceField.setText(String.valueOf(selectedWine.getPricePerLiter()));
                grapeComboBox.setValue(selectedWine.getGrapeVariety());
            }
        });
    }

    @FXML
    private void onAddWine() {
        try {
            String validationError = validateInput();
            if (validationError != null) {
                messageLabel.setText(validationError);
                return;
            }

            Wine wine = new Wine(
                    null,
                    nameField.getText().trim(),
                    typeField.getText().trim(),
                    Double.parseDouble(quantityField.getText().trim()),
                    Double.parseDouble(priceField.getText().trim()),
                    grapeComboBox.getValue()
            );

            wineService.addWine(wine);
            refreshList();
            clearFields();
            messageLabel.setText("Wine added successfully.");
        } catch (Exception e) {
            messageLabel.setText("Error while adding wine: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void onUpdateWine() {
        Wine selected = wineListView.getSelectionModel().getSelectedItem();

        if (selected == null) {
            messageLabel.setText("Please select a wine to update.");
            return;
        }

        try {
            String validationError = validateInput();
            if (validationError != null) {
                messageLabel.setText(validationError);
                return;
            }

            selected.setName(nameField.getText().trim());
            selected.setType(typeField.getText().trim());
            selected.setQuantityLiters(Double.parseDouble(quantityField.getText().trim()));
            selected.setPricePerLiter(Double.parseDouble(priceField.getText().trim()));
            selected.setGrapeVariety(grapeComboBox.getValue());

            wineService.updateWine(selected);
            refreshList();
            clearFields();
            messageLabel.setText("Wine updated successfully.");
        } catch (Exception e) {
            messageLabel.setText("Error while updating wine: " + e.getMessage());
            e.printStackTrace();
        }
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

    @FXML
    private void onDeleteWine() {
        Wine selected = wineListView.getSelectionModel().getSelectedItem();

        if (selected == null) {
            messageLabel.setText("Please select a wine to delete.");
            return;
        }

        try {
            wineService.deleteWine(selected);
            refreshList();
            clearFields();
            messageLabel.setText("Wine deleted successfully.");
        } catch (Exception e) {
            messageLabel.setText("Error while deleting wine: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void onClearFields() {
        clearFields();
        messageLabel.setText("Fields cleared.");
    }

    private void refreshList() {
        List<Wine> wines = wineService.getAllWines();
        wineListView.setItems(FXCollections.observableArrayList(wines));
    }

    private void clearFields() {
        nameField.clear();
        typeField.clear();
        quantityField.clear();
        priceField.clear();
        grapeComboBox.setValue(null);
    }

    private String validateInput() {
        if (nameField.getText() == null || nameField.getText().trim().isEmpty()) {
            return "Wine name is required.";
        }

        if (typeField.getText() == null || typeField.getText().trim().isEmpty()) {
            return "Wine type is required.";
        }

        if (grapeComboBox.getValue() == null) {
            return "Please select a grape variety.";
        }

        double quantity;
        double price;

        try {
            quantity = Double.parseDouble(quantityField.getText().trim());
        } catch (Exception e) {
            return "Quantity must be a valid number.";
        }

        try {
            price = Double.parseDouble(priceField.getText().trim());
        } catch (Exception e) {
            return "Price must be a valid number.";
        }

        if (quantity < 0) {
            return "Quantity cannot be negative.";
        }

        if (price < 0) {
            return "Price cannot be negative.";
        }

        return null;
    }
}
