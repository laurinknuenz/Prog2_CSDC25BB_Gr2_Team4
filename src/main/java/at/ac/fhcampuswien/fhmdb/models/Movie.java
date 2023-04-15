package at.ac.fhcampuswien.fhmdb.models;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Movie {
    private String title;
    private String description;

    private List<Genre> genres;

    public Movie(String title, String description, Genre... genres) {
        this.title = title;
        this.description = description;
        this.genres = List.of(genres);
    }

    public Movie(String title, String description, List<Genre> genres) {
        this.title = title;
        this.description = description;
        this.genres = genres;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public static List<Movie> initializeMovies() {
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie("The Incredibles", "A family of superheroes must come out of retirement to save the world from a vengeful former fan.", Genre.FAMILY, Genre.ACTION, Genre.ANIMATION));
        movies.add(new Movie("Finding Nemo", "A clownfish named Marlin searches for his son Nemo after he is captured by a diver and taken to an aquarium in a dentist's office.",Genre.FAMILY, Genre.ANIMATION));
        movies.add(new Movie("Star Wars: Episode 1", "The story of young Anakin Skywalker's journey as a slave boy, his discovery by the Jedi, and the rise of the Sith threat", Genre.SCIENCE_FICTION, Genre.WAR));
        movies.add(new Movie("Transformers 3", "Autobots and Decepticons battle for a Cybertronian spacecraft on the moon.", Genre.ACTION, Genre.ROMANCE, Genre.ADVENTURE));
        movies.add(new Movie("Encanto",  "A movie about a magical Colombian family, where the youngest member,must save the family's enchanted house.", Genre.ANIMATION, Genre.DRAMA, Genre.FANTASY));
        movies.add(new Movie("The Matrix", "A hacker discovers that reality is a simulation created by  machines, and joins a rebellion to fight.", Genre.ACTION, Genre.THRILLER, Genre.MYSTERY));
        movies.add(new Movie("The Fast and The Furious", "A group of street racers and their allies who engage in heists and various missions involving high-speed car chases.", Genre.DRAMA, Genre.ACTION, Genre.CRIME));
        movies.add(new Movie("Cool Runnings", "A Jamaican bobsled team's underdog journey to compete in the Winter Olympics.", Genre.BIOGRAPHY, Genre.DOCUMENTARY, Genre.SPORT));
        movies.add(new Movie("Django Unchained", "Freed slave Django partners with a bounty hunter to rescue his wife from a cruel plantation owner.", Genre.ACTION, Genre.HISTORY, Genre.WESTERN));
        movies.add(new Movie("Paranormal Activity", "A couple sets up cameras to document strange occurrences in their house, which escalate into terrifying supernatural events.", Genre.THRILLER, Genre.HORROR, Genre.MYSTERY));
        return movies;
    }

    public static List<Movie> filterMoviesByGenre(Genre genre, List<Movie> movies) {
        List<Movie> filteredMovies = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.getGenres().contains(genre)) {
                filteredMovies.add(movie);
            }
        }
        return filteredMovies;
    }

    public static List<Movie> filterMovies(String searchTerm, List<Movie> movies) {
        List<Movie> filteredMovies = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.getTitle().toLowerCase().contains(searchTerm.toLowerCase()) || movie.getDescription().toLowerCase().contains(searchTerm.toLowerCase())) {
                filteredMovies.add(movie);
            }
        }
        return filteredMovies;
    }

    public static List<Movie> sortMovies(List<Movie> movies, boolean ascending){
        Comparator<Movie> titleComparator;
        if(ascending){
            titleComparator = Comparator.comparing(Movie::getTitle);
        }
        else{
            titleComparator = Comparator.comparing(Movie::getTitle).reversed();
        }
        movies.sort(titleComparator);
        return movies;
    }
}
