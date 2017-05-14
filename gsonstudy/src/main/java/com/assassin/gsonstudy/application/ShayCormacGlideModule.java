package com.assassin.gsonstudy.application;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.GlideModule;

/**
 * @Author: Shay-Patrick-Cormac
 * @Email: fang47881@126.com
 * @Ltd: GoldMantis
 * @Date: 2017/5/8 16:22
 * @Version: 1.0
 * @Description: 自定义Glide的一些属性，比如缓存路径，魂村大小，图片质量，根据需求进一步完善。。。。
 */

public class ShayCormacGlideModule implements GlideModule {
    public static final String APP_DIR = "shayCormac_cache";

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //获取内存计算器
        MemorySizeCalculator calculator = new MemorySizeCalculator(context);
        //获取Glide默认内存缓存大小
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        //缓存路径默认路径(即data/data/应用包名/cache/image_manager_disk_cache)
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, APP_DIR, defaultMemoryCacheSize));
        //默认缓存会在 SDCard/Android/data/应用包名/cache/image_manager_disk_cache目录下，修改一下
        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, APP_DIR, defaultMemoryCacheSize));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
