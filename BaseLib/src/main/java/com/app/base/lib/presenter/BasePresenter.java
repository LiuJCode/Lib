/*
 * Copyright (c) 2016 咖枯 <kaku201313@163.com | 3772304@qq.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.app.base.lib.presenter;

import android.support.annotation.NonNull;

import com.app.base.lib.view.BaseView;

/**
 * @author liujing
 * @version 1.0 2018/3/17
 */
public interface BasePresenter {
    /**
     * @data 2018/3/16
     * @author liujing
     * @describe 取消请求
     */
    void cancelRequest();
    /**
     *@data  2018/3/17
     *@author liujing
     *@describe 设置view 对象
     */
    void attachView(@NonNull BaseView view);

}
