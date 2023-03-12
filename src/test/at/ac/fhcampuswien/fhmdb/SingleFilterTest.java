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
        assertEquals(7, movies.size()); // expected number of movies
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
        List<Movie> filteredMovies = Movie.filterMoviesByGenre(Genre.HORROR, movies);

        // Assert
        assertTrue(filteredMovies.isEmpty());
    }

    @Test
    void movies_filterMoviesByGenres_returnsGenreMovies() {
        // Arrange
        List<Movie> movies = Movie.initializeMovies();

        // Act
        List<Movie> filteredMovies = Movie.filterMoviesByGenre(Genre.DRAMA, movies);

        // Assert
        for (Movie movie : filteredMovies) {
            assertTrue(movie.getGenres().contains(Genre.DRAMA));
        }
    }

    @Test
    void movies_filterMovies_returnsEmptyList() {
        // Arrange
        List<Movie> movies = Movie.initializeMovies();

        // ACT
        List<Movie> filteredMovies = Movie.filterMovies("Non existing movie title or description", movies);

        // Assert
        assertTrue(filteredMovies.isEmpty());
    }

    @Test
    void movies_filterByName_returnsMoviesFilteredByNameCaseInsensitive() {
        // Arrange
        List<Movie> movies = Movie.initializeMovies();

        // ACT
        List<Movie> filteredMovies = Movie.filterMovies("Taylor's car", movies);

        // Arrange
        for (Movie movie : filteredMovies) {
            assertTrue(movie.getTitle().toLowerCase().contains("taylor's car"));
        }
    }

    @Test
    void movies_filterByDescription_returnsMoviesFilteredByDescription() {
        List<Movie> movies = Movie.initializeMovies();
        List<Movie> filteredMovies = Movie.filterMovies("some film about christmas", movies);
        for (Movie movie : filteredMovies) {
            assertTrue(movie.getDescription().toLowerCase().contains("some film about christmas"));
        }
    }

    @Test
    void testFilterMoviesCaseInsensitive() {
        List<Movie> movies = Movie.initializeMovies();
        List<Movie> filteredMovies = Movie.filterMovies("Cats", movies);
        for (Movie movie : filteredMovies) {
            assertTrue(movie.getTitle().toLowerCase().contains("cats") || movie.getDescription().toLowerCase().contains("cats"));
        }
    }

    @Test
    void movies_filterMoviesByTitleAndDescription_returnsCorrectValues() {
        List<Movie> movies = Movie.initializeMovies();
        List<Movie> filteredMovies = Movie.filterMovies("drama", movies);
        for (Movie movie : filteredMovies) {
            assertTrue(movie.getTitle().toLowerCase().contains("drama") || movie.getDescription().toLowerCase().contains("drama"));
        }
    }
}