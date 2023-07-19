package com.example.myapplication.domain.usecase.listmovie;

import android.util.Log;

import com.example.myapplication.domain.model.movie.MovieResponse;
import com.example.myapplication.domain.model.movie.MovieResult;
import com.example.myapplication.domain.repo.MovieRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class GetListMoviesUseCase {
    @Inject
    MovieRepository movieRepository;

    @Inject
    public GetListMoviesUseCase(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Single<MovieResponse> getAllMoviesByTopic(String topic, int num_page){
        Log.d("tbh", "getAllMoviesByTopic: ");
        return movieRepository.getListMovieRemote(topic, num_page);
    }

    public Single<List<MovieResult>> getListMovieLocal(){
        return movieRepository.getListMovieLocal();
    }
}
