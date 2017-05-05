package com.assassin.gsonstudy.widget.rcv;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.assassin.gsonstudy.R;
import com.assassin.gsonstudy.utils.dialog.DialogFragmentHelper;
import com.assassin.gsonstudy.widget.net.Callback;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;


/**
 * @Author: Shay-Patrick-Cormac
 * @Email: fang47881@126.com
 * @Ltd: GoldMantis
 * @Date: 2017/5/5 13:47
 * @Version: 1.0
 * @Description: 基于第三方开源控件BaseRecyclerViewHelper的再次封装，
 * 支持单，多种布局，支持错误页面
 */
public abstract class RecyclerViewList<E> {
    protected Context context;
    private RefreshRecyclerView prRecyclerView;
    public final ArrayList<E> mListItems = new ArrayList<>();
    protected BaseRecyclerViewAdapter baseQuickAdapter;

    /**
     * 状态
     */
    @ActionType
    public int actionType = IDLE;
    /**
     * 默认位置，每次状态改变之后都要恢复到这个状态。
     */
    public static final int IDLE = 0x00000001;
    /**
     * 首次进入页面
     */
    public static final int INIT = 0x00000002;
    /**
     * 下拉刷新
     */
    public static final int REFRESH = 0x00000003;
    /**
     * 上拉加载
     */
    public static final int GETMORE = 0x00000004;

    @IntDef({IDLE, INIT, REFRESH, GETMORE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ActionType {

    }

    /**
     * 页码
     */
    protected int pageNum = 1;
    /**
     * 每页加载的数量
     */
    public int pageSize = 10;
    /**
     * 头文件
     */
    protected View headerView;
    /**
     * 请求的数据为空的页面（首次进入以及刷新的情况使用）
     */
    protected View emptyView;
    /**
     * 单个item的xml布局id
     */
    private int layoutResourceId = 11;
    /**
     * 每次请求后台返回数据集合，有则为false,没有为true
     */
    private boolean isNoMore;
    /**
     * 首次进入页面加载的dialog
     */
    private DialogFragment progressDialogFragment;

    private BaseLayoutManagerParam baseLayoutManagerParam;
    /**
     * 加载数据模式
     */
    private int pullToRefreshMode = RefreshRecyclerView.MODE_BOTH;
    /**
     * 请求网络数据
     */
    protected CallBackList callBack = new CallBackList();


    /**
     * 在请求网络之前和加载数据之后的回调接口
     */
    private RecyclerViewListListener recyclerViewListListener;
    /**
     * 支持列表为空，需要加载的emptyView来源。
     */
    private ImageView imgNoData;
    private int emptyImageResource;

    /**
     * 网络请求失败的View提示
     */
    private View errorView;
    /**
     * 布局解析器
     */
    private LayoutInflater inflater;
    /**
     * 设置adapter(仅设置一次)
     */
    private boolean isFirstLoad = true;

    /**
     * 调用网络数据
     */
    public abstract void asyncData();

    /**
     * 分析数据
     *
     * @param response   网络返回数据
     * @param actionType 下拉刷新、上拉加载等状态
     * @return isNomore(true：没有更多数据，false: 有更多数据)
     */
    public abstract boolean handleResponse(Response response, @ActionType int actionType);

    /**
     * 初始化Item
     *
     * @param holder ViewHolder
     * @param object 数据
     */
    public abstract void newItemView(BaseViewHolder holder, E object);

    /**
     * 多种布局的Item的xml文件资源
     */
    private SparseArray<Integer> layouts;

    /**
     * 多个layout Item时，必须实现该方法
     */
    public void addItemViewType() {
    }

    /**
     * 单个Layout(默认加载线性布局)
     *
     * @param context
     * @param prRecyclerView   下拉刷新上拉加载更多RecyclerView
     * @param layoutResourceId layout resource Id
     * @param headerView       header view（无header view时，可以传值null)
     */
    public RecyclerViewList(Context context, RefreshRecyclerView prRecyclerView, int layoutResourceId, View headerView) {
        this(context, prRecyclerView,layoutResourceId, headerView,null);
    }

   

    /**
     * 单个Layout
     *
     * @param context
     * @param prRecyclerView           下拉刷新上拉加载更多RecyclerView
     * @param layoutResourceId         layout resource Id
     * @param headerView               header view（无header view时，可以传值null)
     * @param recyclerViewListListener recyclerView Listener 回调接口
     */
    public RecyclerViewList(Context context, RefreshRecyclerView prRecyclerView, int layoutResourceId,View headerView, RecyclerViewListListener recyclerViewListListener) {
        this.context = context;
        this.prRecyclerView = prRecyclerView;
        this.pullToRefreshMode = prRecyclerView.getPullToRefreshMode();
        this.layoutResourceId = layoutResourceId;
        this.headerView = headerView;
        this.baseLayoutManagerParam = new LinearLayoutManagerParams(context);
        this.recyclerViewListListener = recyclerViewListListener;
        inflater = LayoutInflater.from(context);
        this.emptyView = inflater.inflate(R.layout.recyclerview_empty, (ViewGroup) prRecyclerView.getRecyclerView().getParent(), false);
        this.imgNoData = (ImageView) this.emptyView.findViewById(R.id.img_no_data);
        this.errorView = inflater.inflate(R.layout.recyclerview_empty_no_internet, null);
        this.errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshRecyclerViewStart();
            }
        });
    }

    /**
     * 多个layout
     *
     * @param context
     * @param prRecyclerView 下拉刷新上拉加载更多RecyclerView
     * @param headerView     header view（无header view时，可以传值null)
     */
    public RecyclerViewList(Context context, RefreshRecyclerView prRecyclerView, View headerView) 
    {
        this(context, prRecyclerView, 0, headerView);
    }


    /**
     * 多个layout
     *
     * @param context
     * @param prRecyclerView           下拉刷新上拉加载更多RecyclerView
     * @param headerView               header view（无header view时，可以传值null)
     * @param recyclerViewListListener recyclerView 的监听事件
     */
    public RecyclerViewList(Context context, RefreshRecyclerView prRecyclerView, View headerView, RecyclerViewListListener recyclerViewListListener) {
        this(context, prRecyclerView, 0, headerView, recyclerViewListListener);
    }

    /**
     * 初始化RecyclerView
     */
    private void init() {
        baseQuickAdapter = new BaseRecyclerViewAdapter(mListItems, layoutResourceId);
        baseQuickAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        prRecyclerView.getRecyclerView().setHasFixedSize(true);
        //设置支持带头文件的空布局
        baseQuickAdapter.setHeaderAndEmpty(true);
        prRecyclerView.initPullToRefreshRecyclerView(baseLayoutManagerParam, baseQuickAdapter, new RefreshRecyclerView.PullToRefreshListener() {
            @Override
            public void onPullDownToRefresh() {
                refreshRecyclerViewStart();
            }

            @Override
            public void onPullUpToLoadMore() {
                getMoreRecyclerViewStart();
            }
        });

        if (headerView != null) {
            headerView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            baseQuickAdapter.addHeaderView(headerView);
        }

        if (pullToRefreshMode == RefreshRecyclerView.MODE_ONLY_REFRESH || pullToRefreshMode == RefreshRecyclerView.MODE_NONE) {
            //禁止上拉加载
            baseQuickAdapter.setEnableLoadMore(false);
        } else {
            //允许上拉加载
            baseQuickAdapter.setEnableLoadMore(true);
        }

        //   prRecyclerView.getRecyclerView().setOnTouchListener(mOnTouchListener);
        if (pullToRefreshMode == RefreshRecyclerView.MODE_ONLY_LOAD_MORE || pullToRefreshMode == RefreshRecyclerView.MODE_NONE) {
            if (prRecyclerView.getSwipeRefreshLayout() != null) {
                prRecyclerView.getSwipeRefreshLayout().setEnabled(false);
            }
        }
    }

    /**
     * 必须在每个继承该方法的子类构造方法调用这个方法，否则无法完成初始化
     */
    public void initListViewStart() {
        if (actionType != IDLE) {
            return;
        }
        showProgressDialog();
        actionType = INIT;
        pageNum = 1;
        if (recyclerViewListListener != null) {
            recyclerViewListListener.beforeHandleResponse(INIT);
        }
        asyncData();
    }

    public void refreshRecyclerViewStart() {
        //没有回到空闲状态，直接返回
        if (actionType != IDLE)
            return;
        //设置为刷新状态
        actionType = REFRESH;
        //请求页码设置为1
        pageNum = 1;
        //发送网络请求之前要做的动作
        if (recyclerViewListListener != null) {
            recyclerViewListListener.beforeHandleResponse(REFRESH);
        }
        //不能上拉加载
        if (baseQuickAdapter != null)
            baseQuickAdapter.setEnableLoadMore(false);
        //发送网络请求，具体实现的时候，传入基类的callBack，context,以及页码。
        asyncData();
    }

    public void getMoreRecyclerViewStart() {
        if (actionType != IDLE)
            return;
        actionType = GETMORE;
        pageNum++;
        if (recyclerViewListListener != null) {
            recyclerViewListListener.beforeHandleResponse(GETMORE);
        }
        //不能下拉刷新
        if (prRecyclerView != null)
            prRecyclerView.setEnabled(false);
        asyncData();
    }

    /**
     * 封装基类Adapter
     */
    public class BaseRecyclerViewAdapter extends BaseQuickAdapter<E, BaseViewHolder> {
        public BaseRecyclerViewAdapter(List<E> dataList, int resourceId) {
            super(resourceId, dataList);
            //支持多种布局的item
            addItemViewType();
        }

        @Override
        protected void convert(BaseViewHolder holder, E item) {
            if (holder != null && item != null) {
                newItemView(holder, item);
            }
        }

        @Override
        protected int getDefItemViewType(int position) {
            //判断复合类型数据
            E e = mData.get(position);
            if (e instanceof MultiItemEntity) {
                return ((MultiItemEntity) e).getItemType();
            }
            return super.getDefItemViewType(position);
        }

        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            if (isItemType(viewType)) {
                return createBaseViewHolder(parent, getLayoutId(viewType));
            }
            return super.onCreateDefViewHolder(parent, viewType);
        }

        private boolean isItemType(int viewType) {
            boolean isItemType = false;
            if (layouts != null) {
                isItemType = layouts.get(viewType) != null;
            }
            return isItemType;
        }

        /**
         * 返回xml的id
         *
         * @param viewType
         * @return
         */
        private int getLayoutId(int viewType) {
            //多种布局
            if (layouts != null && layouts.get(viewType) != null) {
                return layouts.get(viewType);
            } else {
                //单种布局
                return layoutResourceId;
            }

        }
    }

    public void addItemType(int type, int layoutResId) {
        if (layouts == null) {
            layouts = new SparseArray<>();
        }
        layouts.put(type, layoutResId);
    }


    private View.OnTouchListener mOnTouchListener = new View.OnTouchListener() {

        public boolean onTouch(View v, MotionEvent event) {
            if (RecyclerViewList.this.actionType != RecyclerViewList.IDLE) {
                return true;
            }
            return false;
        }
    };

    /**
     * 设置布局管理器
     */
    public void setBaseLayoutManagerParam(BaseLayoutManagerParam baseLayoutManagerParam) {
        this.baseLayoutManagerParam = baseLayoutManagerParam;
    }

    /**
     * 根据不同位置设置empty的图片源
     *
     * @param resourceId
     */
    public void setEmptyImageResource(int resourceId) {
        this.emptyImageResource = resourceId;
    }

    /**
     * 显示加载中Dialog
     */
    public void showProgressDialog() {
        if (context instanceof FragmentActivity)
            progressDialogFragment = DialogFragmentHelper.showProgress(((FragmentActivity) context).getSupportFragmentManager(), "正在加载数据...", false);

    }

    /**
     * 关闭加载中Dialog
     */
    public void dismissProgressDialog() {
        if (progressDialogFragment != null)
            progressDialogFragment.dismiss();
    }


    /**
     * 请求网络加载接口
     */
    public class CallBackList extends Callback {
        @Override
        public void onSuccess(Response response)
        {
            super.onSuccess(response);
            //首次加载或者刷新页面，初始化
            if (isFirstLoad)
            {
                isFirstLoad = false;
                init();
            }
            if (actionType == REFRESH) {
                mListItems.clear();
                // baseQuickAdapter.setNewData(mListItems);
            }
            //作为是否可以继续上拉刷新还是显示没有数据的依据。
            isNoMore = handleResponse(response, actionType);
            switch (actionType) {
                case INIT:
                    dismissProgressDialog();
                    if (isNoMore)
                        baseQuickAdapter.setEmptyView(emptyView);
                    else
                        baseQuickAdapter.notifyDataSetChanged();
                    // baseQuickAdapter.notifyItemInserted(0);
                    break;
                case REFRESH:
                    //正在刷新，则不能让其再刷新
                    if (isNoMore)
                        baseQuickAdapter.setEmptyView(emptyView);
                    else
                        baseQuickAdapter.setNewData(mListItems);
                    prRecyclerView.setRefreshing(false);
                    baseQuickAdapter.setEnableLoadMore(true);
                    break;
                case GETMORE:

                    if (isNoMore) {
                        baseQuickAdapter.loadMoreEnd();
                    } else {
                        if (baseQuickAdapter.getData().size() < pageSize)
                            baseQuickAdapter.loadMoreEnd(false);
                        else
                            baseQuickAdapter.loadMoreComplete();
                    }
                    prRecyclerView.setEnabled(true);
                    break;
            }
            //最后归为0
            actionType = IDLE;

            //数据得到后，回调出去。
            if (recyclerViewListListener != null) {
                recyclerViewListListener.afterHandleResponse(mListItems, actionType, isNoMore);
            }
        }

        @Override
        public void onFailure(int statusCode, Exception e) {
            //这里面不走handleResponse，请注意！！
            super.onFailure(statusCode, e);
            if (isFirstLoad)
            {
                isFirstLoad = false;
                init();
            }
            
            if (actionType == REFRESH) {
                mListItems.clear();
                // baseQuickAdapter.setNewData(mListItems);
            }
            switch (actionType) {
                case INIT:
                    dismissProgressDialog();
                    //提示网络错误的图片片？
                    baseQuickAdapter.setEmptyView(errorView);
                    break;
                case REFRESH:
                    //刷新，也提示网络错误。
                    baseQuickAdapter.setNewData(mListItems);
                    prRecyclerView.setRefreshing(false);
                    baseQuickAdapter.setEnableLoadMore(true);
                    baseQuickAdapter.setEmptyView(errorView);
                    break;
                case GETMORE:
                    //加载错误，这个没毛病
                    baseQuickAdapter.loadMoreFail();
                    prRecyclerView.setEnabled(true);
                    break;
            }
            actionType = IDLE;
        }

    }

}
