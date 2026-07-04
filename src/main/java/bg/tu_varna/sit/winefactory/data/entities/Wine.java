package bg.tu_varna.sit.winefactory.data.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "wine")
public class Wine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Wine name is required.")
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message = "Wine type is required.")
    @Column(name = "type", nullable = false)
    private String type;

    @PositiveOrZero(message = "Quantity must be 0 or greater.")
    @Column(name = "quantity_liters", nullable = false)
    private double quantityLiters;

    @PositiveOrZero(message = "Price must be 0 or greater.")
    @Column(name = "price_per_liter", nullable = false)
    private double pricePerLiter;

    @NotNull(message = "Grape variety is required.")
    @ManyToOne
    @JoinColumn(name = "grape_variety_id", nullable = false)
    private GrapeVariety grapeVariety;

    public Wine() {
    }

    public Wine(Long id, String name, String type, double quantityLiters,
                double pricePerLiter, GrapeVariety grapeVariety) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.quantityLiters = quantityLiters;
        this.pricePerLiter = pricePerLiter;
        this.grapeVariety = grapeVariety;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public double getQuantityLiters() {
        return quantityLiters;
    }

    public double getPricePerLiter() {
        return pricePerLiter;
    }

    public GrapeVariety getGrapeVariety() {
        return grapeVariety;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setQuantityLiters(double quantityLiters) {
        this.quantityLiters = quantityLiters;
    }

    public void setPricePerLiter(double pricePerLiter) {
        this.pricePerLiter = pricePerLiter;
    }

    public void setGrapeVariety(GrapeVariety grapeVariety) {
        this.grapeVariety = grapeVariety;
    }

    @Override
    public String toString() {
        return name + " | " + type + " | " + quantityLiters + " L | " + grapeVariety.getName();
    }
}