package com.example.myapplication.domain.usecase.reminder;

import com.example.myapplication.domain.model.detail.MovieDetailResponse;
import com.example.myapplication.domain.model.reminder.Reminder;
import com.example.myapplication.domain.repo.ReminderRepository;

import javax.inject.Inject;

public class InsertReminderUseCase {

    ReminderRepository reminderRepository;
    @Inject
    public InsertReminderUseCase(ReminderRepository reminderRepository) {
        this.reminderRepository = reminderRepository;
    }

    public void addReminder(Reminder reminder){
        reminderRepository.insertReminder(reminder);
    }
}
