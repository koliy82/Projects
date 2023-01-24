package com.koliy82.prtt.Recycler;

import com.koliy82.prtt.api.FoodJSON;

import java.util.ArrayList;

public class FoodList {
    public ArrayList<FoodJSON> list = new ArrayList<FoodJSON>();;

    public void add(String name, String detail, String img, String student, int score) {
        list.add(new FoodJSON(name, detail, img, student, score));
    }

    public void add(int id, String name, String detail, String img, String student, int score) {
        list.add(new FoodJSON(id, name, detail, img, student, score));
    }
}
