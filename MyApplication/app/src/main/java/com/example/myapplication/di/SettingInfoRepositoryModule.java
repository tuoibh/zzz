package com.example.myapplication.di;

import com.example.myapplication.data.repo.SettingInfoRepositoryImpl;
import com.example.myapplication.domain.repo.SettingInfoRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class SettingInfoRepositoryModule {
    @Provides
    public SettingInfoRepository provideSettingInfoRepository(){
        return new SettingInfoRepositoryImpl();
    }
}
