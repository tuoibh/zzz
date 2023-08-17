package com.example.myapplication.ui.fragment.home;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.core.OnItemMovieClickListener;
import com.example.myapplication.databinding.ItemLoadingBinding;
import com.example.myapplication.databinding.ItemMovieGridBinding;
import com.example.myapplication.databinding.ItemMovieLinearBinding;
import com.example.myapplication.domain.model.movie.MovieResult;
import com.example.myapplication.domain.repo.ImageLoader;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_LINEAR_ITEM = 1;
    private static final int TYPE_GRID_ITEM = 2;
    private static final int TYPE_LOADING = 3;
    List<MovieResult> listMovie;
    List<MovieResult> listLocal;
    OnItemMovieClickListener listener;
    ImageLoader imageLoader;
    private boolean isGrid = true;


    public MovieAdapter(ImageLoader imageLoader,
                        List<MovieResult> listMovie,
                        List<MovieResult> listLocal,
                        OnItemMovieClickListener listener) {
        this.listMovie = listMovie;
        this.listener = listener;
        this.imageLoader = imageLoader;
        this.listLocal = listLocal;
    }

    public void setLdListMovie(List<MovieResult> listMovie, List<MovieResult> listLocal) {
        this.listMovie = listMovie;
        this.listLocal = listLocal;
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addLoadingItem() {
        this.listMovie.add(null);
        notifyItemInserted(listMovie.size()-1);
        notifyDataSetChanged();
        Log.d("tbh_", "addLoadingItem: MovieAdapter " + this.listMovie.size());
    }

    public void setItemUI(boolean isGrid) {
        this.isGrid = isGrid;
    }

    @Override
    public int getItemViewType(int position) {
        if (listMovie.get(position) == null) {
            return TYPE_LOADING;
        }
        if (isGrid) return TYPE_GRID_ITEM;
        return TYPE_LINEAR_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (TYPE_GRID_ITEM == viewType) {
            ItemMovieGridBinding binding = ItemMovieGridBinding
                    .inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new MovieHolderGridItem(binding);
        } else if (TYPE_LINEAR_ITEM == viewType) {
            ItemMovieLinearBinding binding = ItemMovieLinearBinding
                    .inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new MovieHolderLinearItem(binding);
        } else {
            ItemLoadingBinding binding = ItemLoadingBinding
                    .inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new MovieHolderLoading(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TYPE_LINEAR_ITEM:
                MovieHolderLinearItem linearItem = (MovieHolderLinearItem) holder;
                imageLoader.loadImage(listMovie.get(position).getPosterPath(), linearItem.binding.imvMovieImage);
                linearItem.binding.txtMovieName.setText(listMovie.get(position).getTitle());
                linearItem.binding.txtOverviewDescription.setText(listMovie.get(position).getOverview());
                linearItem.binding.txtReleaseDateText.setText(listMovie.get(position).getReleaseDate());
                linearItem.binding.txtRatingText.setText(listMovie.get(position).getVoteAverage() + "/10");
                if (listMovie.get(position).isAdult())
                    linearItem.binding.imvAdult.setVisibility(View.VISIBLE);
                linearItem.onClickItem(listener, position);
                linearItem.onFavouriteItemClick(listener, position);
                linearItem.onSetFavouriteState(listener, position);
                break;
            case TYPE_GRID_ITEM:
                MovieHolderGridItem gridItem = (MovieHolderGridItem) holder;
                imageLoader.loadImage(listMovie.get(position).getPosterPath(), gridItem.binding.imvMovieImage);
                gridItem.binding.txtMovieName.setText(listMovie.get(position).getTitle());
                gridItem.onClickItem(listener, position);
                break;
            case TYPE_LOADING:
                break;
            default:
                throw new IllegalArgumentException("Invalid view type");
        }
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return this.listMovie.size();
    }


    public void removeLoadingItem() {
        int position = listMovie.size() - 1;
        MovieResult movieResult = listMovie.get(position);
        if (movieResult == null) {
            listMovie.remove(position);
            notifyItemRemoved(position);
        }
    }

    public static class MovieHolderLinearItem extends RecyclerView.ViewHolder {
        ItemMovieLinearBinding binding;

        public MovieHolderLinearItem(ItemMovieLinearBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void onClickItem(OnItemMovieClickListener listener, int position) {
            itemView.setOnClickListener(v -> {
                listener.onItemClick(v, position);
            });
        }

        public void onFavouriteItemClick(OnItemMovieClickListener listener, int position) {
            binding.imvStarFavorite.setOnClickListener(v -> listener.onFavouriteClick(binding.imvStarFavorite, position));
        }

        public void onSetFavouriteState(OnItemMovieClickListener listener, int position) {
            listener.onChangeFavouriteState(binding.imvStarFavorite, position);
        }

    }

    public static class MovieHolderGridItem extends RecyclerView.ViewHolder {
        ItemMovieGridBinding binding;

        public MovieHolderGridItem(ItemMovieGridBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void onClickItem(OnItemMovieClickListener listener, int position) {
            itemView.setOnClickListener(v -> {
                listener.onItemClick(v, position);
            });
        }
    }


    public static class MovieHolderLoading extends RecyclerView.ViewHolder {
        public MovieHolderLoading(ItemLoadingBinding binding) {
            super(binding.getRoot());
        }
    }
}
