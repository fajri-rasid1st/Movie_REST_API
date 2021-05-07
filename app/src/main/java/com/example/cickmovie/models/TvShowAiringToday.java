package com.example.cickmovie.models;

import com.google.gson.annotations.SerializedName;

public class TvShowAiringToday {
    // attribute
    private String id;
    private String overview;

    @SerializedName("name")
    private String title;

    @SerializedName("vote_average")
    private String voteAverage;

    @SerializedName("poster_path")
    private String imgUrl;

    // constructor
    public TvShowAiringToday() {
    }

    // method
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
