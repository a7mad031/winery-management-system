package bg.tu_varna.sit.winefactory.data.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "bottle_types")
public class BottleType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Bottle size is required.")
    @Column(name = "size_ml", nullable = false, unique = true)
    private Integer sizeMl;

    @PositiveOrZero(message = "Quantity must be 0 or greater.")
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @PositiveOrZero(message = "Critical minimum must be 0 or greater.")
    @Column(name = "critical_minimum", nullable = false)
    private Integer criticalMinimum;

    public BottleType() {
    }

    public BottleType(Long id, Integer sizeMl, Integer quantity, Integer criticalMinimum) {
        this.id = id;
        this.sizeMl = sizeMl;
        this.quantity = quantity;
        this.criticalMinimum = criticalMinimum;
    }

    public Long getId() {
        return id;
    }

    public Integer getSizeMl() {
        return sizeMl;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Integer getCriticalMinimum() {
        return criticalMinimum;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSizeMl(Integer sizeMl) {
        this.sizeMl = sizeMl;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setCriticalMinimum(Integer criticalMinimum) {
        this.criticalMinimum = criticalMinimum;
    }

    @Override
    public String toString() {
        return sizeMl + " ml | qty: " + quantity + " | min: " + criticalMinimum;
    }
}