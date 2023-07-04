package com.example.myapplication.data.repo;

import android.content.SharedPreferences;

import com.example.myapplication.domain.repo.SettingInfoRepository;

import javax.inject.Inject;

public class SettingInfoRepositoryImpl implements SettingInfoRepository {
    @Inject
    SharedPreferences sharedPreferences;


    @Override
    public void insertStringSharedPreferences(String key, String value) {
//        sharedPreferencesEditor.putString(key, value);
//        sharedPreferencesEditor.commit();
        sharedPreferences.edit().putString(key, value);
        sharedPreferences.edit().commit();
    }

    @Override
    public void insertNumFloatSharedPreferences(String key, float value) {
        sharedPreferences.edit().putFloat(key, value);
        sharedPreferences.edit().commit();
    }

    @Override
    public void insertNumIntSharedPreferences(String key, int value) {
        sharedPreferences.edit().putInt(key, value);
        sharedPreferences.edit().commit();
    }

    @Override
    public String getStringSharedPreferences(String key) {
        return sharedPreferences.getString(key, "");
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
