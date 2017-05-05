package com.assassin.gsonstudy.widget.rcv;

import android.content.Context;

/**
 * Created by Android2 on 2016/5/13.
 */
public class StaggeredGridLayoutManagerParams extends BaseLayoutManagerParam {

    /**
     * The number of columns in the grid
     */
    public int spanCount;
    /**
     * Layout orientation. Should be HORIZONTAL or VERTICAL.
     */
    public int orientation;

    /**
     * 瀑布流模式暂不支持header和footer功能
     *
     * @param context
     * @param spanCount
     * @param orientation
     */
    public StaggeredGridLayoutManagerParams(Context context, int spanCount, int orientation) {
        super(context);
        this.spanCount = spanCount;
        this.orientation = orientation;
    }
}
