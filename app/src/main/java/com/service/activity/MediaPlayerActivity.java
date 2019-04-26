package com.service.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.service.R;
import com.service.service.ServiceBoundExp;

public class MediaPlayerActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
    private Button play, stop, pause,mediaFun;
    private ServiceBoundExp serviceBoundExp;
    private boolean isBind = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);
//        initialize the id
        inItId();
//        listener for play button
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isBind)
                {
                    serviceBoundExp.play();
                }
            }
        });
        //        listener for pause button
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBind) {
                    serviceBoundExp.pause();
                }

            }
        });
//        listener for stop button
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isBind)
                {
                    serviceBoundExp.stopPlayer();
                }
            }
        });
 //        listener for stop button
        mediaFun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pageIntent = new Intent(MediaPlayerActivity.this,MediaPlayerFunActivity.class);
                startActivity(pageIntent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        /*bind the service with the activity*/
        final Intent intent = new Intent(MediaPlayerActivity.this, ServiceBoundExp.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    //    create the connection
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "onServiceConnected: " + name);
            ServiceBoundExp.LocalService localService = (ServiceBoundExp.LocalService) service;
            serviceBoundExp = localService.getInstance();
            Log.i(TAG, "onServiceConnected: " + serviceBoundExp);
            isBind = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    private void inItId() {
        pause = findViewById(R.id.btn_pause);
        play = findViewById(R.id.btn_play);
        stop = findViewById(R.id.btn_stop);
        mediaFun = findViewById(R.id.btn_media_fun);
    }


    @Override
    protected void onStop() {
        super.onStop();
     /*   if(isBind)
        {
            unbindService(mConnection);
        }
        isBind= false;*/

    }

}
