package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DoubleFilterTest {
    @Test
    void movies_filterMoviesByTextAndGenre_returnsCorrectValues0() {
        // Arrange
        String searchTerm = "nemo";
        Genre selectedGenre = Genre.FAMILY;
        List<Movie> movies = Movie.initializeMovies();

        // Act
        List<Movie> finalMovies = Movie.filterMoviesByGenre(selectedGenre, movies);
        finalMovies= Movie.filterMovies(searchTerm, finalMovies);

        // Assert
        assertTrue(finalMovies.size() > 0);
    }

    @Test
    void movies_filterMoviesByTextAndGenre_returnsCorrectValues1() {
        // Arrange
        String searchTerm = "nemo";
        Genre selectedGenre = Genre.MYSTERY;
        List<Movie> movies = Movie.initializeMovies();

        // Act
        List<Movie> finalMovies = Movie.filterMoviesByGenre(selectedGenre, movies);
        finalMovies= Movie.filterMovies(searchTerm, finalMovies);

        // Assert
        assertTrue(finalMovies.isEmpty());
    }

    @Test
    void movies_filterMoviesByTextAndGenre_returnsCorrectValues2() {
        // Arrange
        String searchTerm = "";
        Genre selectedGenre = Genre.HORROR;
        List<Movie> movies = Movie.initializeMovies();

        // Act
        List<Movie> finalMovies= Movie.filterMovies(searchTerm, movies);
        finalMovies = Movie.filterMoviesByGenre(selectedGenre, finalMovies);

        // Assert
        assertTrue(finalMovies.size() > 0);
    }

    @Test
    void movies_filterMoviesByTextAndGenre_caseInsensitive_returnsCorrectValues() {
        // Arrange
        String searchTerm = "team's";
        Genre selectedGenre = Genre.SPORT;
        List<Movie> movies = Movie.initializeMovies();

        // Act
        List<Movie> finalMovies = Movie.filterMoviesByGenre(selectedGenre, movies);
        finalMovies= Movie.filterMovies(searchTerm, finalMovies);

        // Assert
        assertTrue(finalMovies.size() == 1);
    }

    @Test
    void movies_filterMoviesByTextAndGenre_TextBoxEmpty_returnsCorrectValues() {
        // Arrange
        String searchTerm = "";
        Genre selectedGenre = Genre.HORROR;
        List<Movie> movies = Movie.initializeMovies();

        // Act
        List<Movie> finalMovies= Movie.filterMovies(searchTerm, movies);
        finalMovies = Movie.filterMoviesByGenre(selectedGenre, finalMovies);

        // Assert
        assertTrue(finalMovies.size() > 0);
    }
}
