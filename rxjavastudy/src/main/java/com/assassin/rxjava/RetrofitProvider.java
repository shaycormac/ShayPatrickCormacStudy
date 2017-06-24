package com.assassin.rxjava;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Author: Shay-Patrick-Cormac
 * @Email: fang47881@126.com
 * @Ltd: GoldMantis
 * @Date: 2017/5/31 16:47
 * @Version: 1.0
 * @Description: 请求的
 */

public class RetrofitProvider 
{
    private static final String ENDPOINT = "https://api.douban.com/";
    
    //静态的方法
    public static Retrofit get()
    {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.readTimeout(50, TimeUnit.SECONDS);
        builder.connectTimeout(50, TimeUnit.SECONDS);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
        }

        //返回一个Retrofit
        return new Retrofit.Builder().baseUrl(ENDPOINT)
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}
