package at.ac.fhcampuswien.fhmdb.models;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    private String title;
    private String description;

    private List<Genre> genres;

    public Movie(String title, String description, Genre... genres) {
        this.title = title;
        this.description = description;
        this.genres = List.of(genres);
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public static List<Movie> initializeMovies() {
        // TODO: Try to mention some movies that actually exist lol
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie("A The first movie", "When the homework is due, two students have to ", Genre.DRAMA, Genre.ACTION, Genre.THRILLER));
        movies.add(new Movie("B Campus krampus", "Some film about christmas and vienna Fh campus idk.", Genre.ACTION, Genre.THRILLER));
        movies.add(new Movie("C Forrest favoriten", "I honestly don't know, but it is the third movie in the list", Genre.COMEDY, Genre.DRAMA, Genre.ROMANCE));
        movies.add(new Movie("D Taylor's car", "Cats, drama, I literally have no idea what im lorem ipsum", Genre.ANIMATION, Genre.DRAMA, Genre.BIOGRAPHY));
        movies.add(new Movie("E Taylor's car", "Cats, drama, I literally have no idea what im lorem ipsum", Genre.ANIMATION, Genre.DRAMA, Genre.BIOGRAPHY));
        movies.add(new Movie("F Taylor's car", "Cats, drama, I literally have no idea what im lorem ipsum", Genre.ANIMATION, Genre.DRAMA, Genre.BIOGRAPHY));
        movies.add(new Movie("G Taylor's car", "Cats, drama, I literally have no idea what im lorem ipsum", Genre.ROMANCE));
        return movies;
    }

    public static List<Movie> filterMoviesByGenre(Genre genre, List<Movie> movies) {
        List<Movie> filteredMovies = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.getGenres().contains(genre)) {
                filteredMovies.add(movie);
            }
        }
        return filteredMovies;
    }

    public static List<Movie> filterMovies(String searchTerm, List<Movie> movies) {
        List<Movie> filteredMovies = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.getTitle().toLowerCase().contains(searchTerm.toLowerCase()) || movie.getDescription().toLowerCase().contains(searchTerm.toLowerCase())) {
                filteredMovies.add(movie);
            }
        }
        return filteredMovies;
    }
}
