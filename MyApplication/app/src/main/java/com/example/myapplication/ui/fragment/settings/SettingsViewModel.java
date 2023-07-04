package com.example.myapplication.ui.fragment.settings;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.core.AppConfig;
import com.example.myapplication.domain.usecase.GetSettingsInforSharedPreferenceUseCase;
import com.example.myapplication.domain.usecase.InsertSettingsInforSharedPreferenceUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class SettingsViewModel extends ViewModel {
    @Inject
    public SettingsViewModel(InsertSettingsInforSharedPreferenceUseCase insertSettingsInforSharedPreferenceUseCase, GetSettingsInforSharedPreferenceUseCase getSettingsInforSharedPreferenceUseCase) {

    }

    @Inject
    InsertSettingsInforSharedPreferenceUseCase insertSettingsInforSharedPreferenceUseCase;
    @Inject
    GetSettingsInforSharedPreferenceUseCase getSettingsInforSharedPreferenceUseCase;



    private MutableLiveData<Float> ldMoviePoint = new MutableLiveData<>(0f);
    public LiveData<Float> mLdMoviePoint = ldMoviePoint;

    private MutableLiveData<Integer> ldYear = new MutableLiveData<>(2023);
    public LiveData<Integer> mLdYear = ldYear;

    private MutableLiveData<String> ldFilterTopic = new MutableLiveData<>();
    public LiveData<String> mLdFilterTopic = ldFilterTopic;

    public void updateSettingMoviePoint(float point){
        ldMoviePoint.postValue(point);
    }

    public void updateSettingYear(int year){
        ldYear.postValue(year);
    }

    public void updateFilterTopic(String filterTopic){
        ldFilterTopic.postValue(filterTopic);
        insertSettingsInforSharedPreferenceUseCase.insertString(AppConfig.Companion.KEY_TOPIC, filterTopic);
        Log.d("tbh_", "updateFilterTopic: SettingsViewModel" + getSettingsInforSharedPreferenceUseCase.getString(AppConfig.Companion.KEY_TOPIC));
    }

}
