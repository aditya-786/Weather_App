package com.example.weatherapp;

import com.google.gson.annotations.SerializedName;

public class Example {

    @SerializedName("main")
    Main main;
    Users users;

    @SerializedName("wind")
    Wind wind;

    @SerializedName("sys")
    Sys sys;

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }
}
