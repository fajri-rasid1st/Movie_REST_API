package com.example.cickmovie.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cickmovie.R;
import com.example.cickmovie.models.TvShowAiringToday;
import com.example.cickmovie.networks.Const;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {
    private final List<TvShowAiringToday> tvShowAiringTodays;
    private final OnItemClick onItemClick;

    public ListAdapter(List<TvShowAiringToday> tvShowAiringTodays, OnItemClick onItemClick) {
        this.tvShowAiringTodays = tvShowAiringTodays;
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_layout, parent, false);

        return new ListViewHolder(view, onItemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        Glide.with(holder.itemView.getContext())
                .load(Const.IMG_URL_300 + tvShowAiringTodays.get(position).getImgUrl())
                .into(holder.ivPoster);

        holder.tvTitle.setText(tvShowAiringTodays.get(position).getTitle());
        holder.tvOverview.setText(tvShowAiringTodays.get(position).getOverview());
        holder.rbvoteAverage.setRating(Float.parseFloat(tvShowAiringTodays.get(position).getVoteAverage()) / 2);
    }

    @Override
    public int getItemCount() {
        return tvShowAiringTodays.size();
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        OnItemClick onItemClick;
        TextView tvTitle;
        TextView tvOverview;
        RatingBar rbvoteAverage;
        ImageView ivPoster;

        public ListViewHolder(@NonNull View itemView, OnItemClick onItemClick) {
            super(itemView);

            itemView.setOnClickListener(this);

            tvTitle = itemView.findViewById(R.id.tv_title_list);
            rbvoteAverage = itemView.findViewById(R.id.rb_vote_average_list);
            tvOverview = itemView.findViewById(R.id.tv_overview_list);
            ivPoster = itemView.findViewById(R.id.iv_poster_list);

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
