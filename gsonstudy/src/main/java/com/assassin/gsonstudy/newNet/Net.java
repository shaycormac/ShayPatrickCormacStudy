package com.assassin.gsonstudy.newNet;


import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.model.HttpParams;

/**
 * @Author: Shay-Patrick-Cormac
 * @Email: fang47881@126.com
 * @Ltd: GoldMantis
 * @Date: 2017/4/25 15:08
 * @Version: 1.0
 * @Description: 存放网络包
 */

public class Net<T>

{
  

    //post请求
    public static void post(Object requestTag, String path, HttpParams params, AbsCallback callback) {
        /*if(!NetWorkUtils.isNetworkConnected(ErpApp.getContext())){
            ToastUtil.showShort("无网络连接,请检查网络设置");
        }*/
        OkGo.post(path)
                .tag(requestTag)
                .params(params)
                .execute(callback);
    }

    //get请求
    public static void get(Object requestTag, String path, HttpParams params, AbsCallback callback) {
       /* if(!NetWorkUtils.isNetworkConnected(ErpApp.getContext())){
            ToastUtil.showShort("无网络连接,请检查网络设置");
        }*/
        OkGo.get(path)
                .tag(requestTag)
                .params(params)
                .execute(callback);
    }

    //delete请求
    public static void delete(Object requestTag, String path, HttpParams params, AbsCallback callback) {
      /*  if(!NetWorkUtils.isNetworkConnected(ErpApp.getContext())){
            ToastUtil.showShort("无网络连接,请检查网络设置");
        }*/
        OkGo.delete(path)
                .tag(requestTag)
                .params(params)
                .execute(callback);
    }
    //post带有表单的请求

    /**
     * 添加日程 (请求头是application_json)
     *
     * @param requestTag  网络请求所依附的activity或者fragment
     * @param path        路径
     * @param requestJson 携带的实体表单转换成String字符串后的内容
     * @param callback    请求返回的结果
     */
    public static void postWithForm(Object requestTag, String path, String requestJson, AbsCallback callback) {
        OkGo.post(path)
                .headers("Content-Type", "application/json")
                .upJson(requestJson)
                .tag(requestTag)
                .execute(callback);
    }

}
