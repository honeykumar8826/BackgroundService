package com.service.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.service.R;
import com.service.service.ServiceImpForeground;

public class ForegroundServiceExpActivity extends AppCompatActivity {
    private Button startService, stopService,nextPage;
    private EditText userInput;
    private String userInputValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foreground_service_exp);
//        initialize the id
        inItId();
//        listener for start service
        startService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInputValue = userInput.getText().toString();
                Intent serviceIntent = new Intent(ForegroundServiceExpActivity.this, ServiceImpForeground.class);
                serviceIntent.putExtra("inputValue",userInputValue);
                startService(serviceIntent);
            }
        });
//        listener for stop service
        stopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent serviceIntent = new Intent(ForegroundServiceExpActivity.this,ServiceImpForeground.class);
                stopService(serviceIntent);
            }
        });

        //        listener for next page or activity

        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForegroundServiceExpActivity.this, BoundServiceActivity.class);
                startActivity(intent);
            }
        });
    }




    private void inItId() {
        startService = findViewById(R.id.btn_foreServiceStart);
        stopService = findViewById(R.id.btn_foreServiceStop);
        userInput = findViewById(R.id.et_user_input);
        nextPage = findViewById(R.id.btn_next);
    }
}
