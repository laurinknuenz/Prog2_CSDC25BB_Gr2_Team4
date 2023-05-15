package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.database.WatchlistMovieEntity;
import at.ac.fhcampuswien.fhmdb.database.WatchlistRepository;
import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.interfaces.ClickEventHandler;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class WatchListController implements Initializable {
    @FXML
    private Button HomeButton;
    @FXML
    public JFXListView<Movie> movieListView;

    WatchlistRepository repo;

    {
        try {
            repo = new WatchlistRepository();
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        movieListView.setItems(FXCollections.observableList(getWatchListMovies()));
        movieListView.setCellFactory(movieListView -> new MovieCell(true, onRemoveFromWatchListClicked, onExpandDetailsClicked));
    }

    @FXML
    protected void HomeOnClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("home-view.fxml")));
        Stage registerStage = (Stage) HomeButton.getScene().getWindow();
        registerStage.setScene(new Scene(root, 980, 650));
    }

    private List<Movie> getWatchListMovies() {
        List<WatchlistMovieEntity> movieEntities = new ArrayList<>();

        try {
            movieEntities = repo.getAll();
        } catch (DatabaseException e) {
            showInfoMessage("Something went wrong while trying to retrieve the movies");
        }
        return Movie.sortMovies(true, WatchlistMovieEntity.entityListToMovieListMapper(movieEntities));
    }

    private final ClickEventHandler<Movie> onRemoveFromWatchListClicked = (clickedMovie) -> {
        WatchlistMovieEntity watchlistMovie = WatchlistMovieEntity.movieToEntityMapper(clickedMovie);
        try {
            repo.removeFromWatchlist(watchlistMovie);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (DatabaseException e) {
            throw new DatabaseException("");
        }
        movieListView.setItems(FXCollections.observableList(getWatchListMovies()));
    };

    private final ClickEventHandler<MovieCell> onExpandDetailsClicked = MovieCell::expandDetails;

    public static void showInfoMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Oops!");
        alert.setHeaderText(message);
        alert.showAndWait();
    }
}



