package com.example.myapplication.domain.usecase.detailmovie;

import com.example.myapplication.domain.model.detail.MovieDetailResponse;
import com.example.myapplication.domain.repo.MovieRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;

public class GetMovieDetailUseCase {

    @Inject
    MovieRepository movieRepository;

    @Inject
    public GetMovieDetailUseCase(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public @NonNull Single<MovieDetailResponse> getMovieDetail(String id){
        return movieRepository.getDetailMovieRemote(id);
    }
}
