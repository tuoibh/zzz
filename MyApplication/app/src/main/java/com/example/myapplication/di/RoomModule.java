package com.example.myapplication.di;

import android.content.Context;

import androidx.room.Room;

import com.example.myapplication.data.local.dao.MovieDao;
import com.example.myapplication.data.local.db.MovieDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class RoomModule {
    @Provides
    @Singleton
    public MovieDatabase provideDB(@ApplicationContext Context context) {
        return Room.databaseBuilder(context, MovieDatabase.class, "movie_database").allowMainThreadQueries().build();
    }

    @Provides
    @Singleton
    public MovieDao provideDao(MovieDatabase movieDatabase){
        return movieDatabase.movieDao();
    }
}
