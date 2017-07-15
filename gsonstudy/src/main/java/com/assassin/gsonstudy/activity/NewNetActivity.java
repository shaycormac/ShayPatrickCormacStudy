package com.assassin.gsonstudy.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.assassin.gsonstudy.R;
import com.assassin.gsonstudy.newNet.Author;
import com.assassin.gsonstudy.newNet.MyCallback;
import com.assassin.gsonstudy.newNet.NetHttp;
import com.assassin.gsonstudy.newNet.NetJson;
import com.assassin.gsonstudy.utils.VolleyLog;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class NewNetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_net);
        //发送网络请求
        initNet();
    }

    private void initNet() 
    {
        new NetHttp<NetJson<List<Author>>>(this, new MyCallback<NetJson<List<Author>>>() 
        {
            @Override
            public void onSuccess(NetJson<List<Author>> listNetJson, Call call, Response response)
            {
                VolleyLog.d("得到的字符串为：%s",listNetJson.data.toString());
                
            }

            @Override
            public void onError(Call call, Response response, Exception e) 
            {
                super.onError(call, response, e);
                VolleyLog.d("错吴的原因：%s",e.getMessage());
                VolleyLog.d("错吴的原因：%s",e.getLocalizedMessage());
                VolleyLog.d("错吴的原因：%s",e.toString());
            }
        }).getJsonObject();
    }
}
