package com.app.base.lib.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.app.base.lib.mbean.ListData;
import com.app.base.lib.mbean.SociaxItem;

/**
 * recycler adapter 公共类
 *
 * @author LIUJING
 * @created at 2018/2/8 16:00
 */
public class ListBaseAdapterRecycler<T extends SociaxItem> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int STATE_EMPTY_ITEM = 0;
    //还有更多
    public static final int STATE_LOAD_MORE = 1;
    //没有更多
    public static final int STATE_NO_MORE = 2;
    //没有任何内容
    public static final int STATE_NO_DATA = 3;
    //少于一页内容
    public static final int STATE_LESS_ONE_PAGE = 4;
    //加载错误
    public static final int STATE_NETWORK_ERROR = 5;
    public static final int STATE_OTHER = 6;
    //没有最新
    public static final int STATE_NO_NEW = 7;
    //有最新内容
    public static final int STATE_NEW_MORE = 8;
    //说明是带有Header的
    public static final int TYPE_HEADER = 0;
    //说明是不带有header和footer的
    public static final int TYPE_NORMAL = 1;
    protected LayoutInflater mInflater;
    protected Context context;
    protected RecyclerView recyclerView;
    protected int state = STATE_LOAD_MORE;
    public ListData<T> mDatas = new ListData<T>();
    protected ListData headListData;
    protected ListData headListData2;
    protected boolean isHeadView = false;

    protected LayoutInflater getLayoutInflater(Context context) {
        if (mInflater == null && context != null) {
            mInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        return mInflater;
    }

    public ListBaseAdapterRecycler(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            return inItHeadView(parent, viewType);
        } else {
            return inItItemView(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position < 0) {
            position = 0;
            // 若列表没有数据，是没有footview/headview的
        }
        int type = getItemViewType(position);
        if (type == TYPE_HEADER) {
            //加载头
            getHeadView(position, holder);
        } else {
            getRealView(position, holder);
        }
    }


    //设置回调方法
    protected void getRealView(int position, RecyclerView.ViewHolder viewHolder) {
    }


    //初始化控件
    protected RecyclerView.ViewHolder inItItemView(ViewGroup parent, int viewType) {

        return null;
    }

    //设置头部回调方法
    protected void getHeadView(int position, RecyclerView.ViewHolder viewHolder) {
    }

    //初始化头部控件
    protected RecyclerView.ViewHolder inItHeadView(ViewGroup parent, int viewType) {

        return null;
    }

    @Override
    public int getItemCount() {

        return getDataSize();
    }

    public int getDataSize() {
        if (!vertifyHeadEmpty()) {
            //列表有头view要显示的话  列表需要加1
            return mDatas.size() + 1;
        } else {
            return mDatas.size();
        }
    }

    /**
     * 判断当前列表是否有头部 并且头部是否有数据
     * 如果有头并且头部有数据就不会显示默认图
     * 如果没有头或者头部没有数据就显示列表的默认图
     */
    public boolean vertifyHeadEmpty() {
        if (hasHeadView() &&
                (((getHeadData() != null && getHeadData().size() > 0)
                        || (getHeadData2() != null && getHeadData2().size() > 0))))
            return false;

        return true;
    }

    public int getMaxId() {
        return 0;
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public int getItemViewType(int position) {
        if (!vertifyHeadEmpty() && position == 0) {
            return TYPE_HEADER;
        } else {
            return TYPE_NORMAL;
        }
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return this.state;
    }

    /**
     * 是否有头部
     * 头部显示在列表第一个
     *
     * @author LIUJING
     * @created at 2017/12/1 22:18
     */
    protected boolean hasHeadView() {
        return false;
    }


    //设置头部数据源
    public void setHeadData(ListData headList) {
        this.headListData = headList;
    }

    //设置头部数据源2
    public void setHeadData2(ListData headList) {
        this.headListData2 = headList;
    }

    public ListData getHeadData() {
        return headListData;
    }

    public ListData getHeadData2() {
        return headListData2;
    }

    /**
     * 设置数据 初始化
     *
     * @author LIUJING
     * @created at 2017/12/1 22:05
     */
    public void setData(ListData<T> data) {
        mDatas = data;
        notifyDataSetChanged();
    }

    public ListData<T> getData() {
        return mDatas == null ? (mDatas = new ListData<T>()) : mDatas;
    }

    public void addData(ListData<T> data) {
        if (mDatas != null && data != null && !data.isEmpty()) {
            mDatas.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void addData(ListData<T> data, int i) {
        if (mDatas != null && data != null && !data.isEmpty()) {
            mDatas.addAll(i, data);
        }
        notifyItemChanged(i);
    }

    public void addItem(T obj) {
        if (mDatas != null) {
            mDatas.add(obj);
        }
        notifyDataSetChanged();
    }

    public void addItem(int pos, T obj) {
        if (mDatas != null) {
            mDatas.add(pos, obj);
        }
        notifyDataSetChanged();
    }

    public T getItem(int arg0) {
        if (arg0 > -1 && mDatas.size() > arg0) {
            return (T) mDatas.get(arg0);
        }
        return null;
    }

    public void setItem(int pos, T obj) {
        if (mDatas != null && mDatas.size() > 0) {
            mDatas.set(pos, obj);
        }
    }

    public int getItemForPosition(T obj) {
        int index = -1;
        for (int i = 0; i < mDatas.size(); i++) {
            if (mDatas.get(i).equals(obj))
                index = i;
        }

        return index;
    }

    public void removeItem(Object obj) {
        mDatas.remove(obj);
        notifyDataSetChanged();
    }

    public void clear() {
        mDatas.clear();
        notifyDataSetChanged();
    }

    /**
     * 是否有底部视图
     *
     * @return
     */
    protected boolean hasFooterView() {
        return false;
    }

}
