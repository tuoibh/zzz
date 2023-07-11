package com.example.myapplication.ui.activity;

import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.core.AppConfig;
import com.example.myapplication.domain.model.Topic;
import com.example.myapplication.domain.usecase.GetSettingsInforSharedPreferenceUseCase;
import com.example.myapplication.domain.usecase.InsertSettingsInforSharedPreferenceUseCase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainViewModel extends ViewModel {
    private final InsertSettingsInforSharedPreferenceUseCase insertSettingsInforSharedPreferenceUseCase;
    private final GetSettingsInforSharedPreferenceUseCase getSettingsInforSharedPreferenceUseCase;

    List<Topic> listTopic = new ArrayList<Topic>();

    @Inject
    public MainViewModel(InsertSettingsInforSharedPreferenceUseCase insertSettingsInforSharedPreferenceUseCase, GetSettingsInforSharedPreferenceUseCase getSettingsInforSharedPreferenceUseCase) {
        this.insertSettingsInforSharedPreferenceUseCase = insertSettingsInforSharedPreferenceUseCase;
        this.getSettingsInforSharedPreferenceUseCase = getSettingsInforSharedPreferenceUseCase;
    }

    private final MutableLiveData<Topic> topicState = new MutableLiveData<>();
    public LiveData<Topic> mTopicState = topicState;

    public void updateTopic(Topic topic){
        insertSettingsInforSharedPreferenceUseCase.insertString(AppConfig.Companion.KEY_TOPIC, topic.key);
        getTopic();
    }

    public void getTopic(){
        Topic topic = listTopic.get(0);
        String str = getSettingsInforSharedPreferenceUseCase.getString(AppConfig.Companion.KEY_TOPIC);
        for(Topic item: listTopic){
            if(item.key.equals(str)){
                topic = item;
            }
        }
        topicState.postValue(topic);
    }

    public void createListTopic(){
        listTopic.add(new Topic(AppConfig.Companion.POPULAR, "POPULAR"));
        listTopic.add(new Topic(AppConfig.Companion.TOP_RATED, "TOP RATE"));
        listTopic.add(new Topic(AppConfig.Companion.UP_COMING, "UP COMING"));
        listTopic.add(new Topic(AppConfig.Companion.NOW_PLAYING, "NOW PLAYING"));
    }

}
