package com.assassin.gsonstudy.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.assassin.gsonstudy.R;
import com.assassin.gsonstudy.entity.GroupItem;
import com.assassin.gsonstudy.entity.LzyResponse;
import com.assassin.gsonstudy.net.Api;
import com.assassin.gsonstudy.net.CallBack;
import com.assassin.gsonstudy.utils.GsonJsonUtil;
import com.assassin.gsonstudy.utils.VolleyLog;

import java.util.List;

import okhttp3.Response;

public class OkGoActivity extends AppCompatActivity {

    private RecyclerView rcvOkGo;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_go);
        activity = this;
        rcvOkGo = (RecyclerView) findViewById(R.id.rcvOkGo);
        //网络请求，gson解析，变成list,加载到rcv
        initNet();
    }

    private void initNet() 
    {
       /* new Api(new CallBack(){
            @Override
            public void onSuccess(int code, String msg, String content) 
            {
                super.onSuccess(code, msg, content);
                VolleyLog.d("这边得到的数据为 %s",content);
            }
        },  activity).getGroupByVitality(1,10);*/
       /* new Api(activity, new DialogCallback<LzyResponse<List<GroupItem>>>(activity) 
        {
            @Override
            public void onSuccess(LzyResponse<List<GroupItem>> lzyResponse, Call call, Response response)
            {
                VolleyLog.d("这边得到的数据为 %s",lzyResponse.content.toString());
                
            }
        }).getGroupByVitality(1,10);*/
        
        new Api(activity,new CallBack(){
            @Override
            public void onRespond(Response response) throws NoSuchMethodException {
                super.onRespond(response);
                LzyResponse<List<GroupItem>> lzyResponse = new GsonJsonUtil<LzyResponse<List<GroupItem>>>()
                {}.getJson(response);
                VolleyLog.d("这边得到的数据为 %s",lzyResponse.content.toString());
            }
        }).getGroupByVitality(1,10);
    }
}
