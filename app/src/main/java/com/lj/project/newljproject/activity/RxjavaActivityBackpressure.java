package com.lj.project.newljproject.activity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.base.lib.activity.widget.SmallDialog;
import com.app.base.lib.base.BaseActivity;
import com.app.base.lib.bean.DataBean;
import com.app.base.lib.utils.LogFactory;
import com.lj.project.newljproject.R;
import com.lj.project.newljproject.http.RetrofitClient;
import com.lj.project.newljproject.mbean.RxBean;
import com.lj.project.newljproject.mbean.RxZipBean;
import com.lj.project.newljproject.mbean.TestBean;
import com.lj.project.newljproject.view.TestIView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/4/28.
 * Rxjava Backpressure 的使用
 */

public class RxjavaActivityBackpressure extends BaseActivity implements TestIView {
    @BindView(R.id.btn_RXJAVAzip)
    Button btn_RXJAVAzip;
    @BindView(R.id.txt_msg)
    TextView txt_msg;
    public static final String TAG = "RXjava";

    @Override
    protected void setViewAndData() {
        ButterKnife.bind(this);
        btn_RXJAVAzip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTestZip();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.rxjavazip;
    }

    @Override
    protected void initPersenter() {}

    /**
     * @data 2018/4/28
     * @author liujing
     * @describe zip操作符
     */
    private void getTestZip() {
        //第一个请求 二个请求分别设置不同的线程，这样他们会同时执行
        Observable<DataBean<TestBean>> observable1 = RetrofitClient.getUserService().gatAllTwo().subscribeOn(Schedulers.io());
        //第二个请求
        Observable<DataBean<RxBean>> observable2 = RetrofitClient.getUserService().gatAllThree().subscribeOn(Schedulers.io());
        //将二个请求结果合并
        Observable.zip(observable1, observable2,
                new BiFunction<DataBean<TestBean>, DataBean<RxBean>, RxZipBean>() {

                    @Override
                    public RxZipBean apply(DataBean<TestBean> testBeanDataBean,
                                           DataBean<RxBean> rxBeanDataBean) throws Exception {
                        LogFactory.createLog(TAG).e("二个请求合并");
                        RxZipBean rxZipBean = new RxZipBean(testBeanDataBean,rxBeanDataBean);
                        rxZipBean.setMsg("合并后...");
                        rxZipBean.setStatus(1);
                        rxZipBean.setData(null);
                        return rxZipBean;
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RxZipBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        showProgress();
                        LogFactory.createLog(TAG).e("onSubscribe");
                    }

                    @Override
                    public void onNext(RxZipBean value) {
                        setText(value);
                        LogFactory.createLog(TAG).e("onNext");
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideProgress();
                        LogFactory.createLog(TAG).e("onError");
                    }

                    @Override
                    public void onComplete() {
                        LogFactory.createLog(TAG).e("onComplete");
                        hideProgress();
                    }
                });
    }
    @Override
    public void setText(DataBean dataBean) {
        if (dataBean != null) {
            String msg = dataBean.getMsg();
            txt_msg.setText(msg);
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
