package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.List;

@DatabaseTable(tableName = "movie")
public class WatchlistMovieEntity {
    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField()
    private String apiId;

    @DatabaseField()
    private String title;

    public WatchlistMovieEntity() {
    }

    public WatchlistMovieEntity(String apiId, String title, String description, List<Genre> genres, Integer releaseYear, String imgUrl, int lengthInMinutes, Double rating) {
        this.apiId = apiId;
        this.title = title;
        this.description = description;
        this.genres = genresToString(genres);
        this.releaseYear = releaseYear;
        this.imgUrl = imgUrl;
        this.lengthInMinutes = lengthInMinutes;
        this.rating = rating;
    }

    @DatabaseField()
    private String description;

    @DatabaseField()
    private String genres;

    @DatabaseField()
    private Integer releaseYear;

    @DatabaseField()
    private String imgUrl;

    @DatabaseField()
    private int lengthInMinutes;

    @DatabaseField()
    private Double rating;

    private String genresToString(List<Genre> genres) {
        StringBuilder genresString = new StringBuilder();
        for (Genre genre : genres) {
            genresString.append(genre.name());
            if (genre != genres.get(genres.size() - 1)) genresString.append(',');
        }
        return genresString.toString();
    }

    @Override
    public String toString() {
        return this.title;
    }

    public String getTitle() {
        return title;
    }

    public static WatchlistMovieEntity movieToEntityMapper(Movie movie) {
        return new WatchlistMovieEntity(movie.getId(), movie.getTitle(), movie.getDescription(), movie.getGenres(),
                movie.getReleaseYear(), "", 0, movie.getRating());
    }

    public Movie entityToMovieMapper() {
        String[] genreStrings = genres.split(",");
        List<Genre> genreList = new ArrayList<>();
        for (String genre : genreStrings) {
            genreList.add(Genre.valueOf(genre));
        }
        return new Movie(apiId, title, description, genreList, releaseYear, imgUrl, lengthInMinutes, rating);
    }

    public static List<Movie> entityListToMovieListMapper(List<WatchlistMovieEntity> entities) {
        List<Movie> movies =new ArrayList<>();
        for(WatchlistMovieEntity entity: entities){
            movies.add(entity.entityToMovieMapper());
        }
        return movies;
    }
}
