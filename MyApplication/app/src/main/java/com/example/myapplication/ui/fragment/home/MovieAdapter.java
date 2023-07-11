package com.example.myapplication.ui.fragment.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.OnItemClickListener;
import com.example.myapplication.databinding.ItemLoadingBinding;
import com.example.myapplication.databinding.ItemMovieLinearBinding;
import com.example.myapplication.domain.model.movie.MovieResult;
import com.example.myapplication.domain.repo.ImageLoader;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<MovieResult> listMovie;
    List<MovieResult> listLocal;
    OnItemClickListener listener;
    HomeViewModel viewModel;
    Context context;
    ImageLoader imageLoader;

    private HandlerThread handlerThread;
    private Handler handler;

    private static final int TYPE_ITEM = 1;
    private static final int TYPE_LOADING = 2;

    private boolean isLoading;

    /*//
    private LiveData<List<MovieResult>> ldListMovie;

    public void setLdListMovie(LiveData<List<MovieResult>> ldListMovie) {
        if(ldListMovie != null){
            ldListMovie.removeObserver(mObserver);
        }
        this.ldListMovie = ldListMovie;
        this.ldListMovie.observeForever(mObserver);
        notifyDataSetChanged();
    }
*/

    public void setLdListMovie(List<MovieResult> listMovie) {
        this.listMovie = listMovie;
        notifyDataSetChanged();
    }

    private Observer<List<MovieResult>> mObserver = resultList -> notifyDataSetChanged();

    @Override
    public int getItemViewType(int position) {
        if(listMovie != null && position == listMovie.size()-1){
            return TYPE_LOADING;
        }
        return TYPE_ITEM;
    }
    public MovieAdapter(ImageLoader imageLoader, Context context, List<MovieResult> listMovie, HomeViewModel viewModel, List<MovieResult> listLocal, OnItemClickListener listener) {
        this.context = context;
        this.listMovie = listMovie;
        this.listener = listener;
        this.viewModel = viewModel;
        this.imageLoader = imageLoader;
        this.listLocal = listLocal;
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
                imageLoader.loadImage(listMovie.get(position).getPosterPath(), viewHolderItem.binding.imvMovieImage);
                viewHolderItem.binding.txtMovieName.setText(listMovie.get(position).getTitle());
                viewHolderItem.binding.txtOverviewDescription.setText(listMovie.get(position).getOverview());
                viewHolderItem.binding.txtReleaseDateText.setText(listMovie.get(position).getReleaseDate());
                viewHolderItem.binding.txtRatingText.setText(listMovie.get(position).getVoteAverage()+"/10");
                if(viewModel.isFavouriteMovie(listMovie.get(position).getId(), listLocal)){
                    viewHolderItem.binding.imvStarFavorite.setChecked(true);
                }
                viewHolderItem.binding.imvStarFavorite.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if(isChecked){
                        viewModel.addMovieToFavourite(listMovie.get(position));
                    }
                    else{
                        viewModel.deleteMovieFavourite(listMovie.get(position).getId());
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

    private void bindImage(String url, ImageView imageView){
        handler.post(() -> {
            try{
                imageLoader.loadImage(url, imageView);
            } catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
//        handlerThread.quit();
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

    public void addLoadingItem(){
        listMovie.add(new MovieResult());
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
        ItemLoadingBinding binding;

        public MovieHolderLoading(ItemLoadingBinding binding) {
            super(binding.getRoot());
        }
    }
}
