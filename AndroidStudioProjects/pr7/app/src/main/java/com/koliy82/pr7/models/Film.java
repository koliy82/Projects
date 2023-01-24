package com.koliy82.pr7.models;

import java.util.ArrayList;

public class Film {
    private ArrayList<Country> countries;
    private int filmId;
    private String filmLength;
    private ArrayList<Genre> genres;
    private String nameEn;
    private String nameRu;
    private String posterUrl;
    private String posterUrlPreview;
    private String rating;
    private Object ratingChange;
    private int ratingVoteCount;
    private String year;

    public ArrayList<Country> getCountries() {
        return countries;
    }

    public void setCountries(ArrayList<Country> countries) {
        this.countries = countries;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public String getFilmLength() {
        return filmLength;
    }

    public void setFilmLength(String filmLength) {
        this.filmLength = filmLength;
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<Genre> genres) {
        this.genres = genres;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameRu() {
        return nameRu;
    }

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getPosterUrlPreview() {
        return posterUrlPreview;
    }

    public void setPosterUrlPreview(String posterUrlPreview) {
        this.posterUrlPreview = posterUrlPreview;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Object getRatingChange() {
        return ratingChange;
    }

    public void setRatingChange(Object ratingChange) {
        this.ratingChange = ratingChange;
    }

    public int getRatingVoteCount() {
        return ratingVoteCount;
    }

    public void setRatingVoteCount(int ratingVoteCount) {
        this.ratingVoteCount = ratingVoteCount;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
