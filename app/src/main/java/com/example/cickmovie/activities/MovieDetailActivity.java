package com.example.cickmovie.activities;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.cickmovie.R;
import com.example.cickmovie.models.Movie;
import com.example.cickmovie.models.Genres;
import com.example.cickmovie.networks.Const;
import com.example.cickmovie.networks.MovieApiClient;
import com.example.cickmovie.networks.MovieApiInterface;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity implements View.OnClickListener {
    // private static final String TAG = "MovieDetailActivity";
    private LinearProgressIndicator lpiMovieDetail;
    private MaterialButton btnFavorite;
    private Movie movie;
    private List<Genres> movieGenres;
    private boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        lpiMovieDetail = findViewById(R.id.lpi_movie_detail);
        btnFavorite = findViewById(R.id.btn_favorite);
        btnFavorite.setOnClickListener(this);

        Toolbar tbDetail = findViewById(R.id.tb_detail);
        setSupportActionBar(tbDetail);
        tbDetail.setTitleTextAppearance(this, R.style.WhiteTextAppearance);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("TITLE"));

        loadData(getIntent().getStringExtra("ID"));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_favorite) btnFavoriteHandler();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        for (int i = 0; i < menu.size(); i++) {
            Drawable drawable = menu.getItem(i).getIcon();

            if (drawable != null) {
                drawable.mutate();
                drawable.setColorFilter(getResources()
                        .getColor(R.color.textOnPrimary), PorterDuff.Mode.SRC_ATOP);
            }
        }

        return true;
    }

    private void loadData(String movieId) {
        MovieApiInterface movieApiInterface = MovieApiClient.getRetrofitMovieDetail()
                .create(MovieApiInterface.class);

        Call<Movie> movieCall = movieApiInterface
                .getMovie(movieId, Const.API_KEY);

        movieCall.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(@NonNull Call<Movie> call, @NonNull Response<Movie> response) {
                if (response.isSuccessful() && response.body() != null) {
                    movie = response.body();

                    if (response.body().getGenres() != null) {
                        movieGenres = movie.getGenres();
                        setDetailActivityContent();
                    }

                    lpiMovieDetail.setVisibility(View.GONE);
                } else {
                    Toast.makeText(MovieDetailActivity.this, "Request Failed", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(() -> lpiMovieDetail.setVisibility(View.GONE), 3000);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Movie> call, @NonNull Throwable t) {
                // Log.d(TAG, t.getMessage());
                Toast.makeText(MovieDetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(() -> lpiMovieDetail.setVisibility(View.GONE), 3000);
            }
        });
    }

    private void setDetailActivityContent() {
        TextView tvTitle = findViewById(R.id.tv_movie_title_detail);
        TextView tvRuntimeDetail = findViewById(R.id.tv_movie_runtime_detail);
        TextView tvVoteCount = findViewById(R.id.tv_movie_vote_count_detail);
        TextView tvFullTitle = findViewById(R.id.tv_movie_full_title_detail);
        TextView tvReleased = findViewById(R.id.tv_movie_release_detail);
        TextView tvVoteAverage = findViewById(R.id.tv_movie_vote_average_detail);
        TextView tvGenres = findViewById(R.id.tv_movie_genres_detail);
        ExpandableTextView etvOverview = findViewById(R.id.etv_movie_overview_detail);
        RatingBar rbVoteAverage = findViewById(R.id.rb_movie_vote_average_detail);
        ImageView ivPoster = findViewById(R.id.iv_movie_poster_detail);
        ImageView ivBanner = findViewById(R.id.iv_movie_banner_detail);

        tvTitle.setText(movie.getTitle());
        tvRuntimeDetail.setText(movie.getRuntime());
        tvVoteCount.setText(movie.getVoteCount());
        tvFullTitle.setText(movie.getTitle());
        tvReleased.setText(movie.getReleaseDate());
        tvVoteAverage.setText(movie.getVoteAverage());
        etvOverview.setText(movie.getOverview());
        rbVoteAverage.setRating(Float.parseFloat(movie.getVoteAverage()) / 2);

        StringBuilder genresText = new StringBuilder();

        for (Genres genres : movieGenres) {
            genresText.append(genres.getGenre()).append(", ");
        }

        tvGenres.setText(genresText.substring(0, genresText.length() - 2));

        Glide.with(MovieDetailActivity.this)
                .load(Const.IMG_URL_300 + movie.getPosterUrl())
                .into(ivPoster);

        Glide.with(MovieDetailActivity.this)
                .load(Const.IMG_URL_500 + movie.getBackdropUrl())
                .into(ivBanner);
    }

    private void btnFavoriteHandler() {
        if (!isFavorite) {
            btnFavorite.setIconResource(R.drawable.ic_baseline_favorite_24);
            Toast.makeText(this, "Anime has been added to favorite", Toast.LENGTH_SHORT).show();
        } else {
            btnFavorite.setIconResource(R.drawable.ic_baseline_favorite_border_24);
            Toast.makeText(this, "Anime has been removed from favorite", Toast.LENGTH_SHORT).show();
        }

        isFavorite = !isFavorite;
    }
}