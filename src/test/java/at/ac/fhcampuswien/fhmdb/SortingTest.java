package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SortingTest {

    private List<Movie> movieList;
    void createTestMovies() {
        movieList = new ArrayList<>();
        movieList.add(new Movie("Movie A", "Description A", Genre.ACTION));
        movieList.add(new Movie("Movie C", "Description C", Genre.HORROR));
        movieList.add(new Movie("Movie B", "Description B", Genre.MYSTERY));
    }

    @Test
    void movies_SortEmptyList_returnsEmptyList() {
        //Arrange
        List<Movie> emptyMovieList = new ArrayList<>();
        //Act
        List<Movie> actual = Movie.sortMovies(emptyMovieList, true);
        //Assert
        assertEquals(0, actual.size());
    }

    @Test
    void movies_SortEmptyListDescending_returnsEmptyList() {
        //Arrange
        List<Movie> emptyMovieList = new ArrayList<>();
        //Act
        List<Movie> actual = Movie.sortMovies(emptyMovieList, false);
        //Assert
        assertEquals(0, actual.size());
    }

    @Test
    void movies_SortAscending_returnsCorrectlyOrderedList() {
        //Arrange
        createTestMovies();
        //Act
        List<Movie> actual = Movie.sortMovies(movieList, true);
        //Assert
        assertEquals("Movie A", actual.get(0).getTitle());
        assertEquals("Movie B", actual.get(1).getTitle());
        assertEquals("Movie C", actual.get(2).getTitle());
    }

    @Test
    void movies_SortDescending_returnsCorrectlyOrderedList() {
        //Arrange
        createTestMovies();
        //Act
        List<Movie> actual = Movie.sortMovies(movieList, false);
        //Assert
        assertEquals("Movie C", actual.get(0).getTitle());
        assertEquals("Movie B", actual.get(1).getTitle());
        assertEquals("Movie A", actual.get(2).getTitle());
    }

    @Test
    void movies_SortListWithSingleMovie_returnsListWithOnlyThisMovie() {
        //Arrange
        List<Movie> singleMovieList = new ArrayList<>();
        singleMovieList.add(new Movie("Single Movie", "Movie Description", Genre.DOCUMENTARY));
        //Act
        List<Movie> actual = Movie.sortMovies(singleMovieList, true);
        //Assert
        assertEquals(1, actual.size());
        assertEquals("Single Movie", actual.get(0).getTitle());
    }

    @Test
    void movies_SortListWithSingleMovieDescending_returnsListWithOnlyThisMovie() {
        //Arrange
        List<Movie> singleMovieList = new ArrayList<>();
        singleMovieList.add(new Movie("Single Movie", "Movie Description", Genre.DOCUMENTARY));
        //Act
        List<Movie> actual = Movie.sortMovies(singleMovieList, false);
        //Assert
        assertEquals(1, actual.size());
        assertEquals("Single Movie", actual.get(0).getTitle());
    }
}
