package com.assassin.gsonstudy.newNet;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;

/**
 * @Author: Shay-Patrick-Cormac
 * @Email: fang47881@126.com
 * @Ltd: GoldMantis
 * @Date: 2017/7/14 11:16
 * @Version: 1.0 
 * @Description:  兼容类
 */

public class HttpApi<T>
{
    //请求网络所依附的activity或者fragment
    private Object object;
    private MyCallback<T> callback;

    public HttpApi(Object object, MyCallback<T> callback) 
    {
        this.object = object;
        this.callback = callback;
    }

   


    //post请求
    public void post( String path, HttpParams params) 
    {
        /*if(!NetWorkUtils.isNetworkConnected(ErpApp.getContext())){
            ToastUtil.showShort("无网络连接,请检查网络设置");
        }*/
        OkGo.<T>post(path)
                .tag(object)
                .params(params)
                .execute(callback);
    }

    //get请求
    public  void get(String path, HttpParams params) 
    {
        /*if(!NetWorkUtils.isNetworkConnected(ErpApp.getContext())){
            ToastUtil.showShort("无网络连接,请检查网络设置");
        }*/
        OkGo.<T>get(path)
                .tag(object)
                .params(params)
                .execute(callback);
    }
    
    /**post带有表单的请求
     * 添加日程 (请求头是application_json)
     * @param path        路径
     * @param requestJson 携带的实体表单转换成String字符串后的内容
     */
    public void postWithForm(String path, String requestJson) {
        OkGo.<T>post(path)
                .headers("Content-Type", "application/json")
                .upJson(requestJson)
                .tag(object)
                .execute(callback);
    }
    
    /*************************************请求的方法体**********************************************************/
    
    public void getJsonObject()
    {
        get(Urls.URL_JSONARRAY, null);
    }
    
}

