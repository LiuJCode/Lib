package com.lj.project.newljproject.http;

import com.app.base.lib.bean.DataBean;
import com.app.base.lib.network.ApiHttpClient;
import com.lj.project.newljproject.mbean.TestBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


/**
 * Created by Administrator on 2018/3/30.
 */

public interface GameService {
    @GET("?mod=Game&act=getAll")
    Call<DataBean<TestBean>> getDataBean();

    /**
     * 根据gameID获取对应的游戏数据
     * @param gameID
     * @return call
     */
    @GET("?mod=Game&act=getAll/{gid}")
    Call<DataBean<TestBean>> getGame(@Path("gid") String gameID);

}
