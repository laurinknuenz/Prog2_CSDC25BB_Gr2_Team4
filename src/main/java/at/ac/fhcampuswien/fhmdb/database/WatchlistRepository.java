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
        dao.create(movie);
    }

    public void removeFromWatchlist(WatchlistMovieEntity movie) throws SQLException {
        dao.delete(movie);
    }

    public List<WatchlistMovieEntity> getAll() throws SQLException {
        return dao.queryForAll();
    }
}
