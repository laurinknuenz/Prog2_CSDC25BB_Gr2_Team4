package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
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
    private String currentTextFilter = "";
    private Genre currentGenreFilter = Genre.ALL;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Movie.initializeMovies();
        observableMovies.addAll(Movie.allMovies);
        // initialize UI stuff
        movieListView.setItems(observableMovies);
        movieListView.setCellFactory(movieListView -> new MovieCell());

        genreComboBox.getItems().addAll(Genre.values());
        genreComboBox.setPromptText("Filter by Genre");

        //TODO: Fix Filter Bug
        /*There is a bug that when you filter for genre, and than for text while genre still activated, and then remove the text filter again, it wont
        display all the movies in that genre but still only the movies filtered by genre AND text, don't know, if this bug exists also the other way
        round, if you filter for text first, then for genre, and change the genre back to ALL, but i dont think so*/

        // Filters using search text only, for now.
        searchBtn.setOnAction(actionEvent -> {
            currentTextFilter = searchField.getText();
            if (currentGenreFilter == Genre.ALL) {
                observableMovies.clear();
                observableMovies.addAll(Movie.filterMovies(currentTextFilter, null));
            } else {
                List<Movie> genreFilteredMovies = new ArrayList<>(observableMovies);
                observableMovies.clear();
                observableMovies.addAll(Movie.filterMovies(currentTextFilter, genreFilteredMovies));
            }
        });

        // Filters using Genre only, for now.
        genreComboBox.setOnAction(actionEvent -> {
            currentGenreFilter = genreComboBox.getValue();
            if (currentTextFilter.equals("")) {
                observableMovies.clear();
                if (currentGenreFilter != Genre.ALL)
                    observableMovies.addAll(Movie.filterMoviesByGenre(currentGenreFilter, null));
                else observableMovies.addAll(Movie.allMovies);
            } else {
                List<Movie> textFilteredMovies = new ArrayList<>(observableMovies);
                observableMovies.clear();
                if (currentGenreFilter != Genre.ALL)
                    observableMovies.addAll(Movie.filterMoviesByGenre(currentGenreFilter, textFilteredMovies));
                else observableMovies.addAll(textFilteredMovies);
            }
        });

        sortObservableList();
    }

    private void sortObservableList() {
        //keep this last, so it filters the "already previously filtered list"
        sortBtn.setOnAction(actionEvent -> {
            if (sortBtn.getText().equals("Sort (asc)")) {
                FXCollections.reverse(observableMovies);
                sortBtn.setText("Sort (desc)");
            } else {
                FXCollections.reverse(observableMovies);
                sortBtn.setText("Sort (asc)");
            }
        });
    }
}