package com.goldmantis.quality.dagger2study.entity;

import dagger.Module;
import dagger.Provides;

/**
 * @Author: Shay-Patrick-Cormac
 * @Email: fang47881@126.com
 * @Ltd: GoldMantis
 * @Date: 2017/7/7 10:45
 * @Version: 1.0
 * @Description: 制作卡车
 */
@Module
public class CarModule 
{
    @Provides
    public Engine provideEngine()
    {
        return new Engine();
    }

    @Provides
    public Seat provideSeat(){
        return new Seat();
    }

    @Provides
    public Wheel provideWheel(){
        return new Wheel();
    }
}
