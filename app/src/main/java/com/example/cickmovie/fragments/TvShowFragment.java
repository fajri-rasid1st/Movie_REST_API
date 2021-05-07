package com.example.cickmovie.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.cickmovie.R;
import com.example.cickmovie.activities.MainActivity;
import com.example.cickmovie.activities.TvShowDetailActivity;
import com.example.cickmovie.adapters.ListAdapter;
import com.example.cickmovie.models.TvShowAiringToday;
import com.example.cickmovie.models.TvShowAiringTodayResponse;
import com.example.cickmovie.networks.Const;
import com.example.cickmovie.networks.TvShowApiClient;
import com.example.cickmovie.networks.TvShowApiInterface;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvShowFragment extends Fragment implements ListAdapter.OnItemClick, SwipeRefreshLayout.OnRefreshListener {
    // private static final String TAG = "TvShowFragment";
    private SwipeRefreshLayout srlTvShow;
    private LinearProgressIndicator lpiTvShow;
    private ConstraintLayout clTvShowError;
    private RecyclerView recyclerView;
    private ListAdapter listAdapter;
    private List<TvShowAiringToday> tvShowAiringTodays;
    private boolean isResponseAlreadySuccess = false;

    public static TvShowFragment newInstance() {
        TvShowFragment tvShowFragment = new TvShowFragment();
        Bundle args = new Bundle();

        args.putString(MainActivity.TOOLBAR_TITLE, "TV Show");
        tvShowFragment.setArguments(args);

        return tvShowFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tv_show, container, false);

        srlTvShow = view.findViewById(R.id.srl_tv_show);
        lpiTvShow = view.findViewById(R.id.lpi_tv_show);
        clTvShowError = view.findViewById(R.id.cl_tv_show_error);
        recyclerView = view.findViewById(R.id.rv_tv_show);

        srlTvShow.setOnRefreshListener(this);
        loadData();

        return view;
    }

    private void loadData() {
        TvShowApiInterface tvShowApiInterface = TvShowApiClient.getRetrofitTvShows()
                .create(TvShowApiInterface.class);

        Call<TvShowAiringTodayResponse> responseCall = tvShowApiInterface
                .getAiringToday(Const.API_KEY);

        responseCall.enqueue(new Callback<TvShowAiringTodayResponse>() {
            @Override
            public void onResponse(@NonNull Call<TvShowAiringTodayResponse> call,
                                   @NonNull Response<TvShowAiringTodayResponse> response) {
                if (response.body() != null) {
                    if (response.isSuccessful() && response.body().getAiringTodays() != null) {
                        isResponseAlreadySuccess = true;
                        tvShowAiringTodays = response.body().getAiringTodays();
                        listAdapter = new ListAdapter(tvShowAiringTodays, TvShowFragment.this);

                        recyclerView.setAdapter(listAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                        lpiTvShow.setVisibility(View.GONE);
                        clTvShowError.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(getActivity(), "Request Failed", Toast.LENGTH_SHORT).show();
                        if (!isResponseAlreadySuccess) clTvShowError.setVisibility(View.VISIBLE);
                        new Handler().postDelayed(() -> lpiTvShow.setVisibility(View.GONE), 3000);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<TvShowAiringTodayResponse> call, @NonNull Throwable t) {
                // Log.d(TAG, t.getMessage());
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                if (!isResponseAlreadySuccess) clTvShowError.setVisibility(View.VISIBLE);
                new Handler().postDelayed(() -> lpiTvShow.setVisibility(View.GONE), 3000);
            }
        });
    }

    @Override
    public void onClick(int position) {
        Intent tvShowdetailActivity = new Intent(getActivity(), TvShowDetailActivity.class);

        tvShowdetailActivity.putExtra("ID", tvShowAiringTodays.get(position).getId());
        tvShowdetailActivity.putExtra("TITLE", tvShowAiringTodays.get(position).getTitle());

        startActivity(tvShowdetailActivity);
    }

    @Override
    public void onRefresh() {
        lpiTvShow.setVisibility(View.VISIBLE);
        loadData();
        new Handler().postDelayed(() -> srlTvShow.setRefreshing(false), 1000);
    }
}