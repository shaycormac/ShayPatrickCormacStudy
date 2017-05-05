package com.assassin.greendaostudy.greendaohelper;

import android.content.Context;
import android.util.Log;

import com.assassin.greendaostudy.entity.DaoMaster;
import com.assassin.greendaostudy.entity.StudentDao;
import com.assassin.greendaostudy.entity.TeacherDao;
import com.assassin.greendaostudy.entity.UserDao;

import org.greenrobot.greendao.database.Database;

/**
 * @Author: Shay-Patrick-Cormac
 * @Email: fang47881@126.com
 * @Ltd: GoldMantis
 * @Date: 2017/5/2 15:51
 * @Version:
 * @Description: 特殊情况可能需要自己编写数据库迁移脚本，这种时候可以自定义DBHelper
 */

public class DBHelper extends DaoMaster.OpenHelper
{
    public static final String DBNAME = "gold_mantis_app.db";
    public DBHelper(Context context, String name) {
        super(context, DBNAME,null);
    }

    //自定义升级策略
    //可以在onUpgrade方法中进行数据库的迁移，如果自定义了DBHelper
    //数据库升级
   /* 前面，我们学会了greenDAO的使用，下面来学习下升级。
    今天研究了下升级，掌握方法了还是蛮简单的，这里对数据库的升级，仅仅是添加字段，添加表。对于删除，修改字段这里不多讲，因为sqlite数据库不适合此操作：
    SQLite supports a limited subset of ALTER TABLE. The ALTER TABLE command in SQLite allows the user to rename a table or to add a new column to an existing table.
    It is not possible to rename a column, remove a column, or add or remove constraints from a table.*/
    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        Log.d("数据库升级啦", "db version update from " + oldVersion + " to " + newVersion);
        switch(oldVersion)
        {
            case 5:
                //不能先删除表，否则数据都木了
               // UserDao.dropTable(db, true);
                //由5升级到6，在Use表中新加了score字段，新建表Student
                UserDao.createTable(db, true);
                db.execSQL("ALTER TABLE 'USER_SCORE' ADD 'SCORE' float;");
                //添加新表
                StudentDao.createTable(db,true);
                break;
            //由6升级到7，在Student表中添加age字段，新建表Teacher
            case 6:
                //添加字段
                StudentDao.createTable(db, true);
                db.execSQL("ALTER TABLE 'STUDENT' ADD 'AGE' int;");
                //添加新表
                TeacherDao.createTable(db,true);
                break;
            default:break;
        }
    }
    /**
     * 简单讲就是 SQlite 数据库仅能重命名表及增加字段，其他不支持，如果您一定要操作，也是可以的，来这里吧：
     SQLite如何删除，修改、重命名列
     1 升级版本号
     上面我们说到，在 gradle 里修改 schemaVersion 即可，现在我们设置为2，编译下，我们可以看到 DaoMaster 里的schema变为2：
     public static final int SCHEMA_VERSION = 2;

     2 实体添加字段
     比如我们的 Student 添加一个 score 字段， 这个可以直接写到 Student 里：
     public String score;

     编译后即可生成完整的 Student 实体及 DAO
     3重写onUpgrade方法升级
     这个就是重写 MyOpenHelper 的 onUpgrade 方法，该方法只在 schema 升级时执行一次.
     在该方法里添加 score 字段即可

     @Override
     public void onUpgrade(Database db, int oldVersion, int newVersion) { 
     KLog.w("db version update from " + oldVersion + " to " + newVersion); 
     switch (oldVersion) { case 1: 
     //不能先删除表，否则数据都木了
     // StudentDao.dropTable(db, true);
     StudentDao.createTable(db, true); 
     // 加入新字段 
     score db.execSQL("ALTER TABLE 'STUDENT' ADD 'SCORE' TEXT;"); 
     break;
     }
     }
     */
}
