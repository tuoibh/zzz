package com.example.myapplication.domain.usecase.detailmovie;

import com.example.myapplication.domain.model.castncrew.CastNCrewResponse;
import com.example.myapplication.domain.repo.MovieRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class GetCastNCrewMovieUseCase {
    @Inject
    MovieRepository movieRepository;

    @Inject
    public GetCastNCrewMovieUseCase(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }
    public Single<CastNCrewResponse> getCastNCrew(String id){
        return movieRepository.getCastNCrew(id);
    }
}
