package bg.tu_varna.sit.winefactory.data.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "wine_types")
public class WineType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Wine type name is required.")
    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "wineType", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<WineRecipeItem> recipeItems = new ArrayList<>();

    public WineType() {
    }

    public WineType(Long id, String name) {
        this.id = id;
        this.name = name;
        this.recipeItems = new ArrayList<>();
    }

    public WineType(Long id, String name, List<WineRecipeItem> recipeItems) {
        this.id = id;
        this.name = name;
        this.recipeItems = recipeItems;
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

    public List<WineRecipeItem> getRecipeItems() {
        return recipeItems;
    }

    public void setRecipeItems(List<WineRecipeItem> recipeItems) {
        this.recipeItems.clear();
        if (recipeItems != null) {
            for (WineRecipeItem item : recipeItems) {
                addRecipeItem(item);
            }
        }
    }

    public void addRecipeItem(WineRecipeItem item) {
        item.setWineType(this);
        this.recipeItems.add(item);
    }

    public void removeRecipeItem(WineRecipeItem item) {
        item.setWineType(null);
        this.recipeItems.remove(item);
    }

    @Override
    public String toString() {
        return name;
    }
}