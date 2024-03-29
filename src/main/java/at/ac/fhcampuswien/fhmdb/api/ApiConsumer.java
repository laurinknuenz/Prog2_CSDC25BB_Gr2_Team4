package at.ac.fhcampuswien.fhmdb.api;

import at.ac.fhcampuswien.fhmdb.HomeController;
import at.ac.fhcampuswien.fhmdb.exceptions.MovieApiException;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ApiConsumer {
    private static final String API = "https://prog2.fh-campuswien.ac.at/movies";
    private static final String ERROR_MESSAGE = "Failed to query movies from API.";
    private static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();

    // https://howtodoinjava.com/gson/gson/ last visited 16/04/2023.
    private static final Gson GSON = new GsonBuilder()
            .enableComplexMapKeySerialization()
            .serializeNulls()
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .setPrettyPrinting()
            .setVersion(1.0)
            .create();

    public List<Movie> getAllMovies() {
        return getMovies(Map.of());
    }

    public List<Movie> getMovies(final Map<String, String> queryParams) throws MovieApiException{
        var urlBuilder = new HttpUrl.Builder()
                .scheme("https")
                .host("prog2.fh-campuswien.ac.at")
                .addPathSegment("movies");

        queryParams.forEach(urlBuilder::addQueryParameter);

        var request = new Request.Builder()
                .url(urlBuilder.build())
                .header("User-Agent", "ok-http-agent-v4.10.0")
                .method("GET", null)
                .build();

        try {
            return queryMoviesFromApi(request);
        } catch (Exception e) {
            HomeController.showInfoMessage(e.getMessage());
            throw new MovieApiException(ERROR_MESSAGE, e);
        }
    }

    private List<Movie> queryMoviesFromApi(Request request) throws MovieApiException {
        try (Response response = HTTP_CLIENT.newCall(request).execute()) {
            Movie[] movies = GSON.fromJson(response.body().charStream(), Movie[].class);
            return Arrays.asList(movies);
        } catch (IOException e) {
            HomeController.showInfoMessage(e.getMessage());
            throw new MovieApiException(ERROR_MESSAGE, e);
        }
    }
}
