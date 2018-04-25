package com.svgouwu.client.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bracode.ui.CaptureActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kili.okhttp.OkHttpUtils;
import com.svgouwu.client.Api;
import com.svgouwu.client.BaseActivity;
import com.svgouwu.client.BuildConfig;
import com.svgouwu.client.MyApplication;
import com.svgouwu.client.R;
import com.svgouwu.client.bean.GoodsSpec;
import com.svgouwu.client.bean.GoodsSpecChange;
import com.svgouwu.client.bean.HttpResult;
import com.svgouwu.client.bean.JsBridgeParam;
import com.svgouwu.client.bean.UserBean;
import com.svgouwu.client.bean.VersionInfo;
import com.svgouwu.client.utils.CommonCallback;
import com.svgouwu.client.utils.CommonUtils;
import com.svgouwu.client.utils.DeviceUuidUtil;
import com.svgouwu.client.utils.DialogUtil;
import com.svgouwu.client.utils.LogUtils;
import com.svgouwu.client.utils.SystemHelper;
import com.svgouwu.client.utils.ToastUtil;
import com.svgouwu.client.utils.UmengStat;
import com.svgouwu.client.view.FlowLayout;
import com.svgouwu.client.view.FlowRadioGroup;
import com.svgouwu.client.view.GoodsSpecDialog;
import com.svgouwu.client.view.MyBridgeWebViewClient;
import com.svgouwu.client.view.MyJsBridgeWebView;
import com.svgouwu.client.view.UpdateManager;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * html5交互页
 */
public class WebViewActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "WebViewActivity";
    private HashMap<String, String> headMap = new HashMap<String, String>();
    private MyJsBridgeWebView mWebView;
    private String url;
    private String old_url;
    private boolean isOpenLogin = false;
    private String from;//从哪里进入此页
    private TextView textGoodsTabTitleName;
    private ImageView iv_right;
    private boolean isShowMenu = false;
    private boolean isFromDeepLink = false;
    private RelativeLayout rl_subject_wv;
    private FrameLayout mFrameLayout;
    private WebView childWebView;
    private String title;
    private boolean isDirectBack = false;//是否直接退出;
    private String postStr;
    private static final String IC_SHARE_INVITATION_CODE = "http://image.kilimall.co.ke/mobile/share_cash_rewards.png";
    private MyBridgeWebViewClient mBridgeWebViewClient;
    private ProgressBar mProgressBar;
    private String mGoodsId;
    private GoodsSpec mGoodsSpec; //商品规格
    private LinearLayout ll_webview_goods_details_bottom;

    @Override
    protected int getContentView() {
        return R.layout.activity_webview;
    }

    @Override
    public void initViews() {

        url = getIntent().getStringExtra("url");
        mGoodsId = getIntent().getStringExtra("goodsId");
        LogUtils.e("goodsId: " + mGoodsId);

        mFrameLayout = (FrameLayout) findViewById(R.id.mFrameLayout);
        mWebView = (MyJsBridgeWebView) findViewById(R.id.webviewID);
        mProgressBar = mWebView.getProgressBar();

        String verCode = SystemHelper.getAppVersionCode(getApplicationContext()) + "";
        headMap.put("version", verCode);
//        headMap.put("key", MyApplication.getInstance().getLoginKey());
        headMap.put("client", "android");

        WebSettings settings = mWebView.getSettings();
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);//根据屏幕缩放
        settings.setDefaultZoom(WebSettings.ZoomDensity.FAR);//根据屏幕缩放
        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setAppCacheEnabled(false);
        LogUtils.e("agent===" + settings.getUserAgentString());
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setSupportMultipleWindows(true);
        settings.setUserAgentString(settings.getUserAgentString() + "[svgw,android," + verCode + "]");
        mWebView.setDefaultHandler(new DefaultHandler());

        mWebView.setWebChromeClient(new WebViewActivity.MyWebChromeClient());
        mBridgeWebViewClient = new MyBridgeWebViewClient(mWebView);
        mWebView.setWebViewClient(mBridgeWebViewClient);

        loadWebUrl();

//        mWebView.loadUrl("http://10.0.0.31/wap/jsBridge/demo.html");

        mWebView.registerHandler("submitFromWeb", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                try {
                    Log.i(TAG, "handler = submitFromWeb, url from web = " + data);
                    function.onCallBack("submitFromWeb exe, response url 中文 from Java");
                    JsBridgeParam params = new Gson().fromJson(data, new TypeToken<JsBridgeParam>() {
                    }.getType());

                    //login.search,goodsDetail,storeDetail,buyVoucher,orderList,buyMore,verifyPhone,html5,userCenter
                    if (params != null) {
                        if (("barcode").equals(params.action)) {
                            Intent loginIntent = new Intent(WebViewActivity.this, CaptureActivity.class);
                            startActivity(loginIntent);
                        } else if ("getSpec".equals(params.action)) {
                            //商品规格
                            showSpecDialog();
                        } else if (("goodsDetail").equals(params.action)) {
                            Intent intent2 = new Intent(WebViewActivity.this, WebViewActivity.class);
                            intent2.putExtra("url", Api.H5_GOODS_DETAILS + params.ref_id);
                            intent2.putExtra("goodsId", params.ref_id);
                            startActivity(intent2);
                        } else if (("category").equals(params.action)) {
                            Intent intent = new Intent(WebViewActivity.this, GoodsListActivity.class);
                            intent.putExtra("category", params.ref_id);
                            startActivity(intent);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

//
//
//
//        mWebView.callHandler("functionInJs", new Gson().toJson(user), new CallBackFunction() {
//            @Override
//            public void onCallBack(String url) {
//
//            }
//        });
//
//        mWebView.send("hello");
    }


    private Dialog down2UpDialog;

    private void showSpecDialog() {
        //弹出规格选择对话框
        if (mGoodsSpec == null) {
            ToastUtil.toast("商品规格异常，请稍后再试");
            return;
        }
        if (down2UpDialog == null) {
//            down2UpDialog = DialogUtil.getDown2UpDialog(WebViewActivity.this, R.layout.dialog_goods_details_buy);
            down2UpDialog = new GoodsSpecDialog(WebViewActivity.this, mGoodsSpec, mGoodsId);
        }

        down2UpDialog.show();
    }

    @Override
    public void initListener() {

    }

    CheckBox cb_goods_fav;

    @Override
    public void initData() {
        //checkAppVersion();  //检测新版本
        ll_webview_goods_details_bottom = (LinearLayout) findViewById(R.id.ll_webview_goods_details_bottom);
        if (!CommonUtils.isEmpty(mGoodsId)) {
            ll_webview_goods_details_bottom.setVisibility(VISIBLE);
            cb_goods_fav = findView(R.id.cb_goods_fav);
            cb_goods_fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!CommonUtils.checkLogin()) {
                        cb_goods_fav.setChecked(false);
                        return;
                    }
                    favGoods(cb_goods_fav.isChecked());
                }
            });

            findViewById(R.id.tv_goods_detail_add_cart).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //弹窗
                    showSpecDialog();
                }
            });
            findViewById(R.id.tv_goods_detail_buy).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //弹窗
                    showSpecDialog();
                }
            });
            //获取商品规格
            getGoodsSpec();

        } else {
            ll_webview_goods_details_bottom.setVisibility(GONE);
        }
    }

    private void getFavState() {
        if (!CommonUtils.isLogin() || CommonUtils.isEmpty(mGoodsId)) return;

        String url = Api.URL_GOODS_FAV_STATE;
        HashMap<String, String> params = new HashMap<>();
        params.put("goodsId", mGoodsId);
        params.put("token", MyApplication.getInstance().getLoginKey());
        OkHttpUtils.post().url(url).params(params).build().execute(new CommonCallback<Object>() {
            @Override
            public void onBefore(Request request) {
                super.onBefore(request);
            }

            @Override
            public void onAfter() {
                super.onAfter();
            }

            @Override
            public void onError(Call call, Exception e) {
                LogUtils.e(e.toString());
                ToastUtil.toast(R.string.text_tips_net_issue);
            }

            @Override
            public void onResponse(HttpResult<Object> response) {
                try {
                    JSONObject jObj = new JSONObject(response.data.toString());
                    int iscollected = jObj.optInt("iscollected");
                    if (iscollected == 1) {
                        //收藏
                        cb_goods_fav.setChecked(true);
                    } else {
                        //未收藏
                        cb_goods_fav.setChecked(false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtil.toast(R.string.text_tips_server_issue);
                }
            }
        });
    }

    //收藏商品
    private void favGoods(final boolean isFav) {
        HashMap<String, String> params = new HashMap<>();
        String url;
        if (isFav) {
            url = Api.URL_GOODS_FAV;
        } else {
            url = Api.URL_GOODS_FAV_CANCEL;
        }
        params.put("goodsId", mGoodsId);
        params.put("token", MyApplication.getInstance().getLoginKey());
        OkHttpUtils.post().url(url).params(params).build().execute(new CommonCallback<GoodsSpec>() {
            @Override
            public void onBefore(Request request) {
                super.onBefore(request);
                weixinDialogInit(getString(R.string.dialog_process));
            }

            @Override
            public void onAfter() {
                super.onAfter();
                cancelWeiXinDialog();
            }

            @Override
            public void onError(Call call, Exception e) {
                LogUtils.e(e.toString());
                ToastUtil.toast(R.string.text_tips_net_issue);
            }

            @Override
            public void onResponse(HttpResult<GoodsSpec> response) {
                try {
                    if (!CommonUtils.isEmpty(response.msg)) {
                        ToastUtil.toast(response.msg);
                    }

                    if (response.isSuccess()) {
                        cb_goods_fav.setText(isFav ? "已收藏" : "收藏");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtil.toast(R.string.text_tips_server_issue);
                }
            }
        });
    }

    //获取商品规格
    private void getGoodsSpec() {
        String url = Api.URL_GOODS_SPEC;
        HashMap<String, String> params = new HashMap<>();
        params.put("id", mGoodsId);
        OkHttpUtils.get().url(url).params(params).build().execute(new CommonCallback<GoodsSpec>() {
            @Override
            public void onBefore(Request request) {
                super.onBefore(request);
                weixinDialogInit(getString(R.string.dialog_process));
            }

            @Override
            public void onAfter() {
                super.onAfter();
                cancelWeiXinDialog();
            }

            @Override
            public void onError(Call call, Exception e) {
                LogUtils.e(e.toString());
                ToastUtil.toast(R.string.text_tips_net_issue);
            }

            @Override
            public void onResponse(HttpResult<GoodsSpec> response) {
                try {
                    if (response.isSuccess()) {
                        mGoodsSpec = response.data;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtil.toast(R.string.text_tips_server_issue);
                }
            }
        });
    }

    @OnClick({R.id.iv_web_view_back})
    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.iv_web_view_back:
                finish();
                break;
        }
    }

    private void loadWebUrl() {
        if (!TextUtils.isEmpty(postStr)) {
            mWebView.postUrl(url.toString(), postStr.getBytes());
        } else {
            mWebView.loadUrl(url.trim(), headMap); //运营中间输入空格，让他们自己改。
        }
        LogUtils.e("mWebView url: ", url.trim());
    }

    private class MyWebChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                mProgressBar.setVisibility(GONE);
            } else {
                if (mProgressBar.getVisibility() == GONE)
                    mProgressBar.setVisibility(VISIBLE);
                mProgressBar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onCloseWindow(WebView window) {
            if (childWebView != null && childWebView.isShown()) {
                childWebView.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out));
                childWebView.setVisibility(GONE);
            }

            super.onCloseWindow(window);
        }

        private View mCustomView;
        private CustomViewCallback mCustomViewCallback;

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            super.onShowCustomView(view, callback);
            if (mCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }
            mCustomView = view;
            mFrameLayout.addView(mCustomView);
            mCustomViewCallback = callback;
            mWebView.setVisibility(View.GONE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        public void onHideCustomView() {
            mWebView.setVisibility(View.VISIBLE);
            if (mCustomView == null) {
                return;
            }
            mCustomView.setVisibility(View.GONE);
            mFrameLayout.removeView(mCustomView);
            mCustomViewCallback.onCustomViewHidden();
            mCustomView = null;
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            super.onHideCustomView();
        }

        //		@Override
//		public void onReceivedTitle(WebView view, String title) {
//			super.onReceivedTitle(view, title);
//			if(!KiliUtils.isEmpty(title))
//				textGoodsTabTitleName.setText(title);
//		}
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();// 返回前一个页面
            return true;
        }
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            exit();
//            return false;
//        }
        return super.onKeyDown(keyCode, event);
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onDestroy() {
        super.onDestroy();
        headMap = null;
        if (mWebView != null) {
            mWebView.setVisibility(GONE);
            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            releaseAllWebViewCallback();
            mWebView.destroy();
            mWebView = null;
        }
    }

    public void releaseAllWebViewCallback() {
        if (Build.VERSION.SDK_INT < 16) {
            try {
                Field field = WebView.class.getDeclaredField("mWebViewCore");
                field = field.getType().getDeclaredField("mBrowserFrame");
                field = field.getType().getDeclaredField("sConfigCallback");
                field.setAccessible(true);
                field.set(null, null);
            } catch (NoSuchFieldException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            } catch (IllegalAccessException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                Field sConfigCallback = Class.forName("android.webkit.BrowserFrame").getDeclaredField("sConfigCallback");
                if (sConfigCallback != null) {
                    sConfigCallback.setAccessible(true);
                    sConfigCallback.set(null, null);
                }
            } catch (NoSuchFieldException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            } catch (ClassNotFoundException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            } catch (IllegalAccessException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null && intent.hasExtra("url") && !TextUtils.isEmpty(intent.getStringExtra("url"))) {
            url = intent.getStringExtra("url");
            loadWebUrl();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
        switch (config.orientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                break;
        }
    }

    private void fullScreen() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(UmengStat.STAYGOODSDETAILS); //
        MobclickAgent.onPause(this);
        mWebView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(UmengStat.STAYGOODSDETAILS); //统计页面
        MobclickAgent.onResume(this);          //统计时长
        mWebView.onResume();
        //获取收藏状态
        getFavState();
    }
}
