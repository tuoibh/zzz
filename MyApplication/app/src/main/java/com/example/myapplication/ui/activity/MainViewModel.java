package com.example.myapplication.ui.activity;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.R;
import com.example.myapplication.core.AppConfig;
import com.example.myapplication.domain.model.reminder.Reminder;
import com.example.myapplication.domain.model.user.User;
import com.example.myapplication.domain.model.Topic;
import com.example.myapplication.domain.usecase.reminder.GetListReminderUseCase;
import com.example.myapplication.domain.usecase.setting.GetSettingsInforSharedPreferenceUseCase;
import com.example.myapplication.domain.usecase.profile.GetUserInfoUseCase;
import com.example.myapplication.domain.usecase.setting.InsertSettingsInforSharedPreferenceUseCase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class MainViewModel extends ViewModel {
    private final InsertSettingsInforSharedPreferenceUseCase insertSettingsInforSharedPreferenceUseCase;
    private final GetSettingsInforSharedPreferenceUseCase getSettingsInforSharedPreferenceUseCase;
    private final GetUserInfoUseCase getUserInfoUseCase;
    private final GetListReminderUseCase getListReminderUseCase;

    List<Topic> listTopic = new ArrayList<Topic>();

    @Inject
    public MainViewModel(InsertSettingsInforSharedPreferenceUseCase insertSettingsInforSharedPreferenceUseCase, GetSettingsInforSharedPreferenceUseCase getSettingsInforSharedPreferenceUseCase, GetUserInfoUseCase getUserInfoUseCase, GetListReminderUseCase getListReminderUseCase) {
        this.insertSettingsInforSharedPreferenceUseCase = insertSettingsInforSharedPreferenceUseCase;
        this.getSettingsInforSharedPreferenceUseCase = getSettingsInforSharedPreferenceUseCase;
        this.getUserInfoUseCase = getUserInfoUseCase;
        this.getListReminderUseCase = getListReminderUseCase;
    }

    private final MutableLiveData<Topic> topicState = new MutableLiveData<>();
    public LiveData<Topic> mTopicState = topicState;

    private final MutableLiveData<List<Reminder>> ldListReminder = new MutableLiveData<>();
    public LiveData<List<Reminder>> mLdReminder = ldListReminder;

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

    public void getListReminder(){
        getListReminderUseCase.getListReminder().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Reminder>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<Reminder> reminders) {
                        ldListReminder.postValue(reminders);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
}
