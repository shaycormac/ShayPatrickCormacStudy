package com.assassin.gsonstudy.net;


import okhttp3.Response;

/**
 * 调用网络接口时的回调，onSuccess（）和 onFailure（）一起使用，onResponse（）单独使用
 */
public class CallBack {

    /**
     * 系统错误
     */
    public static final int ERROR = 0;
    /**
     * 成功
     */
    public static final int SUCCESS = 1;
    /**
     * token失效
     */
    public static final int TOKEN_INVALIDATION = 2;
    /**
     * 参数错误
     */
    public static final int ERROR_PARAMETER = 3;
    /**
     * 失败
     */
    public static final int ERROR_FAILTURE = 4;

    public CallBack() {
    }

    /**
     * 成功获取服务器数据
     *
     * @param code    服务器返回状态码
     * @param msg     返回信息
     * @param content 返回数据
     */
    public void onSuccess(int code, String msg, String content) {
    }
    
    public void onRespond(Response response) throws NoSuchMethodException {}

    /**
     * 获取服务器数据失败,从本地数据库获取
     *
     * @param statusCode     HTTP status code
     * @param failureMessage 失败信息
     * @param code           服务器返回状态码
     * @param msg            返回信息
     * @param content        返回数据
     */
    public void onFailure(int statusCode, String failureMessage, int code, String msg, String content) {
       // ToastUtils.ToastFailureMessage(PuApp.get());
    }

    /**
     * 先从网络获取数据，如果失败再从本地数据库获取
     *
     * @param code           服务器返回状态码
     * @param msg            返回信息
     * @param content        返回数据
     * @param isCache        数据是否从本地数据库获取
     * @param statusCode     HTTP status code
     * @param failureMessage 失败信息
     */
    public void onResponse(int code, String msg, String content, boolean isCache, int statusCode, String failureMessage) {
    }
}
