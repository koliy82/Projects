package com.koliy82.pr7.models;

import java.util.ArrayList;

public class Top {
    private ArrayList<Film> films;
    private int pagesCount;

    public ArrayList<Film> getFilms() {
        return films;
    }

    public void setFilms(ArrayList<Film> films) {
        this.films = films;
    }

    public int getPagesCount() {
        return pagesCount;
    }

    public void setPagesCount(int pagesCount) {
        this.pagesCount = pagesCount;
    }
}
