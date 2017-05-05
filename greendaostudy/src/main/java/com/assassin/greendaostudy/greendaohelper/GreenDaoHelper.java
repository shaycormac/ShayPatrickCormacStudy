package com.assassin.greendaostudy.greendaohelper;

import android.database.sqlite.SQLiteDatabase;

import com.assassin.greendaostudy.app.GreenDaoApp;
import com.assassin.greendaostudy.entity.DaoMaster;
import com.assassin.greendaostudy.entity.DaoSession;

/**
 * @Author: Shay-Patrick-Cormac
 * @Email: fang47881@126.com
 * @Ltd: GoldMantis
 * @Date: 2017/5/2 13:25
 * @Version:
 * @Description: 封装GreenDao
 * @link 
 */
@Deprecated
public class GreenDaoHelper
{
    private static DBHelper helper;
    private static SQLiteDatabase db;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    /**
     * 初始化greenDao，这个操作建议在Application初始化的时候添加；
     */
    public static void intDatabase()
    {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        helper = new DBHelper(GreenDaoApp.instance, "cache_db");
        db = helper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }
    
    public static DaoSession getDaoSession()
    {
        return daoSession;
    }


    public static SQLiteDatabase getDb() {
        return db;
    }
}
