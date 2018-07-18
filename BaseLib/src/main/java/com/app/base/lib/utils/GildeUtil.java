package com.app.base.lib.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.util.Util;
import com.app.base.lib.widget.BlurTransformation;
import com.app.base.lib.base.BaseApplication;

import java.io.File;


/**
 * Created by Administrator on 2017/5/9.
 * 图片加载工具类
 */
public class GildeUtil {

    public static RequestManager GildeWith(Context context) {
        if (context != null) {
            try {
                Activity activity = (Activity) context;
                if (isFinish(activity)) {
                    // 判断是否是主线程 Activity是否关闭
                    return Glide.with(context);
                } else {
                    //Activity已经关闭的时候 返回null 后续就不会做图片加载
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Context 不是Activity", "啦啦啦");
                //说明是ApplicationContext
                return Glide.with(BaseApplication.getContext());
            }
        } else {
            return Glide.with(BaseApplication.getContext());
        }
    }

    /**
     * 页面是否结束掉 如果关掉了 就不加载图片
     *
     * @author LIUJING
     * @created at 2017/12/12 17:16
     */
    public static boolean isFinish(Activity mActivity) {
        if (!mActivity.isFinishing() && Util.isOnMainThread()) {
//            LogTools.loge("进来加载图片了", "啦啦啦");
            return true;
        } else {
//            LogTools.loge("不能加载图片了", "啦啦啦");
            return false;
        }
    }

    /**
     * 取消掉图片加载
     *
     * @author LIUJING
     * @created at 2017/12/12 17:19
     */
    public static void pauseRequest(Activity mActivity) {
        //暂时去掉
        // 为什么会报错呢 java.lang.IllegalArgumentException: You cannot start a load for a destroyed activity
//        if (Util.isOnMainThread()
//                && mActivity != null
//                && !mActivity.isFinishing()) {
//            Glide.with(mActivity).pauseRequests();
//        }

    }

    /**
     * 加载本地图片
     *
     * @author LIUJING
     * @created at 2017/5/9 14:05
     */
    public static void loadLoacalImage(RequestManager glide, int id, ImageView imageView) {
        if (glide!=null)
        glide.load(id).asBitmap().into(imageView); // 加载本地图片
    }

    /**
     * 加载本地手机中的图片
     * RESULT 只缓存加载的图片
     *
     * @author LIUJING
     * @created at 2017/5/9 14:05
     */
    public static void loadLocalImg(RequestManager glide, String url, ImageView view) {
        try {
            if (glide != null && view != null && url != null) {
                File file = new File(url);
                if (FileUtils.fileIsExists(url)) {
                    // 如果本地图片存在 就加载
                    glide.load(file).asBitmap()
                            .into(view);
                }
            }
        } catch (OutOfMemoryError error) {
            if (url != null) {
                Log.e("oomUrl", url);
            }
        }
    }

    /**
     * 加载网络静态图片
     * RESULT 只缓存加载的图片
     *
     * @author LIUJING
     * @created at 2017/5/9 14:05
     */
    public static void loadNetImg(RequestManager glide, String url, ImageView view,
                                  int defautImg) {
        try {
            if (glide != null && view != null && url != null) {
                view.setTag(null);
                glide.load(url).asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.RESULT)
                        .placeholder(defautImg)
                        .into(view);
            }
        } catch (OutOfMemoryError error) {
            if (url != null) {
                Log.e("oomUrl", url);
            }
        }catch (IllegalArgumentException error){
            Log.e("加载布局出错","You must not call setTag() on a view Glide is targeting");
        }
//         glide.load(url).asBitmap()
//        .apply(getOption(defautImg))
//        .into(view);
    }

    /**
     * 加载网络gif图片
     *
     * @author LIUJING
     * @created at 2017/5/9 14:05
     */
    public static void loadNetGiftImg(RequestManager glide, String url, ImageView view,
                                      int defautImg) {
        if (glide != null && view != null) {
            glide.load(url).asGif()
                    .placeholder(defautImg)
                    .into(view);
        }
    }

    /**
     * 加载网络图片不缓存内存
     * GlideCircleTransform
     *
     * @author LIUJING
     * @created at 2017/5/9 14:05
     */
    public static void loadNetImgNoMemory(RequestManager glide, String url, ImageView view,
                                          int defautImg) {
        if (glide != null && view != null) {
            glide.load(url).asBitmap()
                    .placeholder(defautImg)
                    .diskCacheStrategy(DiskCacheStrategy.NONE) //不使用磁盘缓存
                    .skipMemoryCache(true) //不使用内存缓存
                    .into(view);
        }
    }

    /**
     * 加载缩略图 再加载原图
     *
     * @author LIUJING
     * @created at 2017/12/5 22:37
     */
    public static void loadNetImgThumb(RequestManager glide, String url, ImageView view,
                                       int defautImg) {
        //加载yourView1/10尺寸的缩略图，然后加载全图
        glide.load(url).thumbnail(0.1f)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(defautImg).into(view);

    }

    /**
     * 加载一个动画
     *
     * @author LIUJING
     * @created at 2017/12/5 22:37
     */
    public static void loadNetImgAnimar(RequestManager glide, String url, ImageView view,
                                        int defautImg) {

    }


    /**
     * （2）
     * 获取到Bitmap---不设置错误图片，错误图片不显示
     *
     * @param context
     * @param imageView
     * @param url
     */

    public static void showImageViewGone(Context context,
                                         final ImageView imageView, String url) {
        Glide.with(context).load(url).asBitmap()

                .diskCacheStrategy(DiskCacheStrategy.RESULT)// 缓存修改过的图片
                .into(new SimpleTarget<Bitmap>() {

                    @SuppressLint("NewApi")
                    @Override
                    public void onResourceReady(Bitmap loadedImage,
                                                GlideAnimation<? super Bitmap> arg1) {

                        imageView.setVisibility(View.VISIBLE);
                        BitmapDrawable bd = new BitmapDrawable(loadedImage);
                        imageView.setImageDrawable(bd);
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        // TODO Auto-generated method stub
                        super.onLoadFailed(e, errorDrawable);
                        imageView.setVisibility(View.GONE);
                    }

                });

    }

    /**
     * （3）
     * 设置RelativeLayout
     * <p>
     * 获取到Bitmap
     *
     * @param context
     * @param errorimg
     * @param url
     * @param bgLayout
     */

    public static void showImageView(Context context, int errorimg, String url,
                                     final RelativeLayout bgLayout) {
        Glide.with(context).load(url).asBitmap().error(errorimg)// 设置错误图片

                .diskCacheStrategy(DiskCacheStrategy.RESULT)// 缓存修改过的图片
                .placeholder(errorimg)// 设置占位图
                .into(new SimpleTarget<Bitmap>() {

                    @SuppressLint("NewApi")
                    @Override
                    public void onResourceReady(Bitmap loadedImage,
                                                GlideAnimation<? super Bitmap> arg1) {
                        BitmapDrawable bd = new BitmapDrawable(loadedImage);

                        bgLayout.setBackground(bd);

                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        // TODO Auto-generated method stub
                        super.onLoadFailed(e, errorDrawable);

                        bgLayout.setBackgroundDrawable(errorDrawable);
                    }

                });

    }

    /**
     * （4）
     * 设置LinearLayout
     * <p>
     * 获取到Bitmap
     *
     * @param context
     * @param errorimg
     * @param url
     * @param bgLayout
     */

    public static void showImageView(Context context, int errorimg, String url,
                                     final LinearLayout bgLayout) {
        Glide.with(context).load(url).asBitmap().error(errorimg)// 设置错误图片

                .diskCacheStrategy(DiskCacheStrategy.RESULT)// 缓存修改过的图片
                .placeholder(errorimg)// 设置占位图
                .into(new SimpleTarget<Bitmap>() {

                    @SuppressLint("NewApi")
                    @Override
                    public void onResourceReady(Bitmap loadedImage,
                                                GlideAnimation<? super Bitmap> arg1) {
                        BitmapDrawable bd = new BitmapDrawable(loadedImage);

                        bgLayout.setBackground(bd);

                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        // TODO Auto-generated method stub
                        super.onLoadFailed(e, errorDrawable);

                        bgLayout.setBackgroundDrawable(errorDrawable);
                    }

                });

    }

    /**
     * （5）
     * 设置FrameLayout
     * <p>
     * 获取到Bitmap
     *
     * @param context
     * @param errorimg
     * @param url
     * @param frameBg
     */

    public static void showImageView(Context context, int errorimg, String url,
                                     final FrameLayout frameBg) {
        Glide.with(context).load(url).asBitmap().error(errorimg)// 设置错误图片

                .diskCacheStrategy(DiskCacheStrategy.RESULT)// 缓存修改过的图片
                .placeholder(errorimg)// 设置占位图
                .into(new SimpleTarget<Bitmap>() {

                    @SuppressLint("NewApi")
                    @Override
                    public void onResourceReady(Bitmap loadedImage,
                                                GlideAnimation<? super Bitmap> arg1) {
                        BitmapDrawable bd = new BitmapDrawable(loadedImage);

                        frameBg.setBackground(bd);

                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        // TODO Auto-generated method stub
                        super.onLoadFailed(e, errorDrawable);

                        frameBg.setBackgroundDrawable(errorDrawable);
                    }

                });

    }

    /**
     * （6）
     * 获取到Bitmap 高斯模糊         ImageView
     *
     * @param context
     * @param errorimg
     * @param url
     * @param imgview
     */

    public static void showImageViewBlur(Context context, RequestManager glide, int errorimg,
                                         String url, final ImageView imgview) {
        try {
            glide.load(url).asBitmap().error(errorimg)
                    // 设置错误图片

                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    // 缓存修改过的图片
                    .placeholder(errorimg)
                    .transform(new BlurTransformation(context))// 高斯模糊处理
                    // 设置占位图

                    .into(new SimpleTarget<Bitmap>() {

                        @SuppressLint("NewApi")
                        @Override
                        public void onResourceReady(Bitmap loadedImage,
                                                    GlideAnimation<? super Bitmap> arg1) {
                            BitmapDrawable bd = new BitmapDrawable(loadedImage);

                            imgview.setImageDrawable(bd);
                        }
                        @Override
                        public void onLoadFailed(Exception e, Drawable errorDrawable) {
                            // TODO Auto-generated method stub
                            super.onLoadFailed(e, errorDrawable);

                            imgview.setImageDrawable(errorDrawable);
                        }

                    });

        }catch (OutOfMemoryError error){
            Log.e("error","OOM");
        }
    }


    /**
     * 一般是大图不缓存内存 看详情图的时候
     *
     * @author LIUJING
     * @created at 2017/12/5 14:35
     */
//    private static RequestOptions getOption(int defautImg) {
//        RequestOptions options = new RequestOptions()
//                .centerCrop()
//                .placeholder(defautImg)
//                .error(defautImg)
//                .skipMemoryCache(true)
//                .priority(Priority.HIGH);
//        return options;
//    }


    /**
     * 一般是大图不缓存内存 看详情图的时候
     *
     * @author LIUJING
     * @created at 2017/12/5 14:35
     */
//    private static RequestOptions getOptionNoMemory(int defautImg) {
//        RequestOptions options = new RequestOptions()
//                .centerCrop()
//                .placeholder(defautImg)
//                .error(defautImg)
//                .skipMemoryCache(true)
//                .priority(Priority.HIGH);
//        return options;
//    }

    /**
     * 一般是大图不缓存内存 看详情图的时候
     *
     * @author LIUJING
     * @created at 2017/12/5 14:35
     */
//    private static RequestOptions getOptionBlurTransformation(Context context, int defautImg) {
//        if (context == null) return new RequestOptions();
//        RequestOptions options = new RequestOptions()
//                .centerCrop()
//                .placeholder(defautImg)
//                .error(defautImg)
//                .skipMemoryCache(true)
//                .priority(Priority.HIGH)
//                .transforms(new BlurTransformation(context, 14));
//        return options;
//    }


}
