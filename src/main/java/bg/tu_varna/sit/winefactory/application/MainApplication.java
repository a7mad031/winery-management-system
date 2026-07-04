package bg.tu_varna.sit.winefactory.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        DataSeeder.seed();

        FXMLLoader fxmlLoader = new FXMLLoader(
                MainApplication.class.getResource("/fxml/login-view.fxml")
        );

        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Wine Factory Project");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}