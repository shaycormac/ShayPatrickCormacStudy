package com.assassin.shaystudy.model;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.GlideModule;

/**
 * @author 作者：Shay-Patrick-Cormac
 * @datetime 创建时间：2017/4/21 22:45
 * @email 邮箱： 574583006@qq.com
 * @content 说明：自己定制Glide缓存。
 */
public class ShayGlideModel implements GlideModule {
    //刚才创建的GlideModule的实现类时,会要实现两个方法,这里要用到的是其中的applyOptions方法,applyOptions方法
    // 里面提供了一个GlideBuilder,通过GlideBuilder我们就能实现自定义配置了
    @Override
    public void applyOptions(Context context, GlideBuilder builder) 
    {
        //配置图片池大小   20MB
        //但是内存缓存的大小数值其实不应该是随便配置的,Glide的内部的默认值是通过一系列的计算获取的,
        // 比如说判断手机是否高配置手机等(有兴趣的可以去看他的源码,或者等待本系列后面的源码分析) 
       // 这样有个取巧的办法,就是获取Glide默认的设置,然后在这个设置的基础上进行修改
        builder.setBitmapPool(new LruBitmapPool(20*1024*1024));//自定义图片池
       // builder.setDiskCache();//自定义磁盘缓存
        //配置内存缓存大小 10MB
        builder.setMemoryCache(new LruResourceCache(10*1024*1024));//自定义内存缓存
       // builder.setDiskCacheService();//自定义本地缓存的线程池
       // builder.setResizeService();//自定义核心处理的线程池
        //用过Picasso的朋友应该知道,Picasso默认的图片质量是ARGB_8888,而Glide默认的图片质量是RGB_565,
        // 这里我们就来修改默认配置,让Glide的默认质量为ARGB_8888
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);//自定义图片质量
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
