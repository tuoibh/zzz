package com.example.myapplication;

import androidx.annotation.NonNull;

import com.example.myapplication.data.model.movie.MovieResponses;
import com.example.myapplication.data.model.movie.MovieResults;
import com.example.myapplication.domain.model.movie.MovieResponse;
import com.example.myapplication.domain.model.movie.MovieResult;

import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public final class Extension implements Mapper {
    ModelMapper modelMapper = new ModelMapper();
    @NonNull
    @Override
    public List<MovieResult> transferTo(List<MovieResults> movieResultsList) {
        List<MovieResult> rsList = new ArrayList<>();
        for (MovieResults item:movieResultsList) {
            rsList.add(modelMapper.map(item, MovieResult.class));
        }
        return rsList;
    }

    @Override
    public MovieResponse rptransferTo(List<MovieResponses> movieResponses) {
        return modelMapper.map(movieResponses, MovieResponse.class);
    }

    @Override
    public List<MovieResults> transferFrom(List<MovieResult> movieResultList) {
        List<MovieResults> rsList = new ArrayList<>();
        for (MovieResult item:movieResultList) {
            rsList.add(modelMapper.map(item, MovieResults.class));
        }
        return rsList;
    }

    /*@Override
    public Optional<MovieResult> transferTo(MovieResults movie) {
        Optional<MovieResults> opt = Optional.ofNullable(movie);
        return opt.map(x -> new MovieResult(
                movie.getOverview(),
                movie.getOriginalLanguage(),
                movie.getOriginalTitle(),
                movie.isVideo(),
                movie.getTitle(),
                movie.getGenreIds(),
                movie.getPosterPath(),
                movie.getBackdropPath(),
                movie.getReleaseDate(),
                movie.getPopularity(),
                movie.getVoteAverage(),
                movie.getId(),
                movie.isAdult(),
                movie.getVoteCount()));
    }

    @Override
    public Optional<List<MovieResult>> transferTo(List<MovieResults> movieResultsList) {
        Optional<List<MovieResults>> opt = Optional.of(movieResultsList);
        return opt.map();
    }

    @Override
    public Optional<MovieResponse> transferTo(MovieResponses movieResponses) {
        Optional<MovieResponses> opt = Optional.of(movieResponses);
        return opt.map(x -> new MovieResponse(
                movieResponses.getPage(),
                movieResponses.getTotalPages(),
                movieResponses.getResults(),
                movieResponses.getTotalResults()));
    }

    @Override
    public Optional<MovieResults> transferFrom (MovieResult movie){
        Optional<MovieResult> opt = Optional.of(movie);
        return opt.map(x -> new MovieResults(
                movie.getOverview(),
                movie.getOriginalLanguage(),
                movie.getOriginalTitle(),
                movie.isVideo(),
                movie.getTitle(),
                movie.getGenreIds(),
                movie.getPosterPath(),
                movie.getBackdropPath(),
                movie.getReleaseDate(),
                movie.getPopularity(),
                movie.getVoteAverage(),
                movie.getId(),
                movie.isAdult()));
    }

    @Override
    public Optional<List<MovieResults>> transferFrom(List<MovieResult> movieResultList) {
        return Optional.empty();
    }

    @Override
    public Optional<MovieResponses> transferFrom(MovieResponse movieResponse) {
        Optional<MovieResponse> opt = Optional.of(movieResponse);
        return opt.map(x -> new MovieResponses(
                movieResponse.getPage(),
                movieResponse.getTotalPages(),
                movieResponse.getResults(),
                movieResponse.getTotalResults())
        );
    }*/
}