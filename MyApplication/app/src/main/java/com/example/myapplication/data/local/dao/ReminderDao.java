package com.example.myapplication.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.myapplication.data.model.reminder.Reminders;
import java.util.List;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface ReminderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertReminder(Reminders reminders);

    @Query("DELETE FROM reminder WHERE movieId = :reminderId")
    void deleteReminderById(int reminderId);

    @Query("select*from reminder")
    Single<List<Reminders>> getListReminder();
}
