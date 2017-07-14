package com.goldmantis.quality.dagger2study.entity;

import com.goldmantis.quality.dagger2study.util.VolleyLog;

/**
 * @Author: Shay-Patrick-Cormac
 * @Email: fang47881@126.com
 * @Ltd: GoldMantis
 * @Date: 2017/7/7 10:42
 * @Version:制作汽车的工具
 * @Description:
 */

public class MakeCar
{
}

/**
 * 发动机
 */
 class Engine 
{

    public Engine(){
        VolleyLog.d("new Engine()");
    }
}

/**
 * 车座
 */
 class Seat {
    public Seat(){
        VolleyLog.d("new Seat()");
    }
}

/**
 * 轮子
 */
class Wheel {
    public Wheel(){
        VolleyLog.d("new Wheel()");
    }
}
