package com.service.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.service.activity.MainActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ServiceImp extends Service {
    private final String TAG="TAG";
    private Bitmap[] bitmapArray = new Bitmap[3];

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: ");
    }
//    when background service will start
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i(TAG, "onStartCommand: "+Thread.currentThread().getId());
        final String url[] = intent.getStringArrayExtra("urlArray");
        Log.i(TAG, "onStartCommand:with url value "+url[0]);
        new Thread(new Runnable() {
            @Override
            public void run() {
               for(int i=0;i<url.length;i++)
               {
                   try {
                       InputStream in = new URL(url[i]).openStream();
                       Log.i(TAG, "InputStream: " + in);
                       Bitmap bmp = BitmapFactory.decodeStream(in);
                       Log.i(TAG, "Bitmap1: " + bmp);
                       bitmapArray[i] = bmp;
                   } catch (IOException e) {
                       Log.i(TAG, "doInBackground: inside catch first ");
                       e.printStackTrace();
                   }
               }
                Intent broadcastIntent = new Intent("getBitmapMainActivity");
                Log.i(TAG, "run: "+bitmapArray.length);
                broadcastIntent.putExtra("getBitmapRef",bitmapArray);
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(broadcastIntent);
            }
        }).start();
        Toast.makeText(this, "inside service", Toast.LENGTH_SHORT).show();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind: ");
        return null;
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        super.unbindService(conn);
        Log.i(TAG, "unbindService: ");
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.i(TAG, "onRebind: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }
}
