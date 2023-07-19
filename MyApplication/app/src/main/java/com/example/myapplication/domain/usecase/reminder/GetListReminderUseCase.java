package com.example.myapplication.domain.usecase.reminder;

import android.util.Log;

import com.example.myapplication.domain.model.reminder.Reminder;
import com.example.myapplication.domain.repo.ReminderRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GetListReminderUseCase {
    ReminderRepository reminderRepository;

    @Inject
    public GetListReminderUseCase(ReminderRepository reminderRepository) {
        this.reminderRepository = reminderRepository;
    }

    public Single<List<Reminder>> getListReminder(){
        return reminderRepository.getListReminder();
    }
}
