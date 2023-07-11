package com.example.myapplication.di;

import android.content.Context;

import com.example.myapplication.data.repo.ImageLoaderImpl;
import com.example.myapplication.domain.repo.ImageLoader;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class ImageLoaderModule {
    @Provides
    @Singleton
    public ImageLoader provideGlideImageLoader(@ApplicationContext Context context){
        return new ImageLoaderImpl(context);
    }
}
