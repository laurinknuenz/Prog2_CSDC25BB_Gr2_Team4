package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.EventListener.Observer;
import at.ac.fhcampuswien.fhmdb.api.ApiConsumer;
import at.ac.fhcampuswien.fhmdb.database.WatchlistMovieEntity;
import at.ac.fhcampuswien.fhmdb.database.WatchlistRepository;
import at.ac.fhcampuswien.fhmdb.interfaces.ClickEventHandler;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.sortingstates.AscendingState;
import at.ac.fhcampuswien.fhmdb.sortingstates.SortingState;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.IntStream;

public class HomeController implements Initializable, Observer {
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

    WatchlistRepository repo = WatchlistRepository.getInstance();

    private SortingState sortingState = new AscendingState(this);
    public static final HomeController INSTANCE = new HomeController();

    private HomeController() {
        WatchlistRepository.getInstance().addObserver(this);
    }

    public void setSortingState(SortingState sortingState) {
        this.sortingState = sortingState;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // initialize UI stuff
        movieListView.setItems(FXCollections.observableList(apiConsumer.getAllMovies()));
        movieListView.setCellFactory(movieListView -> new MovieCell(false, onAddToWatchListClicked, onExpandDetailsClicked));

        FilteringOperations();

        sortingState.sortObservableList();
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
        sortingState.changeState();
        sortingState.sortObservableList();
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

        sortingState.sortObservableList();
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
        FhmdbApplication.switchToWatchListScene();
    }

    private final ClickEventHandler<Movie> onAddToWatchListClicked = (clickedMovie) -> {
        WatchlistMovieEntity watchlistMovie = WatchlistMovieEntity.movieToEntityMapper(clickedMovie);
        repo.addToWatchlist(watchlistMovie);
    };

    private final ClickEventHandler<MovieCell> onExpandDetailsClicked = MovieCell::expandDetails;

    public static void showInfoMessage(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Alert!");
        alert.setHeaderText(alertMessage);
        alert.showAndWait();
    }

    @Override
    public void update(String message) {
        showInfoMessage(message);
    }
}