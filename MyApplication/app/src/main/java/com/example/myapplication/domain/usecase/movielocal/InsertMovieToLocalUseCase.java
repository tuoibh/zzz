package com.example.myapplication.domain.usecase.movielocal;

import com.example.myapplication.domain.model.movie.MovieResult;
import com.example.myapplication.domain.repo.MovieRepository;

import javax.inject.Inject;

public class InsertMovieToLocalUseCase {
    @Inject
    MovieRepository movieRepository;

    @Inject
    public InsertMovieToLocalUseCase(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public void insertMovieLocal(MovieResult movieResult){
        movieRepository.insertMovieLocal(movieResult);
    }
}
