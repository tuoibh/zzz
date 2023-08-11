package com.example.myapplication.data.repo;

import com.example.myapplication.data.local.dao.ReminderDao;
import com.example.myapplication.data.model.reminder.Reminders;
import com.example.myapplication.domain.model.reminder.Reminder;
import com.example.myapplication.domain.repo.ReminderRepository;
import org.modelmapper.ModelMapper;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.rxjava3.core.Single;

public class ReminderRepositoryImpl implements ReminderRepository {

    ReminderDao reminderDao;
    @Inject
    public ReminderRepositoryImpl(ReminderDao reminderDao) {
        this.reminderDao = reminderDao;
    }

    @Override
    public void insertReminder(Reminder reminder) {
        ModelMapper modelMapper = new ModelMapper();
        reminderDao.insertReminder(modelMapper.map(reminder, Reminders.class));
    }

    @Override
    public void deleteReminderById(int id) {
        reminderDao.deleteReminderById(id);
    }

    @Override
    public Single<List<Reminder>> getListReminder() {
        ModelMapper modelMapper = new ModelMapper();
        List<Reminder> reminderList = new ArrayList<>();
        return reminderDao.getListReminder().map(list -> {
            for(Reminders item : list){
                reminderList.add(modelMapper.map(item, Reminder.class));
            }
            return reminderList;
        });
    }
}
