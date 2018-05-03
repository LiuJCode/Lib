package com.lj.project.kotlin

import butterknife.BindView
import butterknife.ButterKnife
import com.app.base.lib.base.BaseActivity
import com.lj.project.newljproject.R

/**
 * Created by Administrator on 2018/5/3.
 */
class KotlinActivity : BaseActivity(){
    @BindView(R.id.txt_msg)
    val txt_msg = null;
    override fun setViewAndData() {
        ButterKnife.bind(this);
    }

    override fun getLayoutId(): Int {
        return R.layout.rxjava;
    }

    override fun initPersenter() {
    }

}
