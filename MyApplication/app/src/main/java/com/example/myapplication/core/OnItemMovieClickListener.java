package com.example.myapplication.core;

import android.view.View;
import android.widget.CheckBox;

public interface OnItemMovieClickListener {
    void onItemClick(View view, int position);
    void onFavouriteClick(View view, boolean isCheck, int position);

    void onChangeFavouriteState(CheckBox view, int position);
}
