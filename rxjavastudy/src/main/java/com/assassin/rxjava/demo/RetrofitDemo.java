package com.assassin.rxjava.demo;

import android.content.Context;
import android.util.Log;

import com.assassin.rxjava.RetrofitProvider;
import com.assassin.rxjava.interf.Api;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * @Author: Shay-Patrick-Cormac
 * @Email: fang47881@126.com
 * @Ltd: GoldMantis
 * @Date: 2017/5/31 16:53
 * @Version:
 * @Description:
 */

public class RetrofitDemo 
{
    public final String TAG = getClass().getSimpleName();

    public static void demo1(Context context)
    {
        Api api = RetrofitProvider.get().create(Api.class);
        api.get250Top(0, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Response<ResponseBody>>() {
                    @Override
                    public void accept(Response<ResponseBody> response) throws Exception {
                        Log.d("RetrofitDemo得到的数据", response.body().string());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.w("Error", throwable);
                    }
                });
    }
}
