package com.service.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.service.R;
import com.service.service.ServiceImpForeground;
import com.service.service.ServiceIntentExp;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class IntentServiceActivity extends AppCompatActivity {
private ImageView imageView;
private  Bitmap bmp;
private Button startIntentService,moveMultipleImg;
private EditText userInputValue;
private static final String TAG="TAG";
private getBitmap getBitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_service);
//        initialize the id
        inItId();
//        intialization of getBitmap class
            getBitmap = new IntentServiceActivity.getBitmap();
//        register broadcast reciever
            registerReceiver(getBitmap,new IntentFilter("getBitmap"));
//        call or execute the thread class
        /*new ImageDownload().execute();*/

//        listener for button start intent service
        startIntentService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String imgUrl ="https://tineye.com/images/widgets/mona.jpg";
                Intent serviceIntent = new Intent(IntentServiceActivity.this, ServiceIntentExp.class);
               // serviceIntent.putExtra("imgUrl","https://tineye.com/images/widgets/mona.jpg");
                serviceIntent.putExtra("imgView",imgUrl);
                startService(serviceIntent);
            }
        });


//        listener for to move on a next Activity or screen
        moveMultipleImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntentServiceActivity.this,MultipleImageDownloadActivity.class);
                startActivity(intent);
            }
        });
    }

    private void inItId() {
        imageView = findViewById(R.id.img);
        startIntentService = findViewById(R.id.btn_intent_service);
        userInputValue = findViewById(R.id.et_userInput);
        moveMultipleImg = findViewById(R.id.btn_next_multiple_img);
    }

    /*image downloading by using thread async class */

  /*  class ImageDownload extends AsyncTask<Void,Void,Bitmap>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            URL url = null;
            String imgUrl ="https://tineye.com/images/widgets/mona.jpg";
            try {
                InputStream in = new java.net.URL(imgUrl).openStream();
                Log.i(TAG, "doInBackground: "+in);
                bmp = BitmapFactory.decodeStream(in);
                Log.i(TAG, "doInBackground: "+bmp);
            } catch (IOException e) {
                Log.i(TAG, "doInBackground: inside catch ");
                e.printStackTrace();
                
            }
     *//*       try {
                url = new URL("http://tineye.com/images/widgets/mona.jpg");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                Log.i(TAG, "doInBackground: ");
            } catch (IOException e) {
                e.printStackTrace();
            }*//*
           // Log.i(TAG, "doInBackground: "+bmp);
            return bmp;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bmp);
        }
    }*/

//    end of async class
    class getBitmap extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("getBitmap"))
        {
            Bitmap bmpValue = intent.getParcelableExtra("getBitmapRef");
            Log.i(TAG, "onReceive: "+bmpValue);
            imageView.setImageBitmap(bmpValue);
        }
    }
}

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(getBitmap);
    }
}
