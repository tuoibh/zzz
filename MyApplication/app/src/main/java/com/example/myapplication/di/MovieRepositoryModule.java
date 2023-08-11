package com.example.myapplication.di;

import com.example.myapplication.data.local.dao.MovieDao;
import com.example.myapplication.data.remote.MovieApi;
import com.example.myapplication.data.repo.MovieRepositoryImpl;
import com.example.myapplication.domain.repo.MovieRepository;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class MovieRepositoryModule {
    @Provides
    public MovieRepository provideMovieRepository(MovieApi apiService, MovieDao movieDao){
        return new MovieRepositoryImpl(apiService, movieDao);
    }
}
