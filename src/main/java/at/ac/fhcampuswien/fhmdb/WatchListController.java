package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.EventListener.Observer;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class WatchListController implements Initializable, Observer {
    @FXML
    private Button HomeButton;
    @FXML
    public JFXListView<Movie> movieListView;
    public static final WatchListController INSTANCE = new WatchListController();
    private final WatchlistRepository repo = WatchlistRepository.getInstance();

    private WatchListController() {
        repo.addObserver(this);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        movieListView.setItems(FXCollections.observableList(getWatchListMovies()));
        movieListView.setCellFactory(movieListView -> new MovieCell(true, onRemoveFromWatchListClicked, onExpandDetailsClicked));
    }

    @FXML
    protected void HomeOnClick(ActionEvent event) throws IOException {
        repo.removeObserver(this);
        FhmdbApplication.switchToHomeScene();
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

    @Override
    public void update(String message) {
        showInfoMessage(message);
    }
}



