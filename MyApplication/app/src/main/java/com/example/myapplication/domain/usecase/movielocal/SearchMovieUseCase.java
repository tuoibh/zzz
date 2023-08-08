package com.example.myapplication.domain.usecase.movielocal;

import com.example.myapplication.domain.model.movie.MovieResult;
import com.example.myapplication.domain.repo.MovieRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class SearchMovieUseCase {
    @Inject
    MovieRepository movieRepository;

    @Inject
    public SearchMovieUseCase(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Single<List<MovieResult>> searchMovieLocal(String movieTitle){
        return movieRepository.searchMovie(movieTitle);
    }
}
