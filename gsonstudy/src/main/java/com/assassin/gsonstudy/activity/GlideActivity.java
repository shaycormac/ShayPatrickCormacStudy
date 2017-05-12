package com.assassin.gsonstudy.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.assassin.gsonstudy.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class GlideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);
        ImageView imgTest = (ImageView) findViewById(R.id.imgTest);
        Glide.with(this).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1493297470390&di=2c668310acb30d75625d557151f80043&imgtype=0&src=http%3A%2F%2Fimgsports.eastday.com%2Fsports%2Fimg%2F201704200946176071.jpe").placeholder(R.mipmap.img_no_content).error(R.mipmap.iv_group_log).diskCacheStrategy(DiskCacheStrategy.NONE).into(imgTest);
    }
}
