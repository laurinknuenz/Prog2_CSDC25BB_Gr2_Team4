package at.ac.fhcampuswien.fhmdb.ui;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.stream.Collectors;

public class MovieCell extends ListCell<Movie> {
    private final Label title = new Label();
    private final Label detail = new Label();
    private final Label rating = new Label();
    private final Label actors = new Label();

    private final VBox layout = new VBox(title, detail, rating, actors);

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
            rating.setText("Rating: "+ movie.getRating().toString()+ " | Release year: "+ movie.getReleaseYear());
            actors.setText("Main cast: "+ movie.getMainCast().stream().collect(Collectors.joining(", ")));

            // color scheme
            title.getStyleClass().add("text-yellow");
            detail.getStyleClass().add("text-white");
            rating.getStyleClass().add("text-white");
            actors.getStyleClass().add("text-white");
            layout.setBackground(new Background(new BackgroundFill(Color.web("#454545"), null, null)));

            // layout
            title.fontProperty().set(title.getFont().font(20));
            detail.setMaxWidth(this.getScene().getWidth() - 30);
            detail.setWrapText(true);
            rating.fontProperty().set(title.getFont().font(12));
            actors.fontProperty().set(title.getFont().font(12));
            layout.setPadding(new Insets(10));
            layout.spacingProperty().set(10);
            layout.alignmentProperty().set(javafx.geometry.Pos.CENTER_LEFT);
            setGraphic(layout);
        }
    }
}

