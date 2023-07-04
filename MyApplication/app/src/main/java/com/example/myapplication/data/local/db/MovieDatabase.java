package com.example.myapplication.data.local.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.myapplication.data.local.dao.MovieDao;
import com.example.myapplication.data.model.movie.MovieResults;
import com.example.myapplication.data.converter.ConverterGenreID;

@Database(entities = MovieResults.class, version = 1)
@TypeConverters({ConverterGenreID.class})
public abstract class MovieDatabase extends RoomDatabase {
    public abstract MovieDao movieDao();
}
