package com.example.guest.movieapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.guest.movieapp.R;
import com.example.guest.movieapp.adapters.MovieSearchAdapter;
import com.example.guest.movieapp.models.Movie;
import com.example.guest.movieapp.services.MovieService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class SearchResultsActivity extends AppCompatActivity {
    @Bind(R.id.searchResultView) RecyclerView mSearchView;

    private MovieSearchAdapter mAdapter;

    private final CompositeDisposable disposable = new CompositeDisposable();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String searchInput = intent.getStringExtra("search");
        getMovies(searchInput);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }

    private void getMovies(String searchInput) {
        disposable.add(MovieService.makeApiCall(MovieService.buildSearchlUrl(searchInput))
            .subscribeOn(Schedulers.io())
            .map(new Function<String, ArrayList<Movie>>() {
                @Override public ArrayList<Movie> apply(String string) {
                    ArrayList<Movie> movies = new ArrayList<Movie>();
                    JsonArray results = new Gson().fromJson(string, JsonObject.class).getAsJsonArray("results");
                    for (JsonElement item : results) {
                        movies.add(new Gson().fromJson(item.toString(), Movie.class));
                    }
                    return movies;
                }
            })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new DisposableObserver<ArrayList<Movie>>() {
                @Override public void onNext(@NonNull ArrayList<Movie> s) {

                }
                @Override public void onError(@NonNull Throwable e) { e.printStackTrace(); }
                @Override public void onComplete() {}
            }));
    }
}
