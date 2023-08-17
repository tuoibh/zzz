package com.example.myapplication.ui.fragment.settings;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.core.AppConfig;
import com.example.myapplication.domain.usecase.setting.GetSettingsInforSharedPreferenceUseCase;
import com.example.myapplication.domain.usecase.setting.InsertSettingsInforSharedPreferenceUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class SettingsViewModel extends ViewModel {
    private final InsertSettingsInforSharedPreferenceUseCase insertSettingsInforSharedPreferenceUseCase;

    @Inject
    public SettingsViewModel(InsertSettingsInforSharedPreferenceUseCase insertSettingsInforSharedPreferenceUseCase, GetSettingsInforSharedPreferenceUseCase getSettingsInforSharedPreferenceUseCase) {
        this.insertSettingsInforSharedPreferenceUseCase = insertSettingsInforSharedPreferenceUseCase;
    }

    private final MutableLiveData<Float> ldMoviePoint = new MutableLiveData<>(0f);
    public LiveData<Float> mLdMoviePoint = ldMoviePoint;

    private final MutableLiveData<Integer> ldYear = new MutableLiveData<>(2023);
    public LiveData<Integer> mLdYear = ldYear;
    public void updateSettingMoviePoint(float point){
        ldMoviePoint.postValue(point);
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(300L);
                insertSettingsInforSharedPreferenceUseCase.insertFloat(AppConfig.Companion.KEY_POINT, point);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();
    }

    public void updateSettingYear(int year){
        ldYear.postValue(year);
        insertSettingsInforSharedPreferenceUseCase.insertInt(AppConfig.Companion.KEY_YEAR, year);
    }

    public void updateFilterTopic(String filterTopic){
        insertSettingsInforSharedPreferenceUseCase.insertString(AppConfig.Companion.KEY_TOPIC, filterTopic);
    }
    public void updateSortKey(String sortKey){
        insertSettingsInforSharedPreferenceUseCase.insertString(AppConfig.Companion.KEY_SORT, sortKey);
    }

}
