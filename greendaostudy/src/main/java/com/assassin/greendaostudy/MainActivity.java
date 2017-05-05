package com.assassin.greendaostudy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.assassin.greendaostudy.entity.GreenEntity;
import com.assassin.greendaostudy.entity.GreenEntityDao;
import com.assassin.greendaostudy.entity.User;
import com.assassin.greendaostudy.entity.UserDao;
import com.assassin.greendaostudy.greendaohelper.DBManager;

public class MainActivity extends AppCompatActivity {

    GreenEntityDao greenDao1;
    GreenEntity green1;
    User user;
    UserDao userDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //测试实体
        //首先获取UserDao对象：
        //数据库的增删改查我们都将通过UserDao来进行，插入操作如下：
        //User的第一个参数为id，如果这里传null的话在插入的过程中id字段会自动增长
        // （现在知道为什么id要为Long类型了吧！）
       /* greenDao1 = GreenDaoHelper.getDaoSession().getGreenEntityDao();
        green1 = new GreenEntity((long) 1, "协议寇马克", 11,"花花");
        greenDao1.insert(green1);
        green1 = new GreenEntity((long) 2, "我屮艸芔茻", 11,"哎呦");
        greenDao1.insert(green1);
        //查询数据
        GreenEntity greenEntity1 = greenDao1.queryBuilder().where(GreenEntityDao.Properties.NickName.eq("哎呦")).unique();
        if (null == greenEntity1) {
            Toast.makeText(this,"找不到这个类",Toast.LENGTH_SHORT).show();
        }else
            Log.d("得到这个类", greenEntity1.getName() + greenEntity1.getNickName());
        //更新这个数据
        green1 = new GreenEntity((long) 2, "呵呵哒", 11,"我曹");
        greenDao1.update(green1);
         greenEntity1 = greenDao1.queryBuilder().where(GreenEntityDao.Properties.NickName.eq("我曹")).unique();
        if (null == greenEntity1) {
            Toast.makeText(this,"找不到这个类",Toast.LENGTH_SHORT).show();
        }else
            Log.d("得到这个类", greenEntity1.getName() + greenEntity1.getNickName());*/
        
        //删除
        //where表示查询条件，这里我是查询id小于等于10的数据，where中的参数可以有多个，
        // 就是说可以添加多个查询条件。最后的list表示查询结果是一个List集合，如果你只想查询一条数据，
        // 最后unique即可。当然，我们也可以根据id来删除数据：
       /* List<GreenEntity> list = greenDao1.queryBuilder()
                .where(GreenEntityDao.Properties.Id.le(100))
                .build().list();
        for (GreenEntity greenEntity : list) {
            greenDao1.delete(greenEntity);
        }*/


       // userDao = GreenDaoHelper.getDaoSession().getUserDao();
        userDao = DBManager.INSTANCE.getDaoSession().getUserDao();
        user = new User((long) 1, "冰花玩儿", "大花蕙","111",1.0f);
        userDao.insert(user);
        user = new User((long) 2, "花花", "花蕙","222",2.0f);
        userDao.insert(user);
        //查询数据
        User user1 = userDao.queryBuilder().where(UserDao.Properties.NickName.eq("花蕙")).unique();
        if (null == user1) {
            Toast.makeText(this,"找不到这个类",Toast.LENGTH_SHORT).show();
        }else
            Log.d("得到这个类", user1.getUserName() + user1.getNickName()+user1.getUseId());
        //更新这个数据
        user1 = new User((long) 2, "呵呵哒", "我曹","222",3.0f);
        userDao.update(user1);
        user1 = userDao.queryBuilder().where(UserDao.Properties.NickName.eq("我曹")).unique();
        if (null ==  user1) {
            Toast.makeText(this,"找不到这个类",Toast.LENGTH_SHORT).show();
        }else
            Log.d("得到这个类", user1.getUserName() + user1.getNickName()+user1.getUseId());




    }
}
