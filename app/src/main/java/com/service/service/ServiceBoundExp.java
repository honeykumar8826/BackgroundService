package com.service.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.service.activity.MediaPlayerActivity;

public class ServiceBoundExp  extends Service {

    private  final IBinder mBinder = new LocalService();
    private MediaPlayer mediaPlayer;
    public static final String TAG ="TAG";
    @Override
    public void onCreate() {
        super.onCreate();
        if(mediaPlayer==null)
        {

            mediaPlayer=   MediaPlayer.create(ServiceBoundExp.this, Settings.System.DEFAULT_RINGTONE_URI);
            Log.i(TAG, "mediaPlayer: "+mediaPlayer);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlayer();
                }
            });
        }

    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

   public class LocalService extends Binder
   {
      public ServiceBoundExp getInstance()
       {
           return ServiceBoundExp.this;
       }
   }

   public String firstMessage()
   {
       return "first message";
   }
   public String secondMessage()
   {
       return "second message";
   }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopPlayer();
    }

    //   public client method
    public boolean isPlaying()
    {
        return mediaPlayer.isPlaying();
    }
    public void stopPlayer()
    {
        if(mediaPlayer!=null){
            mediaPlayer.release();
           // mediaPlayer=null;
            Toast.makeText(this, "Media Player has been stopped", Toast.LENGTH_SHORT).show();
        }
    }
    public void play()
    {
        mediaPlayer.start();
    }
    public void pause()
    {
        mediaPlayer.pause();
    }

}
