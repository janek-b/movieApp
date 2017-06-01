package com.example.guest.movieapp.services;


import com.example.guest.movieapp.Constants;

import okhttp3.HttpUrl;

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
}
