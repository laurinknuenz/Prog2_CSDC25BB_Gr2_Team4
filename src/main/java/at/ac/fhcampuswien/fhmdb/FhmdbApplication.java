package at.ac.fhcampuswien.fhmdb;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FhmdbApplication extends Application {
    private static Stage mainStage;

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        switchToHomeScene();
    }

    public static void switchToHomeScene() throws IOException {
        SetStage("home-view.fxml");
    }

    public static void switchToWatchListScene() throws IOException {
        SetStage("WatchList-view.fxml");
    }

    private static void SetStage(String FxmlScene) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FhmdbApplication.class.getResource(FxmlScene));

        fxmlLoader.setControllerFactory(MyFactory.INSTANCE);
        Scene scene = new Scene(fxmlLoader.load(), 980, 650);
        scene.getStylesheets().add(FhmdbApplication.class.getResource("styles.css").toExternalForm());
        mainStage.setScene(scene);
        mainStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}