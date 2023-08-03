package com.example.myapplication.domain.repo;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import retrofit2.http.Url;

public interface ImageLoader {
    public void loadImage(String url, ImageView imageView);
    public Bitmap getBitmap(String url);
}
