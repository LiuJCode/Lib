package com.lj.project.newljproject.http;

import com.app.base.lib.network.ApiHttpClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/3/30.
 */

public class RetrofitClient {
    private static GameService gameService;
    private static UserService userservice;
    private static OkHttpClient httpClient ;
    private RetrofitClient() {
    }

    public static GameService getGameService() {
        if (null == gameService) {
            // 打印请求结果log
            if (httpClient == null){
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                httpClient = new OkHttpClient.Builder().addInterceptor(logging).build();

            }
            // 字符串转换
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    .create();//使用 gson coverter，统一日期请求格式

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ApiHttpClient.getApiName())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(httpClient)
                    .build();
             gameService = retrofit.create(GameService.class);
        }
        return gameService;
    }

    //这里应该可以创建多个获取service方法 已实现不同模块 不同service 分类
    public static UserService getUserService() {
        if (null == userservice) {
            // 打印请求结果log
            if (httpClient==null){
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                httpClient = new OkHttpClient.Builder().addInterceptor(logging).build();
            }
            // 字符串转换
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    .create();//使用 gson coverter，统一日期请求格式
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ApiHttpClient.getApiName())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(httpClient)
                    .build();
            userservice = retrofit.create(UserService.class);
        }
        return userservice;
    }
}
