package com.assassin.gsonstudy.widget.rcv;

import java.util.List;

/**
 * Created by Android2 on 2016/6/27.
 * 下拉刷新上拉加载更多监听接口
 */
public interface RecyclerViewListListener {
    
    /**
     * 初始化，刷新，加载之前的回调方法
     * @param actionType（三个状态 INIT,REFRESH,GETMORE）
     */
    void beforeHandleResponse( @RecyclerViewList.ActionType int actionType);

    /**
     * 获取数据后的操作
     */
    void afterHandleResponse(List mListItems,@RecyclerViewList.ActionType int actionType, final boolean isNoMore);
}
