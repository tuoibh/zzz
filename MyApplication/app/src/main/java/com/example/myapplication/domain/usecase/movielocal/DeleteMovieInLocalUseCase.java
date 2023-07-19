package com.example.myapplication.domain.usecase.movielocal;

import com.example.myapplication.domain.repo.MovieRepository;

import javax.inject.Inject;

public class DeleteMovieInLocalUseCase {
    @Inject
    MovieRepository movieRepository;

    @Inject
    public DeleteMovieInLocalUseCase(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public void deleteFavouriteMovie(int movieId){
        movieRepository.deleteFavouriteMovieById(movieId);
    }
}
