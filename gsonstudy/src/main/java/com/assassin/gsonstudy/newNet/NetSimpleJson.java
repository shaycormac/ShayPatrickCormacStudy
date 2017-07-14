package com.assassin.gsonstudy.newNet;

import com.google.gson.annotations.SerializedName;

/**
 * @Author: Shay-Patrick-Cormac
 * @Email: fang47881@126.com
 * @Ltd: GoldMantis
 * @Date: 2017/7/14 13:46
 * @Version:
 * @Description:
 */

public class NetSimpleJson
{
    @SerializedName(value = "status",alternate = {"code",""})
    public int code;


    public String msg;

    public NetJson toJsonResponse() 
    {
        NetJson jsonResponse = new NetJson();
        jsonResponse.code = code;
        jsonResponse.msg = msg;
        return jsonResponse;
    }
}
