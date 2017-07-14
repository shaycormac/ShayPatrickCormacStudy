package com.assassin.gsonstudy.newNet;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @Author: Shay-Patrick-Cormac
 * @Email: fang47881@126.com
 * @Ltd: GoldMantis
 * @Date: 2017/7/14 13:35
 * @Version: 1.0
 * @Description: 用于网络请求的封装
 */

public class NetJson<T> implements Serializable 
{
    @SerializedName(value = "status",alternate = {"code",""})
    public int code;

   
    public String msg;

    public T data;
}
