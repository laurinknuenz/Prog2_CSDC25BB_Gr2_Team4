package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.HomeController;
import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class WatchlistRepository {
    Dao<WatchlistMovieEntity, Long> dao;

    public WatchlistRepository() throws DatabaseException {
        this.dao = Database.getDatabase().getDao();
    }

    public void addToWatchlist(WatchlistMovieEntity movie) throws DatabaseException {
        try{
            List<WatchlistMovieEntity> existingMovies = dao.queryForEq("title", movie.getTitle());
            if (existingMovies.isEmpty()) {
                dao.create(movie);
            }
            else HomeController.showInfoMessage("You already have this in your watch list! \n click ok to continue :) ");
        }catch (SQLException e){
            throw  new DatabaseException(("Connection error"), e);
        }


    }
    public void removeFromWatchlist(WatchlistMovieEntity movie) throws SQLException, DatabaseException {
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
            throw new DatabaseException("Connection error");
        }
    }

    public List<WatchlistMovieEntity> getAll() throws DatabaseException {
        try {
            if (dao != null)
                return dao.queryForAll();
            else
                return Collections.emptyList();
        } catch (SQLException | IllegalArgumentException e) {
            throw new DatabaseException("Connection Error", e);
        }
    }
}
