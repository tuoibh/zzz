package com.example.myapplication.ui.fragment.reminder;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.domain.model.reminder.Reminder;
import com.example.myapplication.domain.repo.ImageLoader;
import com.example.myapplication.domain.usecase.reminder.DeleteReminderUseCase;
import com.example.myapplication.domain.usecase.reminder.GetListReminderUseCase;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class ReminderViewModel extends ViewModel {
    private final GetListReminderUseCase getListReminderUseCase;
    private final DeleteReminderUseCase deleteReminderUseCase;
    ImageLoader imageLoader;

    private MutableLiveData<List<Reminder>> ldListReminder = new MutableLiveData<>();
    public LiveData<List<Reminder>> mLdListReminder = ldListReminder;

    @Inject
    public ReminderViewModel(GetListReminderUseCase getListReminderUseCase, DeleteReminderUseCase deleteReminderUseCase, ImageLoader imageLoader) {
        this.getListReminderUseCase = getListReminderUseCase;
        this.deleteReminderUseCase = deleteReminderUseCase;
        this.imageLoader = imageLoader;
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

    public void deleteReminder(Context context, Reminder reminder){
        deleteReminderUseCase.deleteReminderById(reminder.getMovieId());
        reminder.cancelAlarm(context);
        getListReminder();
    }
}
