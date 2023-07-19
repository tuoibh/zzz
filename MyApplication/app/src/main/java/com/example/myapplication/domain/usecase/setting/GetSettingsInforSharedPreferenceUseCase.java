package com.example.myapplication.domain.usecase.setting;

import com.example.myapplication.domain.repo.SettingInfoRepository;

import javax.inject.Inject;

public class GetSettingsInforSharedPreferenceUseCase {
    private final SettingInfoRepository settingInfoRepository;
    @Inject
    public GetSettingsInforSharedPreferenceUseCase(SettingInfoRepository settingInfoRepository) {
        this.settingInfoRepository = settingInfoRepository;
    }
    public String getString(String key){
        return settingInfoRepository.getStringSharedPreferences(key);
    }

    public float getFloat(String key){
        return settingInfoRepository.getNumFloatSharedPreferences(key);
    }

    public int getInt(String key){
        return settingInfoRepository.getNumIntSharedPreferences(key);
    }
}
