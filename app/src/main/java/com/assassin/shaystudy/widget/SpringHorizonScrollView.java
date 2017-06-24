package com.assassin.shaystudy.widget;

import android.content.Context;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

/**
 * @Author: Shay-Patrick-Cormac
 * @Email: fang47881@126.com
 * @Ltd: GoldMantis
 * @Date: 2017/5/23 09:56
 * @Version: 1.0
 * @Description: 水平方向上的带有弹簧动画的scorllView，前提里面只能有一个子控件。
 */

public class SpringHorizonScrollView extends HorizontalScrollView {
    /**
     * 触摸点的x坐标（相对于屏幕的绝对值）
     */
    private float startDragX;
    /**
     * 弹簧动画
     */
    private SpringAnimation springAnim;

    public SpringHorizonScrollView(Context context) {
        this(context, null);
    }

    public SpringHorizonScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpringHorizonScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //这个构造函数表明在x轴方向，最后回到远点
        springAnim = new SpringAnimation(this, SpringAnimation.TRANSLATION_X, 0);
        SpringForce springForce = springAnim.getSpring();
        //刚度 默认1200 值越大回弹的速度越快
        springForce.setStiffness(SpringForce.STIFFNESS_MEDIUM);
        //阻尼 默认0.5 值越小，回弹之后来回的次数越多
        springForce.setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            //移动，逻辑点
            case MotionEvent.ACTION_MOVE:
                //向右移动
                if (getScrollX()<=0)
                {
                    if (startDragX==0)
                        startDragX = ev.getRawX();
                    float delX = ev.getRawX() - startDragX;
                    if (delX>0)
                    {
                        //设置偏移量
                        setTranslationX(delX/3);
                        return true;
                    }else 
                    {
                        springAnim.cancel();
                        setTranslationX(0);
                    }
                    
                }
                //向左移动(这时候是getScrollX()大于0)
                else if ((getScrollX()+getWidth())>=getChildAt(0).getMinimumWidth())
                {
                    if (startDragX==0)
                        startDragX = ev.getRawX();
                    float delX = ev.getRawX() - startDragX;
                    if (delX<0)
                    {
                        //设置偏移量
                        setTranslationX(delX/3);
                        return true;
                    }else
                    {
                        springAnim.cancel();
                        setTranslationX(0);
                    }
                    
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (getTranslationX()!=0)
                    springAnim.start();
                startDragX = 0;
                break;
        }
        return super.onTouchEvent(ev);
    }
}
