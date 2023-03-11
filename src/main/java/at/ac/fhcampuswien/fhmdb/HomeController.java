package at.ac.fhcampuswien.fhmdb;

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
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

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
    public JFXButton sortBtn;
    private final ObservableList<Movie> observableMovies = FXCollections.observableArrayList();
    public List<Movie> allMovies = Movie.initializeMovies();
    private static boolean isTextFieldActive;
    private static boolean isGenreFilterActive;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Movie.initializeMovies();
        observableMovies.addAll(allMovies);
        // initialize UI stuff
        movieListView.setItems(observableMovies);
        movieListView.setCellFactory(movieListView -> new MovieCell());

        genreComboBox.getItems().addAll(Genre.values());
        genreComboBox.setPromptText("Filter by Genre");

        searchBtn.setOnAction(this::prepareToFilter);
        genreComboBox.setOnAction(this::prepareToFilter);
        sortBtn.setOnAction(this::sortObservableList);
    }

    private void filterMoviesAccordingToState(String searchTerm, Genre selectedGenre) {
        if(!isTextFieldActive && !isGenreFilterActive){
            observableMovies.addAll(allMovies); return;
        }
        if(isTextFieldActive && !isGenreFilterActive){
            observableMovies.addAll(Movie.filterMovies(searchTerm, allMovies)); return;
        }
        if (!isTextFieldActive){
            observableMovies.addAll(Movie.filterMoviesByGenre(selectedGenre, allMovies)); return;
        }
        if (isTextFieldActive){
            List<Movie> finalMovies = Movie.filterMoviesByGenre(selectedGenre, allMovies);
            finalMovies= Movie.filterMovies(searchTerm, finalMovies);
            observableMovies.addAll(finalMovies);
        }
    }

    private void checkActiveFilters() {
        isTextFieldActive= !searchField.getText().isBlank();
        isGenreFilterActive = genreComboBox.getValue() != null && genreComboBox.getValue() != Genre.ALL;
    }

    private void sortObservableList(ActionEvent actionEvent) {
        if (sortBtn.getText().equals("Sort (asc)")) {
            Comparator<Movie> titleComparator = Comparator.comparing(Movie::getTitle);
            FXCollections.sort(observableMovies, titleComparator);
            sortBtn.setText("Sort (desc)");
        } else {
            Comparator<Movie> titleComparator = Comparator.comparing(Movie::getTitle).reversed();
            FXCollections.sort(observableMovies, titleComparator);
            sortBtn.setText("Sort (asc)");
        }
    }

    private void prepareToFilter(ActionEvent actionEvent) {
        String searchTerm = searchField.getText();
        Genre selectedGenre = genreComboBox.getValue();

        checkActiveFilters();
        observableMovies.clear();

        filterMoviesAccordingToState(searchTerm, selectedGenre);
    }
}