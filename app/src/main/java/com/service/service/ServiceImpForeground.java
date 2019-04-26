package com.service.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.service.R;
import com.service.activity.ForegroundServiceExpActivity;

import static com.service.CustomApplication.CHANNEL_ID;

public class ServiceImpForeground extends Service {

public static final String TAG ="TAG";
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input = intent.getStringExtra("inputValue");
        Intent notificationIntent = new Intent(this, ForegroundServiceExpActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0,notificationIntent,0);
        Notification notification = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setContentText(input)
                .setContentTitle("Notification Example")
                .setSmallIcon(R.drawable.ic_call_black_24dp)
                .setContentIntent(pendingIntent).build();
        startForeground(1,notification);
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }
}
