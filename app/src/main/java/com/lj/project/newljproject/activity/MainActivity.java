package com.lj.project.newljproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.app.base.lib.base.BaseActivity;
import com.lj.project.newljproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/16.
 */

public class MainActivity extends BaseActivity {
    @BindView(R.id.txt_msg)
    TextView txt_msg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main;
    }

    @Override
    protected void initPersenter() {

    }

    @Override
    protected void setViewAndData() {
        ButterKnife.bind(this);
        txt_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TestActivity1.class);
                startActivity(intent);
            }
        });
    }

}
