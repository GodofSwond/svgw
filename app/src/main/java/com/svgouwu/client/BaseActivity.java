package com.svgouwu.client;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.svgouwu.client.utils.CommonUtils;
import com.svgouwu.client.utils.SpUtil;
import com.svgouwu.client.utils.WeiXinLoadingDialog;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import cn.magicwindow.Session;


/**
 * 公共基类
 *
 * @author mleon on 2016-3-14
 */

public abstract class BaseActivity extends AppCompatActivity implements OnClickListener {
    public static final int XRECYCLER_HEAD_DEFAULT_COUNT = 1;
    private SparseArray<View> mViews;
    public static Handler mHandler = new Handler();
    protected SharedPreferences mConfigSp;
    public WeiXinLoadingDialog weixinDialog;

    protected abstract int getContentView();

    public abstract void initViews();

    public abstract void initListener();

    public abstract void initData();

    public abstract void processClick(View view);

    protected Context mContext;

    public BaseActivity() {
        mContext = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mConfigSp = SpUtil.getSharePerference(this);
        mViews = new SparseArray<>();
        setContentView(getContentView());
        MobclickAgent.openActivityDurationTrack(false);
        ButterKnife.bind(this);
        initViews();
        initListener();
        initData();

//        if (!(this instanceof WelcomeActivity)) {
//            SystemHelper.setStateBarTint(this);
//        }
    }


    protected <E extends View> E findView(int viewId) {
        E view = (E) mViews.get(viewId);

        if (view == null) {
            view = (E) findViewById(viewId);
            mViews.put(viewId, view);
        }
        return view;
    }

    protected <E extends View> void setOnClickListener(E view) {
        view.setOnClickListener(this);
    }


    boolean isCancelable = false;

    public void setWeixinCancelable(boolean isCancelable) {
        this.isCancelable = isCancelable;
    }

    public void weixinDialogInit(final String msg) {
        // isLoading =true;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                weixinDialog = new WeiXinLoadingDialog(BaseActivity.this);
                weixinDialog.setShowMsg(msg);
                weixinDialog.setCanceledOnTouchOutside(false);
                // 设置ProgressDialog 是否可以按退回键取消
                weixinDialog.setCancelable(isCancelable);

                if (!BaseActivity.this.isFinishing()) {
                    weixinDialog.show();
                }
            }
        });

    }

    public void cancelWeiXinDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {//IllegalArgumentException
                try {
                    if (weixinDialog != null && weixinDialog.isShowing()) {
                        weixinDialog.cancel();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        processClick(view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        Session.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        Session.onPause(this);
    }

    /**
     * 给Activity的顶部View加宽度，颜色同ActionBar，且不可修改
     * 有局限性，适合顶部有actionbar，非图片的Activity
     * @param view
     * @param type
     */
    protected void setTopBarViews(View view, boolean type) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
              /*  window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);*/
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);底部按钮透明设置
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            if(type){
                int statusBarHeight = CommonUtils.getStatusBarHeight(this.getBaseContext());
                ViewGroup.LayoutParams lp=(ViewGroup.LayoutParams)view.getLayoutParams();
                lp.height+=statusBarHeight;
                view.setLayoutParams(lp);
                view.setPadding(0,statusBarHeight,0,0);
            }

        }
    }
}
