package com.app.base.lib.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.app.base.lib.network.ApiHttpClient;
import com.app.base.lib.utils.ActivityStack;
import com.app.base.lib.utils.CrashHandler;

import java.io.File;

/**
 * @author LIUJING
 * @created at 2018/2/8 15:17
 * 全局处理崩溃问题
 * 可以在主工程中继承
 */
public class BaseApplication extends Application {
    public static final String APP = "DuoJiao";  //APP名字
    private static final String cacheDirName = "DuoJiao";  //缓存目录名
    protected static String cachePath;     //缓存路劲
    protected static ActivityStack activityStack;   //当前应用活动的activity栈
    private static Context mContext;

    public BaseApplication() {
        activityStack = new ActivityStack();
    }

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        //针对异常的捕捉要进行全局监控整个项目，所以要将其在Application中注册(也就是初始化)：
        CrashHandler catchHandler = CrashHandler.getInstance();
        catchHandler.init(getApplicationContext());
        registerActivityLifecycle();
        initCachePath();
        ApiHttpClient.newHttpClient(BaseApplication.this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * 初始化缓存路径
     */
    private void initCachePath() {
        File sampleDir = new File(Environment.getExternalStorageDirectory() + File.separator +
                cacheDirName);
        if (!sampleDir.exists()) {
            sampleDir.mkdirs();
        }

        cachePath = sampleDir.getAbsolutePath();
    }

    /**
     * 获取App安装包信息
     *
     * @return
     */
    public PackageInfo getPackageInfo() {
        PackageInfo info = null;
        try {
            info = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
        if (info == null)
            info = new PackageInfo();
        return info;
    }

    // 添加Activity
    public static void addActivity(Activity activity) {
        activityStack.addCache(activity);
    }

    // 移除Activity
    public static void removeActivity(Activity activity) {
        activityStack.removeCache(activity);
    }

    // 通过name获取Activity对象
    public static Activity getActivityByName(String name) {
        return activityStack.getActivityByName(name);
    }

    // 通过name获取Activity对象
    public static Activity getLastActivity() {
        return activityStack.getLastActivity();
    }

    public static void finishActivity(Activity activity) {
        activityStack.finishActivity(activity);
    }

    /**
     * 清空所有activity
     */
    public static void clearAllActivity() {
        activityStack.clear();
    }

    /**
     * 退出程序
     */
    public static void exitApp() {
        activityStack.clear();
        System.exit(0);

    }

    private boolean isCurrentRunningForeground = false;

    // 全局 监听Activity生命周期
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void registerActivityLifecycle() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {

            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityStarted(Activity activity) {
                if (!isCurrentRunningForeground) {
                    Log.e(getApplicationContext().getPackageName() + "", ">>>>>>>>>>>>>>>>>>>切到前台 activity process");
                }

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {
                isCurrentRunningForeground = ActivityStack.isRunningForeground();
                if (!isCurrentRunningForeground) {
                    Log.e(getApplicationContext().getPackageName() + "", ">>>>>>>>>>>>>>>>>>>切到后台 activity process");
                }

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }
}
