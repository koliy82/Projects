package com.koliy82.prtt.api;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("food")
    Call<ArrayList<FoodJSON>> getList();

    @POST("food")
    Call<FoodJSON> createFood(@Body FoodJSON food);

    @DELETE("food/{id}")
    Call<FoodJSON> deleteFood(@Path("id") int itemId);
}
