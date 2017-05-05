package com.assassin.eventbusstudy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import org.greenrobot.eventbus.EventBus;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Button button= (Button) findViewById(R.id.btnOkGo);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                EventBus.getDefault().postSticky("呵呵哒");
                EventBus.getDefault().postSticky(12);
                finish();
               // startActivity(new Intent(Main2Activity.this, MainActivity.class));
            }
        });
    }
}
