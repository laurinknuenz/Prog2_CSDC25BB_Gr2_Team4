package at.ac.fhcampuswien.fhmdb.dal;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends TypeAdapter<Movie> {

    @Override
    public void write(JsonWriter jsonWriter, Movie movie) throws IOException {
        //Not needed, but required to override
    }

    @Override
    public Movie read(JsonReader jsonReader) throws IOException {
        String title = null;
        String description = null;
        List<Genre> genres = null;

        jsonReader.beginObject();
        while(jsonReader.hasNext()){
            String name = jsonReader.nextName();
            switch (name) {
                case "title" -> title = jsonReader.nextString();
                case "description" -> description = jsonReader.nextString();
                case "genres" -> {
                    genres = new ArrayList<>();
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        genres.add(Genre.valueOf(jsonReader.nextString()));
                    }
                    jsonReader.endArray();
                }
                default -> jsonReader.skipValue();
            }
        }
        jsonReader.endObject();

        return new Movie(title, description, genres);
    }
}
