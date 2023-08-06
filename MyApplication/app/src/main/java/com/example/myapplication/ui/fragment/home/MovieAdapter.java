package com.example.myapplication.ui.fragment.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.core.OnItemMovieClickListener;
import com.example.myapplication.databinding.ItemLoadingBinding;
import com.example.myapplication.databinding.ItemMovieLinearBinding;
import com.example.myapplication.domain.model.movie.MovieResult;
import com.example.myapplication.domain.repo.ImageLoader;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<MovieResult> listMovie;
    List<MovieResult> listLocal;
    OnItemMovieClickListener listener;
    ImageLoader imageLoader;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_LOADING = 2;

    public void setLdListMovie(List<MovieResult> listMovie, List<MovieResult> listLocal) {
        this.listMovie = listMovie;
        this.listLocal = listLocal;
        notifyDataSetChanged();
    }
    @Override
    public int getItemViewType(int position) {
        if(listMovie != null && listMovie.size()>=19 && position == listMovie.size()-1){
            return TYPE_LOADING;
        }
        return TYPE_ITEM;
    }
    public MovieAdapter(ImageLoader imageLoader,
                        List<MovieResult> listMovie,
                        List<MovieResult> listLocal,
                        OnItemMovieClickListener listener) {
        this.listMovie = listMovie;
        this.listener = listener;
        this.imageLoader = imageLoader;
        this.listLocal = listLocal;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(TYPE_ITEM==viewType){
            ItemMovieLinearBinding binding = ItemMovieLinearBinding
                    .inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new MovieHolderItem(binding);
        }else {
            ItemLoadingBinding binding  = ItemLoadingBinding
                    .inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new MovieHolderLoading(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case TYPE_ITEM:
                MovieHolderItem viewHolderItem = (MovieHolderItem) holder;
                imageLoader.loadImage(listMovie.get(position).getPosterPath(), viewHolderItem.binding.imvMovieImage);
                viewHolderItem.binding.txtMovieName.setText(listMovie.get(position).getTitle());
                viewHolderItem.binding.txtOverviewDescription.setText(listMovie.get(position).getOverview());
                viewHolderItem.binding.txtReleaseDateText.setText(listMovie.get(position).getReleaseDate());
                viewHolderItem.binding.txtRatingText.setText(listMovie.get(position).getVoteAverage()+"/10");
                viewHolderItem.onClickItem(listener, position);
                viewHolderItem.onFavouriteItemClick(listener, position);
                viewHolderItem.onSetFavouriteState(listener, position);
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
        return listMovie.size();
    }

    public static class MovieHolderItem extends RecyclerView.ViewHolder {
        ItemMovieLinearBinding binding;

        public MovieHolderItem(ItemMovieLinearBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void onClickItem(OnItemMovieClickListener listener, int position){
            itemView.setOnClickListener(v -> {
                listener.onItemClick(v, position);
            });
        }
        public void onFavouriteItemClick(OnItemMovieClickListener listener, int position){
            binding.imvStarFavorite.setOnCheckedChangeListener((buttonView, isChecked) ->
                    listener.onFavouriteClick(buttonView, isChecked, position));
        }
        public void onSetFavouriteState(OnItemMovieClickListener listener, int position){
            listener.onChangeFavouriteState(binding.imvStarFavorite, position);
        }

    }

    public void removeLoadingItem(){
        int position = listMovie.size()-1;
        MovieResult movieResult = listMovie.get(position);
        if(movieResult != null){
            listMovie.remove(position);
            notifyItemRemoved(position);
        }
    }

    public static class MovieHolderLoading extends RecyclerView.ViewHolder {
        public MovieHolderLoading(ItemLoadingBinding binding) {
            super(binding.getRoot());
        }
    }
}
