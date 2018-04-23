package com.app.base.lib.cache;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.text.TextUtils;

/**
 * SharedPreferences 临时存储数据
 *@author LIUJING
 *@created at 2018/2/8 15:57
 */
public class PrefUtils {
   private static final String PREF_IS_LOCAL_REGISTER = "pref_is_local_register";
   private static final String PREF_IS_FIRST_RUN = "is_first_run"; // 安装成功是否第一次打开 app
   private static final String PREF_SOCKET_ADDRESS = "socket_address"; // 保存聊天 socket 地址的
   private static final String PREF_UPDATE_VERSION = "update_version"; // 版本更新字段样式
   private static final String PREF_TOKE = "toke"; // 保存用户的toke
   private static final String LOGIN_FUN = "login_fun"; // 保存用户的登录方式
   private static final String LOGIN_PHONE = "login_phone"; // 保存用户登录的手机号码
   private static final String LOGIN_STATUS = "login_status"; // 保存用户登录是否被禁用
   private static final String IS_FRIST = "is_frist"; // 程序第一次启动

   /**
    * 设置是否是本地注册
    *
    * @param context the context
    * @param isLocal 是否本地注册
    */
   public static void setPrefIsLocalRegister(@NonNull Context context, boolean isLocal) {
       SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
       sp.edit().putBoolean(PREF_IS_LOCAL_REGISTER, isLocal).apply();
   }

   /**
    * 获取是否本地注册，默认为false
    *
    * @param context the context
    * @return
    */
   public static boolean getPrefIsLocalRegister(@NonNull Context context) {
       SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
       return sp.getBoolean(PREF_IS_LOCAL_REGISTER, false);
   }

   /**
    * 安装此应用后是否第一次打开该应用
    *
    * @param context
    * @return true: 第一次运行
    */
   public static boolean isFirstRunApp(Context context) {
       SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
       if (sp.contains(PREF_IS_FIRST_RUN)) {
           return false;
       }
       sp.edit().putBoolean(PREF_IS_FIRST_RUN, false).commit();
       return true;
   }

   /**
    * 保存 socket 服务器 地址
    *
    * @param context
    * @param socketAddress
    */
   public static void saveSocketAddress(Context context, String socketAddress) {
       if (!TextUtils.isEmpty(socketAddress)) {
           SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
           sp.edit().putString(PREF_SOCKET_ADDRESS, socketAddress).commit();
       }
   }

   /**
    * 获取 socket 服务器 地址
    *
    * @param context
    * @return
    */
   public static String getSocketAddress(Context context) {
       SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
       if (sp.contains(PREF_SOCKET_ADDRESS)) {
           return sp.getString(PREF_SOCKET_ADDRESS, "");
       }
       return null;
   }

   /**
    * 缓存用户toke
    *
    * @param context
    * @return
    */
   public static void setToke(Context context, String toke, String SecretToken) {
       if (!TextUtils.isEmpty(toke) && !TextUtils.isEmpty(SecretToken)) {
           SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
           sp.edit().putString(PREF_TOKE, toke + "," + SecretToken).commit();
       }
   }

   /**
    * 获取 用户toke 这个暂时只在语音聊天的那里，退出应用后收到的电话 获取用户信息
    *
    * @param context
    * @return
    */
   public static String getToke(Context context) {
       SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
       if (sp.contains(PREF_TOKE)) {
           return sp.getString(PREF_TOKE, "");
       }
       return null;
   }

   /**
    * 缓存程序第一次启动 显示首页快速下单引导
    * 至少显示一次
    *
    * @param context
    * @return
    */
   public static void setFirst(Context context, boolean loginfun) {
       SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
       sp.edit().putBoolean(IS_FRIST, loginfun).commit();
   }

   /**
    * 缓存程序第一次启动 显示首页快速下单引导
    * 没有存表示还没显示过 必须显示一次
    *
    * @param context
    * @return
    */
   public static boolean getFirst(Context context) {
       SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
       if (sp.contains(IS_FRIST)) {
           return sp.getBoolean(IS_FRIST, true);
       }
       return true;
   }


   /**
    * 记录用户上次登录方式
    *
    * @param context
    * @return
    */
   public static void setLoginFun(Context context, String loginfun) {
       if (!TextUtils.isEmpty(loginfun)) {
           SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
           sp.edit().putString(LOGIN_FUN, loginfun).commit();
       }
   }

   /**
    * 记录用户上次登录方式
    *
    * @param context
    * @return
    */
   public static String getLoginFun(Context context) {
       SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
       if (sp.contains(LOGIN_FUN)) {
           return sp.getString(LOGIN_FUN, "");
       }
       return null;
   }


   /**
    * 记录用户上次登录手机号码
    *
    * @param context
    * @return
    */
   public static void setPhone(Context context, String phone) {
       if (!TextUtils.isEmpty(phone)) {
           SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
           sp.edit().putString(LOGIN_PHONE, phone).commit();
       }
   }

   /**
    * 记录用户上次登录的手机号码
    *
    * @param context
    * @return
    */
   public static String getPhone(Context context) {
       SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
       if (sp.contains(LOGIN_PHONE)) {
           return sp.getString(LOGIN_PHONE, "");
       }
       return null;
   }

   /**
    * 记录用户是否被禁用
    *
    * @param context
    * @return
    */
   public static void setLoginStatus(Context context, String phone) {
       if (!TextUtils.isEmpty(phone)) {
           SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
           sp.edit().putString(LOGIN_STATUS, phone).commit();
       }
   }

   /**
    * 记录用户是否被禁用
    *
    * @param context
    * @return
    */
   public static String getLoginStatus(Context context) {
       SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
       if (sp.contains(LOGIN_STATUS)) {
           return sp.getString(LOGIN_STATUS, "");
       }
       return null;
   }

   /**
    * 记录推荐的ID
    * 以用户ID为键
    * @param context
    * @return
    */
   public static void setmax_ugc_feed_id(Context context,String key, int Feedmaxid) {
       SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
       sp.edit().putInt(key, Feedmaxid).commit();
   }

   /**
    * 记录推荐的ID
    *
    * @param context
    * @return
    */
   public static int getmax_ugc_feed_id(Context context,String key) {
       SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
       if (sp.contains(key)) {
           return sp.getInt(key, 0);
       }
       return 0;
   }
}
