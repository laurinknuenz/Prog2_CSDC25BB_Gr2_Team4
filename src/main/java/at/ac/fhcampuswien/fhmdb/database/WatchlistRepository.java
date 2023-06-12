package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.EventListener.Observable;
import at.ac.fhcampuswien.fhmdb.EventListener.Observer;
import at.ac.fhcampuswien.fhmdb.HomeController;
import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WatchlistRepository implements Observable {
    Dao<WatchlistMovieEntity, Long> dao;
    private List<Observer> observers;

    private static WatchlistRepository instance;

    private WatchlistRepository() throws DatabaseException {
        this.dao = Database.getDatabase().getDao();
        this.observers = new ArrayList<>();
    }

    public static WatchlistRepository getInstance(){
        try {
            if(instance == null) instance = new WatchlistRepository();

        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
        return instance;
    }

    public void addToWatchlist(WatchlistMovieEntity movie) throws DatabaseException {
        try {
            List<WatchlistMovieEntity> existingMovies = dao.queryForEq("title", movie.getTitle());
            if (existingMovies.isEmpty()) {
                dao.create(movie);
                notifyObservers("Movie successfully added to watchlist!");
            } else
                 notifyObservers("You already have this in your watch list! \n click ok to continue :) ");
        } catch (SQLException e) {
            throw new DatabaseException(("Connection error"), e);
        }
    }

    public void removeFromWatchlist(WatchlistMovieEntity movie) throws SQLException, DatabaseException {
        try {
            String apiId = movie.getTitle();
            if (dao != null) {
                DeleteBuilder<WatchlistMovieEntity, Long> deleteBuilder = dao.deleteBuilder();
                if (deleteBuilder != null) {
                    deleteBuilder.where().eq("title", movie.getTitle());
                    dao.delete(deleteBuilder.prepare());
                    //notifyObservers("Movie removed from watchlist!"); can use this after we do the factory pattern 
                    HomeController.showInfoMessage("Movie removed from watchlist!");
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

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
}
