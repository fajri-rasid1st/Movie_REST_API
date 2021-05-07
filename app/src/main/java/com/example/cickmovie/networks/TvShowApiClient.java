package com.example.cickmovie.networks;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TvShowApiClient {
    private static Retrofit retrofitTvShows, retrofitTvShowDetail;

    public static Retrofit getRetrofitTvShows() {
        if (retrofitTvShows == null) {
            retrofitTvShows = new Retrofit.Builder().baseUrl(Const.TV_SHOW_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofitTvShows;
    }

    public static Retrofit getRetrofitTvShowDetail() {
        if (retrofitTvShowDetail == null) {
            retrofitTvShowDetail = new Retrofit.Builder().baseUrl(Const.BASE_URL_DETAIL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofitTvShowDetail;
    }
}
