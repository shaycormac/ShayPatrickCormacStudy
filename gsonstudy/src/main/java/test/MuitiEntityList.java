package test;

import android.content.Context;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.assassin.gsonstudy.R;
import com.assassin.gsonstudy.entity.muilEntity.MuiltTypeTest;
import com.assassin.gsonstudy.entity.muilEntity.TypeA;
import com.assassin.gsonstudy.entity.muilEntity.TypeB;
import com.assassin.gsonstudy.entity.muilEntity.TypeC;
import com.assassin.gsonstudy.widget.net.Api;
import com.assassin.gsonstudy.widget.rcv.RecyclerViewList;
import com.assassin.gsonstudy.widget.rcv.RefreshRecyclerView;
import com.assassin.gsonstudy.widget.rcv.StaggeredGridLayoutManagerParams;
import com.bumptech.glide.Glide;
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
        setBaseLayoutManagerParam(new StaggeredGridLayoutManagerParams(context,2, StaggeredGridLayoutManager.VERTICAL));
        initListViewStart();
    }

    @Override
    public void asyncData() {
        new Api(context, callBack).getShakeRecordList(pageSize, pageNum);
    }

    @Override
    public boolean handleResponse(Response response, int actionType) {
        List<MuiltTypeTest> tests = getList();
        if (mListItems.size()<20) 
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
                Glide.with(context).load(muiltType.typeB.icon).into(img);
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
        TypeB typeB = new TypeB("https://pic.pocketuni.com.cn/data/sys_pic/entry/xybl.png?v=5.9.01493347234", "毛主义万岁");
        muiltTypeTest = new MuiltTypeTest(typeB, MuiltTypeTest.TYPE_B);
        entities.add(muiltTypeTest);
        TypeC typeC = new TypeC("10", 32.5f);
        muiltTypeTest = new MuiltTypeTest(typeC, MuiltTypeTest.TYPE_C);
        entities.add(muiltTypeTest);

        typeA = new TypeA("王二小", 18);
        muiltTypeTest = new MuiltTypeTest(typeA, MuiltTypeTest.TYPE_A);
        entities.add(muiltTypeTest);

        typeB = new TypeB("https://pic.pocketuni.com.cn/data/sys_pic/entry/xy_grow.png?v=5.9.01493347234", "希特勒哈哈");
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

        typeB = new TypeB("https://pic.pocketuni.com.cn/data/sys_pic/entry/pjj.png?v=5.9.01493347234", "镇三股哟无双");
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
