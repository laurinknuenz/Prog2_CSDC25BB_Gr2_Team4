package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.api.ApiConsumer;
import at.ac.fhcampuswien.fhmdb.database.WatchlistMovieEntity;
import at.ac.fhcampuswien.fhmdb.database.WatchlistRepository;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.IntStream;

public class HomeController implements Initializable {
    @FXML
    private Button watchListButton;
    @FXML
    public JFXButton searchBtn;
    @FXML
    public JFXButton resetBtn;
    @FXML
    public TextField searchField;
    @FXML
    public JFXListView<Movie> movieListView;
    @FXML
    public JFXComboBox<Genre> genreComboBox;
    @FXML
    public JFXComboBox<Integer> releaseYear;
    @FXML
    public JFXComboBox<String> rating;
    @FXML
    public JFXButton sortBtn;

    private final ApiConsumer apiConsumer = new ApiConsumer();

    WatchlistRepository repo = new WatchlistRepository();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // initialize UI stuff
        movieListView.setItems(FXCollections.observableList(apiConsumer.getAllMovies()));
        movieListView.setCellFactory(movieListView -> new MovieCell(onAddToWatchListClicked, onExpandDetailsClicked));

        FilteringOperations();
    }

    private void FilteringOperations() {
        setUpGenreComboBox();
        setUpReleaseYearComboBox();
        setupRatingComboBox();

        searchBtn.setOnAction(actionEvent -> onSearchParametersUpdated());
        genreComboBox.setOnAction(actionEvent -> onSearchParametersUpdated());
        rating.setOnAction(actionEvent -> onSearchParametersUpdated());
        releaseYear.setOnAction(actionEvent -> onSearchParametersUpdated());
        sortBtn.setOnAction(this::sortObservableList);
        resetBtn.setOnAction(actionEvent -> OnResetButton());
    }

    private void sortObservableList(ActionEvent actionEvent) {
        boolean isAscendingOrder = sortBtn.getText().equals("Sort (asc)");
        movieListView.setItems(FXCollections.observableList(Movie.sortMovies(isAscendingOrder, movieListView.getItems())));
        sortBtn.setText(isAscendingOrder ? "Sort (desc)" : "Sort (asc)");
    }

    private void onSearchParametersUpdated() {
        Map<String, String> parametersMap = new HashMap<>();

        String searchFieldText = searchField.getText();
        if (searchFieldText != null && !searchFieldText.isBlank()) {
            parametersMap.put("query", searchFieldText);
        }

        Genre genre = genreComboBox.getValue();
        if (genre != null && genre != Genre.ALL) {
            parametersMap.put("genre", genre.name());
        }

        Integer releaseYearValue = releaseYear.getValue();
        if (releaseYearValue != null) {
            parametersMap.put("releaseYear", releaseYearValue.toString());
        }

        String ratingValue = rating.getValue();
        if (ratingValue != null && !ratingValue.isBlank()) {
            parametersMap.put("ratingFrom", ratingValue);
        }

        List<Movie> movies = apiConsumer.getMovies(parametersMap);
        movieListView.setItems(FXCollections.observableList(movies));
    }

    private void setUpGenreComboBox() {
        genreComboBox.setPromptText("Filter by Genre");
        genreComboBox.getItems().addAll(Genre.values());
    }

    private void setUpReleaseYearComboBox() {
        releaseYear.setPromptText("Filter by release year");
        List<Integer> generatedReleaseYears = movieListView.getItems().stream().map(Movie::getReleaseYear).sorted().distinct().toList();
        releaseYear.getItems().addAll(generatedReleaseYears);
    }

    private void setupRatingComboBox() {
        rating.setPromptText("Filter by min rating");
        List<Integer> ratings = IntStream.rangeClosed(0, 10).boxed().toList();
        List<String> ratingStrings = ratings.stream().map(rating -> String.format(Locale.US, "%.1f", rating.doubleValue())).toList();
        rating.getItems().addAll(ratingStrings);
    }

    private void OnResetButton() {
        searchField.setText("");
        genreComboBox.setValue(null);
        rating.setValue(null);
        releaseYear.setValue(null);
        List<Movie> movies = apiConsumer.getAllMovies();
    }

    @FXML
    protected void watchListOnClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("WatchList-view.fxml")));
        Stage registerStage = (Stage) watchListButton.getScene().getWindow();
        registerStage.setScene(new Scene(root, 980, 650));
    }

    private final ClickEventHandler<Movie> onAddToWatchListClicked = (clickedMovie) -> {
        WatchlistMovieEntity watchlistMovie = WatchlistMovieEntity.movieToEntityMapper(clickedMovie);
        try {
            repo.addToWatchlist(watchlistMovie);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    };

    private final ClickEventHandler<MovieCell> onExpandDetailsClicked = MovieCell::expandDetails;
}