package com.example.myapplication.di;

import com.example.myapplication.data.local.dao.ReminderDao;
import com.example.myapplication.data.repo.ReminderRepositoryImpl;
import com.example.myapplication.domain.repo.ReminderRepository;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class ReminderRepositoryModule {
    @Provides
    @Singleton
    public ReminderRepository provideReminderRepo(ReminderDao reminderDao){
        return new ReminderRepositoryImpl(reminderDao);
    }
}
