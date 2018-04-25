package com.svgouwu.client.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.svgouwu.client.BaseFragment;
import com.svgouwu.client.R;
import com.svgouwu.client.utils.UmengStat;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by Administrator on 2017/9/8.
 */

public class DoRuleFragment extends Fragment {

    public WebView wv_doRule;

    private WebSettings webSettings;
    private LayoutInflater inflater;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;
        View rootView = inflater.inflate(R.layout.fragment_dorule, null);
        TextView titleTv = (TextView) rootView.findViewById(R.id.tv_title);
        titleTv.setText("使用优惠券");
        rootView.findViewById(R.id.iv_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        initWebView(rootView);
        return rootView;
    }

    public void initWebView(View rootView) {
        String url = "https://www.svgouwu.com/mobile/index.php?app=default&act=app_couponrule";
        wv_doRule = (WebView) rootView.findViewById(R.id.wv_doRule);
        wv_doRule.setFocusable(false);
        wv_doRule.loadUrl(url);
        webSettings = wv_doRule.getSettings();
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setBlockNetworkImage(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        wv_doRule.setWebViewClient(new DoRuleWebViewClient());
    }

    private class DoRuleWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            webSettings.setBlockNetworkImage(true);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return true;
        }
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(UmengStat.COUPONSRULEPAGE); //统计页面
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(UmengStat.COUPONSRULEPAGE);
    }
}
