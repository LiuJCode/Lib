package com.app.base.lib.network;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.app.base.lib.R;
import com.app.base.lib.base.BaseApplication;
import com.app.base.lib.cache.PrefUtils;
import com.app.base.lib.utils.LogFactory;
import com.app.base.lib.utils.ToolsUtil;

import java.util.Locale;

/**
 * 类说明：配置域名 请求基本地址
 *
 * @author dong.he
 * @version 4.0
 * @date 2015-8-31
 */
public class ApiHttpClient {

    private static ApiHttpClient httpClient;
    private static final String TAG = ApiHttpClient.class.getSimpleName();
    // 网络请求协议
    public static String PROTOCOL_FORMAT = "https://";
    //接口域名
    public static String HOST = "www.duojiao.tv";
    //更换接口只需要改变这部分
    private static String API = "api.php/";
    //baseUrl 必须以斜线结束
    private static String API_URL = "https://www.duojiao.tv/api.php/";
    // 基本参数
    private static String MOD_ACT = "?mod=%s&act=%s";
    public static String TOKEN;
    public static String TOKEN_SECRET;
    private static String mApiVersion;//api_version 版本号  当前为 4.5.0  默认为 系统版4.5.0
    public static final String DELETE = "DELETE";
    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String PUT = "PUT";

    /**
     * 这里可以放置一些网络请求的准备工作，例如设置接口地址，设置网络缓存存放地址等
     *
     */
    public ApiHttpClient() {}

    /**
     * 读取本地的服务器地址
     * @param context
     */
    public static void newHttpClient(BaseApplication context) {
        String PROTOCOL = context.getResources().getStringArray(R.array.protocol)[0];
        PROTOCOL_FORMAT = PROTOCOL + "://";
        HOST = context.getResources().getStringArray(R.array.site_url)[0];
        API = context.getResources().getStringArray(R.array.site_url)[1];
        mApiVersion = context.getResources().getStringArray(R.array.site_url)[3];
        API_URL = PROTOCOL_FORMAT + HOST + "/" + API+"/";

        //初始化client头信息
        StringBuilder ua = new StringBuilder(ApiHttpClient.HOST);
        ua.append('/' + context.getPackageInfo().versionName + '_'
                + context.getPackageInfo().versionCode);// app版本信息
        ua.append("/Android");    // 手机系统平台
        ua.append("/" + android.os.Build.VERSION.RELEASE);// 手机系统版本
        ua.append("/" + android.os.Build.MODEL); // 手机型号
    }

    public static void setTokenInfo(String token, String tokenSecret) {
        ApiHttpClient.TOKEN = token;
        ApiHttpClient.TOKEN_SECRET = tokenSecret;
        PrefUtils.setToke(BaseApplication.getContext(), token, tokenSecret);
    }

    /**
     * @data 2018/4/10
     * @author liujing
     * @describe 获取基本网络地址
     */
    public static String getApiName() {
        return ApiHttpClient.API_URL;
    }

    /**
     * 拼接完整的接口地址,域名+接口+用户认证信息
     *
     * @param mode
     * @param act
     * @return
     */
    public static String getAbsoluteApiUrl(String mode, String act) {
        String mod_act = String.format(MOD_ACT, mode, act);
        String url = API_URL + mod_act;
        url += "&oauth_token=" + TOKEN;
        url += "&oauth_token_secret=" + TOKEN_SECRET;
        if (!TextUtils.isEmpty(act) && ("public_timeline".equals(act)
                || act.equals("getFeeds")
                || act.equals("recommend_timeline")
                || act.equals("hot_timeline")
                || act.equals("user_timeline")
                || act.equals("friends_timeline")
                || act.equals("max_ugc_feed_id")
                || act.equals("get_mountain_feeds")
                || act.equals("user_collections"))) {
            url += "&api_version=" + "5.0.0";
        } else {
            url += "&api_version=" + mApiVersion;
        }

        Log.e("ApiHttpClient", "url:" + url);
        return url;
    }

}
