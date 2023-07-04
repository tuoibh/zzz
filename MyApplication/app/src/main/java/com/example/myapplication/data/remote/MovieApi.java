package com.example.myapplication.data.remote;

import com.example.myapplication.data.model.castncrew.CastNCrewResponses;
import com.example.myapplication.data.model.detail.MovieDetailResponses;
import com.example.myapplication.data.model.movie.MovieResponses;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

//api.themoviedb.org/3/movie/popular?api_key=e7631ffcb8e766993e5ec0c1f4245f93&page={pageNumber}
//api.themoviedb.org/3/movie/{movieId}?api_key=e7631ffcb8e766993e5ec0c1f4245f93

public interface MovieApi {
    @GET("{topic}")
    Single<MovieResponses> getListMovie(@Path("topic") String topic, @Query("api_key") String apiKey, @Query("page") int pageNum);

    @GET("{movieId}")
    Single<MovieDetailResponses> getMovieDetail(@Path("movieId") String movieId, @Query("api_key") String apiKey);

    @GET("{movieId}/credits")
    Single<CastNCrewResponses> getCastNCrewMovie(@Path("movieId") String movieId, @Query("api_key") String apiKey);
}

