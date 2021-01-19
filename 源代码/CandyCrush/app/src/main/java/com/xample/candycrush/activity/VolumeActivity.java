package com.xample.candycrush.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import com.xample.candycrush.R;


public class VolumeActivity extends AppCompatActivity {
    AudioManager am;
    MediaPlayer player1, player2, player3;
    Switch aSwitch;
    Boolean isPause = false; // 是否暂停
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume);

        // 获取系统级服务
        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        player1=MediaPlayer.create(VolumeActivity.this, R.raw.b);
        player2=MediaPlayer.create(VolumeActivity.this, R.raw.a);
        player3=MediaPlayer.create(VolumeActivity.this, R.raw.abc);

        RadioGroup rg = findViewById(R.id.rg);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.radioButton1) {
                    id = 0;
                    play1();
                } else if (i == R.id.radioButton2) {
                    id = 1;
                    play2();
                } else if (i == R.id.radioButton3) {
                    id = 2;
                    play3();
                }
            }
        });

        aSwitch = findViewById(R.id.switch1);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    switch (id) {
                        case 0:
                            player1.start();
                            break;
                        case 1:
                            player2.start();
                            break;
                        case 2:
                            player3.start();
                            break;
                    }
                }
                else {
                    switch (id) {
                        case 0:
                            player1.pause();
                            break;
                        case 1:
                            player2.pause();
                            break;
                        case 2:
                            player3.pause();
                            break;
                    }
                }

            }
        });
        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                am.setStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE,
                        AudioManager.FLAG_SHOW_UI);
            }
        });
    }
    private void play1(){
        try{
            if (player2.isPlaying()) {
                player2.pause();
            }
            if (player3.isPlaying()) {
                player3.pause();
            }
            player1.start();//开始播放
        }catch(Exception e){
            e.printStackTrace();//输出异常信息
        }
    }
    private void play2(){
        try{
            if (player1.isPlaying()) {
                player1.pause();
            }
            if (player3.isPlaying()) {
                player3.pause();
            }
            player2.start();//开始播放
        }catch(Exception e){
            e.printStackTrace();//输出异常信息
        }
    }private void play3(){
        try{
            if (player1.isPlaying()) {
                player1.pause();
            }
            if (player2.isPlaying()) {
                player2.pause();
            }
            player3.start();//开始播放
        }catch(Exception e){
            e.printStackTrace();//输出异常信息
        }
    }

}