package at.ac.fhcampuswien.fhmdb;


import javafx.util.Callback;

public class MyFactory implements Callback<Class<?>, Object> {
    protected static final MyFactory INSTANCE = new MyFactory();

    private MyFactory() {
    }

    @Override
    public Object call(Class<?> aClass) {
        if (aClass == HomeController.class) {
            return HomeController.INSTANCE;
        } else if (aClass == WatchListController.class) {
            return WatchListController.INSTANCE;
        } else {
            throw new RuntimeException("Unknown type:" + aClass);
        }
    }
}
