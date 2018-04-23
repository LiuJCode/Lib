package com.app.base.lib.activity.widget;

import android.content.Context;
import com.app.base.lib.R;

public class SmallDialog extends CustomerDialogNoTitle {
	public SmallDialog(Context context, String title) {
		super(context, R.style.myDialog, R.layout.small_dailog, title);
	}

	public SmallDialog(Context context, String title, float margin) {
		super(context, R.style.myDialog, R.layout.small_dailog, margin, title);
	}
}
