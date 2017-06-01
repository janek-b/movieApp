package com.example.guest.movieapp.models;


public class Movie {
    String title;
    String poster_path;
    String backdrop_path;
    String overview;
    String release_date;
    int id;

    public Movie() {}

    public Movie(String title, String poster_path, String backdrop_path, String overview, String release_date, int id) {
        this.title = title;
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
        this.overview = overview;
        this.release_date = release_date;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public int getId() {
        return id;
    }
}
