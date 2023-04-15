package at.ac.fhcampuswien.fhmdb.dal;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.google.gson.GsonBuilder;
import okhttp3.*;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public final class MovieApi {
    private static final String movieApiUrl = "http://localhost:8080";
    private static final OkHttpClient client = new OkHttpClient();

    public static Movie[] getAllMovies() throws IOException {
        return enqueueRequestCall(movieApiUrl + "/movies");
    }

    public static Movie[] getMoviesByParameter(String query, Genre genre, int year, double rating) throws IOException {
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(movieApiUrl + "/movies")).newBuilder();
        if (query != null) urlBuilder.addQueryParameter("query", query);
        if (genre != null) urlBuilder.addQueryParameter("genre", genre.name());
        if (year != 0) urlBuilder.addQueryParameter("releaseYear", String.valueOf(year));
        if (rating != 0) urlBuilder.addQueryParameter("ratingFrom", String.valueOf(rating));

        String url = urlBuilder.build().toString();

        return enqueueRequestCall(url);
    }

    private static Movie[] enqueueRequestCall(String apiUrl) throws IOException {
        Movie[] requestedMovies;

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Movie.class, new MovieAdapter());
        Gson gson = gsonBuilder.create();

        Request request = new Request.Builder()
                .url(apiUrl)
                .build();

        Call call = client.newCall(request);

        //SYNCHRONOUS Method
            Response response = call.execute();
            String responseData = Objects.requireNonNull(response.body()).string();
            requestedMovies = gson.fromJson(responseData, Movie[].class);
            return requestedMovies;

        //ASYNC Method, couldn't get movies out of the callback to return them.
        /* call.enqueue(new Callback() {
            Movie[] allMovies;
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseData = Objects.requireNonNull(response.body()).string();
                allMovies = gson.fromJson(responseData, Movie[].class);
            }
        }); */
    }

    //Just a method I created to test the ApiClass
    public static void main(String[] args) throws IOException {
        Movie[] movies = getAllMovies();
    }
}
