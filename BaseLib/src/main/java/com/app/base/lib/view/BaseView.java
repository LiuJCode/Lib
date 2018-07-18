package com.app.base.lib.view;

import com.app.base.lib.mbean.SociaxItem;

/**
 * Created by LIUJING on 18/3/17.
 * V
 * 操作view
 */
public interface BaseView<T extends SociaxItem> {
    /**
     * @data 2018/3/16
     * @author liujing
     * @describe 显示进度
     */
    void showProgress();

    /**
     * @data 2018/3/16
     * @author liujing
     * @describe 关闭进度
     */
    void hideProgress();

    /**
     * @data 2018/3/16
     * @author liujing
     * @describe Toast
     */
    void showMsg(String message);

}
