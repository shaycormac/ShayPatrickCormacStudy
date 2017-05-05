package com.assassin.gsonstudy.net;

import android.content.Context;

import com.lzy.okgo.model.HttpParams;

/**
 * @Author: Shay-Patrick-Cormac
 * @Email: fang47881@126.com
 * @Ltd: GoldMantis
 * @Date: 2017/4/26 10:53
 * @Version:
 * @Description:
 */

public class Api extends BaseApi 
{
    public <T> Api(Context mContext,CallBack callback) 
    {
        super(callback, mContext);
    }
    
    //获取数据
    public void getGroupByVitality(int pageSize, int pageNo)
    {
        HttpParams param = new HttpParams();
        param.put("pageSize", pageSize);
        param.put("pageNo", pageNo);
        Client.post(mContext,"/assn/indexVitality",param,shayCallBack);
    }
}
