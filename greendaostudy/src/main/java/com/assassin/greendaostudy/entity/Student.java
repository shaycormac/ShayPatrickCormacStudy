package com.assassin.greendaostudy.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @Author: Shay-Patrick-Cormac
 * @Email: fang47881@126.com
 * @Ltd: GoldMantis
 * @Date: 2017/5/2 17:04
 * @Version: 数据库升级，添加的实体
 * @Description:
 */
@Entity
public class Student {
    @Id
    private Long id;

    @Index(unique = true)
    private String userId;

    @Property(nameInDb = "STUDENT_NAME")
    private String name;

    //7添加字段 age
    private int age;

    @Generated(hash = 1351168286)
    public Student(Long id, String userId, String name, int age) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.age = age;
    }

    @Generated(hash = 1556870573)
    public Student() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
