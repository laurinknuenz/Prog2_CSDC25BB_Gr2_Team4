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
    public List<Movie> allMovies = Movie.initializeMovies();
    private final ObservableList<Movie> observableMovies = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        observableMovies.addAll(allMovies);
        // initialize UI stuff
        movieListView.setItems(observableMovies);
        movieListView.setCellFactory(movieListView -> new MovieCell());

        genreComboBox.getItems().addAll(Genre.values());
        genreComboBox.setPromptText("Filter by Genre");

        //TODO: make both filters work toghether.

        // Filters using search text only, for now.
        searchBtn.setOnAction(actionEvent -> {
            String searchTerm = searchField.getText();
            observableMovies.clear();
            observableMovies.addAll(Movie.filterMovies(allMovies, searchTerm));
        });

        // Filters using Genre only, for now.
        genreComboBox.setOnAction(actionEvent -> {
            Genre selectedGenre = genreComboBox.getValue();
            observableMovies.clear();
            if(selectedGenre != Genre.ALL) observableMovies.addAll(Movie.filterMoviesByGenre(allMovies, selectedGenre));
            else  observableMovies.addAll(allMovies);
        });

        sortObservableList();
    }

    private void sortObservableList() {
        //keep this last, so it filters the "already previously filtered list"
        sortBtn.setOnAction(actionEvent -> {
            if(sortBtn.getText().equals("Sort (asc)")) {
                FXCollections.reverse(observableMovies);
                sortBtn.setText("Sort (desc)");
            } else {
                FXCollections.reverse(observableMovies);
                sortBtn.setText("Sort (asc)");
            }
        });
    }
}