package com.example.cickmovie.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cickmovie.R;
import com.example.cickmovie.models.MovieNowPlaying;
import com.example.cickmovie.networks.Const;

import java.util.List;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.GridViewHolder> {
    private final List<MovieNowPlaying> movieNowPlayings;
    private final OnItemClick onItemClick;

    public GridAdapter(List<MovieNowPlaying> movieNowPlayings, OnItemClick onItemClick) {
        this.movieNowPlayings = movieNowPlayings;
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_grid_layout, parent, false);

        return new GridViewHolder(view, onItemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull GridViewHolder holder, int position) {
        Glide.with(holder.itemView.getContext())
                .load(Const.IMG_URL_300 + movieNowPlayings.get(position).getImgUrl())
                .into(holder.ivPoster);

        holder.tvTitle.setText(movieNowPlayings.get(position).getTitle());
        holder.tvRelease.setText(movieNowPlayings.get(position).getReleaseDate());
        holder.tvVoteAverage.setText(movieNowPlayings.get(position).getVoteAverage());
    }

    @Override
    public int getItemCount() {
        return movieNowPlayings.size();
    }

    public static class GridViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        OnItemClick onItemClick;
        TextView tvTitle;
        TextView tvRelease;
        TextView tvVoteAverage;
        ImageView ivPoster;

        public GridViewHolder(@NonNull View itemView, OnItemClick onItemClick) {
            super(itemView);

            itemView.setOnClickListener(this);

            tvTitle = itemView.findViewById(R.id.tv_title_grid);
            tvRelease = itemView.findViewById(R.id.tv_release_grid);
            tvVoteAverage = itemView.findViewById(R.id.tv_vote_average_grid);
            ivPoster = itemView.findViewById(R.id.iv_poster_grid);

            this.onItemClick = onItemClick;
        }

        @Override
        public void onClick(View v) {
            onItemClick.onClick(getAdapterPosition());
        }
    }

    public interface OnItemClick {
        void onClick(int position);
    }
}
