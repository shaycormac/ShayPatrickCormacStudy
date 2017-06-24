package com.assassin.shaystudy.widget;

import android.content.Context;
import android.support.animation.SpringAnimation;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * @Author: Shay-Patrick-Cormac
 * @Email: fang47881@126.com
 * @Ltd: GoldMantis
 * @Date: 2017/5/24 11:23
 * @Version:
 * @Description:
 */

public class SpringRecyclerView extends RecyclerView {
    private float startDragY;
    private SpringAnimation springAnim;
    public SpringRecyclerView(Context context) {
        this(context,null);
    }
    public SpringRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SpringRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        springAnim = new SpringAnimation(this, SpringAnimation.TRANSLATION_Y, 0);
        //刚度 默认1200 值越大回弹的速度越快
        springAnim.getSpring().setStiffness(800.0f);
        //阻尼 默认0.5 值越小，回弹之后来回的次数越多
        springAnim.getSpring().setDampingRatio(0.4f);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        Log.d("scrollY()", getScrollY()+"");
        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (getScrollY() <= 0)
                {
                    //顶部下拉
                    if (startDragY == 0) {
                        startDragY = e.getRawY();
                    }
                    if (e.getRawY() - startDragY >= 0) {
                        setTranslationY((e.getRawY() - startDragY) / 3);
                        return true;
                    } else {
                        startDragY = 0;
                        springAnim.cancel();
                        setTranslationY(0);
                    }
                } else if (isVisBottom())
                {
                    //底部上拉
                    if (startDragY == 0) {
                        startDragY = e.getRawY();
                    }
                    if (e.getRawY() - startDragY <= 0) {
                        setTranslationY((e.getRawY() - startDragY) / 3);
                        return true;
                    } else {
                        startDragY = 0;
                        springAnim.cancel();
                        setTranslationY(0);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (getTranslationY() != 0) {
                    springAnim.start();
                }
                startDragY = 0;
                break;
        }
        return super.onTouchEvent(e);
    }
    

    /*public boolean isSlideToBottom() 
    {
     
        if (computeVerticalScrollExtent() + computeVerticalScrollOffset() >= computeVerticalScrollRange())
            return true;
        return false;
    }
*/

    public boolean isVisBottom()
    {
        LinearLayoutManager layoutManager = (LinearLayoutManager) getLayoutManager();
        //屏幕中最后一个可见子项的position
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        //当前屏幕所看到的子项个数
        int visibleItemCount = layoutManager.getChildCount();
        //当前RecyclerView的所有子项个数
        int totalItemCount = layoutManager.getItemCount();
        //RecyclerView的滑动状态
      /*  int state = getScrollState();
        if(visibleItemCount > 0 && lastVisibleItemPosition == totalItemCount - 1 ){
            return true;
        }else {
            return false;
        }*/
        boolean result = false;
        if (lastVisibleItemPosition==totalItemCount - 1)
        {
            
            View bottomChildView = getChildAt(lastVisibleItemPosition - firstVisibleItemPosition);
            result = (getHeight() >= bottomChildView.getBottom());
        }
        return result;
    }


   /* public boolean isBottom()
    {
        boolean result = false;
        if (getLastVisiblePosition()==(getCount()-1))
        {
            //先弄清一个概念。getTop，getLeft等是相对于它的父布局来说的，这里就是listView
            //最后一个view是这么找到的，当前屏幕的最后一个可见位置减去第一个可见位置
            View bottomChildView = getChildAt(getLastVisiblePosition() - getFirstVisiblePosition());
            //是否到底部是当前listView的高度是否大于最后一个位置的高度
            result = (getHeight() >= bottomChildView.getBottom());
        }
        return result;
    }*/


}
