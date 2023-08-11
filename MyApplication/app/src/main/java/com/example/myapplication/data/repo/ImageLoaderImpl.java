package com.example.myapplication.data.repo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.R;
import com.example.myapplication.domain.repo.ImageLoader;
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
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(url).apply(requestOptions).into(imageView);
    }

    @Override
    public Bitmap getBitmap(Context context) {
//        Bitmap bm;
//        try {
//            URL url = new URL(imageUrl);
//            bm = Glide.with(context).asBitmap().load(url).submit().get();
//        } catch (ExecutionException | InterruptedException | MalformedURLException e) {
//            throw new RuntimeException(e);
//        }
//        Log.d("tbh_", "getBitmap: ImageLoaderImpl "+bm);
//        return bm;
        Log.d("tbh_", "getBitmap: ImageLoaderImpl"+ BitmapFactory.decodeResource(context.getResources(), R.drawable.img_0));
        return BitmapFactory.decodeResource(context.getResources(), R.drawable.img_0);
    }
}
