package com.example.myapplication.domain.model.reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.myapplication.core.AppConfig;
import com.example.myapplication.service.ReminderMovieReceiver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Reminder {
    private int movieId;
    private String movieName;
    private String posterUrl;
    private String voteAverage;
    private String dateReminder;
    private String timeReminder;

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getDateReminder() {
        return dateReminder;
    }

    public void setDateReminder(String dateReminder) {
        this.dateReminder = dateReminder;
    }

    public String getTimeReminder() {
        return timeReminder;
    }

    public void setTimeReminder(String timeReminder) {
        this.timeReminder = timeReminder;
    }

    public Reminder() {
    }

    public Reminder(int movieId, String movieName, String posterUrl, String voteAverage, String dateReminder, String timeReminder) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.posterUrl = posterUrl;
        this.voteAverage = voteAverage;
        this.dateReminder = dateReminder;
        this.timeReminder = timeReminder;
    }

    @Override
    public String toString() {
        return "Reminder{" + "movieId=" + movieId + ", movieName='" + movieName + '\'' + ", posterUrl='" + posterUrl + '\'' + ", voteAverage='" + voteAverage + '\'' + ", dateReminder='" + dateReminder + '\'' + ", timeReminder='" + timeReminder + '\'' + '}';
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public void setAlarm(Context context) {
        Intent intent = new Intent(context, ReminderMovieReceiver.class);
        intent.setAction(AppConfig.Companion.ACTION_NOTI);
        intent.putExtra(AppConfig.Companion.NOTIFICATION_ID_MOVIE, this.movieId);
        intent.putExtra(AppConfig.Companion.NOTIFICATION_TITLE_MOVIE, this.movieName);
        intent.putExtra(AppConfig.Companion.NOTIFICATION_URL_POSTER_MOVIE, this.getPosterUrl());
        intent.putExtra(AppConfig.Companion.NOTIFICATION_VOTE_AVERAGE_MOVIE, this.voteAverage);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 101, intent,
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                convertToTimeInMilis(this.dateReminder + " " + this.timeReminder),
                pendingIntent);
    }

    public void cancelAlarm(Context context){
        Intent intent = new Intent(context, ReminderMovieReceiver.class);
        intent.setAction(AppConfig.Companion.ACTION_NOTI);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 101, intent,
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }
    public Long convertToTimeInMilis(String dateString) {
        long timeInMillis = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        try {
            Date date = format.parse(dateString);
            timeInMillis = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeInMillis;
    }
}
