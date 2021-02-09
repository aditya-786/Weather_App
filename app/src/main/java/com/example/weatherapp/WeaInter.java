package com.example.weatherapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeaInter {


    @GET("weather")
    Call<Example> getWeather(@Query("q") String cityName, @Query("appid") String apikey);

}
