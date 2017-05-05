package com.assassin.gsonstudy.widget.net;

import com.assassin.gsonstudy.application.StudyApp;
import com.assassin.gsonstudy.utils.ToastUtil;


import okhttp3.Response;

/**
 * @Author: Shay-Patrick-Cormac
 * @Email: fang47881@126.com
 * @Ltd: GoldMantis
 * @Date: 2017/5/3 13:56
 * @Version: 1.0
 * @Description: 网络请求回调函数
 */

public  class Callback 
{
    /**
     * 网络请求之前的操作
     */
    public void onBefore()
    {
        
    }
    /**
     * 成功的回调
     */
    public void onSuccess(Response response)
    {
        
    }
    /**
     * 失败的回调
     */
    public void onFailure(int statusCode,Exception e)
    {
        ToastUtil.INSTANCE.toastBottom(StudyApp.getInstance(),"网络请求失败~");
    }

    /**
     * Post执行上传过程中的进度回调，get请求不回调，UI线程
     *
     * @param currentSize  当前上传的字节数
     * @param totalSize    总共需要上传的字节数
     * @param progress     当前上传的进度
     * @param networkSpeed 当前上传的速度 字节/秒
     */
    public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
    }

    /**
     * 执行下载过程中的进度回调，UI线程
     *
     * @param currentSize  当前下载的字节数
     * @param totalSize    总共需要下载的字节数
     * @param progress     当前下载的进度
     * @param networkSpeed 当前下载的速度 字节/秒
     */
    public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
    }
}
