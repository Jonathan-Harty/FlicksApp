package com.example.jonathan.flicksapp;

import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.jonathan.flicksapp.adapters.MoviesAdapter;
import com.example.jonathan.flicksapp.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private static final String MOVIE_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    private List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movies = new ArrayList<>();

        RecyclerView rvMovieList = findViewById(R.id.rvMovieList);
        final MoviesAdapter adapter = new MoviesAdapter(this, movies);
        rvMovieList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvMovieList.setAdapter(adapter);

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(MOVIE_URL, new JsonHttpResponseHandler() {
           @Override
           public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
               try {
                   JSONArray jsonMovieArray = response.getJSONArray("results");
                   movies.addAll(Movie.fromJsonArray(jsonMovieArray));
                   adapter.notifyDataSetChanged();
               } catch (JSONException e) {
                   e.printStackTrace();
               }
           }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }


}
