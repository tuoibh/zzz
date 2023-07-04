package com.example.myapplication.ui.activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.domain.model.Topic;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainViewModel extends ViewModel {

    @Inject
    public MainViewModel() {
    }

    private final MutableLiveData<Topic> topicState = new MutableLiveData<>();
    public LiveData<Topic> mTopicState = topicState;

    public void updateTopic(Topic topic){
        topicState.setValue(topic);
    }
}
