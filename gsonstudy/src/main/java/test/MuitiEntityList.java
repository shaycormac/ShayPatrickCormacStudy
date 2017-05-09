package test;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.assassin.gsonstudy.R;
import com.assassin.gsonstudy.entity.muilEntity.MuiltTypeTest;
import com.assassin.gsonstudy.entity.muilEntity.TypeA;
import com.assassin.gsonstudy.entity.muilEntity.TypeB;
import com.assassin.gsonstudy.entity.muilEntity.TypeC;
import com.assassin.gsonstudy.utils.GlideUtil;
import com.assassin.gsonstudy.widget.net.Api;
import com.assassin.gsonstudy.widget.rcv.GridLayoutManagerParams;
import com.assassin.gsonstudy.widget.rcv.RecyclerViewList;
import com.assassin.gsonstudy.widget.rcv.RefreshRecyclerView;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * @Author: Shay-Patrick-Cormac
 * @Email: fang47881@126.com
 * @Ltd: GoldMantis
 * @Date: 2017/5/5 14:05
 * @Version:
 * @Description:
 */

public class MuitiEntityList extends RecyclerViewList<MuiltTypeTest> {
    public MuitiEntityList(Context context, RefreshRecyclerView prRecyclerView, View headerView) {
        super(context, prRecyclerView, headerView);
      //  setBaseLayoutManagerParam(new StaggeredGridLayoutManagerParams(context,2, StaggeredGridLayoutManager.VERTICAL));
        setBaseLayoutManagerParam(new GridLayoutManagerParams(context,2));
        initListViewStart();
    }

    @Override
    public void asyncData() {
        new Api(context, callBack).getShakeRecordList(pageSize, pageNum);
    }

    @Override
    public boolean handleResponse(Response response, int actionType) {
        List<MuiltTypeTest> tests = getList();
        if (mListItems.size()<16) 
        {
            if (actionType != RecyclerViewList.GETMORE)
                mListItems.addAll(tests);
            else
                baseQuickAdapter.addData(tests);
            return false;
        }
        return true;
    }

    @Override
    public void newItemView(BaseViewHolder holder, MuiltTypeTest muiltType) {
        //具体布局使用
        switch (muiltType.getItemType()) {
            case MuiltTypeTest.TYPE_A:
                holder.setText(R.id.tvTypeA_name, muiltType.typeA.name);
                holder.setText(R.id.tvTypeA_age, String.valueOf(muiltType.typeA.age));
                break;
            case MuiltTypeTest.TYPE_B:
                holder.setText(R.id.tvTypeB_title, muiltType.typeB.title);
                ImageView img = holder.getView(R.id.imgTypeBIcon);
                //测试
              //  GlideUtil.loadCircleImage(context,img,muiltType.typeB.icon);
              //  GlideUtil.loadBlurImage(context,img,muiltType.typeB.icon);
                GlideUtil.loadRoundImage(context,img,muiltType.typeB.icon,30);
                break;
            case MuiltTypeTest.TYPE_C:
                holder.setText(R.id.tvTypeC_userId, "用户的id为： " + muiltType.typeC.userId);
                holder.setText(R.id.tvTypeC_price, "用户需要花钱为： " + muiltType.typeC.price);
                break;
            default:
                break;
        }

    }

    @Override
    public void addItemViewType() {
       addItemType(MuiltTypeTest.TYPE_A, R.layout.rcv_type_a);
        addItemType(MuiltTypeTest.TYPE_B, R.layout.rcv_type_b);
        addItemType(MuiltTypeTest.TYPE_C, R.layout.rcv_type_c);
        super.addItemViewType();
    }


    private List<MuiltTypeTest> getList() {
        List<MuiltTypeTest> entities = new ArrayList<>();
        //第一种类型
        TypeA typeA = new TypeA("小兵张嘎子", 16);
        MuiltTypeTest muiltTypeTest = new MuiltTypeTest(typeA, MuiltTypeTest.TYPE_A);
        entities.add(muiltTypeTest);
        typeA = new TypeA("白洋淀", 35);
        muiltTypeTest = new MuiltTypeTest(typeA, MuiltTypeTest.TYPE_A);
        entities.add(muiltTypeTest);
        TypeB typeB = new TypeB("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1493297470390&di=2c668310acb30d75625d557151f80043&imgtype=0&src=http%3A%2F%2Fimgsports.eastday.com%2Fsports%2Fimg%2F201704200946176071.jpeg", "毛主义万岁");
        muiltTypeTest = new MuiltTypeTest(typeB, MuiltTypeTest.TYPE_B);
        entities.add(muiltTypeTest);
        TypeC typeC = new TypeC("10", 32.5f);
        muiltTypeTest = new MuiltTypeTest(typeC, MuiltTypeTest.TYPE_C);
        entities.add(muiltTypeTest);

        typeA = new TypeA("王二小", 18);
        muiltTypeTest = new MuiltTypeTest(typeA, MuiltTypeTest.TYPE_A);
        entities.add(muiltTypeTest);

        typeB = new TypeB("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1493297492557&di=eeaa836e97379953fde3a19af31b4b67&imgtype=0&src=http%3A%2F%2Ffwimage.cnfanews.com%2Fwebsiteimg%2F2017%2F20170419%2F20065767%2Fcibu7urj00bv0005.jpg", "希特勒哈哈");
        muiltTypeTest = new MuiltTypeTest(typeB, MuiltTypeTest.TYPE_B);
        entities.add(muiltTypeTest);

        typeA = new TypeA("张二毛子", 24);
        muiltTypeTest = new MuiltTypeTest(typeA, MuiltTypeTest.TYPE_A);
        entities.add(muiltTypeTest);
        typeC = new TypeC("18", 6f);
        muiltTypeTest = new MuiltTypeTest(typeC, MuiltTypeTest.TYPE_C);
        entities.add(muiltTypeTest);
        typeC = new TypeC("20", 441f);
        muiltTypeTest = new MuiltTypeTest(typeC, MuiltTypeTest.TYPE_C);
        entities.add(muiltTypeTest);

        typeB = new TypeB("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1493297507797&di=7ca09fb34b8e9a6c8f878e008cd10bc5&imgtype=0&src=http%3A%2F%2Ffwimage.cnfanews.com%2Fwebsiteimg%2F2017%2F20170316%2F84172%2Fimg8935341_n.jpg", "镇三股哟无双");
        muiltTypeTest = new MuiltTypeTest(typeB, MuiltTypeTest.TYPE_B);
        entities.add(muiltTypeTest);


        typeC = new TypeC("11", 4f);
        muiltTypeTest = new MuiltTypeTest(typeC, MuiltTypeTest.TYPE_C);
        entities.add(muiltTypeTest);
        typeC = new TypeC("12", 66f);
        muiltTypeTest = new MuiltTypeTest(typeC, MuiltTypeTest.TYPE_C);
        entities.add(muiltTypeTest);
        typeC = new TypeC("15", 8f);
        muiltTypeTest = new MuiltTypeTest(typeC, MuiltTypeTest.TYPE_C);
        entities.add(muiltTypeTest);
        return entities;
    }
}
