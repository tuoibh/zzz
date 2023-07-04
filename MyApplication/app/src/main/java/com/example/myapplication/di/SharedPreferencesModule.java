package com.example.myapplication.di;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class SharedPreferencesModule {
    /*@Provides
    @Singleton
    public SharedPreferences.Editor getEditor(@NonNull SharedPreferences sharedPreferences){
        return sharedPreferences.edit();
    }*/
    @Provides
    @Singleton
    public SharedPreferences getSharedPreferences(@NonNull @ApplicationContext Context context){
        return context.getSharedPreferences("my_shared_preferences", Context.MODE_PRIVATE);
    }
}
