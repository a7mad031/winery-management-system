package bg.tu_varna.sit.winefactory.data.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;

@Entity
@Table(name = "production_batches")
public class ProductionBatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Batch code is required.")
    @Column(name = "batch_code", nullable = false)
    private String batchCode;

    @NotNull(message = "Production date is required.")
    @Column(name = "production_date", nullable = false)
    private LocalDate productionDate;

    @PositiveOrZero(message = "Liters produced must be 0 or greater.")
    @Column(name = "liters_produced", nullable = false)
    private double litersProduced;

    @NotBlank(message = "Status is required.")
    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "notes")
    private String notes;

    @NotNull(message = "Wine is required.")
    @ManyToOne
    @JoinColumn(name = "wine_id", nullable = false)
    private Wine wine;

    public ProductionBatch() {
    }

    public ProductionBatch(Long id, String batchCode, LocalDate productionDate,
                           double litersProduced, String status, String notes, Wine wine) {
        this.id = id;
        this.batchCode = batchCode;
        this.productionDate = productionDate;
        this.litersProduced = litersProduced;
        this.status = status;
        this.notes = notes;
        this.wine = wine;
    }

    public Long getId() {
        return id;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public LocalDate getProductionDate() {
        return productionDate;
    }

    public double getLitersProduced() {
        return litersProduced;
    }

    public String getStatus() {
        return status;
    }

    public String getNotes() {
        return notes;
    }

    public Wine getWine() {
        return wine;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public void setProductionDate(LocalDate productionDate) {
        this.productionDate = productionDate;
    }

    public void setLitersProduced(double litersProduced) {
        this.litersProduced = litersProduced;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setWine(Wine wine) {
        this.wine = wine;
    }

    @Override
    public String toString() {
        return batchCode + " | " + productionDate + " | " + litersProduced + " L | " + wine.getName();
    }
}