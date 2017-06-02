package com.assassin.anim.widget;

import android.view.View;

/**
 * @Author: Shay-Patrick-Cormac
 * @Email: fang47881@126.com
 * @Ltd: GoldMantis
 * @Date: 2017/5/20 10:58
 * @Version: 1.0
 * @Description: 对View进行包装，改变某些属性
 */

public class ViewWrapper 
{
    private View target;

    public ViewWrapper(View target) {
        this.target = target;
    }
    //通过set,get改变属性，譬如改变控件的高度
    public int getHeight()
    {
        return target.getLayoutParams().height;
    }
    
    public void setHeight(int height)
    {
        target.getLayoutParams().height = height;
        //重新绘制
        target.requestLayout();
    }
}
