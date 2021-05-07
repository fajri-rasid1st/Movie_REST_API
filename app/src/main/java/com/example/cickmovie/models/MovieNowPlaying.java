package com.example.cickmovie.models;

import com.google.gson.annotations.SerializedName;

public class MovieNowPlaying {
    // attribute
    private String id;
    private String title;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("vote_average")
    private String voteAverage;

    @SerializedName("poster_path")
    private String imgUrl;

    // constructor
    public MovieNowPlaying() {
    }

    // method
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate.split("-")[0];
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;

    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
