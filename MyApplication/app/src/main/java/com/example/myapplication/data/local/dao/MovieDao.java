package com.example.myapplication.data.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.myapplication.data.model.movie.MovieResults;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

@Dao
public interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(MovieResults movieResults);

    @Query("select*from movies_result")
    Single<List<MovieResults>> getAllMovieLocal();

    @Query("DELETE FROM movies_result WHERE id = :movieId")
    void deleteMovieById(int movieId);

    @Query("select*from movies_result where title like '%' || :movieName || '%'")
    Single<List<MovieResults>> searchMovie(String movieName);
}
