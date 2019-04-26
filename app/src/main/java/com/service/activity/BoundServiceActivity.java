package com.service.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.service.R;
import com.service.service.ServiceBoundExp;

public class BoundServiceActivity extends AppCompatActivity {
private Button firstMs,secondMs,nextIntentService;
private TextView showMs;
private ServiceBoundExp serviceBoundExp;
private static final String TAG ="TAG";
private boolean isBind = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bound_service);
//        initialize the id
        inItId();
        /*bind the service with the activity*/
        Intent intent = new Intent(BoundServiceActivity.this, ServiceBoundExp.class);
        bindService(intent,mConnection, Context.BIND_AUTO_CREATE);
//        listener for button first
        firstMs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceBoundExp.firstMessage();
                showMs.setText(serviceBoundExp.firstMessage());
            }
        });
//        listener for button second
        secondMs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showMs.setText(serviceBoundExp.secondMessage());
            }
        });
//        listener for button next page or activity
        nextIntentService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(BoundServiceActivity.this,IntentServiceActivity.class);
                startActivity(intent1);
            }
        });

    }

    private void inItId() {
        firstMs = findViewById(R.id.btn_first_ms);
        secondMs = findViewById(R.id.btn_second_ms);
        showMs = findViewById(R.id.tv_show_ms);
        nextIntentService = findViewById(R.id.btn_next_intentService);
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "onServiceConnected: "+name);
        ServiceBoundExp.LocalService localService = (ServiceBoundExp.LocalService)service;
        serviceBoundExp = localService.getInstance();
            Log.i(TAG, "onServiceConnected: "+serviceBoundExp);
            isBind = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        if(isBind)
        {
            unbindService(mConnection);
            isBind = false;
        }
    }
}
