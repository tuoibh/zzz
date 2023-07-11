package com.example.myapplication.data.repo;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.R;
import com.example.myapplication.domain.repo.ImageLoader;

import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;

public class ImageLoaderImpl implements ImageLoader {

    private final @ApplicationContext Context context;

    @Inject
    public ImageLoaderImpl(Context context) {
        this.context = context;
    }

    @Override
    public void loadImage(String url, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions().priority(Priority.HIGH)
                .placeholder(R.drawable.ic_splash_app)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true);
        Glide.with(context).load(url).apply(requestOptions).into(imageView);
    }
}
