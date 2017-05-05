package com.assassin.gsonstudy.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.assassin.gsonstudy.R;
import com.assassin.gsonstudy.widget.rcv.RefreshRecyclerView;

import test.MuitiEntityList;

public class ShakeRecordListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake_record_list);
        RefreshRecyclerView recyclerView = (RefreshRecyclerView) findViewById(R.id.refresh_rcv_group_list);
        View headView = LayoutInflater.from(this).inflate(R.layout.item_group_list, null);
      //  new ShakeRecordList(this, recyclerView,headView);
        new MuitiEntityList(this, recyclerView, headView);
    }
}
