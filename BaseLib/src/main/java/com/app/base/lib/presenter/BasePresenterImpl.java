
package com.app.base.lib.presenter;

import android.support.annotation.NonNull;


import com.app.base.lib.listener.RequestCallBack;
import com.app.base.lib.view.BaseView;


/**
 * @author liujing
 * @version 1.0 2018/3/17
 */
public class BasePresenterImpl<T extends BaseView, E> implements BasePresenter, RequestCallBack<E> {
    protected T mView;

    @Override
    public void beforeRequest() {
        if (mView != null) {
            mView.showProgress();
        }
    }

    @Override
    public void msuccess(E data) {
        // 子类实现这个方法 在子类中编写逻辑
        if (mView != null) {
            mView.hideProgress();
        }
    }

    @Override
    public void onError(String errorMsg) {
        if (mView != null) {
            mView.hideProgress();
            mView.showMsg(errorMsg);
        }
    }

    @Override
    public void cancelRequest() {
        //取消请求
    }

    @Override
    public void attachView(@NonNull BaseView view) {
        this.mView = (T) view;
    }
}
