package bg.tu_varna.sit.winefactory.data.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "wine_recipe_items")
public class WineRecipeItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "wine_type_id")
    private WineType wineType;

    @NotNull(message = "Grape variety is required.")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "grape_variety_id", nullable = false)
    private GrapeVariety grapeVariety;

    @Positive(message = "Required quantity must be greater than 0.")
    @Column(nullable = false)
    private double requiredQuantityKg;

    public WineRecipeItem() {
    }

    public WineRecipeItem(Long id, GrapeVariety grapeVariety, double requiredQuantityKg) {
        this.id = id;
        this.grapeVariety = grapeVariety;
        this.requiredQuantityKg = requiredQuantityKg;
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

    public GrapeVariety getGrapeVariety() {
        return grapeVariety;
    }

    public void setGrapeVariety(GrapeVariety grapeVariety) {
        this.grapeVariety = grapeVariety;
    }

    public double getRequiredQuantityKg() {
        return requiredQuantityKg;
    }

    public void setRequiredQuantityKg(double requiredQuantityKg) {
        this.requiredQuantityKg = requiredQuantityKg;
    }

    @Override
    public String toString() {
        return grapeVariety.getName() + " - " + requiredQuantityKg + " kg";
    }
}