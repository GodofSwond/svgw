package com.svgouwu.client;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.svgouwu.client.utils.WeiXinLoadingDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by topwolf on 2017/12/25.
 */
public abstract class BaseTopFragment extends Fragment implements View.OnClickListener {

    public static final int XRECYCLER_HEAD_DEFAULT_COUNT = 1;
    public WeiXinLoadingDialog weixinDialog;
    boolean isCancelable = false;
    /**
     * 当前显示的内容
     **/
    public ViewGroup convertView;
    public View mStatusBarView;
    private SparseArray<View> mViews;
    private Unbinder unbinder;
    public static Handler mHandler = new Handler();

    protected abstract int getContentView();

    public abstract void initViews();

    public abstract void initListener();

    public abstract void initData();

    public abstract void processClick(View view);

    public BaseTopFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mViews = new SparseArray<>();
        if (convertView == null) {
            convertView = (ViewGroup) inflater.inflate(getContentView(), container, false);
        }
        ViewGroup parent = (ViewGroup) convertView.getParent();
        if (parent != null) {
            parent.removeView(convertView);
        }
        addStatusBar();
        unbinder = ButterKnife.bind(this, convertView);

        initViews();
        initListener();
        initData();
        return convertView;
    }

    protected <E extends View> E findView(int viewId) {
        if (convertView != null) {
            E view = (E) mViews.get(viewId);

            if (view == null) {
                view = (E) convertView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return view;
        }
        return null;
    }

    protected <E extends View> void setOnClickListener(E view) {
        view.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        processClick(view);
    }


    public void setWeixinCancelable(boolean isCancelable) {
        this.isCancelable = isCancelable;
    }

    public void weixinDialogInit(String msg) {
        // isLoading =true;
        weixinDialog = new WeiXinLoadingDialog(getActivity());
        weixinDialog.setShowMsg(msg);
        weixinDialog.setCanceledOnTouchOutside(false);
        // 设置ProgressDialog 是否可以按退回键取消
        weixinDialog.setCancelable(isCancelable);
        weixinDialog.show();
    }

    public void cancelWeiXinDialog() {
        if (weixinDialog != null && weixinDialog.isShowing()) {
            weixinDialog.cancel();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    /**
     * 添加一个view在bar顶部，适合Activity
     * 也适合一个Activity中包含多个Fragment
     */
    private void addStatusBar() {
        if (mStatusBarView == null) {
            mStatusBarView = new View(getContext());
            int screenWidth = getResources().getDisplayMetrics().widthPixels;
            int statusBarHeight = getStatusBarHeight(getActivity());
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(screenWidth, statusBarHeight);
            mStatusBarView.setLayoutParams(params);
            mStatusBarView.requestLayout();
            if (convertView != null)
                convertView.addView(mStatusBarView, 0);
        }
    }
    public static int getStatusBarHeight(Activity activity) {
        int statusBarHeight = 0;
        if (activity != null) {
            int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
            statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

}
