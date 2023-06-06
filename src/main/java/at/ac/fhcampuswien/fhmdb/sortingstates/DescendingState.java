package at.ac.fhcampuswien.fhmdb.sortingstates;

import at.ac.fhcampuswien.fhmdb.HomeController;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.FXCollections;

public class DescendingState extends SortingState {
    public DescendingState(HomeController hc) {
        super(hc);
    }

    @Override
    public void sortObservableList() {
        homeController.movieListView.setItems(FXCollections.observableList(Movie.sortMovies(false, homeController.movieListView.getItems())));
        homeController.sortBtn.setText("Sort (asc)");
    }

    @Override
    public void changeState() {
        homeController.setSortingState(new AscendingState(homeController));
    }
}
