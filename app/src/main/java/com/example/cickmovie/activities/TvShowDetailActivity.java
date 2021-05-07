package com.example.cickmovie.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

import com.bumptech.glide.Glide;
import com.example.cickmovie.R;
import com.example.cickmovie.models.Genres;
import com.example.cickmovie.models.TvShow;
import com.example.cickmovie.networks.Const;
import com.example.cickmovie.networks.TvShowApiClient;
import com.example.cickmovie.networks.TvShowApiInterface;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvShowDetailActivity extends AppCompatActivity implements View.OnClickListener {
    // private static final String TAG = "TvShowDetailActivity";
    private LinearProgressIndicator lpiTvShowDetail;
    private MaterialButton btnFavorite;
    private TvShow tvShow;
    private List<Genres> tvShowGenres;
    private boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show_detail);

        lpiTvShowDetail = findViewById(R.id.lpi_tvshow_detail);
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
        TvShowApiInterface tvShowApiInterface = TvShowApiClient.getRetrofitTvShowDetail()
                .create(TvShowApiInterface.class);

        Call<TvShow> tvShowCall = tvShowApiInterface
                .getTvShow(movieId, Const.API_KEY);

        tvShowCall.enqueue(new Callback<TvShow>() {
            @Override
            public void onResponse(@NonNull Call<TvShow> call, @NonNull Response<TvShow> response) {
                if (response.isSuccessful() && response.body() != null) {
                    tvShow = response.body();

                    if (response.body().getGenres() != null) {
                        tvShowGenres = tvShow.getGenres();
                        setDetailActivityContent();
                    }

                    lpiTvShowDetail.setVisibility(View.GONE);
                } else {
                    Toast.makeText(TvShowDetailActivity.this, "Request Failed", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(() -> lpiTvShowDetail.setVisibility(View.GONE), 3000);
                }
            }

            @Override
            public void onFailure(@NonNull Call<TvShow> call, @NonNull Throwable t) {
                // Log.d(TAG, t.getMessage());
                Toast.makeText(TvShowDetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(() -> lpiTvShowDetail.setVisibility(View.GONE), 3000);
            }
        });
    }

    private void setDetailActivityContent() {
        TextView tvTitle = findViewById(R.id.tv_tvshow_title_detail);
        TextView tvRuntimeDetail = findViewById(R.id.tv_tvshow_runtime_detail);
        TextView tvVoteCount = findViewById(R.id.tv_tvshow_vote_count_detail);
        TextView tvFullTitle = findViewById(R.id.tv_tvshow_full_title_detail);
        TextView tvReleased = findViewById(R.id.tv_tvshow_release_detail);
        TextView tvVoteAverage = findViewById(R.id.tv_tvshow_vote_average_detail);
        TextView tvGenres = findViewById(R.id.tv_tvshow_genres_detail);
        ExpandableTextView etvOverview = findViewById(R.id.etv_tvshow_overview_detail);
        RatingBar rbVoteAverage = findViewById(R.id.rb_tvshow_vote_average_detail);
        ImageView ivPoster = findViewById(R.id.iv_tvshow_poster_detail);
        ImageView ivBanner = findViewById(R.id.iv_tvshow_banner_detail);

        tvTitle.setText(tvShow.getTitle());
        tvRuntimeDetail.setText(tvShow.getRuntime());
        tvVoteCount.setText(tvShow.getVoteCount());
        tvFullTitle.setText(tvShow.getTitle());
        tvReleased.setText(tvShow.getReleaseDate());
        tvVoteAverage.setText(tvShow.getVoteAverage());
        etvOverview.setText(tvShow.getOverview());
        rbVoteAverage.setRating(Float.parseFloat(tvShow.getVoteAverage()) / 2);

        StringBuilder genresText = new StringBuilder();

        for (Genres genres : tvShowGenres) {
            genresText.append(genres.getGenre()).append(", ");
        }

        tvGenres.setText(genresText.substring(0, genresText.length() - 2));

        Glide.with(TvShowDetailActivity.this)
                .load(Const.IMG_URL_300 + tvShow.getPosterUrl())
                .into(ivPoster);

        Glide.with(TvShowDetailActivity.this)
                .load(Const.IMG_URL_500 + tvShow.getBackdropUrl())
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