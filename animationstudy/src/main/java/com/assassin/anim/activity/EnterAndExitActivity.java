package com.assassin.anim.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.assassin.anim.R;

public class EnterAndExitActivity extends AppCompatActivity {
    private RelativeLayout rlTop;
    private RelativeLayout rlBottom;
    private Button btnExit;
    private Activity activity;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.activity_enter_and_exit);
        rlTop = (RelativeLayout) findViewById(R.id.rlTop);
        rlBottom = (RelativeLayout) findViewById(R.id.rlBottom);
        btnExit = (Button) findViewById(R.id.btnExit);
        //播放动画
        Animation topEnter = AnimationUtils.loadAnimation(this, R.anim.top_enter);
        rlTop.startAnimation(topEnter);
        Animation bottomEnter = AnimationUtils.loadAnimation(this, R.anim.bottom_enter);
        rlBottom.startAnimation(bottomEnter);
        btnExit.setOnClickListener(new View.OnClickListener() 
        {
            @Override
            public void onClick(View v) 
            {
                v.setEnabled(false);
                Animation topEnter = AnimationUtils.loadAnimation(activity, R.anim.top_exit);
                rlTop.startAnimation(topEnter);
                Animation bottomEnter = AnimationUtils.loadAnimation(activity, R.anim.bottom_exit);
                rlBottom.startAnimation(bottomEnter);
                topEnter.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation)
                    {
                        
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) 
                    {
                        btnExit.setEnabled(false);
                        activity.finish();

                     }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });
    }
}
