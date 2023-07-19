package com.example.myapplication.domain.usecase.reminder;

import com.example.myapplication.domain.repo.ReminderRepository;

import javax.inject.Inject;

public class DeleteReminderUseCase {
    private ReminderRepository reminderRepository;

    @Inject
    public DeleteReminderUseCase(ReminderRepository reminderRepository) {
        this.reminderRepository = reminderRepository;
    }

    public void deleteReminderById(int reminder_id){
        reminderRepository.deleteReminderById(reminder_id);
    }
}
