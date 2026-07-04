package bg.tu_varna.sit.winefactory.presentation.controllers;

import bg.tu_varna.sit.winefactory.application.MainApplication;
import bg.tu_varna.sit.winefactory.business.services.GrapeVarietyService;
import bg.tu_varna.sit.winefactory.business.services.WineTypeService;
import bg.tu_varna.sit.winefactory.data.entities.GrapeVariety;
import bg.tu_varna.sit.winefactory.data.entities.WineRecipeItem;
import bg.tu_varna.sit.winefactory.data.entities.WineType;
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

import java.util.ArrayList;
import java.util.List;

public class WineRecipeController {

    @FXML
    private TextField wineTypeNameField;

    @FXML
    private ComboBox<GrapeVariety> grapeComboBox;

    @FXML
    private TextField requiredQuantityField;

    @FXML
    private ListView<WineRecipeItem> recipeItemsListView;

    @FXML
    private ListView<WineType> wineTypesListView;

    @FXML
    private Label messageLabel;

    private final WineTypeService wineTypeService = WineTypeService.getInstance();
    private final GrapeVarietyService grapeVarietyService = GrapeVarietyService.getInstance();

    private final List<WineRecipeItem> currentRecipeItems = new ArrayList<>();

    private WineType selectedWineType;

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

        refreshWineTypes();

        wineTypesListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, wineType) -> {
            selectedWineType = wineType;

            if (wineType != null) {
                wineTypeNameField.setText(wineType.getName());
                currentRecipeItems.clear();
                currentRecipeItems.addAll(wineType.getRecipeItems());
                refreshRecipeItems();
                messageLabel.setText("Wine type loaded for editing.");
            }
        });
    }

    @FXML
    private void onAddRecipeItem() {
        GrapeVariety selectedGrape = grapeComboBox.getValue();

        if (selectedGrape == null) {
            messageLabel.setText("Please select a grape variety.");
            return;
        }

        double requiredKg;
        try {
            requiredKg = Double.parseDouble(requiredQuantityField.getText().trim());
        } catch (Exception e) {
            messageLabel.setText("Required quantity must be a valid number.");
            return;
        }

        if (requiredKg <= 0) {
            messageLabel.setText("Required quantity must be greater than 0.");
            return;
        }

        for (WineRecipeItem item : currentRecipeItems) {
            if (item.getGrapeVariety().getId().equals(selectedGrape.getId())) {
                item.setRequiredQuantityKg(item.getRequiredQuantityKg() + requiredKg);
                refreshRecipeItems();
                grapeComboBox.setValue(null);
                requiredQuantityField.clear();
                messageLabel.setText("Quantity added to existing grape in recipe.");
                return;
            }
        }

        WineRecipeItem item = new WineRecipeItem(null, selectedGrape, requiredKg);
        currentRecipeItems.add(item);

        refreshRecipeItems();
        grapeComboBox.setValue(null);
        requiredQuantityField.clear();
        messageLabel.setText("Recipe item added.");
    }

    @FXML
    private void onDeleteWineType() {
        WineType wineTypeToDelete = wineTypesListView.getSelectionModel().getSelectedItem();

        if (wineTypeToDelete == null) {
            messageLabel.setText("Please select a wine type to delete.");
            return;
        }

        try {
            wineTypeService.deleteWineType(wineTypeToDelete);
            refreshWineTypes();
            clearFields();
            messageLabel.setText("Wine type deleted successfully.");
        } catch (Exception e) {
            messageLabel.setText("Error while deleting wine type.");
            e.printStackTrace();
        }
    }

    @FXML
    private void onRemoveRecipeItem() {
        WineRecipeItem selectedItem = recipeItemsListView.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            messageLabel.setText("Please select a recipe item to remove.");
            return;
        }

        currentRecipeItems.remove(selectedItem);
        refreshRecipeItems();
        messageLabel.setText("Recipe item removed.");
    }

    @FXML
    private void onSaveWineType() {
        String wineTypeName = wineTypeNameField.getText().trim();

        if (wineTypeName.isEmpty()) {
            messageLabel.setText("Wine type name is required.");
            return;
        }

        if (currentRecipeItems.isEmpty()) {
            messageLabel.setText("Add at least one recipe item.");
            return;
        }

        try {
            if (selectedWineType == null) {
                WineType newWineType = new WineType();
                newWineType.setName(wineTypeName);
                newWineType.setRecipeItems(new ArrayList<>(currentRecipeItems));
                wineTypeService.addWineType(newWineType);
                messageLabel.setText("Wine type saved successfully.");
            } else {
                selectedWineType.setName(wineTypeName);
                selectedWineType.setRecipeItems(new ArrayList<>(currentRecipeItems));
                wineTypeService.updateWineType(selectedWineType);
                messageLabel.setText("Wine type updated successfully.");
            }

            refreshWineTypes();
            clearFields();
        } catch (Exception e) {
            messageLabel.setText("Error while saving wine type.");
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
            Scene scene = new Scene(loader.load(), 600, 500);

            Stage stage = (Stage) wineTypeNameField.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Dashboard");
            stage.show();
        } catch (Exception e) {
            messageLabel.setText("Cannot return to dashboard.");
            e.printStackTrace();
        }
    }

    private void refreshRecipeItems() {
        recipeItemsListView.setItems(FXCollections.observableArrayList(currentRecipeItems));
    }

    private void refreshWineTypes() {
        wineTypesListView.setItems(FXCollections.observableArrayList(wineTypeService.getAllWineTypes()));
    }

    private void clearFields() {
        selectedWineType = null;
        wineTypeNameField.clear();
        grapeComboBox.setValue(null);
        requiredQuantityField.clear();
        currentRecipeItems.clear();
        refreshRecipeItems();
        wineTypesListView.getSelectionModel().clearSelection();
    }
}