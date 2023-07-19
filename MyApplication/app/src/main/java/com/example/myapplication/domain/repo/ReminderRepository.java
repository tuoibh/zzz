package com.example.myapplication.domain.repo;


import com.example.myapplication.domain.model.reminder.Reminder;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface ReminderRepository {
    public void insertReminder(Reminder reminder);
    public void deleteReminderById(int id);
    public Single<List<Reminder>> getListReminder();
}
