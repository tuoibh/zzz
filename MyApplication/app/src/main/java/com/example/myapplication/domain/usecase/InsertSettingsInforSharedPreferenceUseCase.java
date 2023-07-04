package com.example.myapplication.domain.usecase;

import com.example.myapplication.domain.repo.SettingInfoRepository;

import javax.inject.Inject;

public class InsertSettingsInforSharedPreferenceUseCase {
    @Inject
    public InsertSettingsInforSharedPreferenceUseCase(SettingInfoRepository settingInfoRepository) {
        this.settingInfoRepository = settingInfoRepository;
    }

    @Inject
    SettingInfoRepository settingInfoRepository;

    public void insertString(String key, String value){
        settingInfoRepository.insertStringSharedPreferences(key, value);
    }
}
