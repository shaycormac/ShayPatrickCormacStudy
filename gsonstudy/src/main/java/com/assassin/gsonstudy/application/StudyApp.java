package com.assassin.gsonstudy.application;

import android.app.Application;
import android.content.pm.PackageManager;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cookie.store.PersistentCookieStore;
import com.lzy.okgo.model.HttpHeaders;

import java.util.logging.Level;

/**
 * @Author: Shay-Patrick-Cormac
 * @Email: fang47881@126.com
 * @Ltd: GoldMantis
 * @Date: 2017/4/26 08:45
 * @Version:
 * @Description:
 */

public class StudyApp extends Application
{
    private static StudyApp instance;
    @Override
    public void onCreate() 
    {
        super.onCreate();
        instance = this;
        //初始化OkGo
        OkGo.init(this);
        initOkGoParams();
    }

    private void initOkGoParams() 
    {
        //添加头部文件
        HttpHeaders headers = new HttpHeaders();
        headers.put("client", "Android");
        headers.put("product", android.os.Build.MODEL);
        headers.put("osVersion", android.os.Build.VERSION.RELEASE);
        headers.put("appVersion", StudyApp.getInstance().getAppVersion());
        try {
            OkGo.getInstance().debug("ShayCormac", Level.INFO, true)
                    .setCookieStore(new PersistentCookieStore())
            .addCommonHeaders(headers);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static StudyApp getInstance()
    {
        return instance;
    }
    //获取版本号
    public static String getAppVersion()
    {
        if (null==instance)
            return "";
        String appVersion = "";
        try {
            appVersion = instance.getPackageManager().getPackageInfo(instance.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appVersion;
    }
}
