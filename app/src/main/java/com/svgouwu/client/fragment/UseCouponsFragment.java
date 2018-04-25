package com.svgouwu.client.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.svgouwu.client.BaseFragment;
import com.svgouwu.client.R;
import com.svgouwu.client.activity.CouponsCenterActivity;
import com.svgouwu.client.activity.GoodsDetailsWebView;
import com.svgouwu.client.bean.JsBridgeParam;
import com.svgouwu.client.utils.LogUtils;
import com.svgouwu.client.utils.SystemHelper;
import com.svgouwu.client.utils.UmengStat;
import com.svgouwu.client.view.MyBridgeWebViewClient;
import com.svgouwu.client.view.MyJsBridgeWebView;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.svgouwu.client.Api.URL_GOODS_DETAILS;

/**
 * Created by Administrator on 2017/9/8.
 * 使用优惠券
 */

public class UseCouponsFragment extends BaseFragment {

    private String url;

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.fragment_details_fl)
    FrameLayout fl;
    @BindView(R.id.webviewID)
    MyJsBridgeWebView webviewID;
    private ProgressBar mProgressBar;
    private HashMap<String, String> headMap = new HashMap<String, String>();
    private MyBridgeWebViewClient mBridgeWebViewClient;

    public UseCouponsFragment() {

    }

    public static UseCouponsFragment newInstance() {
        return new UseCouponsFragment();
    }

    @Override
    protected int getContentView() {
        return R.layout.details_fragment;
    }

    @Override
    public void initViews() {
        tv_title.setText("大礼包详情");
//        url = "http://www.svgouwu.com/mobile/index.php?app=default&act=fallNew&sv=app";
        Intent intent = getActivity().getIntent();
        String useMobileUrl = intent.getStringExtra("useMobileUrl");
        url = useMobileUrl;
        mProgressBar = webviewID.getProgressBar();
        String verCode = SystemHelper.getAppVersionCode(getContext()) + "";
        headMap.put("version", verCode);
//        headMap.put("key", MyApplication.getInstance().getLoginKey());
        headMap.put("client", "android");

        WebSettings settings = webviewID.getSettings();
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
        webviewID.setDefaultHandler(new DefaultHandler());

        webviewID.setWebChromeClient(new UseCouponsFragment.MyWebChromeClient());
        mBridgeWebViewClient = new MyBridgeWebViewClient(webviewID);
        webviewID.setWebViewClient(mBridgeWebViewClient);

        loadWebUrl();

        webviewID.registerHandler("submitFromWeb", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                try {
                    LogUtils.e("handler = submitFromWeb, url from web = " + data);
                    function.onCallBack("submitFromWeb exe, response url 中文 from Java");
                    JsBridgeParam params = new Gson().fromJson(data, new TypeToken<JsBridgeParam>() {
                    }.getType());
                    //login.search,goodsDetail,storeDetail,buyVoucher,orderList,buyMore,verifyPhone,html5,userCenter
                    if (params != null) {
                        if (("coupons").equals(params.action)) {
                            MobclickAgent.onEvent(getContext(), UmengStat.HOMEPAGETOCLASSIFICATIONNUMBER);
                            Intent intent3 = new Intent(getActivity(), CouponsCenterActivity.class);
                            intent3.putExtra("coupons", "");
                            startActivity(intent3);
                        } else if (("goodsDetail").equals(params.action)) {//跳转到详情页面
                            Intent intent2 = new Intent(getActivity(), GoodsDetailsWebView.class);
                            intent2.putExtra("url", URL_GOODS_DETAILS + params.ref_id);
                            intent2.putExtra("goodsId", params.ref_id);
                            startActivity(intent2);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void loadWebUrl() {
        webviewID.loadUrl(url.trim(), headMap); //运营中间输入空格，让他们自己改。
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

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.iv_left})
    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                getActivity().finish();
                break;
        }
    }
}