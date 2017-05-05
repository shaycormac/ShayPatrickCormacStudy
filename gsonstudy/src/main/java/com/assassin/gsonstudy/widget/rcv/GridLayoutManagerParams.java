package com.assassin.gsonstudy.widget.rcv;

import android.content.Context;

/**
 * Created by Android2 on 2016/5/13.
 */
public class GridLayoutManagerParams extends BaseLayoutManagerParam {

    public GridLayoutManagerParams(Context context, int spanCount) {
        super(context);
        this.spanCount = spanCount;
    }

    public GridLayoutManagerParams(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context);
        this.spanCount = spanCount;
        this.orientation = orientation;
        this.reverseLayout = reverseLayout;
    }

    /**
     * The number of columns in the grid
     */
    public int spanCount;
    /**
     * Layout orientation. Should be HORIZONTAL or VERTICAL.
     */
    public int orientation = -1;
    /**
     * When set to true, layouts from end to start.
     */
    public boolean reverseLayout;

}
