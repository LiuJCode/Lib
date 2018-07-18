package com.app.base.lib.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.webkit.WebView;

import com.app.base.lib.widget.EmptyLayout;
import com.app.base.lib.widget.SmallDialog;
import com.app.base.lib.presenter.BasePresenterImpl;
import com.app.base.lib.utils.ActivityStack;
import com.app.base.lib.utils.GildeUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.app.base.lib.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseActivity extends FragmentActivity
        implements View.OnTouchListener {
    protected BasePresenterImpl basePresenter;
    protected static final String TAG = "BaseActivity";
    protected Bundle data;
    protected FragmentManager fragmentManager = this.getSupportFragmentManager();
    protected FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    protected SmallDialog smallDialog;
    protected EmptyLayout error_layout; // 公共布局

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置状态栏
//        StatusBarUtil.StatusBarLightMode(this, StatusBarUtil.StatusBarLightMode(this));
        //设置页面背景颜色
//        ColorDrawable bgDrawable = new ColorDrawable(getResources().getColor(R.color.themecolor));
//        this.getWindow().setBackgroundDrawable(bgDrawable);
        initCreate();
        setViewAndData();
        initPersenter();
    }

    // 设置数据
    protected abstract void setViewAndData();

    //获取布局文件
    protected abstract int getLayoutId();
    /**
     *@data  2018/3/17
     *@author liujing
     *@describe 初始化网络请求
     */
    protected abstract void initPersenter();


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     * 设置布局
     */
    private void initCreate() {
        if (getIntent().hasExtra("new_user")
                && getIntent().getBooleanExtra("new_user", false)) {
            //如果是新用户则跳转至补全注册流程
        }
        setContentView(this.getLayoutId());
        ActivityStack.addCache(this);
    }
    // 初始化默认view
    protected void initDefautView() {
        error_layout = (EmptyLayout) findViewById(R.id.error_layout);
    }

    public Bundle getIntentData() {
        if (data != null)
            return data;
        data = new Bundle();
        return data;
    }

    @Override
    protected void onDestroy() {
        //Only the original thread that created a view hierarchy can touch its views.
        if (basePresenter != null) {
            basePresenter.cancelRequest();
        }
        GildeUtil.pauseRequest(this);
        ActivityStack.removeCache(this);
        if (smallDialog != null && smallDialog.isShowing()) {
            smallDialog.dismiss();
            smallDialog = null;
        }
        System.gc();
        super.onDestroy();
    }


    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    /**
     * java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
     *
     * @author LIUJING
     * @created at 2017/12/13 18:10
     * onSaveInstanceState 反射中调用解决这个问题的
     */
    private Method noteStateNotSavedMethod;
    private Object fragmentMgr;
    private String[] activityClassName = {"Activity", "FragmentActivity"};

    protected void invokeFragmentManagerNoteStateNotSaved() {
        //java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return;
        }
        try {
            if (noteStateNotSavedMethod != null && fragmentMgr != null) {
                noteStateNotSavedMethod.invoke(fragmentMgr);
                return;
            }
            Class cls = getClass();
            do {
                cls = cls.getSuperclass();
            } while (!(activityClassName[0].equals(cls.getSimpleName())
                    || activityClassName[1].equals(cls.getSimpleName())));

            Field fragmentMgrField = prepareField(cls, "mFragments");
            if (fragmentMgrField != null) {
                fragmentMgr = fragmentMgrField.get(this);
                noteStateNotSavedMethod = getDeclaredMethod(fragmentMgr, "noteStateNotSaved");
                if (noteStateNotSavedMethod != null) {
                    noteStateNotSavedMethod.invoke(fragmentMgr);
                }
            }

        } catch (Exception ex) {
        }
    }

    private Field prepareField(Class<?> c, String fieldName) throws NoSuchFieldException {
        while (c != null) {
            try {
                Field f = c.getDeclaredField(fieldName);
                f.setAccessible(true);
                return f;
            } finally {
                c = c.getSuperclass();
            }
        }
        throw new NoSuchFieldException();
    }

    private Method getDeclaredMethod(Object object, String methodName, Class<?>... parameterTypes) {
        Method method = null;
        for (Class<?> clazz = object.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                method = clazz.getDeclaredMethod(methodName, parameterTypes);
                return method;
            } catch (Exception e) {
            }
        }
        return null;
    }

    /**
     * @data 2018/3/15
     * @author liujing
     * @describe 清除webview 缓存
     */
    public static void DestroyWebView(WebView webView) {
        if (webView != null) {
            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再
            // destory()
            ViewParent parent = webView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(webView);
            }
            webView.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            webView.getSettings().setJavaScriptEnabled(false);
            webView.clearHistory();
            webView.clearView();
            webView.removeAllViews();
            try {
                webView.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
