package com.example.myapplication.di;

import android.content.SharedPreferences;
import com.example.myapplication.data.repo.SettingInfoRepositoryImpl;
import com.example.myapplication.domain.repo.SettingInfoRepository;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class SettingInfoRepositoryModule {
    @Provides
    public SettingInfoRepository provideSettingInfoRepository(SharedPreferences sharedPreferences){
        return new SettingInfoRepositoryImpl(sharedPreferences);
    }
}
