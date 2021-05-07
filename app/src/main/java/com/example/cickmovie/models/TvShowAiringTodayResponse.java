package com.example.cickmovie.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvShowAiringTodayResponse {
    @SerializedName("results")
    @Expose
    private final List<TvShowAiringToday> airingTodays;

    public TvShowAiringTodayResponse(List<TvShowAiringToday> airingTodays) {
        this.airingTodays = airingTodays;
    }

    public List<TvShowAiringToday> getAiringTodays() {
        return airingTodays;
    }
}
