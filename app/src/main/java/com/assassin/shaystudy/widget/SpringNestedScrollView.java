package com.assassin.shaystudy.widget;

import android.content.Context;
import android.support.animation.SpringAnimation;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @Author: Shay-Patrick-Cormac
 * @Email: fang47881@126.com
 * @Ltd: GoldMantis
 * @Date: 2017/5/8 17:40
 * @Version:
 * @Description:
 */

public class SpringNestedScrollView extends NestedScrollView 
{
    /**
     * 开始回弹的位置
     */
    private float startDragY;
    private SpringAnimation springAnim;
    public SpringNestedScrollView(Context context) {
        this(context,null);
    }

    public SpringNestedScrollView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SpringNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        springAnim = new SpringAnimation(this, SpringAnimation.TRANSLATION_Y, 0);
        //刚度 默认1200 值越大回弹的速度越快
        springAnim.getSpring().setStiffness(800.0f);
        //阻尼 默认0.5 值越小，回弹之后来回的次数越多
        springAnim.getSpring().setDampingRatio(0.60f);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) 
    {
        switch(ev.getAction())
        {
          case MotionEvent.ACTION_DOWN :
              if (getScrollY()<=0)
              {
                  //顶部下拉
                  if (startDragY ==0)
                      startDragY = ev.getRawY();
                  if (ev.getRawY() -startDragY >=0)
                  {
                      setTranslationY((float) ((ev.getRawY() - startDragY) * 0.3));
                      return true;
                  }else 
                  {
                      startDragY = 0;
                      springAnim.cancel();
                      setTranslationY(0);
                  }
              }else if(getScrollY()+getHeight() >=getChildAt(0).getMeasuredHeight())
              {
                  //底部上拉
                  if (startDragY == 0) {
                      startDragY = ev.getRawY();
                  }
                  if (ev.getRawY() -startDragY <=0)
                  {
                      setTranslationY((float) ((ev.getRawY() - startDragY) * 0.3));
                      return true;
                  }else 
                  {
                      startDragY = 0;
                      springAnim.cancel();
                      setTranslationY(0);
                  }
                  
              }
            break;
            case  MotionEvent.ACTION_CANCEL : 
          case  MotionEvent.ACTION_UP:
              if (getTranslationY()!=0)
                  springAnim.start();
              startDragY = 0;
            break;
        }
        return super.onTouchEvent(ev);
    }
}
