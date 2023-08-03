package com.example.myapplication.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.example.myapplication.core.AppConfig;

public class ReminderMovieReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction()!=null){
            if(intent.getAction().equals(AppConfig.Companion.ACTION_NOTI)){
                setAlarmOneOn(context, intent);
            } else if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
                setAlarmAllOn(context);
            }
        }
    }

    private void setAlarmAllOn(Context context) {
    }

    private void setAlarmOneOn(Context context, Intent intent){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent aIntent = new Intent(context, ReminderMovieService.class);
            String title = intent.getStringExtra(AppConfig.Companion.NOTIFICATION_TITLE_MOVIE);
            int id = intent.getIntExtra(AppConfig.Companion.NOTIFICATION_ID_MOVIE, 0);
            String url_poster = intent.getStringExtra(AppConfig.Companion.NOTIFICATION_URL_POSTER_MOVIE);
            String vote_average = intent.getStringExtra(AppConfig.Companion.NOTIFICATION_VOTE_AVERAGE_MOVIE);

            aIntent.putExtra(AppConfig.Companion.NOTIFICATION_ID_MOVIE, id);
            aIntent.putExtra(AppConfig.Companion.NOTIFICATION_TITLE_MOVIE, title);
            aIntent.putExtra(AppConfig.Companion.NOTIFICATION_URL_POSTER_MOVIE, url_poster);
            aIntent.putExtra(AppConfig.Companion.NOTIFICATION_VOTE_AVERAGE_MOVIE, vote_average);
            context.startService(aIntent);
        }
    }

}
