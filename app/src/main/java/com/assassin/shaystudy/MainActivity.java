package com.assassin.shaystudy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.assassin.shaystudy.activity.SpringActivity;
import com.assassin.shaystudy.handle.CornersTransform;
import com.bumptech.glide.Glide;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imgGlide = (ImageView) findViewById(R.id.imgGlide);
        ImageView imgVideoThumb = (ImageView) findViewById(R.id.imgVideoThumb);
        String url = "https://img.gcall.com/dca5/M00/10/8E/wKhoNlggetaENWylAAAAAAAAAAA457.jpg";
        //加载图片
        //有时候加载的图片过大时,或者网络不好时,我们经常希望控件在加载过程中有一张默认的占位图
        //当图片链接地址有误或者网络不行的时候,我们需要显示一个错误图片 
        //加载gif
        String urlGif = "http://img2.imgtn.bdimg.com/it/u=1128030020,1207223400&fm=23&gp=0.jpg";
        Glide.with(this).load(urlGif).placeholder(R.mipmap.placeholder).error(R.mipmap.error).dontAnimate().into(imgGlide);
        //加载视频的缩略图
        File videoFile = new File(Environment.getExternalStorageDirectory(), "无耻/无耻之徒.Shameless.US.S07E11.720p.HDTV.X264-双语字幕.mp4");
        Glide.with(this).load(videoFile).placeholder(R.mipmap.placeholder).error(R.mipmap.error).transform(new CornersTransform(this, 50f)).into(imgVideoThumb);
        imgGlide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SpringActivity.class));
                
            }
        });
    }
}
