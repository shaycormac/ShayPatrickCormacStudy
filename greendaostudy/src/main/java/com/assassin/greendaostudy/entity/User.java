package com.assassin.greendaostudy.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;

/**
 * @Author: Shay-Patrick-Cormac
 * @Email: fang47881@126.com
 * @Ltd: GoldMantis
 * @Date: 2017/5/2 14:32
 * @Version:
 * @Description: 第二个实体
 */
@Entity
public class User {
    @Id
    private Long useId;

    private String userName;

    private String nickName;
    //唯一主键
    //每个实体类都应该有一个long或者LONG型属性作为主键；如果你不想用long或者LONG型作为主键，你可以使用一个唯一索引
    // (使用@Index(unique = true)注释使普通属性改变成唯一索引属性)属性作为关键属性。
    @Index(unique = true)
    private String keyId;
    //添加新的字段
    @Property(nameInDb = "USER_SCORE")
    private float score;

    @Generated(hash = 885630725)
    public User(Long useId, String userName, String nickName, String keyId,
            float score) {
        this.useId = useId;
        this.userName = userName;
        this.nickName = nickName;
        this.keyId = keyId;
        this.score = score;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public Long getUseId() {
        return this.useId;
    }

    public void setUseId(Long useId) {
        this.useId = useId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return this.nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getKeyId() {
        return this.keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public float getScore() {
        return this.score;
    }

    public void setScore(float score) {
        this.score = score;
    }
    
}
