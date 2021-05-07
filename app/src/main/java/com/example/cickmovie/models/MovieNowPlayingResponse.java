package com.example.cickmovie.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieNowPlayingResponse {
    @SerializedName("results")
    @Expose
    private final List<MovieNowPlaying> nowPlayings;

    public MovieNowPlayingResponse(List<MovieNowPlaying> nowPlayings) {
        this.nowPlayings = nowPlayings;
    }

    public List<MovieNowPlaying> getNowPlayings() {
        return nowPlayings;
    }
}
