package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SortingTest {

    private List<Movie> movieList;
    private Movie movieA = new Movie("Movie A", "Description A", Genre.ACTION);
    private Movie movieB = new Movie("Movie B", "Description B", Genre.HORROR);
    private Movie movieC = new Movie("Movie C", "Description C", Genre.MYSTERY);

    void createTestMovies() {
        movieList = new ArrayList<>();
        movieList.add(movieA);
        movieList.add(movieC);
        movieList.add(movieB);
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
        assertEquals(movieA, actual.get(0));
        assertEquals(movieB, actual.get(1));
        assertEquals(movieC, actual.get(2));
    }

    @Test
    void movies_SortDescending_returnsCorrectlyOrderedList() {
        //Arrange
        createTestMovies();
        //Act
        List<Movie> actual = Movie.sortMovies(movieList, false);
        //Assert
        assertEquals(movieC, actual.get(0));
        assertEquals(movieB, actual.get(1));
        assertEquals(movieA, actual.get(2));
    }

    @Test
    void movies_SortListWithSingleMovie_returnsListWithOnlyThisMovie() {
        //Arrange
        List<Movie> singleMovieList = new ArrayList<>();
        Movie singleMovie = new Movie("Single Movie", "Movie Description", Genre.DOCUMENTARY);
        singleMovieList.add(singleMovie);
        //Act
        List<Movie> actual = Movie.sortMovies(singleMovieList, true);
        //Assert
        assertEquals(1, actual.size());
        assertEquals(singleMovie, actual.get(0));
    }

    @Test
    void movies_SortListWithSingleMovieDescending_returnsListWithOnlyThisMovie() {
        //Arrange
        List<Movie> singleMovieList = new ArrayList<>();
        Movie singleMovie = new Movie("Single Movie", "Movie Description", Genre.DOCUMENTARY);
        singleMovieList.add(singleMovie);
        //Act
        List<Movie> actual = Movie.sortMovies(singleMovieList, false);
        //Assert
        assertEquals(1, actual.size());
        assertEquals(singleMovie, actual.get(0));
    }
}
