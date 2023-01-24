package com.koliy82.pr7.models;

import com.google.gson.annotations.SerializedName;
import com.koliy82.pr7.models.item2.Item;

import java.util.ArrayList;

public class FilmVideos {
    private int total;
    private ArrayList<Item> items;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
}

