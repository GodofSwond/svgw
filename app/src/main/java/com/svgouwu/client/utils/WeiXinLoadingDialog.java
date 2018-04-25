package com.svgouwu.client.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.svgouwu.client.R;

public class WeiXinLoadingDialog extends ProgressDialog {
	private TextView tv;
	private Context context;
	String msg = "";

	public WeiXinLoadingDialog(Context context) {
//		super(context);
		super(context, R.style.loadingDialogStyle);
		this.context = context;
	}

	private WeiXinLoadingDialog(Context context, int theme) {
		super(context, theme);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weixin_dialog_loading);
		tv = (TextView) this.findViewById(R.id.tv);
		tv.setText(msg);
		LinearLayout linearLayout = (LinearLayout) this
				.findViewById(R.id.LinearLayout);
		linearLayout.getBackground().setAlpha(210);
	}

	public void setShowMsg(String msg) {
		if(!TextUtils.isEmpty(msg))
		{
			this.msg = msg;
		}else{
			this.msg = context.getString(R.string.dialog_process);
		}
	}
}
