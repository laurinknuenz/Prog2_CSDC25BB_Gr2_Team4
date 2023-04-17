package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.api.ApiConsumer;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HomeController implements Initializable {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // initialize UI stuff
        movieListView.setItems(FXCollections.observableList(apiConsumer.getAllMovies()));
        movieListView.setCellFactory(movieListView -> new MovieCell());

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
        sortMoviesByOrder(isAscendingOrder);
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

    private void OnResetButton() {
        searchField.setText("");
        genreComboBox.setValue(null);
        rating.setValue(null);
        releaseYear.setValue(null);
        List<Movie> movies = apiConsumer.getAllMovies();
    }

    private String getMostPopularActor(List<Movie> movies) {
        return movies.stream().map(Movie::getMainCast).flatMap(List::stream)
                .collect(Collectors.groupingBy(s -> s, Collectors.counting())).entrySet().stream()
                .max(Map.Entry.comparingByValue()).orElse(null).getKey();
    }

    private int getLongestMovieTitle(List<Movie> movies) {
        return movies.stream().map(Movie::getTitle).max(Comparator.comparingInt(String::length)).get().length();
    }

    private long countMoviesFrom(List<Movie> movies, String director) {
        return movies.stream().map(Movie::getDirectors).flatMap(List::stream).filter(d -> d.equals(director)).count();
    }

    private List<Movie> getMoviesBetweenYears(List<Movie> movies, int startYear, int endYear) {
        return movies.stream().filter(m -> m.getReleaseYear() >= startYear && m.getReleaseYear() <= endYear).toList();
    }
}