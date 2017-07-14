package com.goldmantis.quality.dagger2study.entity2;

import com.goldmantis.quality.dagger2study.MainActivity;

import dagger.Component;

/**
 * @Author: Shay-Patrick-Cormac
 * @Email: fang47881@126.com
 * @Ltd: GoldMantis
 * @Date: 2017/7/7 14:18
 * @Version:
 * @Description:
 */
@Component
public interface ProductComponent
{
    void inject(MainActivity activity);
}
