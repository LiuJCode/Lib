package com.app.base.lib.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.app.base.lib.base.BaseApplication;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

 /**
  * Activity
  *@author LIUJING
  *@created at 2018/2/8 15:29
  */
public class ActivityStack extends Stack<Activity> {
    private static Stack<Activity> cacheExit;

    public ActivityStack() {
        super();
        cacheExit = new Stack<Activity>();
    }

    public static void addCache(Activity activity) {
        if (cacheExit == null) {
            cacheExit = new Stack<Activity>();
        }
        cacheExit.push(activity);
    }

    public static void removeCache(Activity activity) {
        if (cacheExit == null || cacheExit.isEmpty()) {
            return;
        }
        if (activity != null) {
            cacheExit.remove(activity);
        }
    }

    public static void startActivity(Activity now, Class<? extends Activity> target,
                                     Bundle data) {
        Intent intent = new Intent();
        if (now != null && intent != null) {
            intent.setClass(now, target);
            if (data != null) {
                if (intent.getExtras() != null) {
                    intent.replaceExtras(data);
                } else {
                    intent.putExtras(data);
                }
            }
            now.startActivity(intent);
//            Anim.in(now);
        }

    }

    public static void startActivity(Activity now, String target,
                                     Bundle data) {
        Intent intent = new Intent();
        intent.setClassName(now, target);
        if (data != null) {
            if (intent.getExtras() != null) {
                intent.replaceExtras(data);
            } else {
                intent.putExtras(data);
            }
        }
        now.startActivity(intent);
//        Anim.in(now);
    }

    public static void startActivity(Activity now, Class<? extends Activity> target,
                                     Bundle data, int flag) {
        Intent intent = new Intent();
        intent.setClass(now, target);
        intent.setFlags(flag); // 注意本行的FLAG设置
        if (data != null) {
            if (intent.getExtras() != null) {
                intent.replaceExtras(data);
            } else {
                intent.putExtras(data);
            }
        }
        now.startActivity(intent);
//        Anim.in(now);
    }

    public static void startActivity(Activity now, Class<? extends Activity> target) {
        startActivity(now, target, null);
    }

    public static void startActivity(Activity now, String target) {
        startActivity(now, target, null);
    }

    public static void startActivityForResult(Activity now,
                                              Class<? extends Activity> target, Bundle data) {
        Intent intent = new Intent();
        intent.setClass(now, target);
        if (data != null) {
            if (intent.getExtras() != null) {
                intent.replaceExtras(data);
            } else {
                intent.putExtras(data);
            }
        }
        now.startActivityForResult(intent, 3456);
//        Anim.in(now);
    }

    public void returnActivity(Activity now, Bundle data) {
        if (cacheExit.empty()) {
            return;
        }
        Intent intent = new Intent();
        Activity temp = cacheExit.pop();
        intent.setClass(now, temp.getClass());
        if (data != null) {
            intent.putExtras(data);
        }
        now.startActivity(intent);
//        Anim.in(now);
        now.finish();
    }

    public void returnActivity(Activity now) {
        this.returnActivity(now, null);
    }

    //返回上一个页面
    public void finishActivity(Activity now) {
        if (cacheExit.empty()) {
            return;
        }

        Activity temp = cacheExit.pop();
        temp.finish();
//        Anim.exit(now);
    }

    // 通过name获取Activity对象
    public static Activity getActivityByName(String name) {
        Activity getac = null;
        for (Activity ac : cacheExit) {
            if (ac.getClass().getName().indexOf(name) >= 0) {
                getac = ac;
            }
        }
        return getac;
    }

    public static Activity getLastActivity() {
        int size = cacheExit.size();
        if (size > 0) {
            return cacheExit.get(size - 1);
        } else {
            return null;
        }
    }

    @Override
    public void clear() {
        while (!cacheExit.empty()) {
            Activity activity = cacheExit.pop();
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }

        super.clear();
    }

    // status 为0表示正常退出  为1表示异常退出
    public static void exit(int status) {
        if (cacheExit != null) {
            Log.e("remove activity count=", cacheExit.size() + "");
            for (int i = 0; i < cacheExit.size(); i++) {
                Activity activity = cacheExit.get(i);
                if (activity != null) {
                    activity.finish();
                }
            }
            cacheExit.clear();
            System.exit(1);
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public static void finishActivity(Class<?> cls) {
        if (cacheExit == null) {
            return;
        }
        Iterator<Activity> iterator = cacheExit.iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            if (activity != null && activity.getClass().equals(cls)) {
                activity.finish();
                iterator.remove();
            }
        }
    }

    @Override
    public synchronized Activity pop() {
        return super.pop();
    }

    // 检测应用是否在前台运行
    public static boolean isRunningForeground() {
        ActivityManager activityManager = (ActivityManager) BaseApplication.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcessInfos = activityManager.getRunningAppProcesses();
        // 枚举进程
        for (ActivityManager.RunningAppProcessInfo appProcessInfo : appProcessInfos) {
            if (appProcessInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                if (appProcessInfo.processName.equals(BaseApplication.getContext().getPackageName())) {
                    Log.d(BaseApplication.getContext().getPackageName() + "", "EntryActivity isRunningForeGround");
                    return true;
                }
            }
        }
        Log.d(BaseApplication.getContext().getPackageName() + "", "EntryActivity isRunningBackGround");
        return false;
    }

     /**
      * 设置启动模式
      *@author LIUJING
      *@created at 2017/12/21 23:27
      */
    public static void startTopTaskActivity(Context now, Class<? extends Activity> target,
                                     Bundle data) {
        Intent intent = new Intent();
        if (now != null && intent != null) {
            intent.setClass(now, target);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (data != null) {
                if (intent.getExtras() != null) {
                    intent.replaceExtras(data);
                } else {
                    intent.putExtras(data);
                }
                intent.putExtras(data);
            }
            now.startActivity(intent);
        }

    }

    /**
     * 设置启动模式
     *@author LIUJING
     *@created at 2017/12/21 23:27
     */
    public static void startsingTopActivity(Context now, Class<? extends Activity> target,
                                            Bundle data) {
        Intent intent = new Intent();
        if (now != null && intent != null) {
            intent.setClass(now, target);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (data != null) {
                if (intent.getExtras() != null) {
                    intent.replaceExtras(data);
                } else {
                    intent.putExtras(data);
                }
            }
            now.startActivity(intent);
        }

    }
}
