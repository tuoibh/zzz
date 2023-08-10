package com.example.myapplication.domain.repo;

public interface SettingInfoRepository {

    public void insertStringSharedPreferences(String key, String value);
    public void insertNumFloatSharedPreferences(String key, float value);
    public void insertNumIntSharedPreferences(String key, int value);
    public String getStringSharedPreferences(String key, String defValue);
    public float getNumFloatSharedPreferences(String key);
    public int getNumIntSharedPreferences(String key);
}
