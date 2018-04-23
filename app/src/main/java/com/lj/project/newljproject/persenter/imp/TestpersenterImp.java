package com.lj.project.newljproject.persenter.imp;

import android.support.annotation.NonNull;

import com.app.base.lib.bean.DataBean;
import com.app.base.lib.presenter.BasePresenterImpl;
import com.app.base.lib.utils.LogFactory;
import com.app.base.lib.view.BaseView;
import com.lj.project.newljproject.http.RetrofitClient;
import com.lj.project.newljproject.mbean.TestBean;
import com.lj.project.newljproject.view.TestIView;
import com.lj.project.newljproject.persenter.Testpersenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2018/3/16.
 */

public class TestpersenterImp extends BasePresenterImpl<TestIView, DataBean> implements Testpersenter {
    @Override
    public void getTest() {
        beforeRequest();
        //网络请求
        Call<DataBean<TestBean>> dataBean = RetrofitClient.getGameService().getDataBean();
        dataBean.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                LogFactory.createLog().e(response.body().toString());
                DataBean dataBean1 = (DataBean) response.body();
                msuccess(dataBean1);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                LogFactory.createLog().e("出错");
                onError("出错");
            }
        });
    }

    @Override
    public void msuccess(DataBean data) {
        super.msuccess(data);
        // 当同一个页面需要调用多个不同接口的时候, 总回调在这里，但是可以对DataBean设置不同的type 分别调用页面中的不同view层方法.
        if (mView != null) {
            //接口请求结果
            mView.setText(data);
        }
    }

    @Override
    public void beforeRequest() {
        super.beforeRequest();
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
    }

    @Override
    public void attachView(@NonNull BaseView view) {
        super.attachView(view);
    }
}
