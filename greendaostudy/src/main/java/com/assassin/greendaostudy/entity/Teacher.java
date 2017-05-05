package com.assassin.greendaostudy.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @Author: Shay-Patrick-Cormac
 * @Email: fang47881@126.com
 * @Ltd: GoldMantis
 * @Date: 2017/5/2 17:23
 * @Version:
 * @Description: 数据库升级到7
 */
@Entity
public class Teacher
{
    @Id
    private Long id;

    @Index(unique = true)
    private String teacherId;

    private String teacherName;

    @Generated(hash = 1213519437)
    public Teacher(Long id, String teacherId, String teacherName) {
        this.id = id;
        this.teacherId = teacherId;
        this.teacherName = teacherName;
    }

    @Generated(hash = 1630413260)
    public Teacher() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeacherId() {
        return this.teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return this.teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}
