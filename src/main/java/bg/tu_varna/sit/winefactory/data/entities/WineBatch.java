package bg.tu_varna.sit.winefactory.data.entities;

import java.time.LocalDate;

public class WineBatch {
    private Long id;
    private WineType wineType;
    private double quantityLiters;
    private LocalDate productionDate;

    public WineBatch() {
    }

    public WineBatch(Long id, WineType wineType, double quantityLiters, LocalDate productionDate) {
        this.id = id;
        this.wineType = wineType;
        this.quantityLiters = quantityLiters;
        this.productionDate = productionDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WineType getWineType() {
        return wineType;
    }

    public void setWineType(WineType wineType) {
        this.wineType = wineType;
    }

    public double getQuantityLiters() {
        return quantityLiters;
    }

    public void setQuantityLiters(double quantityLiters) {
        this.quantityLiters = quantityLiters;
    }

    public LocalDate getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(LocalDate productionDate) {
        this.productionDate = productionDate;
    }
}
