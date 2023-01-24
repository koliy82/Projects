package com.koliy82.prtt.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestBuilder {
    private static String URL = "https://mptenglishlearning.ru/api/";
    private static Retrofit retrofit = null;

    public static Retrofit buildRequest(){
        return (retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build());
    }
}
