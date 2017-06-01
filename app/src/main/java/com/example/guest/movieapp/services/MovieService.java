package com.example.guest.movieapp.services;


import com.example.guest.movieapp.Constants;

import okhttp3.HttpUrl;

public class MovieService {

    public static String buildSimilarUrl(int id) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.BASE_URL).newBuilder();
        urlBuilder.addPathSegment(String.format(Constants.SIMILAR, id));
        urlBuilder.addQueryParameter(Constants.API_PARAM, Constants.API_KEY);
        return urlBuilder.build().toString();
    }
}
