package com.example.myapplication.data.local.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import com.example.myapplication.data.converter.UriConverter;
import com.example.myapplication.data.local.dao.MovieDao;
import com.example.myapplication.data.local.dao.ReminderDao;
import com.example.myapplication.data.local.dao.UserDao;
import com.example.myapplication.data.model.movie.MovieResults;
import com.example.myapplication.data.converter.ConverterGenreID;
import com.example.myapplication.data.model.reminder.Reminders;
import com.example.myapplication.data.model.user.Users;

@Database(entities = {MovieResults.class, Users.class, Reminders.class}, version = 1)
@TypeConverters({ConverterGenreID.class, UriConverter.class})
public abstract class MovieDatabase extends RoomDatabase {
    public abstract MovieDao movieDao();
    public abstract UserDao userDao();
    public abstract ReminderDao reminderDao();
}
