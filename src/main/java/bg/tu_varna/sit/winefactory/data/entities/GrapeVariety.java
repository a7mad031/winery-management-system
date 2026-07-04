package bg.tu_varna.sit.winefactory.data.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "grape_varieties")
public class GrapeVariety {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required.")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "Category is required.")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GrapeCategory category;

    @PositiveOrZero(message = "Quantity must be 0 or greater.")
    @Column(nullable = false)
    private double quantityKg;

    @PositiveOrZero(message = "Wine yield must be 0 or greater.")
    @Column(nullable = false)
    private double wineYieldPerKg;

    @PositiveOrZero(message = "Critical minimum must be 0 or greater.")
    @Column(nullable = false)
    private double criticalMinimumKg;

    public GrapeVariety() {
    }

    public GrapeVariety(Long id, String name, GrapeCategory category, double quantityKg,
                        double wineYieldPerKg, double criticalMinimumKg) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.quantityKg = quantityKg;
        this.wineYieldPerKg = wineYieldPerKg;
        this.criticalMinimumKg = criticalMinimumKg;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GrapeCategory getCategory() {
        return category;
    }

    public void setCategory(GrapeCategory category) {
        this.category = category;
    }

    public double getQuantityKg() {
        return quantityKg;
    }

    public void setQuantityKg(double quantityKg) {
        this.quantityKg = quantityKg;
    }

    public double getWineYieldPerKg() {
        return wineYieldPerKg;
    }

    public void setWineYieldPerKg(double wineYieldPerKg) {
        this.wineYieldPerKg = wineYieldPerKg;
    }

    public double getCriticalMinimumKg() {
        return criticalMinimumKg;
    }

    public void setCriticalMinimumKg(double criticalMinimumKg) {
        this.criticalMinimumKg = criticalMinimumKg;
    }

    @Override
    public String toString() {
        return name + " | " + category + " | " + quantityKg + " kg";
    }
}