package com.app.base.lib.utils;

import com.app.base.lib.base.BaseApplication;

 /**
  * 输出log辅助类
  *@author LIUJING
  *@created at 2018/2/8 15:56
  */
public class LogFactory {

	private static final String TAG = BaseApplication.APP;
	private static CommonLog log = null;

	public static CommonLog createLog() {
		if (log == null) {
			log = new CommonLog();
		}

		log.setTag(TAG);
		return log;
	}

	public static CommonLog createLog(String tag) {
		if (log == null) {
			log = new CommonLog();
		}

		if (tag == null || tag.length() < 1) {
			log.setTag(TAG);
		} else {
			log.setTag(tag);
		}
		return log;
	}
}