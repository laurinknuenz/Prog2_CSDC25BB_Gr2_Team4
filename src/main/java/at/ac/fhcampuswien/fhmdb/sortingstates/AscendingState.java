package at.ac.fhcampuswien.fhmdb.sortingstates;

import at.ac.fhcampuswien.fhmdb.HomeController;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.FXCollections;

public class AscendingState extends SortingState {
    public AscendingState(HomeController hc) {
        super(hc);
    }

    @Override
    public void sortObservableList() {
        homeController.movieListView.setItems(FXCollections.observableList(Movie.sortMovies(true, homeController.movieListView.getItems())));
        homeController.sortBtn.setText("Sort (desc)");
    }

    @Override
    public void changeState() {
        homeController.setSortingState(new DescendingState(homeController));
    }
}
