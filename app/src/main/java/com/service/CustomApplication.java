package com.service;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class CustomApplication extends Application {
    public static final String CHANNEL_ID="exampleServiceChannel1";
    @Override
    public void onCreate() {
        super.onCreate();
        makeNotificationChannel();
    }


    private void makeNotificationChannel() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,"Example Service channel1"
                    , NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }

    }
}
