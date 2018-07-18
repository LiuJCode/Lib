package com.lj.project.newljproject.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.base.lib.widget.SmallDialog;
import com.app.base.lib.base.BaseActivity;
import com.app.base.lib.mbean.DataBean;
import com.app.base.lib.utils.LogFactory;
import com.lj.project.newljproject.R;
import com.lj.project.newljproject.http.RetrofitClient;
import com.lj.project.newljproject.mbean.TestBean;
import com.lj.project.newljproject.view.TestIView;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/4/27.
 */

public class RxJavaActivityMap extends BaseActivity implements TestIView {
    @BindView(R.id.btn_Rxjava)
    Button btn_Rxjava;
    @BindView(R.id.txt_msg)
    TextView txt_msg;
    @BindView(R.id.btn_Rxjava_Retrofit)
    Button btn_Rxjava_Retrofit;
    @BindView(R.id.btn_RxMap)
    Button btn_RxMap;
    @BindView(R.id.btn_RxZip)
    Button btn_RxZip;
    @BindView(R.id.btn_flowable)
    Button btn_flowable;
    @BindView(R.id.button_Rxplugins)
    Button button_Rxplugins;
    // 存储请求的 当页面结束后需要清空
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void setViewAndData() {
        ButterKnife.bind(this);
        btn_Rxjava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxjavaTest();
            }
        });
        btn_Rxjava_Retrofit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNetRxjava();
            }
        });
        btn_RxMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTwoNetMap();
            }
        });
        btn_RxZip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RxJavaActivityMap.this, RxjavaActivity2Zip.class);
                startActivity(intent);
            }
        });

        btn_flowable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RxJavaActivityMap.this, RxFlowableActivity.class);
                startActivity(intent);
            }
        });
        button_Rxplugins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNetRxjavaPlugins();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.rxjava;
    }

    @Override
    protected void initPersenter() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    private void RxjavaTest() {
        //Rxjava 主要包括Observable（被观察者）、Observer(观察者)、subscribe（订阅着）
        // 首先创建一个被观察者 （observable）
        Observable observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.e("RXjava", "Thread = " + Thread.currentThread().getName());
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        });
        //再创建一个观察者(observer)
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e("RXjava ", "onSubscribe");
//                d.dispose();//断开连接
            }

            @Override
            public void onNext(Integer value) {
                Log.e("RXjava ", "onNext = " + value);
                Log.e("RXjava", "Thread = " + Thread.currentThread().getName());
            }

            @Override
            public void onError(Throwable e) {
                //Observable遇到错误时会调用这个方法
                Log.e("RXjava ", " onError ");
            }

            @Override
            public void onComplete() {
                //Observable正常终止
                Log.e("RXjava ", " onComplete ");
            }
        };
        //将观察者和被观察者关联起来（订阅subscribe）
        //现在我需要把被观察者变为子线程执行  ，观察者变为主线程执行，模拟网络请求和UI更新
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    // 模拟内存爆炸的问题
    private void Rxjava(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; ; i++) {    //无限循环发事件
                    emitter.onNext(i);
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Thread.sleep(5000);
                        Log.d(TAG, "" + integer);
                    }
                });
    }


    public void getNetRxjava() {
        // 观察者如何设置的发送方法？难道是Retrofit框架处理了? 答案是：对的
        RetrofitClient.getUserService().gatAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DataBean<TestBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                        showProgress();
                        LogFactory.createLog("RXjava").e("onSubscribe ");
                    }

                    @Override
                    public void onNext(DataBean<TestBean> value) {
                        LogFactory.createLog("RXjava").e("onNext 》》》" + "Thread = " + Thread.currentThread().getName());
                        setText(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        setText(null);
                        LogFactory.createLog("RXjava").e("onError ");
                    }

                    @Override
                    public void onComplete() {
                        hideProgress();
                        LogFactory.createLog("RXjava").e("onComplete ");
                    }
                });
    }

    /**
     * @data 2018/4/27
     * @author liujing
     * @describe 连续调用2次接口，切换
     * 适用于 执行完一个步骤 再接着一个步骤  比如 先注册  再登录
     */
    private void getTwoNetMap() {
        RetrofitClient.getUserService().gatAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<DataBean<TestBean>>() {
                    @Override
                    public void accept(DataBean<TestBean> testBeanDataBean) throws Exception {
                        //第一个接口调用完 可以处理一些UI
                        LogFactory.createLog("RXjava").e("第一个接口调用完成");
                        testBeanDataBean.setMsg("第一个接口调用完成");
                        setText(testBeanDataBean);
                    }
                })
                .observeOn(Schedulers.io())
                .concatMap(new Function<DataBean<TestBean>, ObservableSource<DataBean<TestBean>>>() {
                    @Override
                    public ObservableSource<DataBean<TestBean>> apply(DataBean<TestBean> testBeanDataBean) throws Exception {
                        // 执行第二个接口
                        //第一个接口调用完 可以处理一些UI
                        LogFactory.createLog("RXjava").e("第二个接口调用开始");
                        return RetrofitClient.getUserService().gatAllTwo();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DataBean<TestBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        LogFactory.createLog("RXjava").e("onSubscribe");
                        showProgress();
                    }

                    @Override
                    public void onNext(DataBean<TestBean> value) {
                        LogFactory.createLog("RXjava").e("onNext");
                        value.setMsg("第二个接口调用完成");
                        setText(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogFactory.createLog("RXjava").e("onError");
                        hideProgress();
                    }

                    @Override
                    public void onComplete() {
                        LogFactory.createLog("RXjava").e("onComplete");
                        hideProgress();
                    }
                });
    }

    public void getNetRxjavaPlugins() {
        // RxjavaPlugins 在开发过程中更实用
        RetrofitClient.getUserService().gatAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DataBean<TestBean>>() {
                    @Override
                    public void accept(DataBean<TestBean> testBeanDataBean) throws Exception {
                        LogFactory.createLog("RXjava").e("accept");
                        testBeanDataBean.setMsg("accept 调用");
                        setText(testBeanDataBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "Something wrong", throwable);
                        if (throwable instanceof ConnectException) { //ConnectionException
                            LogFactory.createLog("RXjava").e("没有网络连接");
//                            Log.d(TAG, "没有网络连接");
                        } else if (throwable instanceof SocketTimeoutException) {
                            LogFactory.createLog("RXjava").e("连接超时");
//                            Log.d(TAG, "连接超时");
                        } else if (throwable instanceof UnknownHostException){
                            LogFactory.createLog("RXjava").e("无法连接该服务器");
                        }else{
                            //...
                            LogFactory.createLog("RXjava").e(throwable.getMessage());
                        }
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
