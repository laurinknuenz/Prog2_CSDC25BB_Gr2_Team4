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
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class HomeController implements Initializable {
    @FXML
    public JFXButton searchBtn;
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

    private final MovieRepository repository = new MovieRepository();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        repository.addAll(Movie.initializeMovies());

        // initialize UI stuff
        movieListView.setItems(FXCollections.observableList(repository.getAll()));
        movieListView.setCellFactory(movieListView -> new MovieCell());

        setUpGenreComboBox();
        setUpReleaseYearComboBox();
        setupRatingComboBox();

        searchBtn.setOnAction(actionEvent -> filterMoviesAccordingToState());
        genreComboBox.setOnAction(actionEvent -> filterMoviesAccordingToState());
        sortBtn.setOnAction(this::sortObservableList);
    }

    private void sortObservableList(ActionEvent actionEvent) {
        boolean isAscendingOrder = sortBtn.getText().equals("Sort (asc)");
        sortMoviesByOrder(isAscendingOrder);
        sortBtn.setText(isAscendingOrder ? "Sort (desc)" : "Sort (asc)");
    }

    private void filterMoviesAccordingToState() {
        Stream<Movie> moviesToSet = repository.getAll().stream();

        String searchTerm = searchField.getText();
        if (!searchTerm.isBlank()) {
            moviesToSet = moviesToSet.filter(movie -> movie.getTitle().toLowerCase().contains(searchTerm.toLowerCase()));
        }

        Genre selectedGenre = genreComboBox.getValue();
        if (isGenreFilterActive(selectedGenre)) {
            moviesToSet = moviesToSet.filter(movie -> movie.getGenres().contains(selectedGenre));
        }

        movieListView.setItems(FXCollections.observableList(moviesToSet.toList()));
    }

    private boolean isGenreFilterActive(Genre selectedGenre) {
        return selectedGenre != null && selectedGenre != Genre.ALL;
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
        Comparator<Movie> titleComparator = Comparator.comparing(Movie::getTitle);
        if (!ascending) {
            titleComparator = titleComparator.reversed();
        }

        List<Movie> moviesToSort = new ArrayList<>(movieListView.getItems());
        moviesToSort.sort(titleComparator);
        movieListView.setItems(FXCollections.observableList(moviesToSort));
    }
}