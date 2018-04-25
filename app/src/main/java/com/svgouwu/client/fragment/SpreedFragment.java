package com.svgouwu.client.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bracode.ui.CaptureActivity;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.svgouwu.client.Api;
import com.svgouwu.client.BaseFragment;
import com.svgouwu.client.R;
import com.svgouwu.client.activity.GoodsDetailsWebView;
import com.svgouwu.client.activity.GoodsListActivity;
import com.svgouwu.client.activity.WebViewActivity;
import com.svgouwu.client.bean.JsBridgeParam;
import com.svgouwu.client.utils.LogUtils;
import com.svgouwu.client.utils.SystemHelper;
import com.svgouwu.client.utils.UmengStat;
import com.svgouwu.client.view.MyBridgeWebViewClient;
import com.svgouwu.client.view.MyJsBridgeWebView;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.svgouwu.client.Api.URL_GOODS_DETAILS;

/**
 * Created by whq on 2017/9/1.
 */

public class SpreedFragment extends BaseFragment {

    String url;
    private ProgressBar mProgressBar;
    private HashMap<String, String> headMap = new HashMap<String, String>();
    private MyJsBridgeWebView mWebView;
    private MyBridgeWebViewClient mBridgeWebViewClient;

    @Override
    protected int getContentView() {
        return R.layout.fragment_spreed;
    }

    @Override
    public void initViews() {
        url = "https://www.svgouwu.com/mobile/?act=package_list";

        mWebView = (MyJsBridgeWebView) findView(R.id.spreed_webviewID);
        mProgressBar = mWebView.getProgressBar();
        WebSettings settings = mWebView.getSettings();
        String verCode = SystemHelper.getAppVersionCode(getContext()) + "";
        headMap.put("version", verCode);
        headMap.put("client", "android");

        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);//根据屏幕缩放
        settings.setDefaultZoom(WebSettings.ZoomDensity.FAR);//根据屏幕缩放
        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setAppCacheEnabled(false);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setSupportMultipleWindows(true);
        settings.setUserAgentString(settings.getUserAgentString() + "[svgw,android," + verCode + "]");
        mWebView.setDefaultHandler(new DefaultHandler());

        mWebView.setWebChromeClient(new SpreedFragment.MyWebChromeClient());
        mBridgeWebViewClient = new MyBridgeWebViewClient(mWebView);
        mWebView.setWebViewClient(mBridgeWebViewClient);

        loadWebUrl();
        mWebView.registerHandler("submitFromWeb", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                try {
                    function.onCallBack("submitFromWeb exe, response url 中文 from Java");
                    JsBridgeParam params = new Gson().fromJson(data, new TypeToken<JsBridgeParam>() {
                    }.getType());
                    Log.d("whq", "==========" + params);
                    if (params != null) {
                        if (("barcode").equals(params.action)) {
                            Intent loginIntent = new Intent(getActivity(), CaptureActivity.class);
                            startActivity(loginIntent);
                        } else if (("goodsDetail").equals(params.action)) {
                            Intent intent2 = new Intent(getActivity(), GoodsDetailsWebView.class);
                            intent2.putExtra("url", URL_GOODS_DETAILS + params.ref_id);
                            intent2.putExtra("goodsId", params.ref_id);
                            startActivity(intent2);
                        } else if (("category").equals(params.action)) {
                            Intent intent = new Intent(getActivity(), GoodsListActivity.class);
                            intent.putExtra("category", params.ref_id);
                            getActivity().startActivity(intent);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });


    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        TextView tv_title = findView(R.id.tv_title);
        tv_title.setText("大礼包详情");
        findView(R.id.iv_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    @Override
    public void processClick(View view) {

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

    private void loadWebUrl() {
        mWebView.loadUrl(url.trim());
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(UmengStat.ACTIVITYPROJECTPAGE);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(UmengStat.ACTIVITYPROJECTPAGE);
    }
}
