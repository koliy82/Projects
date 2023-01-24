package com.koliy82.pr7.models;

import java.util.ArrayList;

public class Item {
    private ArrayList<Country> countries;
    private int duration;
    private ArrayList<Genre> genres;
    private int kinopoiskId;
    private String nameEn;
    private String nameRu;
    private String posterUrl;
    private String posterUrlPreview;
    private String premiereRu;
    private int year;

    public ArrayList<Country> getCountries() {
        return countries;
    }

    public void setCountries(ArrayList<Country> countries) {
        this.countries = countries;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<Genre> genres) {
        this.genres = genres;
    }

    public int getKinopoiskId() {
        return kinopoiskId;
    }

    public void setKinopoiskId(int kinopoiskId) {
        this.kinopoiskId = kinopoiskId;
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

    public String getPremiereRu() {
        return premiereRu;
    }

    public void setPremiereRu(String premiereRu) {
        this.premiereRu = premiereRu;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
