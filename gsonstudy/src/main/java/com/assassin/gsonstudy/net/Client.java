package com.assassin.gsonstudy.net;

import android.content.Context;

import com.assassin.gsonstudy.utils.VolleyLog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.model.HttpParams;

/**
 * @Author: Shay-Patrick-Cormac
 * @Email: fang47881@126.com
 * @Ltd: GoldMantis
 * @Date: 2017/4/26 09:45
 * @Version:
 * @Description:
 */

public class Client
{
    private static final String TAG = "Client";
    public static final String HOST = "121.41.38.32:3000";
    public static final String PROTOCOL_HOST = "http://" + HOST;
    
    //封装post请求
    public static void post(Context context, String path, HttpParams params, AbsCallback callback)
    {
        String url = PROTOCOL_HOST + path;
        VolleyLog.d("url: %s",url);
            OkGo.post(url)
                    .tag(context)
                    .params(params)
                    .execute(callback);
    }
}
