package bg.tu_varna.sit.winefactory.application;

import bg.tu_varna.sit.winefactory.business.services.BottleTypeService;
import bg.tu_varna.sit.winefactory.business.services.GrapeVarietyService;
import bg.tu_varna.sit.winefactory.business.services.UserService;
import bg.tu_varna.sit.winefactory.business.services.WineTypeService;
import bg.tu_varna.sit.winefactory.data.entities.BottleType;
import bg.tu_varna.sit.winefactory.data.entities.GrapeCategory;
import bg.tu_varna.sit.winefactory.data.entities.GrapeVariety;
import bg.tu_varna.sit.winefactory.data.entities.WineRecipeItem;
import bg.tu_varna.sit.winefactory.data.entities.WineType;

public class DataSeeder {

    private DataSeeder() {
    }

    public static void seed() {
        UserService userService = UserService.getInstance();
        GrapeVarietyService grapeService = GrapeVarietyService.getInstance();
        BottleTypeService bottleService = BottleTypeService.getInstance();
        WineTypeService wineTypeService = WineTypeService.getInstance();

        seedUsers(userService);
        seedGrapes(grapeService);
        seedBottles(bottleService);
        seedWineTypes(grapeService, wineTypeService);
    }

    private static void seedUsers(UserService userService) {
        if (userService.getAllUsers().isEmpty()) {
            userService.createAdmin(null, "admin", "admin");
            userService.createOperator(null, "operator", "1234");
            userService.createWarehouseKeeper(null, "keeper", "1234");
        }
    }

    private static void seedGrapes(GrapeVarietyService grapeService) {
        if (grapeService.getAllGrapeVarieties().isEmpty()) {
            GrapeVariety merlot = new GrapeVariety(null, "Merlot", GrapeCategory.BLACK, 500, 0.7, 100);
            GrapeVariety muscat = new GrapeVariety(null, "Muscat", GrapeCategory.WHITE, 300, 0.8, 80);

            grapeService.addGrapeVariety(merlot);
            grapeService.addGrapeVariety(muscat);
        }
    }

    private static void seedBottles(BottleTypeService bottleService) {
        if (bottleService.getAllBottleTypes().isEmpty()) {
            bottleService.addBottleType(new BottleType(null, 750, 200, 20));
            bottleService.addBottleType(new BottleType(null, 375, 150, 20));
            bottleService.addBottleType(new BottleType(null, 200, 100, 15));
            bottleService.addBottleType(new BottleType(null, 187, 80, 10));
        }
    }

    private static void seedWineTypes(GrapeVarietyService grapeService, WineTypeService wineTypeService) {
        if (wineTypeService.getAllWineTypes().isEmpty()) {
            GrapeVariety merlot = grapeService.getAllGrapeVarieties().stream()
                    .filter(g -> g.getName().equalsIgnoreCase("Merlot"))
                    .findFirst()
                    .orElse(null);

            GrapeVariety muscat = grapeService.getAllGrapeVarieties().stream()
                    .filter(g -> g.getName().equalsIgnoreCase("Muscat"))
                    .findFirst()
                    .orElse(null);

            if (merlot != null) {
                WineType blackWine = new WineType(null, "Black Wine");
                blackWine.addRecipeItem(new WineRecipeItem(null, merlot, 100));
                wineTypeService.addWineType(blackWine);
            }

            if (muscat != null) {
                WineType whiteWine = new WineType(null, "White Wine");
                whiteWine.addRecipeItem(new WineRecipeItem(null, muscat, 80));
                wineTypeService.addWineType(whiteWine);
            }
        }
    }
}