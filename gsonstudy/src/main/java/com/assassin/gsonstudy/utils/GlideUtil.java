package com.assassin.gsonstudy.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.assassin.gsonstudy.R;
import com.assassin.gsonstudy.utils.transformation.BlurTransformation;
import com.assassin.gsonstudy.utils.transformation.CornersTransform;
import com.assassin.gsonstudy.utils.transformation.CropCircleTransformation;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;



/**
 * @Author: Shay-Patrick-Cormac
 * @Email: fang47881@126.com
 * @Ltd: GoldMantis
 * @Date: 2017/5/8 15:10
 * @Version: 1.0
 * @Description: 对Glide图库的进一步封装。
 */

public class GlideUtil {
    /**
     * 校验图片链接地址是否为gif;
     *
     * @param url
     * @return
     */
    public static boolean isGif(String url) {
        if (TextUtils.isEmpty(url))
            throw new RuntimeException("传入的字符串不能为null或者空字符串");
        int index = url.lastIndexOf('.');
        return index > 0 && "gif".toUpperCase().equals(url.substring(index + 1).toUpperCase());
    }

    public static void loadGif(Context context, ImageView imageView, String gifUrl) {
        //占位图和错误的图等美工给图，先用这两个图代替
        //gif图加载策略使用DiskCacheStrategy.SOURCE，解决gif卡顿，提升流畅
           Glide.with(context)
                .load(gifUrl).asGif()
                .placeholder(R.drawable.img_no_content)
                .error(R.drawable.img_no_internet)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);


    }

    public static void loadBitmap(Context context, ImageView imageView, String url) {
           Glide.with(context)
                .load(url).asBitmap()
                .placeholder(R.drawable.img_no_content)
                .error(R.drawable.img_no_internet)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    //加载圆形图片
    public static void loadCircleImage(Context context, ImageView imageView, String url) 
    {
           Glide.with(context)
                .load(url)
                .bitmapTransform(new CropCircleTransformation(context))
                .placeholder(R.drawable.img_no_content)
                .error(R.drawable.img_no_internet)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    /**
     * 圆角图片
     * @param context 上下文
     * @param imageView 
     * @param url
     * @param radius 像素值
     */
    public static void loadRoundImage(Context context, ImageView imageView, String url,int radius)
    {
           Glide.with(context)
                .load(url)
                .bitmapTransform(new CornersTransform(context,radius))
                .placeholder(R.drawable.img_no_content)
                .error(R.drawable.img_no_internet)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    /**
     * 高斯模糊
     * @param context
     * @param imageView
     * @param url
     */
    public static void loadBlurImage(Context context, ImageView imageView, String url)
    {
           Glide.with(context)
                .load(url)
                .bitmapTransform(new BlurTransformation(context))
                .placeholder(R.drawable.img_no_content)
                .error(R.drawable.img_no_internet)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }
    

}
