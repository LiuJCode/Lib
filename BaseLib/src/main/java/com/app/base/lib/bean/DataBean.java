package com.app.base.lib.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/2/8.
 * 请求数据结果
 */
public class DataBean <T extends SociaxItem>  extends SociaxItem {
    private int status; // 请求结果状态
    private String msg; // 请求结果描述
    private ListData<T> data;// 请求结果
    @Override
    public boolean checkValid() {
        return false;
    }

    @Override
    public String getUserface() {
        return null;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ListData<T> getData() {
        return data;
    }

    public void setData(ListData<T> data) {
        this.data = data;
    }
}
