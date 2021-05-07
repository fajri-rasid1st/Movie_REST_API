package com.example.cickmovie.networks;

import com.example.cickmovie.models.Movie;
import com.example.cickmovie.models.MovieNowPlayingResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApiInterface {
    @GET("now_playing")
    Call<MovieNowPlayingResponse> getNowPlaying(@Query("api_key") String apiKey);

    @GET("movie/{movie_id}")
    Call<Movie> getMovie(@Path("movie_id") String id, @Query("api_key") String apiKey);
}
