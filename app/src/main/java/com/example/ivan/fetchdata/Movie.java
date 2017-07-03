package com.example.ivan.fetchdata;


public class Movie {

    private String title;
    private String imdbRating;

    public Movie(String title, String imdbRating) {
        this.title = title;
        this.imdbRating = imdbRating;
    }

    public String getTitle() {
        return title;
    }

    public String getImdbRating() {
        return imdbRating;
    }
}
