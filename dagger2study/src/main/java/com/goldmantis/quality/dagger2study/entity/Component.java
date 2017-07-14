package com.goldmantis.quality.dagger2study.entity;

/**
 * @Author: Shay-Patrick-Cormac
 * @Email: fang47881@126.com
 * @Ltd: GoldMantis
 * @Date: 2017/7/7 10:47
 * @Version:
 * @Description: 连接Module和你的Car
 */
@dagger.Component(modules = CarModule.class)
public interface Component 
{
    void inject(Car car);
}
