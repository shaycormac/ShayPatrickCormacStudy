package com.assassin.anim.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.assassin.anim.R;
import com.assassin.anim.widget.AnimaButton;

public class AnimaButtonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anima_button);
        final AnimaButton animBtn = (AnimaButton) findViewById(R.id.animBtn);
        animBtn.setAnimationButtonListener(new AnimaButton.AnimationButtonListener() {
            @Override
            public void onClickListener() {
                //启动
                animBtn.start();
                
            }

            @Override
            public void animationFinish() 
            {
                Toast.makeText(AnimaButtonActivity.this,"动画执行完毕",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
