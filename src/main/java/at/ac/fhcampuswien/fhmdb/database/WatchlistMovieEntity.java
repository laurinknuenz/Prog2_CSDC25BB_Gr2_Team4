package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

@DatabaseTable(tableName = "movie")
public class WatchlistMovieEntity {
    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField()
    private String apild;

    @DatabaseField()
    private String title;

    public WatchlistMovieEntity(){
    }

    public WatchlistMovieEntity(String title, String description, List<Genre> genres, Integer releaseYear, String imgUrl, int lengthInMinutes, Double rating) {
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
            if (genre != genres.get(genres.size()-1)) genresString.append(',');
        }
        return genresString.toString();
    }

    @Override
    public String toString() {
        return this.title;
    }

    public String getTitle(){
        return title;
    }
}
