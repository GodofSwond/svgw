package com.svgouwu.client.fragment;

import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.bracode.ui.CaptureActivity;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.svgouwu.client.BaseTopFragment;
import com.svgouwu.client.R;
import com.svgouwu.client.activity.GoodsDetailsWebView;
import com.svgouwu.client.activity.GoodsListActivity;
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
 * Created by topwolf on 2017/6/6.
 */

public class VideoFragment extends BaseTopFragment {

    private MyBridgeWebViewClient mBridgeWebViewClient;
    private ProgressBar mProgressBar;
    private FrameLayout mFrameLayout;
    private HashMap<String, String> headMap = new HashMap<String, String>();
    private MyJsBridgeWebView mWebView;
    private String url;

    public VideoFragment() {}

    public static VideoFragment newInstance() {
        return new VideoFragment();
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_video;
    }

    @Override
    public void initViews() {
        url = "https://www.svgouwu.com/mobile/index.php?app=search&act=appzhibo";
        mStatusBarView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        mFrameLayout = (FrameLayout) findView(R.id.mFrameLayout);
        mWebView = (MyJsBridgeWebView) findView(R.id.webviewID);
        mProgressBar = mWebView.getProgressBar();

        String verCode = SystemHelper.getAppVersionCode(getContext()) + "";
        headMap.put("version", verCode);
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        mWebView.setDefaultHandler(new DefaultHandler());

        mWebView.setWebChromeClient(new VideoFragment.MyWebChromeClient());
        mBridgeWebViewClient = new MyBridgeWebViewClient(mWebView);
        mWebView.setWebViewClient(mBridgeWebViewClient);

        loadWebUrl();
        mWebView.registerHandler("submitFromWeb", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                try {
                    LogUtils.e("handler = submitFromWeb, url from web = " + data);
                    function.onCallBack("submitFromWeb exe, response url 中文 from Java");
                    JsBridgeParam params = new Gson().fromJson(data, new TypeToken<JsBridgeParam>() {
                    }.getType());

                    //login.search,goodsDetail,storeDetail,buyVoucher,orderList,buyMore,verifyPhone,html5,userCenter
                    if (params != null) {
                        if (("barcode").equals(params.action)) {
                            Intent loginIntent = new Intent(getActivity(), CaptureActivity.class);
                            startActivity(loginIntent);
                        }else if (("goodsDetail").equals(params.action)) {
                            Intent intent2 = new Intent(getActivity(), GoodsDetailsWebView.class);
                            intent2.putExtra("url", URL_GOODS_DETAILS+params.ref_id);
                            intent2.putExtra("goodsId",params.ref_id);
                            startActivity(intent2);
                        }else if (("category").equals(params.action)) {
                            Intent intent = new Intent(getActivity(), GoodsListActivity.class);
                            intent.putExtra("category",params.ref_id);
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

    }

    @Override
    public void processClick(View view) {

    }

    @Override //Fragment之间切换时调用。控制视频播放
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if(!hidden){
            if(mWebView!=null){
                mWebView.onResume();
            }
        }else{
            if(mWebView!=null){
                mWebView.onPause();
            }
        }

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
//        if (!TextUtils.isEmpty(postStr)) {
//            mWebView.postUrl(url.toString(), postStr.getBytes());
//        } else {
        mWebView.loadUrl(url.trim(), headMap); //运营中间输入空格，让他们自己改。
//        }
        LogUtils.e("mWebView url: ", url.trim());
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(UmengStat.LIVEPAGE);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(UmengStat.LIVEPAGE);
    }
}
