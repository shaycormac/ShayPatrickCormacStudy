package test;

import android.content.Context;
import android.view.View;

import com.assassin.gsonstudy.R;
import com.assassin.gsonstudy.entity.JsonResponse;
import com.assassin.gsonstudy.entity.ShakeRecord;
import com.assassin.gsonstudy.utils.GsonUtil;
import com.assassin.gsonstudy.widget.net.Api;
import com.assassin.gsonstudy.widget.rcv.GridLayoutManagerParams;
import com.assassin.gsonstudy.widget.rcv.RecyclerViewList;
import com.assassin.gsonstudy.widget.rcv.RefreshRecyclerView;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import okhttp3.Response;

/**
 * 摇一摇奖品展示列表
 *
 * @author 作者： adminstrator(范方方)
 * @datetime 创建时间：2016-07-05 17:33 GMT+8
 * @email 邮箱： 574583006@qq.com
 */
public class ShakeRecordList extends RecyclerViewList<ShakeRecord> {
    private int num;
    public ShakeRecordList(Context context, RefreshRecyclerView prRecyclerView, View headView) 
    {
        //todo 布局文件没有写
        super(context, prRecyclerView, R.layout.list_item_shake_reword,null);
        //设置布局管理器
        setBaseLayoutManagerParam(new GridLayoutManagerParams(context,2));
        initListViewStart();
    }

    @Override
    public void asyncData() {
        new Api(context, callBack).getShakeRecordList(pageSize, pageNum);
    }

    @Override
    public boolean handleResponse(Response response, int actionType) 
    {
        num++;
       // baseQuickAdapter.loadMoreEnd();
        JsonResponse<List<ShakeRecord>> jsonResponse = null;
        try {
            jsonResponse = new GsonUtil<JsonResponse<List<ShakeRecord>>>() {
            }.parseToJson(response);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        List<ShakeRecord> groupItems = jsonResponse.content;

        if (groupItems != null && !groupItems.isEmpty())
        {
                if (actionType!=RecyclerViewList.GETMORE)
                    mListItems.addAll(groupItems);
                else
                    baseQuickAdapter.addData(groupItems);
                return false;
        }
        return true;
    }

    @Override
    public void newItemView(BaseViewHolder holder, final ShakeRecord record) 
    {
        //todo H5网址带好
        holder.setText(R.id.tv_shake_gift_name,record.awardName);
        holder.setText(R.id.tv_shake_gift_time,"时间："+record.createTime);
        if (record.source==0)
        {
            holder.setText(R.id.tv_shake_gift_source,"来源：摇一摇，免费");
        }else if (record.source==1)
        {
            holder.setText(R.id.tv_shake_gift_source,"来源：摇一摇，1pu币");
        }
        else if (record.source==2)
        {
            holder.setText(R.id.tv_shake_gift_source,"来源：摇一摇，2pu币");
        }else
        holder.setText(R.id.tv_shake_gift_source,"来源：摇一摇，5pu币");
        if (record.type==0) {
            holder.setText(R.id.tv_shake_gift_type, "类型：实物");
           
        }else
            holder.setText(R.id.tv_shake_gift_type, "类型：奖券"); 
       

        holder.setTag(R.id.rl_shake_click,record.url);
       

    }
    
    
}
