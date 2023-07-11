package com.example.myapplication.domain.repo;

import com.example.myapplication.domain.model.castncrew.CastNCrewResponse;
import com.example.myapplication.domain.model.detail.MovieDetailResponse;
import com.example.myapplication.domain.model.movie.MovieResponse;
import com.example.myapplication.domain.model.movie.MovieResult;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;

public interface MovieRepository {
    Single<MovieResponse> getListMovieRemote(String topic, int num_page);
    void insertMovieLocal(MovieResult listMovie);
    Single<List<MovieResult>> getListMovieLocal();

    @NonNull Single<MovieDetailResponse> getDetailMovieRemote(String id);

    Single<CastNCrewResponse> getCastNCrew(String movieId);

    void deleteFavouriteMovieById(int movieId);
}
