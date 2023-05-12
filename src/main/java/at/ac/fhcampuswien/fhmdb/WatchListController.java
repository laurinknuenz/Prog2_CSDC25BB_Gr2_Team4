package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.api.ApiConsumer;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class WatchListController implements Initializable {
    @FXML private Button HomeButton;
    //public JFXListView<Movie> movieListView;
    //private final ApiConsumer apiConsumer = new ApiConsumer();


    @FXML
    protected void HomeOnClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("home-view.fxml")));
        Stage registerStage = (Stage) HomeButton.getScene().getWindow();
        registerStage.setScene(new Scene(root, 980, 650));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //movieListView.setItems(FXCollections.observableList(apiConsumer.getAllMovies()));
        //movieListView.setCellFactory(movieListView -> new MovieCell());
    }
}



