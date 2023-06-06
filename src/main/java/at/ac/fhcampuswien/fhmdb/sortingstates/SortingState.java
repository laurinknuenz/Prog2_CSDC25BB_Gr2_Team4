package at.ac.fhcampuswien.fhmdb.sortingstates;

import at.ac.fhcampuswien.fhmdb.HomeController;

public abstract class SortingState {
    protected HomeController homeController;

    protected SortingState(HomeController hc) {
        this.homeController = hc;
    }

    public abstract void sortObservableList();
    public abstract void changeState();
}