package com.assassin.greendaostudy.app;

import android.app.Application;

import com.assassin.greendaostudy.greendaohelper.DBManager;
import com.facebook.stetho.Stetho;

/**
 * @Author: Shay-Patrick-Cormac
 * @Email: fang47881@126.com
 * @Ltd: GoldMantis
 * @Date: 2017/5/2 13:42
 * @Version:
 * @Description:
 */

public class GreenDaoApp extends Application 
{
    public static GreenDaoApp instance;
    @Override
    public void onCreate() 
    {
        super.onCreate();
        instance = this;
       // GreenDaoHelper.intDatabase();
        //初始化数据库
        DBManager.INSTANCE.initDataBase();
        //facebook调试数据库
        Stetho.initializeWithDefaults(this);
    }
}
