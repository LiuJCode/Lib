package com.app.base.lib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.base.lib.R;
import com.app.base.lib.utils.UnitSociax;

/**
 * Created by liujing
 */
public class EmptyLayout extends LinearLayout implements View.OnClickListener {
    public static final int HIDE_LAYOUT = 4;
    public static final int NETWORK_ERROR = 1;
    public static final int NETWORK_LOADING = 2;
    public static final int NODATA = 3;
    public static final int NODATA_ENABLE_CLICK = 5;


    private ProgressBar animProgress;
    public ImageView img;
    private TextView tv;
    private RelativeLayout mLayout;
    private LinearLayout ll_content;
    private OnClickListener listener;
    private int mErrorState;
    private String strNoDataContent = "";
    private boolean clickEnable = true;
    private Context context;
    private Button btn_click;
    private boolean isSimple;    //设置没有数据为简单样式


    public EmptyLayout(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public EmptyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }


    private void init() {
        View view = View.inflate(context, R.layout.error_layout, null);
        ll_content = (LinearLayout) view.findViewById(R.id.ll_content);

        img = (ImageView) view.findViewById(R.id.iv_error_pic);      //显示的图片
        tv = (TextView) view.findViewById(R.id.tv_error_layout);     //显示的文字
        mLayout = (RelativeLayout) view.findViewById(R.id.pageerrLayout);  //整个布局
        btn_click = (Button) view.findViewById(R.id.btn_click);  //按钮
        animProgress = (ProgressBar) view.findViewById(R.id.animProgress); //
        setOnClickListener(this);   //整个布局设置点击事件

        addView(view);
        changeErrorLayoutBgMode(context);
    }

    public void changeErrorLayoutBgMode(Context context1) {
    }

    public void dismiss() {
        mErrorState = HIDE_LAYOUT;
        setVisibility(View.GONE);
    }

    public int getErrorState() {
        return mErrorState;
    }

    public boolean isLoadError() {
        return mErrorState == NETWORK_ERROR;
    }

    public boolean isLoading() {
        return mErrorState == NETWORK_LOADING;
    }

    @Override
    public void onClick(View v) {
        if (clickEnable) {
            // setErrorType(NETWORK_LOADING);
            if (listener != null)
                listener.onClick(v);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public void setDayNight(boolean flag) {
    }

    public void setErrorMessage(String msg) {
        tv.setText(msg);
    }

    /**
     * 新添设置背景
     */
    public void setErrorImag(int imgResource) {
        try {
            img.setImageResource(imgResource);
        } catch (Exception e) {
        }
    }

    public void setOnLayoutClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    public void setErrorType(int i) {
        setVisibility(View.VISIBLE);
        switch (i) {
            case NETWORK_ERROR:
                setVisibility(View.VISIBLE);
                mErrorState = NETWORK_ERROR;
                if (UnitSociax.isNetWorkON(context)) {
                    tv.setText(R.string.error_view_load_error_click_to_refresh);
                } else {
                    tv.setText(R.string.error_view_network_error_click_to_refresh);
                }
                animProgress.setVisibility(View.GONE);
                btn_click.setText("再试试");
                btn_click.setVisibility(View.VISIBLE);
                img.setVisibility(View.VISIBLE);
                clickEnable = true;
                break;
            case NETWORK_LOADING:
                setVisibility(View.VISIBLE);
                mErrorState = NETWORK_LOADING;
                animProgress.setVisibility(View.VISIBLE);
                img.setVisibility(View.INVISIBLE);
                btn_click.setVisibility(View.GONE);
                tv.setText("努力加载中");
         /*       iv_loading.setBackgroundResource(R.drawable.loading_rocket);
                AnimationDrawable loadingAnimation = (AnimationDrawable) iv_loading.getBackground();
                loadingAnimation.start();*/
                clickEnable = false;
                break;
            case NODATA:
                setVisibility(View.VISIBLE);
                mErrorState = NODATA;
                animProgress.setVisibility(View.GONE);
                setTvNoDataContent();
                //设置按钮可点击
                clickEnable = true;
                break;
            case HIDE_LAYOUT:
                setVisibility(View.GONE);
                break;

            case NODATA_ENABLE_CLICK:
                setVisibility(View.VISIBLE);
                mErrorState = NODATA_ENABLE_CLICK;
                animProgress.setVisibility(View.GONE);
                setTvNoDataContent();
                clickEnable = true;
                break;
        }
    }

    public void setNoDataContent(String noDataContent) {
        this.strNoDataContent = noDataContent;
    }

    //设置没有数据error页面
    public void setTvNoDataContent() {
        if (isSimple) {
            tv.setVisibility(View.GONE);   //由于屏幕适配问题，暂时不显示
            img.setVisibility(View.GONE);
            btn_click.setVisibility(View.GONE);
        } else {
            tv.setVisibility(View.VISIBLE);
            img.setVisibility(View.VISIBLE);
            btn_click.setVisibility(View.VISIBLE);
        }
        img.setImageResource(R.drawable.duojiao_empty_data);
        if (!strNoDataContent.equals("")) {
            tv.setText(strNoDataContent);
        } else {
        }
    }

    public void showTvNoData(String message) {
        setNoDataContent(message);
        setTvNoDataContent();
    }

    @Override
    public void setVisibility(int visibility) {
        if (visibility == View.GONE)
            mErrorState = HIDE_LAYOUT;
        super.setVisibility(visibility);
    }
}
