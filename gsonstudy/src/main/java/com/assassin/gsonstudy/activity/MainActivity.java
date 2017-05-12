package com.assassin.gsonstudy.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.assassin.gsonstudy.R;
import com.assassin.gsonstudy.utils.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private int count = 0;
    @Bind(R.id.btnOkGo)
    Button btnOkGo;
    @Bind(R.id.btnOkGo1)
    Button btnOkGo1;
    @Bind(R.id.btnOkGo2)
    Button btnOkGo2;
    @Bind(R.id.btnOkGo3)
    Button btnOkGo3;
    @Bind(R.id.btnOkGo4)
    Button btnOkGo4;

    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
        ButterKnife.bind(this);

    }


    @OnClick({R.id.btnOkGo, R.id.btnOkGo1, R.id.btnOkGo2, R.id.btnOkGo3, R.id.btnOkGo4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnOkGo:
                startActivity(new Intent(activity, OkGoActivity.class));
                break;
            case R.id.btnOkGo1:
                count++;
                ToastUtil.INSTANCE.toastBottom(activity,"点击的次数"+count);
                break;
            case R.id.btnOkGo2:
                ToastUtil.INSTANCE.cancelToast();
                break;
            case R.id.btnOkGo3:
                startActivity(new Intent(activity, ShakeRecordListActivity.class));
                break;
            case R.id.btnOkGo4:
                startActivity(new Intent(activity, GlideActivity.class));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ToastUtil.INSTANCE.cancelToast();
    }
}
