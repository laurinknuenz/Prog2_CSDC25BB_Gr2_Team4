package at.ac.fhcampuswien.fhmdb.api;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

public class ApiConsumer {
    private static final String API = "https://prog2.fh-campuswien.ac.at/movies";
    private static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();

    // https://howtodoinjava.com/gson/gson/ last visited 16/04/2023.
    private static final Gson GSON = new GsonBuilder()
            .enableComplexMapKeySerialization()
            .serializeNulls()
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .setPrettyPrinting()
            .setVersion(1.0)
            .create();

    public Collection<Movie> getAllMovies() {
        Request request = new Request.Builder()
                .url(API)
                .header("User-Agent", "ok-http-agent-v4.10.0")
                .method("GET", null)
                .build();

        try (Response response = HTTP_CLIENT.newCall(request).execute()) {
            Movie[] movies = GSON.fromJson(response.body().charStream(), Movie[].class);
            return Arrays.asList(movies);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
