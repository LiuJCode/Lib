package com.lj.project.newljproject.http;

import com.app.base.lib.mbean.DataBean;
import com.lj.project.newljproject.mbean.RxBean;
import com.lj.project.newljproject.mbean.TestBean;

import io.reactivex.Observable;
import retrofit2.http.GET;


/**
 * Created by Administrator on 2018/3/30.
 */

public interface UserService {
    @GET("?mod=Game&act=getAll")
    Observable<DataBean<TestBean>> gatAll();

    @GET("?mod=Game&act=getAll")
    Observable<DataBean<TestBean>> gatAllTwo();

    @GET("?mod=Game&act=getAll")
    Observable<DataBean<RxBean>> gatAllThree();
}
