package com.lj.project.newljproject.mbean;

import com.app.base.lib.bean.DataBean;
import com.app.base.lib.bean.ListData;
import com.app.base.lib.bean.SociaxItem;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/3/29.
 */

public class TestBean extends DataBean<TestBean> {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
