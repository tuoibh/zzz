package com.example.myapplication.domain.usecase;

import com.example.myapplication.domain.repo.SettingInfoRepository;

import javax.inject.Inject;

public class GetSettingsInforSharedPreferenceUseCase {
    @Inject
    public GetSettingsInforSharedPreferenceUseCase() {
    }
    @Inject
    SettingInfoRepository settingInfoRepository;


    public String getString(String key){
        return settingInfoRepository.getStringSharedPreferences(key);
    }
}
