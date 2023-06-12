package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.database.WatchlistRepository;
import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

public class FhmdbApplication extends Application {
    private static Stage mainStage;
    private static final MyFactory myFactory = new MyFactory();
    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        switchToHomeScene();
    }

    public static void switchToHomeScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FhmdbApplication.class.getResource("home-view.fxml"));
        fxmlLoader.setControllerFactory(myFactory.getInstance());

        Scene scene = new Scene(fxmlLoader.load(), 980, 650);
        scene.getStylesheets().add(FhmdbApplication.class.getResource("styles.css").toExternalForm());
        mainStage.setScene(scene);
        mainStage.show();
    }

    public static void switchToWatchListScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FhmdbApplication.class.getResource("WatchList-view.fxml"));
        fxmlLoader.setControllerFactory(myFactory.getInstance());

        Scene scene = new Scene(fxmlLoader.load(), 980, 650);
        scene.getStylesheets().add(FhmdbApplication.class.getResource("styles.css").toExternalForm());
        mainStage.setScene(scene);
        mainStage.show();
    }
    public static void main(String[] args) {
        launch();
    }

    private static URL getResourceOrThrow(final String resourceName) {
        return Optional.ofNullable(FhmdbApplication.class.getResource(resourceName))
                .orElseThrow();
    }
}