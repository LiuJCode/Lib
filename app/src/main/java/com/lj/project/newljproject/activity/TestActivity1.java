package com.lj.project.newljproject.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.base.lib.activity.widget.SmallDialog;
import com.app.base.lib.base.BaseActivity;
import com.app.base.lib.bean.DataBean;
import com.lj.project.newljproject.R;
import com.lj.project.newljproject.persenter.imp.TestpersenterImp;
import com.lj.project.newljproject.view.TestIView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/16.
 */

public class TestActivity1 extends BaseActivity implements TestIView {
    private TestpersenterImp testpersenterImp;
    @BindView(R.id.txt_msg)
    TextView txt_msg;
    @BindView(R.id.btn_get)
    Button btn_get;

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
        testpersenterImp = new TestpersenterImp();
        basePresenter = testpersenterImp;
        basePresenter.attachView(this);

    }

    @Override
    protected void setViewAndData() {
        ButterKnife.bind(this);
        btn_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testpersenterImp.getTest();
            }
        });
    }

    @Override
    public void setText(DataBean dataBean) {
        if (dataBean != null) {
            txt_msg.setText(dataBean.getMsg());
            showMsg(dataBean.getMsg());
        } else {
            showMsg("mvp 模式逻辑不对");
        }
    }


    @Override
    public void showProgress() {
        Log.e("mvp", "showProgress");
        if (smallDialog == null) {
            smallDialog = new SmallDialog(this, "加载中...");
            smallDialog.setCanceledOnTouchOutside(true);
        }
        if (smallDialog != null) {
            smallDialog.show();
        }
    }

    @Override
    public void hideProgress() {
        Log.e("mvp", "hideProgress");
        if (smallDialog != null && smallDialog.isShowing()) {
            smallDialog.dismiss();
        }
    }

    @Override
    public void showMsg(String message) {
        Toast.makeText(this, "输出:" + message, Toast.LENGTH_SHORT).show();
    }
}
