package com.assassin.gsonstudy.widget.rcv;

import android.content.Context;

/**
 * Created by Android2 on 2016/5/13.
 */
public class LinearLayoutManagerParams extends BaseLayoutManagerParam {
    /**
     * Layout orientation. Should be HORIZONTAL or VERTICAL.
     */
    public int orientation = -1;
    /**
     * When set to true, layouts from end to start.
     */
    public boolean reverseLayout;

    public LinearLayoutManagerParams(Context context) {
        super(context);
    }

    public LinearLayoutManagerParams(Context context, int orientation, boolean reverseLayout) {
        super(context);
        this.orientation = orientation;
        this.reverseLayout = reverseLayout;
    }
}
