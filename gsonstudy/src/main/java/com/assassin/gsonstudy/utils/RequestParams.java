package com.assassin.gsonstudy.utils;

import android.support.v4.util.ArrayMap;

import java.util.List;

/**
 * @Author: Shay-Patrick-Cormac
 * @Email: fang47881@126.com
 * @Ltd: GoldMantis
 * @Date: 2017/4/26 10:11
 * @Version:
 * @Description: post请求的添加参数，将token添加进去
 */

public class RequestParams 
{
    public ArrayMap param = new ArrayMap();
    //token
    public String token = "";

    public RequestParams()
    {
        //todo 构造参数里面添加token
      //  if (Session.getUserInfoSession() != null && Session.getUserInfoSession().token != null)
           // token = Session.getUserInfoSession().token;
    }

    public void put(String key, String value) {
        param.put(key, value);
    }

    public void put(String key, int value) {
        param.put(key, value);
    }

    public void put(String key, boolean value) {
        param.put(key, value);
    }

    public void put(String key, long value) {
        param.put(key, value);
    }

    public void put(String key, double value) {
        param.put(key, value);
    }

    public void put(String key, List value) {
        param.put(key, value);
    }

    //todo 表单添加图片
    /*public void put(String key, ImageInfo value) {
        param.put(key, value);
    }*/

    public void put(String key, Object value) {
        param.put(key, value);
    }

    @Override
    public int hashCode() {
        return 66 + param.hashCode() + token.hashCode();
    }

    @Override
    public String toString() {
        return param.toString() + "_" + token;
    }
}
