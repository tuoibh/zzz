package com.example.myapplication.ui.fragment.favourite;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.core.OnItemClickListener;
import com.example.myapplication.R;
import com.example.myapplication.databinding.ItemMovieLinearBinding;
import com.example.myapplication.domain.model.movie.MovieResult;

import java.util.List;

public class MovieFavouriteAdapter extends RecyclerView.Adapter<MovieFavouriteAdapter.MovieHolderItem> {
    List<MovieResult> listMovie;
    OnItemClickListener listener;
    FavouriteMoviesViewModel viewModel;

    private boolean isLoading;

    public MovieFavouriteAdapter(List<MovieResult> listMovie, FavouriteMoviesViewModel viewModel, OnItemClickListener listener) {
        this.listMovie = listMovie;
        this.listener = listener;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public MovieFavouriteAdapter.MovieHolderItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMovieLinearBinding binding = ItemMovieLinearBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MovieHolderItem(binding);
    }

    @SuppressLint("SetTextI18n")
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
        holder.binding.imvStarFavorite.setChecked(true);
        holder.binding.imvStarFavorite.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(!isChecked){
                viewModel.deleteFavouriteMovie(listMovie.get(position).getId());
            }
        });
        holder.onClickItem(listener, position);
    }

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
}
