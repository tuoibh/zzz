package com.example.myapplication.service;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.bumptech.glide.Glide;
import com.example.myapplication.App;
import com.example.myapplication.DaggerApp_HiltComponents_SingletonC;
import com.example.myapplication.R;
import com.example.myapplication.core.AppConfig;
import com.example.myapplication.data.repo.ImageLoaderImpl;
import com.example.myapplication.di.ImageLoaderModule;
import com.example.myapplication.domain.model.reminder.Reminder;
import com.example.myapplication.domain.repo.ImageLoader;
import com.example.myapplication.domain.usecase.reminder.GetListReminderUseCase;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

import javax.inject.Inject;

import dagger.hilt.android.EntryPointAccessors;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ReminderMovieService extends Service {

    @Inject
    GetListReminderUseCase getListReminderUseCase;
    ImageLoader imageLoader = new ImageLoaderImpl(this);
    private List<Reminder> reminderList;

    @Override
    public void onCreate() {
        super.onCreate();
        EntryPointAccessors.fromApplication(this, DaggerApp_HiltComponents_SingletonC.class);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createNotificationChannel();
        createNotify(intent);
        return START_NOT_STICKY;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String name = "movie";
            String descriptionText = "notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel mChannel = new NotificationChannel(AppConfig.Companion
                    .CHANNEL_ID, name, importance);
            mChannel.setDescription(descriptionText);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(mChannel);
        }
    }

    private void createNotify(Intent intent) {
        String imageUrl = intent.getStringExtra(AppConfig.Companion.NOTIFICATION_URL_POSTER_MOVIE);
        try{
            Notification notification = new NotificationCompat.Builder(this, AppConfig.Companion.CHANNEL_ID)
                    .setLargeIcon(imageLoader.getBitmap(imageUrl))
                    .setContentTitle(intent.getStringExtra(AppConfig.Companion.NOTIFICATION_TITLE_MOVIE))
                    .setContentText(intent.getStringExtra(AppConfig.Companion.NOTIFICATION_VOTE_AVERAGE_MOVIE) +"/10")
                    .setAutoCancel(true)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .build();
            startForeground(101, notification);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void getListReminder(){
        getListReminderUseCase.getListReminder().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Reminder>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<Reminder> reminders) {
                        reminderList = reminders;
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
}
