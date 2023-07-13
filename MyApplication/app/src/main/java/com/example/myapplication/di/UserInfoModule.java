package com.example.myapplication.di;

import com.example.myapplication.data.local.dao.UserDao;
import com.example.myapplication.data.repo.UserInfoRepositoryImpl;
import com.example.myapplication.domain.repo.UserInfoRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class UserInfoModule {

    @Provides
    @Singleton
    public UserInfoRepository provideUserDao(UserDao userDao){
        return new UserInfoRepositoryImpl(userDao);
    }
}
