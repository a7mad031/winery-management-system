package bg.tu_varna.sit.winefactory.presentation.controllers;

import bg.tu_varna.sit.winefactory.application.MainApplication;
import bg.tu_varna.sit.winefactory.business.services.BottleTypeService;
import bg.tu_varna.sit.winefactory.business.services.BottledWineService;
import bg.tu_varna.sit.winefactory.business.services.GrapeVarietyService;
import bg.tu_varna.sit.winefactory.data.entities.BottleType;
import bg.tu_varna.sit.winefactory.data.entities.BottledWine;
import bg.tu_varna.sit.winefactory.data.entities.GrapeVariety;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class ReportController {

    @FXML
    private DatePicker fromDatePicker;

    @FXML
    private DatePicker toDatePicker;

    @FXML
    private ListView<String> grapeReportListView;

    @FXML
    private ListView<String> bottleReportListView;

    @FXML
    private ListView<BottledWine> bottledWineReportListView;

    @FXML
    private Label messageLabel;

    private final GrapeVarietyService grapeVarietyService = GrapeVarietyService.getInstance();
    private final BottleTypeService bottleTypeService = BottleTypeService.getInstance();
    private final BottledWineService bottledWineService = BottledWineService.getInstance();

    @FXML
    private void initialize() {
        LocalDate today = LocalDate.now();
        fromDatePicker.setValue(today.minusMonths(1));
        toDatePicker.setValue(today);
        loadCurrentStockReports();
        loadBottledWineReport();
    }

    @FXML
    private void onGenerateReport() {
        LocalDate fromDate = fromDatePicker.getValue();
        LocalDate toDate = toDatePicker.getValue();

        if (fromDate == null || toDate == null) {
            messageLabel.setText("Please select both start and end dates.");
            return;
        }

        if (fromDate.isAfter(toDate)) {
            messageLabel.setText("Start date cannot be after end date.");
            return;
        }

        loadCurrentStockReports();
        loadBottledWineReport();
        messageLabel.setText("Report generated successfully.");
    }

    @FXML
    private void onBackToDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("/fxml/dashboard-view.fxml"));
            Scene scene = new Scene(loader.load(), 900, 600);

            Stage stage = (Stage) messageLabel.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Dashboard");
            stage.show();
        } catch (Exception e) {
            messageLabel.setText("Cannot return to dashboard.");
            e.printStackTrace();
        }
    }

    private void loadCurrentStockReports() {
        List<String> grapeLines = grapeVarietyService.getAllGrapeVarieties().stream()
                .map(g -> g.getName() + " | qty: " + g.getQuantityKg() + " kg | min: " + g.getCriticalMinimumKg() + " kg")
                .collect(Collectors.toList());

        List<String> bottleLines = bottleTypeService.getAllBottleTypes().stream()
                .map(b -> b.getSizeMl() + " ml | qty: " + b.getQuantity() + " | min: " + b.getCriticalMinimum())
                .collect(Collectors.toList());

        grapeReportListView.setItems(FXCollections.observableArrayList(grapeLines));
        bottleReportListView.setItems(FXCollections.observableArrayList(bottleLines));
    }

    private void loadBottledWineReport() {
        LocalDate fromDate = fromDatePicker.getValue();
        LocalDate toDate = toDatePicker.getValue();

        if (fromDate == null || toDate == null) {
            bottledWineReportListView.setItems(FXCollections.observableArrayList());
            return;
        }

        LocalDateTime fromDateTime = fromDate.atStartOfDay();
        LocalDateTime toDateTime = toDate.atTime(LocalTime.MAX);

        List<BottledWine> filtered = bottledWineService.getAllBottledWines().stream()
                .filter(bw -> bw.getBottledAt() != null
                        && !bw.getBottledAt().isBefore(fromDateTime)
                        && !bw.getBottledAt().isAfter(toDateTime))
                .collect(Collectors.toList());

        bottledWineReportListView.setItems(FXCollections.observableArrayList(filtered));
    }
}