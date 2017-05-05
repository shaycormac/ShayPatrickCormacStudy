package com.assassin.gsonstudy.net;

import android.content.Context;

import com.assassin.gsonstudy.utils.ShayCallBack;

import java.text.SimpleDateFormat;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @Author: Shay-Patrick-Cormac
 * @Email: fang47881@126.com
 * @Ltd: GoldMantis
 * @Date: 2017/4/26 10:33
 * @Version:
 * @Description: 网络请求基本
 */

public class BaseApi<T>
{
    private static final String TAG = "BaseApi";

    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
    private static final String RESPONSE_STATUS = "code";
    private static final String RESPONSE_MSG = "msg";
    private static final String RESPONSE_DATA = "content";
    protected  CallBack mCallBack;
    protected  Context mContext;
    //protected  DialogCallback dialogCallback;
    protected ShayCallBack<T> shayCallBack;
    

    public  BaseApi(final CallBack callBack, final Context mContext) 
    {
        this.mCallBack = callBack;
        this.mContext = mContext;
        shayCallBack = new ShayCallBack<T>() 
        {
            @Override
            public T convertSuccess(Response response) throws Exception 
            {
                callBack.onRespond(response);
                return super.convertSuccess(response);
            }

            @Override
            public void onError(Call call, Response response, Exception e) 
            {
                super.onError(call, response, e);
            }
        };
        
       /* dialogCallback = new DialogCallback<LzyResponse<String>>((Activity) mContext) {
            @Override
            public void onSuccess(String s, Call call, Response response)
            {
                VolleyLog.d("得到的数据为： %s", s);
                if (null == mCallBack || null == mContext)
                {
                    return;
                }
                mCallBack.onSuccess(1,"呵呵",s);
            }
        };*/
       /* dialogCallback = new DialogCallback<LzyResponse>((Activity) mContext) {
            @Override
            public void onSuccess(LzyResponse stringLzyResponse, Call call, Response response) 
            {
                VolleyLog.d("得到的数据分别为： %s，--%d--%s", stringLzyResponse.content,stringLzyResponse.code,stringLzyResponse.msg);
                if (null == mCallBack || null == mContext)
                {
                    return;
                }
                mCallBack.onSuccess(stringLzyResponse.code,stringLzyResponse.msg,stringLzyResponse.content.toString());

            }
        };*/
       /* dialogCallback = new DialogCallback<LzyResponse<List<GroupItem>>>((Activity) mContext) {
            @Override
            public void onSuccess(LzyResponse<List<GroupItem>> stringLzyResponse, Call call, Response response) 
            {
                VolleyLog.d("得到的数据分别为： %s，--%d--%s", stringLzyResponse.content.toString(),stringLzyResponse.code,stringLzyResponse.msg);
                if (null == mCallBack || null == mContext)
                {
                    return;
                }
                mCallBack.onSuccess(stringLzyResponse.code,stringLzyResponse.msg,stringLzyResponse.content.toString());

            }
        };*/
    }

    /*protected DialogCallback<String> dialogCallback = new DialogCallback<String>((Activity) mContext) {
        @Override
        public void onSuccess(String s, Call call, Response response)
        {
            VolleyLog.d("得到的数据为： %s", s);
            if (null == mCallBack || null == mContext) 
            {
                return;
            }
            mCallBack.onSuccess(1,"呵呵",s);
        }
    };*/
}
