package com.app.base.lib.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by Administrator on 2016/7/26.
 */
public class ToolsUtil {

    public static String getDeviceId(Context context) {
        //获取TelephonyManager服务
        TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        return TelephonyMgr.getDeviceId();
    }

    /**
     * 去掉小数点后面的0  如果全是0 就直接省去小数点
     *
     * @author LIUJING
     * @created at 2017/3/23 16:26
     */
    public static String indexfloat(String value) {
        if (value.indexOf(".") > 0) {
            //正则表达
            value = value.replaceAll("0+?$", "");//去掉后面无用的零
            value = value.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
        }
        return value;
    }

    //  字符串是否为null
    public static boolean isNotNull(String value) {
        if (value == null) {
            return false;
        }
        if ("".equals(value)) {
            return false;
        }
        if ("null".equals(value)) {
            return false;
        }

        return true;
    }

    /**
     * 获取屏幕的宽度
     */
    public final static int getWindowsWidth(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    // 从服务器下载apk:
    public static File getFileFromServer(Context context, Handler handler, String path)
            throws Exception {
        // 如果相等的话表示当前的sdcard挂载在手机上并且是可用的
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            // 获取到文件的大小
            if (conn.getContentLength() > 0) {
                Message message = new Message();
                message.obj = conn.getContentLength();
                message.what = 1000;
                handler.sendMessage(message);
            } else {
                Message message = new Message();
                message.obj = "文件大小：file size is 0";
                message.what = 3000;
                handler.sendMessage(message);
            }
            Log.i("文件大小 = ", "::" + conn.getContentLength());
            InputStream is = conn.getInputStream();
            File file = new File(Environment.getExternalStorageDirectory(),
                    "DuoJiao.apk");
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len;
            int total = 0;
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                total += len;
                Message message = new Message();
                message.obj = total;
                message.what = 2000;
                handler.sendMessage(message);
            }
            fos.close();
            bis.close();
            is.close();
            return file;
        } else {
            return null;
        }
    }

    public static boolean isEmojiCharacter(char codePoint) {
        return !((codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD)
                || ((codePoint >= 0x20) && codePoint <= 0xD7FF)) || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    /**
     * 判断是否安装微信
     *
     * @author LIUJING
     * @created at 2017/3/3 10:05
     */
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 判断qq是否可用
     *
     * @param context
     * @return
     */
    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 将手机号码中间位数变星
     *
     * @author LIUJING
     * @created at 2017/9/29 9:55
     */
    public static String phoneTox(String pNumber) {
        if (!TextUtils.isEmpty(pNumber) && pNumber.length() > 6) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < pNumber.length(); i++) {
                char c = pNumber.charAt(i);
                if (i >= 3 && i <= 6) {
                    sb.append('*');
                } else {
                    sb.append(c);
                }
            }
            return sb.toString();
        }
        return "";
    }
}
