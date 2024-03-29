package at.ac.fhcampuswien.fhmdb.models;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Movie {
    private String id;
    private String title;
    private List<Genre> genres;
    private Integer releaseYear;
    private String description;
    private List<String> directors;
    private List<String> writers;
    private List<String> mainCast;
    private Double rating;

    public String getTitle() {
        return title;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public String getDescription() {
        return description;
    }

    public Movie(String id, String title, String description, List<Genre> genres, Integer releaseYear, String imgUrl, Integer lengthInMinutes, Double rating){
        this.id = id;
        this.title = title;
        this.description=description;
        this.genres= genres;
        this.releaseYear = releaseYear;
        this.rating = rating;
        directors = new ArrayList<>();
        writers=new ArrayList<>();
        mainCast=new ArrayList<>();
    }

    public List<String> getDirectors() {
        return directors;
    }

    public List<String> getMainCast() {
        return mainCast;
    }

    public Double getRating() {
        return rating;
    }

    public String getId(){
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie movie)) return false;
        return Objects.equals(id, movie.id) && Objects.equals(title, movie.title) && Objects.equals(genres, movie.genres) && Objects.equals(releaseYear, movie.releaseYear) && Objects.equals(description, movie.description) && Objects.equals(directors, movie.directors) && Objects.equals(writers, movie.writers) && Objects.equals(mainCast, movie.mainCast) && Objects.equals(rating, movie.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, genres, releaseYear, description, directors, writers, mainCast, rating);
    }

    public static List<Movie> sortMovies(final boolean ascending, List<Movie> moviesToSort) {
        Comparator<Movie> titleComparator = Comparator.comparing(Movie::getTitle);
        if (!ascending) {
            titleComparator = titleComparator.reversed();
        }
        moviesToSort.sort(titleComparator);
        return moviesToSort;
    }
}
