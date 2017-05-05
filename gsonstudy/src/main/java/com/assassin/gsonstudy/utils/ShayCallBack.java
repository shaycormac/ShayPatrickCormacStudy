package com.assassin.gsonstudy.utils;

import com.lzy.okgo.callback.AbsCallback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @Author: Shay-Patrick-Cormac
 * @Email: fang47881@126.com
 * @Ltd: GoldMantis
 * @Date: 2017/4/27 15:19
 * @Version:
 * @Description:
 */

public abstract class ShayCallBack<T> extends AbsCallback<T> 
{
    @Override
    public void onSuccess(T t, Call call, Response response) 
    {
        
    }


    @Override
    public T convertSuccess(Response response) throws Exception {
        return null;
    }

    @Override
    public void onError(Call call, Response response, Exception e) 
    {
        super.onError(call, response, e);
    }
}
