package bg.tu_varna.sit.winefactory.data.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "bottled_wines")
public class BottledWine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "wine_type_id", nullable = false)
    private WineType wineType;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "bottle_type_id", nullable = false)
    private BottleType bottleType;

    @Column(name = "bottled_liters", nullable = false)
    private double bottledLiters;

    @Column(name = "bottle_count", nullable = false)
    private int bottleCount;

    @Column(name = "bottled_at", nullable = false)
    private LocalDateTime bottledAt;

    public BottledWine() {
    }

    public BottledWine(Long id, WineType wineType, BottleType bottleType,
                       double bottledLiters, int bottleCount, LocalDateTime bottledAt) {
        this.id = id;
        this.wineType = wineType;
        this.bottleType = bottleType;
        this.bottledLiters = bottledLiters;
        this.bottleCount = bottleCount;
        this.bottledAt = bottledAt;
    }

    public Long getId() { return id; }
    public WineType getWineType() { return wineType; }
    public BottleType getBottleType() { return bottleType; }
    public double getBottledLiters() { return bottledLiters; }
    public int getBottleCount() { return bottleCount; }
    public LocalDateTime getBottledAt() { return bottledAt; }

    public void setId(Long id) { this.id = id; }
    public void setWineType(WineType wineType) { this.wineType = wineType; }
    public void setBottleType(BottleType bottleType) { this.bottleType = bottleType; }
    public void setBottledLiters(double bottledLiters) { this.bottledLiters = bottledLiters; }
    public void setBottleCount(int bottleCount) { this.bottleCount = bottleCount; }
    public void setBottledAt(LocalDateTime bottledAt) { this.bottledAt = bottledAt; }

    @Override
    public String toString() {
        return wineType.getName() + " | " + bottleType.getSizeMl() + " ml | " +
                bottleCount + " bottles | " + bottledLiters + " L";
    }
}