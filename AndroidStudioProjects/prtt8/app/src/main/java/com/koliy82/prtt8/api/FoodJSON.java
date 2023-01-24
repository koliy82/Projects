package com.koliy82.prtt8.api;

import com.google.gson.annotations.SerializedName;

public class FoodJSON {
    @SerializedName("id_food")
    private int id;
    @SerializedName("food_name")
    private String name;
    @SerializedName("food_detail")
    private String detail;
    @SerializedName("student_fio")
    private String student;
    @SerializedName("student_score")
    private Integer score;

    public FoodJSON(String name, String detail,  String student, Integer score) {
        this.id = 0;
        this.name = name;
        this.detail = detail;
        this.student = student;
        if(score>10) this.score = 1;
        else this.score = score;
    }

    public FoodJSON(int id,String name, String detail, String student, Integer score) {
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.student = student;
        if(score>10) this.score = 1;
        else this.score = score;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDetail() {
        return detail;
    }

    public String getStudent() {
        return student;
    }

    public Integer getScore() {
        return score;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
