package com.hanabi.apircvdemo.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NewsResponse {

    @SerializedName("articles")
    ArrayList<News> news;

    public ArrayList<News> getNews() {
        return news;
    }
}
