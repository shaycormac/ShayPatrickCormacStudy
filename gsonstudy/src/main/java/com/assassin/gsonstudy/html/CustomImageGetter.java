package com.assassin.gsonstudy.html;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BaseTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;

import java.lang.ref.WeakReference;

/**
 * @author 作者：Shay-Patrick-Cormac
 * @datetime 创建时间：2017/5/6 23:29
 * @email 邮箱： 574583006@qq.com
 * @content 说明：一般来说，html文件常常是含有图片，如果需要在Textview中实现文字和图片的混排，
 * 需要使用ImageGetter。ImageGetter是Html类中一个接口，作用是给img标签获取图片内容，
 * 主要提供了一个getDrawable的方法。
 */
public class CustomImageGetter implements Html.ImageGetter
{
    //使用弱引用
    WeakReference<TextView> tvReference;
    Context context;
    int picWidth;

    public CustomImageGetter(WeakReference<TextView> tvReference, Context context) 
    {
        this.tvReference = tvReference;
        this.context = context;
        picWidth = tvReference.get().getWidth(); 
    }

    @Override
    public Drawable getDrawable(String url) 
    {
        final Drawable[] drawable = {null};
        Glide.with(context).load(url).into(new BaseTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) 
            {
                drawable[0] = resource;
            }

            @Override
            public void getSize(SizeReadyCallback cb) 
            {
                

            }
        });
        return drawable[0];
    }
    
}
