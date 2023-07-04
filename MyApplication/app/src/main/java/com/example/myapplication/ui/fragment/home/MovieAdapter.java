package com.example.myapplication.ui.fragment.home;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.OnItemClickListener;
import com.example.myapplication.R;
import com.example.myapplication.databinding.ItemLoadingBinding;
import com.example.myapplication.databinding.ItemMovieLinearBinding;
import com.example.myapplication.domain.model.movie.MovieResult;
import com.example.myapplication.ui.fragment.home.HomeViewModel;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<MovieResult> listMovie;
    OnItemClickListener listener;
    HomeViewModel viewModel;

    private static final int TYPE_ITEM = 1;
    private static final int TYPE_LOADING = 2;

    private boolean isLoading;

    @Override
    public int getItemViewType(int position) {
        if(listMovie != null && position == listMovie.size()-1){
            return TYPE_LOADING;
        }
        return TYPE_ITEM;
    }

    public MovieAdapter(List<MovieResult> listMovie, HomeViewModel viewModel, OnItemClickListener listener) {
        this.listMovie = listMovie;
        this.listener = listener;
        this.viewModel = viewModel;
    }

    public MovieAdapter(List<MovieResult> listMovie, OnItemClickListener listener) {
        this.listMovie = listMovie;
        this.listener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public MovieAdapter(List<MovieResult> listMovie) {
        this.listMovie = listMovie;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(TYPE_ITEM==viewType){
            ItemMovieLinearBinding binding = ItemMovieLinearBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new MovieHolderItem(binding);
        }else {
            ItemLoadingBinding binding  = ItemLoadingBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new MovieHolderLoading(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        switch (holder.getItemViewType()){
            case TYPE_ITEM:
                MovieHolderItem viewHolderItem = (MovieHolderItem) holder;
                Glide.with(viewHolderItem.binding.getRoot())
                        .load(listMovie.get(position)
                                .getPosterPath())
                        .placeholder(R.drawable.ic_splash_app)
                        .into(viewHolderItem.binding.imvMovieImage);
                viewHolderItem.binding.txtMovieName.setText(listMovie.get(position).getTitle());
                viewHolderItem.binding.txtOverviewDescription.setText(listMovie.get(position).getOverview());
                viewHolderItem.binding.txtReleaseDateText.setText(listMovie.get(position).getReleaseDate());
                viewHolderItem.binding.txtRatingText.setText(listMovie.get(position).getVoteAverage()+"/10");
                if(viewModel.isFavouriteMovie(listMovie.get(position).getId())){
                    viewHolderItem.binding.imvStarFavorite.setChecked(true);
                }
                viewHolderItem.binding.imvStarFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            Log.d("tbh", "onCheckedChanged: chT");
                            viewModel.addMovieToFavourite(listMovie.get(position));
                        }
                        else{
                            Log.d("tbh_", "onCheckedChanged: MovieAdapter F");
                            viewModel.deleteMovieFavourite(listMovie.get(position).getId());
                        }
                    }
                });
                viewHolderItem.onClickItem(listener, position);
                break;
            case TYPE_LOADING:
                MovieHolderLoading viewHolderLoading = (MovieHolderLoading) holder;
                break;
            default:
                throw new IllegalArgumentException("Invalid view type");
        }
    }

    /*@SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MovieHolderItem holder, @SuppressLint("RecyclerView") int position) {
            Glide.with(holder.binding.getRoot())
                    .load(listMovie.get(position)
                            .getPosterPath())
                    .placeholder(R.drawable.ic_splash_app)
                    .into(holder.binding.imvMovieImage);
        holder.binding.txtMovieName.setText(listMovie.get(position).getTitle());
        holder.binding.txtOverviewDescription.setText(listMovie.get(position).getOverview());
        holder.binding.txtReleaseDateText.setText(listMovie.get(position).getReleaseDate());
        holder.binding.txtRatingText.setText(listMovie.get(position).getVoteAverage()+"/10");
        holder.binding.imvStarFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Log.d("tbh", "onCheckedChanged: chT");
                    viewModel.addMovieToFavourite(listMovie.get(position));
                }
            }
        });
        holder.onClickItem(listener, position);
    }*/

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    public static class MovieHolderItem extends RecyclerView.ViewHolder {
        ItemMovieLinearBinding binding;

        public MovieHolderItem(ItemMovieLinearBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void onClickItem(OnItemClickListener listener, int position){
            itemView.setOnClickListener(v -> {
                listener.onItemClick(v, position);
            });
        }
    }

    public static class MovieHolderLoading extends RecyclerView.ViewHolder {
        ItemLoadingBinding binding;

        public MovieHolderLoading(ItemLoadingBinding binding) {
            super(binding.getRoot());
        }
    }
}
