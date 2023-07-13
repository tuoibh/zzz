package com.example.myapplication.ui.activity;

import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.R;
import com.example.myapplication.core.AppConfig;
import com.example.myapplication.data.repo.user.User;
import com.example.myapplication.domain.model.Topic;
import com.example.myapplication.domain.usecase.GetSettingsInforSharedPreferenceUseCase;
import com.example.myapplication.domain.usecase.GetUserInfoUseCase;
import com.example.myapplication.domain.usecase.InsertSettingsInforSharedPreferenceUseCase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainViewModel extends ViewModel {
    private final InsertSettingsInforSharedPreferenceUseCase insertSettingsInforSharedPreferenceUseCase;
    private final GetSettingsInforSharedPreferenceUseCase getSettingsInforSharedPreferenceUseCase;
    private final GetUserInfoUseCase getUserInfoUseCase;

    List<Topic> listTopic = new ArrayList<Topic>();

    @Inject
    public MainViewModel(InsertSettingsInforSharedPreferenceUseCase insertSettingsInforSharedPreferenceUseCase, GetSettingsInforSharedPreferenceUseCase getSettingsInforSharedPreferenceUseCase, GetUserInfoUseCase getUserInfoUseCase) {
        this.insertSettingsInforSharedPreferenceUseCase = insertSettingsInforSharedPreferenceUseCase;
        this.getSettingsInforSharedPreferenceUseCase = getSettingsInforSharedPreferenceUseCase;
        this.getUserInfoUseCase = getUserInfoUseCase;
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

    public User getUser(String packageName){
        User userGet = getUserInfoUseCase.getUserInfo();
        if (userGet != null) return userGet;
        return new User(1, "User name", "Email", "YYYY/MM/DD", "None",
                Uri.parse("android.resource://" + packageName + "/" + R.drawable.ic_default_avatar));
    }
}
