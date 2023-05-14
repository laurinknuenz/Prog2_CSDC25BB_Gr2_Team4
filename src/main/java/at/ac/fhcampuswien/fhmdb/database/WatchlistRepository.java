package at.ac.fhcampuswien.fhmdb.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WatchlistRepository {
    Dao<WatchlistMovieEntity, Long> dao;

    public WatchlistRepository() {
        this.dao = Database.getDatabase().getDao();
    }

    public void addToWatchlist(WatchlistMovieEntity movie) throws SQLException {
        List<WatchlistMovieEntity> existingMovies = dao.queryForEq("title", movie.getTitle());
        if (existingMovies.isEmpty()) {
            dao.create(movie);
        } else {
            //TODO throw exception say it already exists or sth.
        }
    }

    public void removeFromWatchlist(WatchlistMovieEntity movie) throws SQLException {
        //dao.delete(movie);
        try {
            String apiId = movie.getTitle();
            if(dao != null){
                DeleteBuilder<WatchlistMovieEntity, Long> deleteBuilder = dao.deleteBuilder();
                if(deleteBuilder != null){
                    deleteBuilder.where().eq("title", movie.getTitle());
                    dao.delete(deleteBuilder.prepare());
                }
            }
        } catch (SQLException | IllegalArgumentException e) {
        }
    }

    public List<WatchlistMovieEntity> getAll() throws SQLException {
        return dao.queryForAll();
    }
}
