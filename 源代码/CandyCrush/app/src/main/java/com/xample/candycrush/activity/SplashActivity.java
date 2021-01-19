package com.xample.candycrush.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.xample.candycrush.R;


public class SplashActivity extends AppCompatActivity {

    private static final int MSG1 = 1;
    private Handler myHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        myHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case MSG1:
                        Intent in = new Intent(SplashActivity.this,LoginActivity.class);
                        startActivity(in);
                }
            }
        };

        // 创建线程并启动
        Thread t = new Thread(new MyThread());
        t.start();
    }

    // 线程的Runnable接口类
   class MyThread implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myHandler.sendEmptyMessage(MSG1);
        }
    }
}