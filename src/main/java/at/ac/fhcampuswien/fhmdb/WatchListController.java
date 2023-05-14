package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.api.ApiConsumer;
import at.ac.fhcampuswien.fhmdb.database.WatchlistMovieEntity;
import at.ac.fhcampuswien.fhmdb.database.WatchlistRepository;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.ui.WatchListMovieCell;
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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class WatchListController implements Initializable {
    @FXML private Button HomeButton;
    @FXML
    public JFXListView<Movie> movieListView;
    private final ApiConsumer apiConsumer = new ApiConsumer();

    WatchlistRepository repo = new WatchlistRepository();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<WatchlistMovieEntity> movies = new ArrayList<>();

        try {
            movies = repo.getAll();
        } catch (SQLException e) {
            //TODO: Tell user, what the error is
        }

        for(WatchlistMovieEntity movie : movies){
            System.out.println(movie.toString());

            movieListView.setItems(FXCollections.observableList(MapApiMoviesToWatchListMovies(apiConsumer, movies)));
            movieListView.setCellFactory(movieListView -> new WatchListMovieCell());
        }
    }

    public List<Movie> MapApiMoviesToWatchListMovies(ApiConsumer apiConsumer, List<WatchlistMovieEntity> watchlistMovies) {
        return apiConsumer.getAllMovies().stream()
                .filter(apiMovie -> watchlistMovies.stream()
                        .anyMatch(watchlistMovie -> apiMovie.getTitle().equalsIgnoreCase(watchlistMovie.getTitle()))
                )
                .distinct()
                .collect(Collectors.toList());
    }

    @FXML
    protected void HomeOnClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("home-view.fxml")));
        Stage registerStage = (Stage) HomeButton.getScene().getWindow();
        registerStage.setScene(new Scene(root, 980, 650));
    }
}



