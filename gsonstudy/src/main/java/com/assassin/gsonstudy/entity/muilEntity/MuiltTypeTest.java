package com.assassin.gsonstudy.entity.muilEntity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * @Author: Shay-Patrick-Cormac
 * @Email: fang47881@126.com
 * @Ltd: GoldMantis
 * @Date: 2017/5/5 13:50
 * @Version:
 * @Description:
 */

public class MuiltTypeTest implements MultiItemEntity {
    public TypeA typeA;
    public TypeB typeB;
    public TypeC typeC;

    public static final int TYPE_A = 1;
    public static final int TYPE_B = 2;
    public static final int TYPE_C = 3;

    public int type;
    @Override
    public int getItemType() 
    {
        return type;
    }

    public MuiltTypeTest(TypeA typeA, int type) {
        this.typeA = typeA;
        this.type = type;
    }

    public MuiltTypeTest(TypeB typeB, int type) {
        this.typeB = typeB;
        this.type = type;
    }

    public MuiltTypeTest(TypeC typeC, int type) {
        this.typeC = typeC;
        this.type = type;
    }
}
