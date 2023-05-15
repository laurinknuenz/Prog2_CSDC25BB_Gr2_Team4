package at.ac.fhcampuswien.fhmdb.ui;

import at.ac.fhcampuswien.fhmdb.interfaces.ClickEventHandler;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.stream.Collectors;

public class MovieCell extends ListCell<Movie> {
    private final Label title = new Label();
    private final Label detail = new Label();
    private final Label rating = new Label();
    private final Label actors = new Label();
    private final Button buttonWatchlist = new Button();
    private final Button buttonDetails = new Button("Expand Details");
    private final HBox buttons = new HBox(10, buttonWatchlist, buttonDetails);
    private final VBox layout = new VBox(title, buttons);

    private boolean detailsAreVisible = false;

    public MovieCell(Boolean isWatchList, ClickEventHandler<Movie> addToWatchListClicked, ClickEventHandler<MovieCell> expandDetailsClicked) {
        buttonWatchlist.setOnMouseClicked(mouseEvent -> {
            addToWatchListClicked.onClick(getItem());
        });
        buttonDetails.setOnMouseClicked(mouseEvent -> {
            expandDetailsClicked.onClick(this);
        });
        buttonWatchlist.setText(isWatchList ? "Remove from Watch List" : "Add to Watch List");
    }

    @Override
    protected void updateItem(Movie movie, boolean empty) {
        super.updateItem(movie, empty);

        if (empty || movie == null) {
            setText(null);
            setGraphic(null);
        } else {
            this.getStyleClass().add("movie-cell");
            title.setText(movie.getTitle());
            detail.setText(
                    (movie.getDescription() != null)
                            ? (movie.getDescription() + "\n\n " +
                            movie.getGenres().stream().map(Enum::name).sorted().reduce((a, b) -> a + ", " + b).orElse(""))
                            : "No description available"
            );
            rating.setText("Rating: " + movie.getRating().toString() + " | Release year: " + movie.getReleaseYear());
            actors.setText("Main cast: " + movie.getMainCast().stream().collect(Collectors.joining(", ")));

            // color scheme
            title.getStyleClass().add("text-yellow");
            detail.getStyleClass().add("text-white");
            rating.getStyleClass().add("text-white");
            actors.getStyleClass().add("text-white");
            layout.setBackground(new Background(new BackgroundFill(Color.web("#454545"), null, null)));
            buttonWatchlist.getStyleClass().add("my-button-style");
            buttonDetails.getStyleClass().add("my-button-style");

            // layout
            title.fontProperty().set(title.getFont().font(20));
            detail.setMaxWidth(this.getScene().getWidth() - 30);
            detail.setWrapText(true);
            rating.fontProperty().set(title.getFont().font(12));
            actors.fontProperty().set(title.getFont().font(12));
            layout.setPadding(new Insets(10));
            layout.spacingProperty().set(10);
            layout.alignmentProperty().set(Pos.CENTER_LEFT);
            buttons.setAlignment(Pos.CENTER_RIGHT);
            setGraphic(layout);
        }
    }

    public void expandDetails() {
        detailsAreVisible = !detailsAreVisible;
        if (detailsAreVisible) {
            buttonDetails.setText("Collapse details");
            layout.getChildren().remove(buttons);
            layout.getChildren().addAll(detail, rating, actors, buttons);

        } else {
            buttonDetails.setText("Expand details");
            layout.getChildren().removeAll(detail, rating, actors);
        }
    }
}

