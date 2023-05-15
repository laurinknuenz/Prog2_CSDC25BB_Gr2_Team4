package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.HomeController;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
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
            HomeController.showInfoMessage("You already have this in your watch list! \n click ok to continue :) ");
        }
    }
    public void removeFromWatchlist(WatchlistMovieEntity movie) throws SQLException {
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
