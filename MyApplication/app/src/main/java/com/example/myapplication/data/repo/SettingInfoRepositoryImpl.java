package com.example.myapplication.data.repo;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.myapplication.domain.repo.SettingInfoRepository;

import javax.inject.Inject;

public class SettingInfoRepositoryImpl implements SettingInfoRepository {
    SharedPreferences sharedPreferences;

    @Inject
    public SettingInfoRepositoryImpl(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void insertStringSharedPreferences(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    @Override
    public void insertNumFloatSharedPreferences(String key, float value) {
        sharedPreferences.edit().putFloat(key, value).apply();
    }

    @Override
    public void insertNumIntSharedPreferences(String key, int value) {
        sharedPreferences.edit().putInt(key, value).apply();
    }

    @Override
    public String getStringSharedPreferences(String key, String defValue) {
        return sharedPreferences.getString(key, defValue);
    }

    @Override
    public float getNumFloatSharedPreferences(String key) {
        return sharedPreferences.getFloat(key, 0f);
    }

    @Override
    public int getNumIntSharedPreferences(String key) {
        return sharedPreferences.getInt(key, 0);
    }
}
