package at.ac.fhcampuswien.fhmdb.data;

import at.ac.fhcampuswien.fhmdb.models.Movie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MovieRepository {
    private final List<Movie> movies = new ArrayList<>();

    public void addAll(final Collection<Movie> movies) {
        movies.forEach(this::add);
    }

    public void add(Movie movie) {
        movies.add(movie);
    }

    public List<Movie> getByCriteria(final Predicate<Movie> predicate) {
        return movies.stream().filter(predicate).collect(Collectors.toList());
    }

    public List<Movie> getAll() {
        return List.copyOf(movies);
    }
}
