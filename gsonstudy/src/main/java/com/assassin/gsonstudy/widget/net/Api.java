package com.assassin.gsonstudy.widget.net;

import android.content.Context;

import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.request.BaseRequest;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @Author: Shay-Patrick-Cormac
 * @Email: fang47881@126.com
 * @Ltd: GoldMantis
 * @Date: 2017/5/3 13:55
 * @Version: 1.0
 * @Description: 网络请求
 */

public class Api<T>
{
    private static final String RESPONSE_STATUS = "code";
    private static final String RESPONSE_MSG = "msg";
    private static final String RESPONSE_DATA = "content";
    protected Callback callBack;
    protected Context context;
    
    public Api( Context context,Callback callBack) 
    {
        this.callBack = callBack;
        this.context = context;
    }

    protected AbsCallback<T> absCallback=new AbsCallback<T>()
    {
        @Override
        public T convertSuccess(Response response) throws Exception {
            return null;
        }

        @Override
        public void onSuccess(T t, Call call, Response response) 
        {
            if (callBack==null || context==null)
                return;
            callBack.onSuccess(response);
        }

        @Override
        public void onBefore(BaseRequest request) 
        {
            super.onBefore(request);
            if (callBack==null)
                return;
            callBack.onBefore();
        }

        @Override
        public void onError(Call call, Response response, Exception e) 
        {
            super.onError(call, response, e);
            if (callBack==null || context==null )
                return;
            int statusCode;
              if (response!=null)
                  statusCode = response.code();
             else
                  statusCode = 404;
                callBack.onFailure(statusCode, e);
        }

        @Override
        public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
            super.upProgress(currentSize, totalSize, progress, networkSpeed);
            if (callBack==null)
                return;
            callBack.upProgress(currentSize, totalSize, progress, networkSpeed);
        }

        @Override
        public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
            super.downloadProgress(currentSize, totalSize, progress, networkSpeed);
            if (callBack==null )
                return;
            callBack.downloadProgress(currentSize, totalSize, progress, networkSpeed);
        }
    };
    
    
    //具体的Api接口
    /**
     * 用户摇到的奖品列表
     */
    public void getShakeRecordList(int pageSize, int pageNo) {
        HttpParams param = new HttpParams();
        param.put("pageSize", pageSize);
        param.put("pageNo", pageNo);
        Net.post(context, "/award/luckyRecord", param, absCallback);
    }
    
    
    
}
