package com.assassin.gsonstudy.entity;

import com.assassin.gsonstudy.utils.ShayCallBack;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @Author: Shay-Patrick-Cormac
 * @Email: fang47881@126.com
 * @Ltd: GoldMantis
 * @Date: 2017/4/27 15:45
 * @Version:
 * @Description:
 */

public class AA <T>
{
    protected ShayCallBack<T> shayCallBack = new ShayCallBack<T>() {
        @Override
        public T convertSuccess(Response response) throws Exception 
        {
            return super.convertSuccess(response);
        }

        @Override
        public void onError(Call call, Response response, Exception e) {
            super.onError(call, response, e);
        }
    };
}
