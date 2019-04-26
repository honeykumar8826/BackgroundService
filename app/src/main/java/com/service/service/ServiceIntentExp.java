package com.service.service;

import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.ImageView;

import com.service.R;

import java.io.IOException;
import java.io.InputStream;

import static com.service.CustomApplication.CHANNEL_ID;

public class ServiceIntentExp extends IntentService {
    private PowerManager.WakeLock wakeLock ;
    private static final String TAG="TAG";
    public ServiceIntentExp() {
        super("name");
        setIntentRedelivery(true);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: ");
        PowerManager powerManager = (PowerManager)getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"Example:WakeLog");
        wakeLock.acquire();
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            Notification notification = new NotificationCompat.Builder(this,CHANNEL_ID)
                    .setContentText("Example Intent Service")
                    .setContentTitle("Notification Example")
                    .setSmallIcon(R.drawable.ic_call_black_24dp)
                    .build();
            startForeground(1,notification);
        }
    }

    @Override
    protected void onHandleIntent( Intent intent) {
        ImageView imageView;
        String imgUrl = intent.getStringExtra("imgUrl");
       // Log.i(TAG, "onHandleIntent: "+imgUrl);
//        for fetch the image by url
       // String imgUrl ="https://tineye.com/images/widgets/mona.jpg";
        try {
            InputStream in = new java.net.URL(imgUrl).openStream();
            Log.i(TAG, "doInBackground: "+in);
           Bitmap bmp = BitmapFactory.decodeStream(in);
            Intent broadcastIntent = new Intent("getBitmap");
            broadcastIntent.putExtra("getBitmapRef",bmp);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("getBitmap");
            sendBroadcast(broadcastIntent);
            Log.i(TAG, "doInBackground: "+bmp);

        } catch (IOException e) {
            Log.i(TAG, "doInBackground: inside catch ");
            e.printStackTrace();

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
        wakeLock.release();
    }
}
