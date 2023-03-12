package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SingleFilterTest {
    @Test
    void testInitializeMovies() {
        List<Movie> movies = Movie.initializeMovies();
        assertEquals(10, movies.size()); // expected number of movies
        assertNotNull(movies.get(0)); // first movie should not be null
        assertNotNull(movies.get(movies.size() - 1)); // last movie should not be null
    }

    @Test
    void movies_filterMoviesByGenre_returnsCorrectValues() {
        // Arrange
        Movie movie1 = new Movie("Movie 1", "Movie description 1", Genre.ACTION, Genre.ROMANCE);
        Movie movie2 = new Movie("Movie 2", "Movie description 2", Genre.ACTION, Genre.ANIMATION);
        Movie movie3 = new Movie("Movie 3", "Movie description 3", Genre.DRAMA);
        List<Movie> movies = List.of(movie1, movie2, movie3);

        // Act
        List<Movie> filteredMovies = Movie.filterMoviesByGenre(Genre.ACTION, movies);

        // Assert
        List<Movie> expectedMovies = Arrays.asList(movie1, movie2);
        assertEquals(expectedMovies, filteredMovies);
    }

    @Test
    void movies_filterMoviesByGenre_returnsEmptyList0() {
        // Arrange
        List<Movie> movies = new ArrayList<>();

        // Act
        List<Movie> filteredMovies = Movie.filterMoviesByGenre(Genre.ACTION, movies);

        // Assert
        List<Movie> expectedMovies = new ArrayList<>();
        assertEquals(expectedMovies, filteredMovies);
    }

    @Test
    void movies_filterMoviesByGenre_returnsEmptyList1() {
        // Arrange
        List<Movie> movies = Movie.initializeMovies();

        // ACT
        List<Movie> filteredMovies = Movie.filterMoviesByGenre(Genre.MUSICAL, movies);

        // Assert
        assertTrue(filteredMovies.isEmpty());
    }

    @Test
    void movies_filterMoviesByGenre_returnsCorrectNumberOfResults() {
        // Arrange
        List<Movie> movies = Movie.initializeMovies();

        // ACT
        List<Movie> filteredMovies = Movie.filterMoviesByGenre(Genre.ACTION, movies);

        // Assert
        assertEquals(filteredMovies.size(), 5);
    }

    @Test
    void movies_filterMoviesByGenres_returnsFirstMovieContainingGenre() {
        // Arrange
        List<Movie> movies = Movie.initializeMovies();

        // Act
        List<Movie> filteredMovies = Movie.filterMoviesByGenre(Genre.DRAMA, movies);

        // Assert
        assertTrue(filteredMovies.get(0).getGenres().contains(Genre.DRAMA));
    }

    @Test
    void movies_filterMoviesWithNonExistingText_returnsEmptyList() {
        // Arrange
        List<Movie> movies = Movie.initializeMovies();

        // ACT
        List<Movie> filteredMovies = Movie.filterMovies("Non existing movie title or description", movies);

        // Assert
        assertTrue(filteredMovies.isEmpty());
    }

    @Test
    void movies_filterMovies_returnsMoviesFilteredByNameCaseInsensitive() {
        // Arrange
        List<Movie> movies = Movie.initializeMovies();

        // ACT
        List<Movie> filteredMovies = Movie.filterMovies("COOL RUNNINGS", movies);

        // Arrange
        assertTrue(filteredMovies.get(0).getTitle().contains("Cool Runnings"));
    }

    @Test
    void movies_filterByDescription_returnsMoviesFilteredByDescription0() {
        List<Movie> movies = Movie.initializeMovies();

        List<Movie> filteredMovies = Movie.filterMovies("some film about christmas", movies);

        assertTrue(filteredMovies.isEmpty());
    }

    @Test
    void movies_filterByDescription_returnsMoviesFilteredByDescription1() {
        // Arrange
        List<Movie> movies = Movie.initializeMovies();

        // Act
        List<Movie> filteredMovies = Movie.filterMovies("nemo", movies);

        // Assert
        assertTrue(filteredMovies.size() >= 1);
        assertTrue(filteredMovies.get(0).getTitle().toLowerCase().contains("nemo") || filteredMovies.get(0).getDescription().toLowerCase().contains("nemo"));
    }

    @Test
    void movies_filterMoviesByTitleAndDescription_returnsCorrectValues() {
        // Arrange
        List<Movie> movies = Movie.initializeMovies();

        // Act
        List<Movie> filteredMovies = Movie.filterMovies("drama", movies);

        // Assert
        assertTrue(filteredMovies.isEmpty());
    }
}