package com.hanabi.apircvdemo.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class News {

    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo
    @SerializedName("title")
    private String title;
    @ColumnInfo
    @SerializedName("description")
    private String description;
    @ColumnInfo
    @SerializedName("url")
    private String url;
    @ColumnInfo
    @SerializedName("urlToImage")
    private String imageUrl;
    @ColumnInfo
    @SerializedName("publishedAt")
    private String publishedAt;
    @ColumnInfo
    private Boolean bookmarked = false;
    @ColumnInfo
    private String pathSave;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Boolean getBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(Boolean bookmarked) {
        this.bookmarked = bookmarked;
    }

    public String getPathSave() {
        return pathSave;
    }

    public void setPathSave(String pathSave) {
        this.pathSave = pathSave;
    }
}
