package at.ac.fhcampuswien.fhmdb;


import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import javafx.util.Callback;

import java.util.HashMap;
import java.util.Map;

public class MyFactory implements Callback<Class<?>, Object> {
    private static MyFactory instance;

    protected MyFactory() {
    }

    public static synchronized MyFactory getInstance() {
        if (instance == null) {
            instance = new MyFactory();
        }
        return instance;
    }

    @Override
    public Object call(Class<?> aClass) {
        try {
            if (aClass == HomeController.class) {
                return HomeController.getInstance();
            } else if (aClass == WatchListController.class) {
                return WatchListController.getInstance();
            }
            return aClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
