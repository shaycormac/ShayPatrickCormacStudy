package com.assassin.greendaostudy.entity;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;

/**
 * @Author: Shay-Patrick-Cormac
 * @Email: fang47881@126.com
 * @Ltd: GoldMantis
 * @Date: 2017/5/2 13:09
 * @Version:
 * @Description: 测试greenDao数据库
 */
/**1.实体@Entity注解 
 * schema：告知GreenDao当前实体属于哪个schema  // 如果你有超过一个的数据库结构，可以通过这个字段来区分 该实体属于哪个结构
  * active：标记一个实体处于活动状态，活动实体有更新、删除和刷新方法
  * nameInDb：在数据中使用的别名，默认使用的是实体的类名
  * indexes：定义索引，可以跨越多个列
  * createInDb：标记创建数据库表 DAO是否应该创建数据库表的标志(默认为true) 如果你有多对一的表，将这个字段设置为false 或者你已经在GreenDAO之外创建了表，也将其置为false
  */
/*@Entity(schema = "shayschema", active = true, nameInDb = "shaytable", indexes = {@Index
        (value = "shayPatrickCormac", unique = true)}, createInDb = true)*/
@Entity( active = true, nameInDb = "shaytable")
public class GreenEntity 
{
    /**2.基础属性注解
     * @Id :主键 Long型，可以通过@Id(autoincrement = true)设置自增长
       @Property：设置一个非默认关系映射所对应的列名，默认是的使用字段名 举例：@Property (nameInDb="name")
       @NotNul：设置数据库表当前列不能为空
       @Transient ：添加次标记之后不会生成数据库表的列
     */
   // @Id(autoincrement = true)
    @Id(autoincrement = false)
    private Long id;
 @Property(nameInDb = "USER_NAME")
    private String name;
    @NotNull
    private int repos;
    @Transient
    private int tempUseCount;
    @Property(nameInDb = "NICK_NAME")
    private String nickName;
    /**3.索引注解
     * @Index：使用@Index作为一个属性来创建一个索引，通过name设置索引别名，也可以通过unique给索引添加约束
       @Unique：向数据库列添加了一个唯一的约束
     */
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 948746620)
    private transient GreenEntityDao myDao;
    @Generated(hash = 2082539500)
    public GreenEntity(Long id, String name, int repos, String nickName) {
        this.id = id;
        this.name = name;
        this.repos = repos;
        this.nickName = nickName;
    }
    @Generated(hash = 1875989347)
    public GreenEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getRepos() {
        return this.repos;
    }
    public void setRepos(int repos) {
        this.repos = repos;
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    public String getNickName() {
        return this.nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1401174405)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getGreenEntityDao() : null;
    }

    /**4.关系注解
     * @ToOne：定义与另一个实体（一个实体对象）的关系
       @ToMany：定义与多个实体对象的关系
     */
    /**
     * 编译项目，GreenDao1实体类会自动编译，生成get、set方法并且会在com.assassin.greendaostudy.gen目录下生成三个文件，
     * DaoMaster 、DaoSession、Dao类；
     */
}

