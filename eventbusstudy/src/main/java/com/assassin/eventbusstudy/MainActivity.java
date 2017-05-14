package com.assassin.eventbusstudy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        
        Button button= (Button) findViewById(R.id.btnOkGo);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) 
            {
                startActivity(new Intent(MainActivity.this,Main2Activity.class));
                
            }
        });
        findViewById(R.id.btnOkGo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Main3Activity.class));
            }
        });
    }
    @Subscribe(threadMode = ThreadMode.ASYNC,sticky = true,priority = 1)
    public void onAsync(String hehe)
    {
        Log.e("TAG", "Async: " + Thread.currentThread().getName());
        Log.e("String", "Async: " + hehe);
    }
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onBackgroundThread(String hehe) {
        Log.e("TAG", "BackgroundThread: " + Thread.currentThread().getName());
        Log.e("String", "BackgroundThread: " + hehe);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainThread(String hehe) 
    {
        Toast.makeText(this,hehe,Toast.LENGTH_SHORT).show();
        Log.e("TAG", "MainThread: " + Thread.currentThread().getName());
        Log.e("String", "MainThread: " + hehe);
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onPostThread(String hehe) 
    {
        Log.e("TAG", "PostThread: " + Thread.currentThread().getName());
        Log.e("String", "PostThread: " + hehe);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
