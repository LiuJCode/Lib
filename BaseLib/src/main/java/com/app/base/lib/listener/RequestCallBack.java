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
package com.app.base.lib.listener;

/**
 * @author liujing
 * @version 1.0 2018/3/17
 */
public interface RequestCallBack<T> {

    /**
     *@data  2018/3/17
     *@author liujing
     *@describe 请求前
     */
    void beforeRequest();

    /**
     *@data  2018/3/17
     *@author liujing
     *@describe 请求成功后
     */
    void msuccess(T data);

    /**
     *@data  2018/3/17
     *@author liujing
     *@describe 请求错误
     */
    void onError(String errorMsg);
}
