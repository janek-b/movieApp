package com.example.guest.movieapp.services;


import com.example.guest.movieapp.Constants;

import java.io.IOException;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MovieService {

    public static String buildDetailUrl(String segment, int id) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.BASE_URL + String.format(segment, id)).newBuilder();
        urlBuilder.addQueryParameter(Constants.API_PARAM, Constants.API_KEY);
        return urlBuilder.build().toString();
    }
    public static String buildSearchlUrl(String search) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.BASE_URL + Constants.SEARCH_MOVIE).newBuilder();
        urlBuilder.addQueryParameter(Constants.API_PARAM, Constants.API_KEY);
        urlBuilder.addQueryParameter(Constants.SEARCH_PARAM, search);
        return urlBuilder.build().toString();
    }

    public static Observable<String> makeApiCall(final String url) {
        final OkHttpClient client = new OkHttpClient.Builder().build();
        return Observable.defer(new Callable<ObservableSource<? extends String>>() {
            @Override
            public ObservableSource<? extends String> call() throws Exception {
                try {
                    return Observable.just(client.newCall(new Request.Builder().url(url).build()).execute().body().string());
                } catch (IOException e) { return Observable.error(e); }
            }
        });
    }
}
