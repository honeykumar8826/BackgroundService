package com.service.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.service.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultipleImageDownloadActivity extends AppCompatActivity {
    private ImageView imageView, imageViewFirst;
    private Handler handler;
    private Button start_thread_pool;
    public static final String TAG = "TAG";
    private static final int DEFAULT_SIZE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_image_download);
        //        initialize the id
        inItId();
//        listener for start  the thread pool
        start_thread_pool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExecutorService executorService = Executors.newFixedThreadPool(DEFAULT_SIZE);
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        Log.i(TAG, "run: first" + Thread.currentThread().getId());
                        firstLoop();

                    }
                });
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        Log.i(TAG, "run:second " + Thread.currentThread().getId());
                        secondLoop();
                    }
                });
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        Log.i(TAG, "run:second " + Thread.currentThread().getId());
                        thirdLoop();
                    }
                });
            }
        });
//        handler for updation on a UI thread
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bitmap bitmap=msg.getData().getParcelable("bitmapValue");
                Bitmap bitmap1=msg.getData().getParcelable("bitmapValue1");
                if (bitmap !=null)
                {
                    imageView.setImageBitmap(bitmap);
                }
                if(bitmap1 !=null)
                {
                    imageViewFirst.setImageBitmap(bitmap1);
                }
                Log.i(TAG, "handleMessageinside : "+ msg.getData().getParcelable("bitmapValue1"));
                Log.i(TAG, "handleMessage: "+msg);
            }
        };

    }

    private void thirdLoop() {
        for (int i = 0; i < 50; i++) {
            Log.i(TAG, "thirdLoop: " + Thread.currentThread().getId());
            Log.i(TAG, "thirdLoop: " + i);
        }
    }

    private void secondLoop() {
        Log.i(TAG, "secondLoop: " + Thread.currentThread().getId());
        ImageView imageView;
        String imgUrl = "https://tineye.com/images/widgets/mona.jpg";
        try {
            InputStream in = new java.net.URL(imgUrl).openStream();
            Log.i(TAG, "doInBackground: " + in);
            Bitmap bmp = BitmapFactory.decodeStream(in);
            Bundle bundle = new Bundle();
            bundle.putParcelable("bitmapValue", bmp);
            Message message = Message.obtain();
            message.setData(bundle);
            handler.sendMessage(message);
            Log.i(TAG, "Bitmap: " + bmp);

        } catch (IOException e) {
            Log.i(TAG, "doInBackground: inside catch second ");
            e.printStackTrace();

        }
  /*      for(int i=1;i<50;i++)
        {
            Log.i(TAG, "secondLoop: "+i);

        }*/
    }

    private void firstLoop() {
        Log.i(TAG, "firstLoop: " + Thread.currentThread().getId());
        String imgUrl = "https://tineye.com/images/widgets/mona.jpg";
        try {
            InputStream in = new java.net.URL(imgUrl).openStream();
            Log.i(TAG, "doInBackground: " + in);
            Bitmap bmp = BitmapFactory.decodeStream(in);
            Log.i(TAG, "Bitmap: " + bmp);
            //imageView.setImageBitmap(bmp);
            Bundle bundle = new Bundle();
            bundle.putParcelable("bitmapValue1", bmp);
            Message message = Message.obtain();
            message.setData(bundle);
            Thread.sleep(1000);
            handler.sendMessage(message);
            Log.i(TAG, "Bitmap: " + bmp);

        } catch (IOException e) {
            Log.i(TAG, "doInBackground: inside catch first ");
            e.printStackTrace();

        } catch (InterruptedException e) {

        }
 /*       for(int i=0;i<50;i++)
        {
            Log.i(TAG, "firstLoop: "+i);
        }*/
    }

    private void inItId() {
        start_thread_pool = findViewById(R.id.btn_thread_pool);
        imageView = findViewById(R.id.img);
        imageViewFirst = findViewById(R.id.img1);
    }
}
