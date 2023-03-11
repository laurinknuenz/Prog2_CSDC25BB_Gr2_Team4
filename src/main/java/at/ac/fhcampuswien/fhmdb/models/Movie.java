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

    public static List<Movie> allMovies = new ArrayList<>();

    public static void initializeMovies() {
        // TODO: Try to mention some movies that actually exist lol
        allMovies.add(new Movie("A The first movie", "When the homework is due, two students have to ", Genre.DRAMA, Genre.ACTION, Genre.THRILLER));
        allMovies.add(new Movie("B Campus krampus", "Some film about christmas and vienna Fh campus idk.", Genre.ACTION, Genre.THRILLER));
        allMovies.add(new Movie("C Forrest favoriten", "I honestly dont know, but it is the third movie in the list", Genre.COMEDY, Genre.DRAMA, Genre.ROMANCE));
        allMovies.add(new Movie("D Taylor's car", "Cats, drama, I literally have no idea what im lorem ipsum", Genre.ANIMATION, Genre.DRAMA, Genre.BIOGRAPHY));
        allMovies.add(new Movie("E Taylor's car", "Cats, drama, I literally have no idea what im lorem ipsum", Genre.ANIMATION, Genre.DRAMA, Genre.BIOGRAPHY));
        allMovies.add(new Movie("F Taylor's car", "Cats, drama, I literally have no idea what im lorem ipsum", Genre.ANIMATION, Genre.DRAMA, Genre.BIOGRAPHY));
        allMovies.add(new Movie("G Taylor's car", "Cats, drama, I literally have no idea what im lorem ipsum", Genre.ROMANCE));
    }

    public static List<Movie> filterMoviesByGenre(Genre genre, List<Movie> textFilteredMovies) {
        List<Movie> moviesToFilter = new ArrayList<>(textFilteredMovies != null ? textFilteredMovies : allMovies);
        List<Movie> filteredMovies = new ArrayList<>();
        for (Movie movie : moviesToFilter) {
            if (movie.getGenres().contains(genre)) {
                filteredMovies.add(movie);
            }
        }
        return filteredMovies;
    }

    public static List<Movie> filterMovies(String searchTerm, List<Movie> genreFilteredMovies) {
        List<Movie> moviesToFilter = new ArrayList<>(genreFilteredMovies != null ? genreFilteredMovies : allMovies);
        List<Movie> filteredMovies = new ArrayList<>();
        for (Movie movie : moviesToFilter) {
            if (movie.getTitle().toLowerCase().contains(searchTerm.toLowerCase()) || movie.getDescription().toLowerCase().contains(searchTerm.toLowerCase())) {
                filteredMovies.add(movie);
            }
        }
        return filteredMovies;
    }
}
