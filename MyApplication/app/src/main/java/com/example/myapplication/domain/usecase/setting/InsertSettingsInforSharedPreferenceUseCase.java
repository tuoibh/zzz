package com.example.myapplication.domain.usecase.setting;

import com.example.myapplication.domain.repo.SettingInfoRepository;

import javax.inject.Inject;

public class InsertSettingsInforSharedPreferenceUseCase {
    private final SettingInfoRepository settingInfoRepository;
    @Inject
    public InsertSettingsInforSharedPreferenceUseCase(SettingInfoRepository settingInfoRepository) {
        this.settingInfoRepository = settingInfoRepository;
    }

    public void insertString(String key, String value){
        settingInfoRepository.insertStringSharedPreferences(key, value);
    }

    public void insertFloat(String key, float value){
        settingInfoRepository.insertNumFloatSharedPreferences(key, value);
    }

    public void insertInt(String key, int value){
        settingInfoRepository.insertNumIntSharedPreferences(key, value);
    }
}
