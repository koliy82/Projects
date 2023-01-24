package com.koliy82.pr7.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIBUILDER {
    private static String URL = "https://kinopoiskapiunofficial.tech/api/v2.2/films/";

    public static Retrofit buildRequest(){
        //OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();

        return retrofit;
    }
}
