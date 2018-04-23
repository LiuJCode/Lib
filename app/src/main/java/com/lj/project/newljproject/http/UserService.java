package com.lj.project.newljproject.http;

import com.app.base.lib.bean.DataBean;
import com.lj.project.newljproject.mbean.TestBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


/**
 * Created by Administrator on 2018/3/30.
 */

public interface UserService {
    @GET("?mod=User&act=getAll")
    Call<DataBean<TestBean>> getDataBean();
    /**
     * 根据gameID获取对应的用户数据
     * @param userID
     * @return call
     */
    @GET("?mod=User&act=getUser/{uid}")
    Call<DataBean<TestBean>> getUser(@Path("uid") String userID);

}
