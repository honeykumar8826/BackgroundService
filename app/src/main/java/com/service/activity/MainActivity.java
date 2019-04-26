package com.service.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.service.R;
import com.service.service.ServiceImp;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = "TAG";
    public Handler handler;
    String imgUrl[] = {"https://tineye.com/images/widgets/mona.jpg", "https://tineye.com/images/widgets/mona.jpg", "https://tineye.com/images/widgets/mona.jpg"};
    private Button startService, stopService, nextPage,mediaActivity;
    private GetBitmap getBitmap;
    private ImageView firstImg, secondImg, thirdImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        initialize the id
        inItId();
//        initialize the broadcast reciever class
        getBitmap = new GetBitmap();
//        register broadcast reciever

        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(getBitmap, new IntentFilter("getBitmapMainActivity"));
        startService.setOnClickListener(this);
        stopService.setOnClickListener(this);
        nextPage.setOnClickListener(this);
        mediaActivity.setOnClickListener(this);
//        upate the UI part by handler
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

            }
        };
    }

    private void inItId() {
        startService = findViewById(R.id.btn_start_service);
        stopService = findViewById(R.id.btn_stop_service);
        nextPage = findViewById(R.id.btn_next);
        firstImg = findViewById(R.id.first_img);
        secondImg = findViewById(R.id.second_img);
        thirdImg = findViewById(R.id.third_img);
        mediaActivity = findViewById(R.id.btn_media_playerActivity);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start_service:
                startServices();
                break;

            case R.id.btn_stop_service:
                stopServices();
                break;
            case R.id.btn_next:
                nextActivity();
                break;
            case R.id.btn_media_playerActivity:
                moveMediaPlayerActivity();
                break;
            default:
                Toast.makeText(this, "wrong choices", Toast.LENGTH_SHORT).show();
        }
    }

    private void moveMediaPlayerActivity() {
        Intent intent = new Intent(MainActivity.this,MediaPlayerActivity.class);
        startActivity(intent);
    }

    private void nextActivity() {
        Intent intent = new Intent(this, ForegroundServiceExpActivity.class);
        startActivity(intent);
    }

    private void stopServices() {

        stopService(new Intent(this, ServiceImp.class));
        //Toast.makeText(this, "stop", Toast.LENGTH_SHORT).show();
    }

    private void startServices() {

        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                Log.i(TAG, "startServices: " + Thread.currentThread().getId());
                Intent intent = new Intent(MainActivity.this, ServiceImp.class);
                intent.putExtra("urlArray", imgUrl);
                startService(intent);
            }
        };
        thread.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(getBitmap);
    }

    //    broadcast reciever class to recieve the bitmap
    class GetBitmap extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("getBitmapMainActivity")) {
                Log.i(TAG, "onReceive: " + intent.getParcelableArrayExtra("getBitmapRef"));
                Bitmap bmpValue[] = (Bitmap[]) intent.getParcelableArrayExtra("getBitmapRef");
                // Bitmap bitmap[]=intent.getParcelableArrayExtra("")
                // Log.i(TAG, "onReceive: "+);
                if (bmpValue.length > 0) {
                    firstImg.setImageBitmap(bmpValue[0]);
                    secondImg.setImageBitmap(bmpValue[1]);
                    thirdImg.setImageBitmap(bmpValue[2]);
                }
                // Log.i(TAG, "onReceive: "+bmpValue);
                //imageView.setImageBitmap(bmpValue);
            }
        }
    }
}
