package com.example.myapplication.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class ReminderMovieService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    private void sendNotification() {
        /*val icon = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
        val notificationBuilder: NotificationCompat.Builder = NotificationCompat.Builder(this,)

        notificationBuilder.setSmallIcon(R.drawable.ic_launcher_foreground)
        notificationBuilder.setContentTitle("Foreground Service")
        notificationBuilder.setContentText("Foreground Service started")
        notificationBuilder.setLargeIcon(icon)
        notificationBuilder.priority = NotificationCompat.PRIORITY_HIGH
        startForeground(101*//* Notification Id *//*, notificationBuilder.build())*/

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
    }
}
