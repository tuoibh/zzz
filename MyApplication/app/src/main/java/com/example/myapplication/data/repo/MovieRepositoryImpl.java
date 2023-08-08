package com.example.myapplication.data.repo;

import com.example.myapplication.core.AppConfig;
import com.example.myapplication.data.converter.IsFavouriteConverter;
import com.example.myapplication.data.local.dao.MovieDao;
import com.example.myapplication.data.model.castncrew.CastNCrewResponses;
import com.example.myapplication.data.model.detail.MovieDetailResponses;
import com.example.myapplication.data.model.movie.MovieResults;
import com.example.myapplication.data.remote.MovieApi;
import com.example.myapplication.domain.model.castncrew.CastNCrewResponse;
import com.example.myapplication.domain.model.detail.MovieDetailResponse;
import com.example.myapplication.domain.model.movie.MovieResponse;
import com.example.myapplication.domain.model.movie.MovieResult;
import com.example.myapplication.domain.repo.MovieRepository;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;

public class MovieRepositoryImpl implements MovieRepository {
    MovieApi api;
    MovieDao movieDao;

    @Inject
    public MovieRepositoryImpl(MovieApi api, MovieDao movieDao) {
        this.api = api;
        this.movieDao = movieDao;
    }

    @Override
    public Single<MovieResponse> getListMovieRemote(String topic, int num_page) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
        return api.getListMovie(topic, AppConfig.Companion.API_KEY, num_page).map(x -> modelMapper.map(x, MovieResponse.class));
    }

    @Override
    public void insertMovieLocal(MovieResult movieResult) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new IsFavouriteConverter());
        movieDao.insertMovie(modelMapper.map(movieResult, MovieResults.class));
    }
    @Override
    public Single<List<MovieResult>> getListMovieLocal() {
        ModelMapper modelMapper = new ModelMapper();
        return movieDao.getAllMovieLocal().map(list -> {
            List<MovieResult> listResult = new ArrayList<>();
            for(MovieResults item: list){
                listResult.add(modelMapper.map(item, MovieResult.class));
            }
            return listResult;
        });
    }

    @Override
    public @NonNull Single<MovieDetailResponse> getDetailMovieRemote(String id) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.createTypeMap(MovieDetailResponses.class, MovieDetailResponse.class);
        return api.getMovieDetail(id, AppConfig.Companion.API_KEY).map(x -> modelMapper.map(x, MovieDetailResponse.class));
    }

    @Override
    public Single<CastNCrewResponse> getCastNCrew(String movieId) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.createTypeMap(CastNCrewResponses.class, CastNCrewResponse.class);
        return api.getCastNCrewMovie(movieId, AppConfig.Companion.API_KEY).map(x -> modelMapper.map(x, CastNCrewResponse.class));
    }

    @Override
    public void deleteFavouriteMovieById(int movieId) {
        movieDao.deleteMovieById(movieId);
    }

    @Override
    public Single<List<MovieResult>> searchMovie(String movieTitle) {
        ModelMapper modelMapper = new ModelMapper();
        return movieDao.searchMovie(movieTitle).map(list -> {
            List<MovieResult> listResult = new ArrayList<>();
            for(MovieResults item: list){
                listResult.add(modelMapper.map(item, MovieResult.class));
            }
            return listResult;
        });
    }
}
