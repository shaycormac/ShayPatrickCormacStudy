package com.assassin.gsonstudy.widget.rcv;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import com.chad.library.adapter.base.BaseQuickAdapter;


/**基于RecycleView上拉加载下拉刷新控件
 * Created by Android2 on 2016/5/11.
 * 解决与子控件水平方向的冲突
 */
public class RefreshRecyclerView extends SwipeRefreshLayout {
    public static int MODE_ONLY_REFRESH = 0x1001;
    public static int MODE_ONLY_LOAD_MORE = 0x1002;
    public static int MODE_BOTH = 0x1003;
    public static int MODE_NONE = 0x1004;

    RecyclerView recyclerView;
    private Context context;
    /**
     * 默认模式支持上拉加载下拉刷新
     */
    private int pullToRefreshMode = MODE_BOTH;
    
    private PullToRefreshListener pullToRefreshListener;
    /**
     * 第三方的封装adapter控件基类
     */
    private BaseQuickAdapter baseQuickAdapter;
    //2016/11/11 fix bug  java.lang.IndexOutOfBoundsException: Inconsistency detected. Invalid item position xx(offset:xx).state:xx+1
    //private boolean isRefreshing;

    private int mTouchSlop;
    // 上一次触摸时的X坐标
    private float mPrevX;
    
    /**
     * 上拉加载下拉刷新回调接口
     */
    public interface PullToRefreshListener {
        //下拉刷新
        void onPullDownToRefresh();

        //上拉加载更多
        void onPullUpToLoadMore();
    }


    public RefreshRecyclerView(Context context) {
        this(context,null);
        this.context = context;
        initView();
    }

    public RefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }
    

    private void initView() 
    {
        //xml引入该控件的时候，不执行
        if (isInEditMode())
            return;
        // 触发移动事件的最短距离，如果小于这个距离就不触发移动控件
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
      
        recyclerView = new RecyclerView(context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));//默认的layoutManager
        this.addView(recyclerView);
    }

    /**
     * 供BaseRecycleViewList调用
     * @param baseLayoutManagerParam 布局管理器
     * @param baseQuickAdapter adapter基类
     * @param pullToRefreshListener 回调接口
     */
    public void initPullToRefreshRecyclerView(BaseLayoutManagerParam baseLayoutManagerParam, final BaseQuickAdapter baseQuickAdapter, final PullToRefreshListener pullToRefreshListener) {
        if (baseLayoutManagerParam instanceof LinearLayoutManagerParams) {//LinearLayout
            LinearLayoutManagerParams llParam = (LinearLayoutManagerParams) baseLayoutManagerParam;
            if (llParam.orientation >= 0) {
                recyclerView.setLayoutManager(new LinearLayoutManager(llParam.context, llParam.orientation, llParam.reverseLayout));
            } else {
                recyclerView.setLayoutManager(new LinearLayoutManager(llParam.context));
            }
        } else if (baseLayoutManagerParam instanceof GridLayoutManagerParams) 
        {//GridView
            final GridLayoutManagerParams glParam = (GridLayoutManagerParams) baseLayoutManagerParam;
            GridLayoutManager glManager = null;
            if (glParam.orientation >= 0) {
                glManager = new GridLayoutManager(glParam.context, glParam.spanCount, glParam.orientation, glParam.reverseLayout);

            } else {
                glManager = new GridLayoutManager(glParam.context, glParam.spanCount);
            }
            glManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) 
                {
                    //header、loading 和footer不按GridView排序
                    if (baseQuickAdapter.getItemViewType(position) == BaseQuickAdapter.HEADER_VIEW || baseQuickAdapter.getItemViewType(position) == BaseQuickAdapter.FOOTER_VIEW
                            || baseQuickAdapter.getItemViewType(position) == BaseQuickAdapter.LOADING_VIEW) {
                        return glParam.spanCount;
                    }
                    return 1;
                }
            });
           /* baseQuickAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
                @Override
                public int getSpanSize(GridLayoutManager gridLayoutManager, int position) 
                {
                    return 0;
                }
            });*/
            recyclerView.setLayoutManager(glManager);

        } else if (baseLayoutManagerParam instanceof StaggeredGridLayoutManagerParams) 
        {//瀑布流暂时添加不支持添加header和footer
            final StaggeredGridLayoutManagerParams sglParam = (StaggeredGridLayoutManagerParams) baseLayoutManagerParam;
            StaggeredGridLayoutManager sgManager = new StaggeredGridLayoutManager(sglParam.spanCount, sglParam.orientation);
            recyclerView.setLayoutManager(sgManager);
        }
       //得到回掉接口和基类adapter
        this.pullToRefreshListener = pullToRefreshListener;
        this.baseQuickAdapter = baseQuickAdapter;
        //基类Adapter实现的上拉加载接口
        setAdapter(baseQuickAdapter);
        //swipeRefreshLayout默认实现的接口
        setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (pullToRefreshListener != null) {
                    pullToRefreshListener.onPullDownToRefresh();
                }
            }
        });
    }

    private void setAdapter(BaseQuickAdapter baseQuickAdapter)
    {
        //两次接口的回调，将基类adapter的上拉回掉接口传过来，给本控件，这个控件再用回调接口传出去。
        baseQuickAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (pullToRefreshListener != null) {
                    //上拉请求
                    pullToRefreshListener.onPullUpToLoadMore();
                }
            }
        });
        //recycleView设置adapter.
        recyclerView.setAdapter(baseQuickAdapter);
    }

    public int getPullToRefreshMode() {
        return pullToRefreshMode;
    }

    public void setPullToRefreshMode(int pullToRefreshMode) {
        this.pullToRefreshMode = pullToRefreshMode;
    }

    public BaseQuickAdapter getBaseQuickAdapter() {
        return baseQuickAdapter;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return this;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPrevX = event.getX();
                break;

            case MotionEvent.ACTION_MOVE:
                final float eventX = event.getX();
                float xDiff = Math.abs(eventX - mPrevX);
                // Log.d("refresh" ,"move----" + eventX + "   " + mPrevX + "   " + mTouchSlop);
                // 增加60的容差，让下拉刷新在竖直滑动时就可以触发
                if (xDiff > mTouchSlop + 60) {
                    return false;
                }
        }

        return super.onInterceptTouchEvent(event);
    }
}
