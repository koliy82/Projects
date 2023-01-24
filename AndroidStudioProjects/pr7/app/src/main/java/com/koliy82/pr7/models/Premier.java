package com.koliy82.pr7.models;

import java.util.ArrayList;

public class Premier {
    private ArrayList<Item> items;
    private int total;

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}