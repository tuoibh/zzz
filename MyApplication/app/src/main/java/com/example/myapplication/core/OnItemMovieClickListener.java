package com.example.myapplication.core;

import android.view.View;
import android.widget.ImageView;

public interface OnItemMovieClickListener {
    void onItemClick(View view, int position);
    void onFavouriteClick(ImageView view, int position);
    void onChangeFavouriteState(ImageView view, int position);
}
