package com.goldmantis.quality.dagger2study.entity;

import com.goldmantis.quality.dagger2study.util.VolleyLog;

import javax.inject.Inject;

/**
 * @Author: Shay-Patrick-Cormac
 * @Email: fang47881@126.com
 * @Ltd: GoldMantis
 * @Date: 2017/7/7 10:48
 * @Version: 1.0
 * @Description: 制作的汽车
 */

public class Car 
{
    @Inject
    Engine engine;
    @Inject
    Seat seat;
    @Inject
    Wheel wheel;
    
    public Car()
    {
       DaggerComponent.builder()
               .carModule(new CarModule())
               .build()
               .inject(this);
        VolleyLog.d("new Car()");
        
    }
}
