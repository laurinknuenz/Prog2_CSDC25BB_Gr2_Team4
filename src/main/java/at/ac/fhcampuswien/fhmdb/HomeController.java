package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.data.MovieRepository;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HomeController implements Initializable {
    @FXML
    public JFXButton searchBtn;
    @FXML
    public TextField searchField;
    @FXML
    public JFXListView movieListView;
    @FXML
    public JFXComboBox<Genre> genreComboBox;
    @FXML
    public JFXComboBox<Integer> releaseYear;
    @FXML
    public JFXComboBox<String> rating;
    @FXML
    public JFXButton sortBtn;

    private final MovieRepository repository = new MovieRepository();
    private final ObservableList<Movie> observableMovies = FXCollections.observableArrayList();

    private static boolean isTextFieldActive;
    private static boolean isGenreFilterActive;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //allMovies = Movie.initializeMovies();
        repository.addAll(Movie.initializeMovies());
        observableMovies.addAll(repository.getAll());

        // initialize UI stuff
        movieListView.setItems(observableMovies);
        movieListView.setCellFactory(movieListView -> new MovieCell());

        setUpGenreComboBox();
        setUpReleaseYearComboBox();
        setupRatingComboBox();

        searchBtn.setOnAction(this::prepareToFilter);
        genreComboBox.setOnAction(this::prepareToFilter);
        sortBtn.setOnAction(this::sortObservableList);
    }

    private void sortObservableList(ActionEvent actionEvent) {
        boolean isAscendingOrder = sortBtn.getText().equals("Sort (asc)");
        sortMoviesByOrder(isAscendingOrder);
        sortBtn.setText(isAscendingOrder ? "Sort (desc)" : "Sort (asc)");
    }

    private void prepareToFilter(ActionEvent actionEvent) {
        checkActiveFilters();
        observableMovies.clear();

        filterMoviesAccordingToState();
    }

    private void checkActiveFilters() {
        isTextFieldActive = !searchField.getText().isBlank();
        isGenreFilterActive = genreComboBox.getValue() != null && genreComboBox.getValue() != Genre.ALL;
    }

    private void filterMoviesAccordingToState() {
        String searchTerm = searchField.getText();
        Genre selectedGenre = genreComboBox.getValue();

        if (!isTextFieldActive && !isGenreFilterActive) {
            observableMovies.addAll(repository.getAll());
            return;
        }

        List<Movie> filteredMovies = repository.getAll().stream()
                .filter(movie -> isTextFieldActive ? movie.getTitle().toLowerCase().contains(searchTerm.toLowerCase()) : true)
                .filter(movie -> isGenreFilterActive ? movie.getGenres().contains(selectedGenre) : true)
                .collect(Collectors.toList());

        observableMovies.addAll(filteredMovies);
    }

    private void setUpGenreComboBox() {
        genreComboBox.setPromptText("Filter by Genre");
        genreComboBox.getItems().addAll(Genre.values());
    }

    private void setUpReleaseYearComboBox() {
        releaseYear.setPromptText("Filter by release year");
        List<Integer> generatedReleaseYears = IntStream.rangeClosed(1900, 2023).boxed().toList();
        releaseYear.getItems().addAll(generatedReleaseYears);
    }

    private void setupRatingComboBox() {
        rating.setPromptText("Filter by min rating");
        List<Integer> ratings = IntStream.rangeClosed(0, 10).boxed().toList();
        List<String> ratingStrings = ratings.stream().map(rating -> String.format("%.1f", rating.doubleValue())).toList();
        rating.getItems().addAll(ratingStrings);
    }

    private void sortMoviesByOrder(final boolean ascending) {
        List<Movie> moviesToSort = new ArrayList<>(observableMovies);

        Comparator<Movie> titleComparator;
        if (ascending) {
            titleComparator = Comparator.comparing(Movie::getTitle);
        } else {
            titleComparator = Comparator.comparing(Movie::getTitle).reversed();
        }

        moviesToSort.sort(titleComparator);
        observableMovies.clear();
        observableMovies.addAll(moviesToSort);
    }
}