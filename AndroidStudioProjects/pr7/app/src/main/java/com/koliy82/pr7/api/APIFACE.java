package com.koliy82.pr7.api;

import com.koliy82.pr7.models.Premier;
import com.koliy82.pr7.models.Top;
import com.koliy82.pr7.models.FilmVideos;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIFACE {
    @Headers("X-API-KEY: f2fac878-8cc8-4d84-b53f-3602292d5e5d")
    @GET("premieres")
    Call<Premier> getPremierList(@Query("year") int year, @Query("month") String month);

    @Headers("X-API-KEY: f2fac878-8cc8-4d84-b53f-3602292d5e5d")
    @GET("top")
    Call<Top> getTopList(@Query("page") int page);

    @Headers("X-API-KEY: f2fac878-8cc8-4d84-b53f-3602292d5e5d")
    @GET("{id}/videos")
    Call<FilmVideos> getVideosByFilmID(@Path("id") int filmId);
}
