package com.lj.project.newljproject;

import android.app.Application;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.app.base.lib.bean.DataBean;
import com.app.base.lib.utils.Exceptions;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.plugins.RxJavaPlugins;

/**
 * Created by Administrator on 2018/5/3.
 */

public class MyApplaction extends Application {
    public static final String TAG = "MyApplaction";
    private Handler mainHandler;
    @Override
    public void onCreate() {
        super.onCreate();
        mainHandler = new Handler();
        RxJavaPlugins.setOnObservableSubscribe(new BiFunction<Observable, Observer, Observer>() {
            @Override
            public Observer apply(Observable observable, Observer observer) throws Exception {
                return new ObservableSubscribeHooker(observer);
            }
        });
    }

    class ObservableSubscribeHooker<T> implements Observer<T> {
        private Observer<T> actual;

        public ObservableSubscribeHooker(Observer<T> actual) {
            this.actual = actual;
        }

        @Override
        public void onSubscribe(Disposable d) {
            actual.onSubscribe(d);
        }

        @Override
        public void onNext(T t) {
            hookOnNext(t);
            actual.onNext(t);
        }

        private void hookOnNext(T t) {
            if (t instanceof DataBean) {
                DataBean baseResponse = (DataBean) t;
                if (baseResponse.getStatus() == 100) {

                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "登录过期", Toast.LENGTH_SHORT).show();
                        }
                    });
                    // 登录
//                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
                    throw new Exceptions.TokenExpired();
                }
            }
        }


        @Override
        public void onError(Throwable e) {

            if (e instanceof ConnectException) {
                Log.e(TAG, "Connect failed: ", e);
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "无网络连接", Toast.LENGTH_SHORT).show();
                    }
                });
                actual.onError(new Exceptions.Offline());
                return;
            }

            if (e instanceof SocketTimeoutException) {
                Log.e(TAG, "Time out ", e);
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "连接超时", Toast.LENGTH_SHORT).show();
                    }
                });
                actual.onError(new Exceptions.TimeOut());
                return;
            }
            if (e instanceof UnknownHostException){
                Log.e(TAG, "Time out ", e);
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "无法连接服务器", Toast.LENGTH_SHORT).show();
                    }
                });
                actual.onError(new Exceptions.TimeOut());
                return;
            }
            //其余的异常处理...
            actual.onError(e);
        }

        @Override
        public void onComplete() {
            actual.onComplete();
        }
    }
}
