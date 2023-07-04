package com.example.myapplication;


import com.example.myapplication.data.model.movie.MovieResponses;
import com.example.myapplication.data.model.movie.MovieResults;
import com.example.myapplication.domain.model.movie.MovieResponse;
import com.example.myapplication.domain.model.movie.MovieResult;

import java.util.List;

interface Mapper{
    /*public Optional<MovieResult> transferTo(MovieResults movie);
    public Optional<List<MovieResult>> transferTo(List<MovieResults> movieResultsList);
    public Optional<MovieResponse> transferTo(MovieResponses movieResponses);
    public Optional<MovieResults> transferFrom(MovieResult movie);
    public Optional<List<MovieResults>> transferFrom(List<MovieResult> movieResultList);
    public Optional<MovieResponses> transferFrom(MovieResponse movieResponse);*/

    List<MovieResult> transferTo(List<MovieResults> movieResultsList);
    MovieResponse rptransferTo(List<MovieResponses> movieResponses);
    List<MovieResults> transferFrom(List<MovieResult> movieResultList);
}
//transferTo: data to domain
//transferFrom: domain to data