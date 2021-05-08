package com.example.cickmovie.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.cickmovie.R;
import com.example.cickmovie.activities.MainActivity;
import com.example.cickmovie.activities.MovieDetailActivity;
import com.example.cickmovie.adapters.GridAdapter;
import com.example.cickmovie.models.MovieNowPlaying;
import com.example.cickmovie.models.MovieNowPlayingResponse;
import com.example.cickmovie.networks.Const;
import com.example.cickmovie.networks.MovieApiClient;
import com.example.cickmovie.networks.MovieApiInterface;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieFragment extends Fragment implements GridAdapter.OnItemClick, SwipeRefreshLayout.OnRefreshListener {
    // private static final String TAG = "MovieFragment";
    private SwipeRefreshLayout srlMovie;
    private LinearProgressIndicator lpiMovie;
    private ConstraintLayout clMovieError;
    private RecyclerView recyclerView;
    private GridAdapter gridAdapter;
    private List<MovieNowPlaying> movieNowPlayings;
    private boolean isResponseAlreadySuccess = false;

    public static MovieFragment newInstance() {
        MovieFragment movieFragment = new MovieFragment();
        Bundle args = new Bundle();

        args.putString(MainActivity.TOOLBAR_TITLE, "Movie");
        movieFragment.setArguments(args);

        return movieFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);

        srlMovie = view.findViewById(R.id.srl_movie);
        lpiMovie = view.findViewById(R.id.lpi_movie);
        clMovieError = view.findViewById(R.id.cl_movie_error);
        recyclerView = view.findViewById(R.id.rv_movie);

        srlMovie.setOnRefreshListener(this);
        loadData();

        return view;
    }

    private void loadData() {
        MovieApiInterface movieApiInterface = MovieApiClient.getRetrofitMovies()
                .create(MovieApiInterface.class);

        Call<MovieNowPlayingResponse> responseCall = movieApiInterface
                .getNowPlaying(Const.API_KEY);

        responseCall.enqueue(new Callback<MovieNowPlayingResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieNowPlayingResponse> call,
                                   @NonNull Response<MovieNowPlayingResponse> response) {
                if (response.body() != null) {
                    if (response.isSuccessful() && response.body().getNowPlayings() != null) {
                        isResponseAlreadySuccess = true;
                        movieNowPlayings = response.body().getNowPlayings();
                        gridAdapter = new GridAdapter(movieNowPlayings, MovieFragment.this);

                        recyclerView.setAdapter(gridAdapter);
                        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

                        lpiMovie.hide();
                        clMovieError.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(getActivity(), "Request Failed", Toast.LENGTH_SHORT).show();
                        if (!isResponseAlreadySuccess) clMovieError.setVisibility(View.VISIBLE);
                        new Handler().postDelayed(() -> lpiMovie.hide(), 3000);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieNowPlayingResponse> call, @NonNull Throwable t) {
                // Log.d(TAG, t.getMessage());
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                if (!isResponseAlreadySuccess) clMovieError.setVisibility(View.VISIBLE);
                new Handler().postDelayed(() -> lpiMovie.hide(), 3000);
            }
        });
    }

    @Override
    public void onClick(int position) {
        Intent movieDetailActivity = new Intent(getActivity(), MovieDetailActivity.class);

        movieDetailActivity.putExtra("ID", movieNowPlayings.get(position).getId());
        movieDetailActivity.putExtra("TITLE", movieNowPlayings.get(position).getTitle());

        startActivity(movieDetailActivity);
    }

    @Override
    public void onRefresh() {
        lpiMovie.show();
        loadData();
        new Handler().postDelayed(() -> srlMovie.setRefreshing(false), 1000);
    }
}