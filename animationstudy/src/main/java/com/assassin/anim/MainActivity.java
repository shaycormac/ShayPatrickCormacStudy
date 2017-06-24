package com.assassin.anim;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;

import com.assassin.anim.activity.AnimaButtonActivity;
import com.assassin.anim.activity.EnterAndExitActivity;
import com.assassin.anim.widget.ViewWrapper;

public class MainActivity extends AppCompatActivity {

    private Button btnAnim;
    private FrameLayout flAnimation;
    //控件的高度
    private int flWidth;
    //
    private boolean isFirst;
    //
    private boolean isCalculat=true;

    private Button btnAnimaTest;

    private Button btnEnterExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAnim = (Button) findViewById(R.id.btnAnim);
        btnAnimaTest = (Button) findViewById(R.id.btnAnimaTest);
        //使用View动画
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.shay_anim_view);
       // btnAnim.startAnimation(animation);
        //使用属性动画
        flAnimation = (FrameLayout) findViewById(R.id.flAnimation);
        //在这里获取不到控件的宽高，只有onCreate方法执行完了之后才可以！！
        Log.d("宽幅", flAnimation.getHeight() +"");
       // ObjectAnimator.ofInt(flAnimation,"height",200).setDuration(5000).start();

        ViewTreeObserver observer = flAnimation.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() 
            {
                //绘制好了。，获取当前的宽高
                if (isCalculat)
                {
                    flWidth = flAnimation.getHeight();
                    isCalculat = false;
                }
                
            }
        });
        //属性动画改变背景
        ValueAnimator colorAnim = ObjectAnimator.ofInt(flAnimation, "backgroundColor", 0Xffff8080, 0xff8080ff);
        colorAnim.setDuration(3000);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatCount(ValueAnimator.INFINITE);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim.start();

        AnimatorSet animator = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.propertity_animator_stydy);
        animator.setTarget(btnAnim);
        animator.start();
        
        //通过包装类，改变控件的属性
        final ViewWrapper wrapper = new ViewWrapper(flAnimation);
      //  ObjectAnimator.ofInt(wrapper,"height",800,100).setDuration(5000).start();

        btnAnim.setOnClickListener(new View.OnClickListener() 
        {
            @Override
            public void onClick(View v)
            {
                if (isFirst)
                {
                    //展开
                    isFirst = false;
                    ObjectAnimator.ofInt(wrapper,"height",0,flWidth).setDuration(1000).start();
                }else 
                {
                    isFirst = true;
                    //闭合
                    ObjectAnimator.ofInt(wrapper,"height",flWidth,0).setDuration(1000).start();
                }
                
                
                //dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("注意")
                        .setMessage("请注意啦，白鹿原开闭哦~~")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) 
                            {
                                dialog.dismiss();
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) 
                            {
                                dialog.dismiss();
                            }
                        });
                builder.show();
            }
        });
        
        
        btnAnimaTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AnimaButtonActivity.class));
            }
        });


        btnEnterExit = (Button) findViewById(R.id.btnEnterExit);
        btnEnterExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, EnterAndExitActivity.class));
            }
        });
    }


    
}
