package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.HomeController;
import at.ac.fhcampuswien.fhmdb.WatchListController;
import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class Database {
    public static final String DB_URL = "jdbc:h2:file: ./db/watchlistsdb";
    public static final String user = "user";
    public static final String password = "password";

    private static ConnectionSource connectionSource;

    private Dao<WatchlistMovieEntity, Long> dao;

    private static Database instance;

    private Database() throws DatabaseException {
        try {
            createConnectionSource();
            dao = DaoManager.createDao(connectionSource, WatchlistMovieEntity.class);
            createTables();
        } catch (SQLException e) {
            WatchListController.showInfoMessage("Database connection Error occurred" + e.getMessage());
            throw new DatabaseException("Connection error", e);
        }
    }

    public static Database getDatabase() throws DatabaseException {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public Dao<WatchlistMovieEntity, Long> getDao() {
        return dao;
    }

    public static void createTables() throws DatabaseException {
        try {
            TableUtils.createTableIfNotExists(connectionSource, WatchlistMovieEntity.class);
        }catch (SQLException | IllegalArgumentException e) {
            throw new DatabaseException("Error while creating tables.", e);
        }
    }

    private static void createConnectionSource() throws DatabaseException {

        try {
            connectionSource = new JdbcConnectionSource(DB_URL, user, password);

        } catch (SQLException | IllegalArgumentException e) {
            throw new DatabaseException("Error while creating connection source", e);
        }
    }
}
