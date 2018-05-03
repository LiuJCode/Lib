package com.lj.project.newljproject.mbean;

import com.app.base.lib.bean.DataBean;

/**
 * Created by Administrator on 2018/4/27.
 */

public class RxZipBean extends DataBean<TestBean> {
    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public  RxZipBean(DataBean<TestBean> testBean, DataBean<RxBean>rxBean){};
}
