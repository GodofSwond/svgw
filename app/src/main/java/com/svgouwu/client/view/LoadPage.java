package com.svgouwu.client.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.svgouwu.client.R;


/**
 * 网络未连接显示
 */
public class LoadPage extends FrameLayout {
	public static final int STATE_LOADING = 0;
	public static final int STATE_NO_NET = 1;
	public static final int STATE_NO_DATA = 2;
	public static final int STATE_HIDE = 3;
	private View loadingView;// 加载中的界面
	private View noNetView;// 错误界面
	private View noDataView;// 空界面
	private TextView tv_nodata_desc;//空界面提示
	private GetDataListener getDataListener;
	public interface GetDataListener {
		void onGetData();
	}

	public LoadPage(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public LoadPage(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public LoadPage(Context context) {
		super(context);
		init();
	}
	
 
	private void init() {
		if(isInEditMode()) return;

//		this.setVisibility(VISIBLE);
		loadingView = createLoadingView(); // 创建了加载中的界面
		if (loadingView != null) {
			this.addView(loadingView, new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}
		noNetView = createErrorView(); // 加载错误界面
		if (noNetView != null) {
			this.addView(noNetView, new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}
		noDataView = createEmptyView(); // 加载空的界面
		if (noDataView != null) {
			tv_nodata_desc = (TextView) noDataView.findViewById(R.id.tv_nodata_desc);
			this.addView(noDataView, new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}
	}

	/* 创建了空的界面 */
	private View createEmptyView() {
		View view = View.inflate(getContext(), R.layout.view_no_data,
				null);
		return view;
	}

	/* 创建了错误界面 */
	private View createErrorView() {
		View view = View.inflate(getContext(), R.layout.view_no_net,
				null);
		LinearLayout ll_no_net = (LinearLayout) view.findViewById(R.id.ll_no_net);
		ll_no_net.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (getDataListener != null){
					getDataListener.onGetData();
				}
			}
		});
		return view;
	}

	/* 创建加载中的界面 */
	private View createLoadingView() {
		View view = View.inflate(getContext(),
				R.layout.view_loading_data, null);
		ProgressBar widget_loadingDialog_progress = (ProgressBar) view.findViewById(R.id.widget_loadingDialog_progress);
//		widget_loadingDialog_progress.setIndeterminateDrawable(getContext().getResources().getDrawable(R.drawable.loading));
		return view;
	}

	public void setGetDataListener(GetDataListener getDataListener) {
		this.getDataListener = getDataListener;
	}
	// 根据不同的状态显示不同的界面
	public void switchPage(int i) {
		switch (i) {
			case 0:
				if (loadingView != null) {
					loadingView.setVisibility(View.VISIBLE);
				}
				if (noNetView != null) {
					noNetView.setVisibility(View.GONE);
				}
				if (noDataView != null) {
					noDataView.setVisibility(View.GONE);
				}
				break;
			case 1:
				if (loadingView != null) {
					loadingView.setVisibility(View.GONE);
				}
				if (noNetView != null) {
					noNetView.setVisibility(View.VISIBLE);
				}
				if (noDataView != null) {
					noDataView.setVisibility(View.GONE);
				}
				break;
			case 2:
				if (loadingView != null) {
					loadingView.setVisibility(View.GONE);
				}
				if (noNetView != null) {
					noNetView.setVisibility(View.GONE);
				}
				if (noDataView != null) {
					noDataView.setVisibility(View.VISIBLE);
				}
				break;
			case 3:
				if (loadingView != null) {
					loadingView.setVisibility(View.GONE);
				}
				if (noNetView != null) {
					noNetView.setVisibility(View.GONE);
				}
				if (noDataView != null) {
					noDataView.setVisibility(View.GONE);
				}
				break;
			default:
				break;
		}
	}

	public void setNoDataText(String text){
		if(tv_nodata_desc!=null){
			tv_nodata_desc.setText(text);
		}
	}
}
