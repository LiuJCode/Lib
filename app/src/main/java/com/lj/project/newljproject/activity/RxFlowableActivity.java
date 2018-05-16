package com.lj.project.newljproject.activity;

import android.provider.Settings;
import android.view.View;
import android.widget.Button;

import com.app.base.lib.base.BaseActivity;
import com.app.base.lib.bean.DataBean;
import com.app.base.lib.utils.LogFactory;
import com.lj.project.newljproject.R;
import com.lj.project.newljproject.mbean.TestBean;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/5/2.
 * Flowable 的使用
 */

public class RxFlowableActivity extends BaseActivity{
    @BindView(R.id.btn_RxFlowable)
    Button btn_RxFlowable;
    @Override
    protected void setViewAndData() {
        ButterKnife.bind(this);
        btn_RxFlowable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rxFlowable();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.rxjavaflowable;
    }

    @Override
    protected void initPersenter() {

    }
    public void rxFlowable(){
        Flowable.create(new FlowableOnSubscribe<DataBean<TestBean>>() {
            @Override
            public void subscribe(FlowableEmitter<DataBean<TestBean>> e) throws Exception {
                DataBean dataBean = new DataBean();
                dataBean.setMsg("dataBean");
                e.onNext(dataBean);

                DataBean dataBean1 = new DataBean();
                dataBean1.setMsg("dataBean1");
                e.onNext(dataBean1);
            }
        }, BackpressureStrategy.ERROR).subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<DataBean<TestBean>>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(3);
            }

            @Override
            public void onNext(DataBean<TestBean> testBeanDataBean) {
                LogFactory.createLog(TAG).e(testBeanDataBean.getMsg());
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
