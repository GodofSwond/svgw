package com.svgouwu.client.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bracode.ui.CaptureActivity;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kili.okhttp.OkHttpUtils;
import com.svgouwu.client.BaseTopFragment;
import com.svgouwu.client.CommonFragmentActivity;
import com.svgouwu.client.MyApplication;
import com.svgouwu.client.R;
import com.svgouwu.client.activity.CouponsCenterActivity;
import com.svgouwu.client.activity.GoodsDetailsWebView;
import com.svgouwu.client.activity.GoodsListActivity;
import com.svgouwu.client.activity.LoginActivity;
import com.svgouwu.client.bean.HomeBean;
import com.svgouwu.client.bean.HttpResult;
import com.svgouwu.client.bean.JsBridgeParam;
import com.svgouwu.client.bean.SpreedData;
import com.svgouwu.client.utils.CommonCallback;
import com.svgouwu.client.utils.CommonUtils;
import com.svgouwu.client.utils.CustomToast;
import com.svgouwu.client.utils.LogUtils;
import com.svgouwu.client.utils.SpUtil;
import com.svgouwu.client.utils.SystemHelper;
import com.svgouwu.client.utils.UmengStat;
import com.svgouwu.client.view.MyBridgeWebViewClient;
import com.svgouwu.client.view.MyJsBridgeWebView;
import com.svgouwu.client.view.SharePopupwindow;
import com.svgouwu.client.view.SpreedDialog;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.svgouwu.client.Api.URL_GOODS_DETAILS;
import static com.svgouwu.client.Api.URL_SPREED_DATA;
import static com.svgouwu.client.Api.URL_SPREED_RECEIVE;
import static com.svgouwu.client.MyApplication.getIPAddress;

/**
 * Created by topwolf on 2017/6/6.
 */
public class HomeFragment extends BaseTopFragment implements View.OnClickListener {

    private MyBridgeWebViewClient mBridgeWebViewClient;
    private ProgressBar mProgressBar;
    private HashMap<String, String> headMap = new HashMap<String, String>();
    private MyJsBridgeWebView mWebView;
    private String url;
    private boolean cpOutData = true;//礼包过期
    SharePopupwindow sharePopupwindow;
    ShareBoardConfig scf = new ShareBoardConfig();

    @BindView(R.id.ll_hf_coupon)
    LinearLayout ll_hf_coupon;//中间礼包展示
    @BindView(R.id.tv_hf_coupon)
    TextView tv_hf_coupon;//礼包状态文字
    @BindView(R.id.rl_home_title)//首页头
            RelativeLayout rl_home_title;
    @BindView(R.id.tv_home_news)
    TextView tv_home_news;//消息
    @BindView(R.id.rl_second_sharetop)//专题/二级页头
            RelativeLayout rl_second_sharetop;
    @BindView(R.id.tv_title)
    TextView tv_second_title;//二级页面标题
    @BindView(R.id.iv_top_right2)//二级页面菜单
            ImageView iv_top_right2;


    private boolean isSecond;//是否是二级页面
    private String secondShareUrl = "";
    private String mShareContent = "天天低价，全场包邮，全网精选好货，尽在四维购物！";
    String stype = "0";

    public HomeFragment() {

    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_home;
    }

    String act;

    @Override
    public void initViews() {
        mStatusBarView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        Bundle bundle = getArguments();
        if (bundle != null) {
            act = bundle.getString("act");
        }
        url = "https://www.svgouwu.com/mobile/index.php?app=default&act=" + (act != null ? act : "index_ceshi_gb") + "&sv=app";
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

        mWebView.setWebChromeClient(new HomeFragment.MyWebChromeClient());
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
                    Log.i("whq", "action: " + data);
                    if (params != null) {
                        if (("barcode").equals(params.action)) {
                           /* Intent loginIntent = new Intent(getActivity(), CaptureActivity.class);
                            startActivity(loginIntent);*/
                        } else if (("goodsDetail").equals(params.action)) {
                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put("goodid", params.ref_id);
                            MobclickAgent.onEvent(getContext(), UmengStat.HOMEPAGETODETAILSCLICKNUMBER, map);
                            Intent intent2 = new Intent(getActivity(), GoodsDetailsWebView.class);
                            intent2.putExtra("url", URL_GOODS_DETAILS + params.ref_id);
                            intent2.putExtra("goodsId", params.ref_id);
                            startActivity(intent2);
                        } else if (("category").equals(params.action)) {
                            MobclickAgent.onEvent(getContext(), UmengStat.HOMEPAGETOCLASSIFICATIONNUMBER);
                            Intent intent = new Intent(getActivity(), GoodsListActivity.class);
                            intent.putExtra("category", params.ref_id);
                            getActivity().startActivity(intent);
                        } else if (("coupons").equals(params.action)) {
                            MobclickAgent.onEvent(getContext(), UmengStat.HOMEPAGETOCLASSIFICATIONNUMBER);
                            Intent intent3 = new Intent(getActivity(), CouponsCenterActivity.class);
                            intent3.putExtra("coupons", "");
                            startActivity(intent3);
                        } else if (("searchIndex").equals(params.action)) {
                           /* Intent intent4 = new Intent(getContext(), CommonFragmentActivity.class);
                            intent4.putExtra(CommonFragmentActivity.TARGET, CommonFragmentActivity.FRAGMENT_SEARCH);
                            intent4.putExtra("searchIndex", "");
                            startActivity(intent4);*/
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
        //    CommonUtils.myTopBar(getActivity(), R.color.ic_yellow);
    //    getHomeBean();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        tv_home_news.setVisibility(GONE);
        SpreedData();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!cpOutData) {
            //礼包未过期
            if (SpUtil.getBoolean(getActivity(), "coupon")) {
                if (!CommonUtils.isLogin()) {
                    ll_hf_coupon.setVisibility(VISIBLE);
                    tv_hf_coupon.setText("领取礼包");
                    statenum = "0054";
                } else {
                    SpreedState();
                }
            } else {
                if (!CommonUtils.isLogin() && SpUtil.getBoolean(getContext(), "isout")) {
                    ll_hf_coupon.setVisibility(VISIBLE);
                    tv_hf_coupon.setText("领取礼包");
                    statenum = "0054";
                }
            }
        }
        MobclickAgent.onPageStart(UmengStat.STAYHOMEPAGE);
        CommonUtils.umTongJi(getContext(), getIPAddress(), "1", "0");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(UmengStat.STAYHOMEPAGE);
    }

    @OnClick({R.id.ll_hf_coupon, R.id.tv_home_search, R.id.tv_home_scan, R.id.tv_home_news,
            R.id.iv_left, R.id.iv_top_right2})
    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.iv_pic:
                break;
            case R.id.ll_hf_coupon:
                if (statenum.equals("0056") || statenum.equals("0055")) {
                    //去使用状态，跳转wap页
                    startActivity(new Intent(getActivity(), CommonFragmentActivity.class).putExtra(CommonFragmentActivity.TARGET, 14));
                } else {
                    //去领取,调用领取接口
                    if (CommonUtils.isLogin()) {
                        //登陆了
                        SpreedReceive();
                    } else {
                        //没登录
                        startActivityForResult(new Intent(getActivity(), LoginActivity.class), 10001);
                    }

                }
                break;
            case R.id.tv_home_search:
                //跳转搜索页
                Intent intent4 = new Intent(getActivity(), CommonFragmentActivity.class);
                intent4.putExtra(CommonFragmentActivity.TARGET, CommonFragmentActivity.FRAGMENT_SEARCH);
                intent4.putExtra("searchIndex", "");
                startActivity(intent4);
                break;
            case R.id.tv_home_scan:
                if (CommonUtils.checkLogin()) {
                    //登陆后才能进行扫码登陆
                    startActivity(new Intent(getActivity(), CaptureActivity.class));
                }
                break;
            case R.id.tv_home_news:
                //    CustomToast.toast(getActivity(), "弹出的消息了！");
                break;
            case R.id.iv_left:
                //二级页面返回
                if (act != null) {
                    stype = "3";
                    mWebView.loadUrl("https://www.svgouwu.com/mobile/index.php?app=default&act=index_ceshi_gb&sv=app".trim(), headMap);
                } else {
                    mWebView.goBack();
                }
                break;
            case R.id.iv_top_right2:
                //弹出菜单
                showMenu();
                break;
            case R.id.ll_share_menu_home:
                //返回首页
                hidMenu();
                if (act != null) {
                    stype = "3";
                    mWebView.loadUrl("https://www.svgouwu.com/mobile/index.php?app=default&act=index_ceshi_gb&sv=app".trim(), headMap);
                } else {
                    mWebView.goBack();
                }
                break;
            case R.id.ll_share_menu_share:
                //登陆条件下，分享弹窗
                if (CommonUtils.checkLogin()) {
                    shareSDKPopupwindow();
                }
                hidMenu();
                break;
            default:
                break;

        }
    }


    private void loadWebUrl() {
        mWebView.loadUrl(url.trim(), headMap); //运营中间输入空格，让他们自己改。
    }

    /**
     * 大礼包红包是否过期接口
     * 0052：活动未开始，0053：活动已结束，0054：活动进行
     */
    private void SpreedData() {
        String url = URL_SPREED_DATA;
        OkHttpUtils.post().url(url).params(null).build().execute(new CommonCallback<SpreedData>() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(HttpResult<SpreedData> response) {
                try {
                    if (response.code != null && response.code.equals("0054")) {
                        //礼包没过期
                        cpOutData = false;
                        if (CommonUtils.isLogin()) {
                            //登陆了
                            SpreedState();
                        } else {
                            //没登录
                            if (SpUtil.getBoolean(getActivity(), "coupon")) {
                                ll_hf_coupon.setVisibility(VISIBLE);
                                tv_hf_coupon.setText("领取礼包");
                                statenum = "0054";
                            } else {
                                SpreedDialoghf();
                            }
                        }
                    } else {
                        //礼包过期了，以后不显示
                        cpOutData = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 大礼包领取状态
     * 0054：未领取
     */
    private void SpreedState() {
        String url = URL_SPREED_DATA;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", MyApplication.getInstance().getLoginKey());
        ll_hf_coupon.setVisibility(VISIBLE);
        OkHttpUtils.post().url(url).params(params).build().execute(new CommonCallback<SpreedData>() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(HttpResult<SpreedData> response) {
                try {
                    if (response.code != null && response.code.equals("0054")) {
                        //未领取
                        statenum = response.code;
                        tv_hf_coupon.setText("领取礼包");
                    } else {
                        //领取了
                        statenum = response.code;
                        tv_hf_coupon.setText("使用礼包");
                        //    SpreedReceive();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 大礼包立即领取接口,返回状态
     * 0055：您已领取，0056：领取成功
     * 返回一个链接
     */
    private void SpreedReceive() {
        String url = URL_SPREED_RECEIVE;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", MyApplication.getInstance().getLoginKey());
        ll_hf_coupon.setVisibility(VISIBLE);
        OkHttpUtils.post().url(url).params(params).build().execute(new CommonCallback<SpreedData>() {
            @Override
            public void onError(Call call, Exception e) {
                tv_hf_coupon.setText("领取礼包");
                statenum = "0054";
            }

            @Override
            public void onResponse(HttpResult<SpreedData> response) {
                try {
                    if (response.code != null && response.code.equals("0056")) {
                        //恭喜领取成功
                        CustomToast.showToasts(getContext(), "领取成功",  R.drawable.hf_coupon_success);
                        tv_hf_coupon.setText("使用礼包");
                        statenum = response.code;
                        startActivity(new Intent(getActivity(), CommonFragmentActivity.class).putExtra(CommonFragmentActivity.TARGET, 14));
                    }
                    if (response.code != null && response.code.equals("0055")) {
                        //已领取过
                        tv_hf_coupon.setText("使用礼包");
                        statenum = response.code;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * 大礼包弹窗
     */
    String statenum = "0054";//状态码，默认0054
    SpreedDialog spreedDialog;

    private void SpreedDialoghf() {
        final View viewDialog = LayoutInflater.from(getActivity()).inflate(R.layout.homefragment_dialog, null, false);
        final SpreedDialog.Builder builder = new SpreedDialog.Builder(getActivity());
        spreedDialog = builder.setContentView(viewDialog, statenum)
                .setCancelListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //关闭弹窗,记录本地，下次不展示全屏
                        dialog.dismiss();
                        SpUtil.setBoolean(getActivity(), "coupon", true);
                        //展示中段dialog
                        ll_hf_coupon.setVisibility(VISIBLE);
                        tv_hf_coupon.setText("领取礼包");
                    }
                }).setReceiveListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SpUtil.setBoolean(getActivity(), "coupon", true);
                        if (spreedDialog != null) {
                            spreedDialog.dismiss();
                        }
                        //没登录，去登陆
                        startActivityForResult(new Intent(getActivity(), LoginActivity.class), 10001);

                    }
                }).create();
        spreedDialog.setCancelable(false);
        spreedDialog.show();


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 10001:
                SpreedReceive();
                break;
            default:
                break;
        }
    }


    /**
     * webview加载状态
     */

    private class MyWebChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            isSecond = mWebView.canGoBack();
            hidMenu();
            if (newProgress == 100) {
                mProgressBar.setVisibility(GONE);
                getShareUrl(mWebView.getUrl());
                if (act != null) {
                    if ("0".equals(stype)) {
                        showTopTitle(true);
                    } else {
                        showTopTitle(false);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                stype = "0";
                                if ("3".equals(stype)) {
                                    stype = "0";
                                }
                            }
                        }, 1000);
                    }

                } else {
                    showTopTitle(isSecond);
                }

            } else {
                if (mProgressBar.getVisibility() == GONE)
                    mProgressBar.setVisibility(VISIBLE);
                mProgressBar.setProgress(newProgress);

            }
            super.onProgressChanged(view, newProgress);

        }


    }

    /**
     * 拼接分享链接的接口
     *
     * @param surl
     */
    private void getShareUrl(String surl) {
        if(surl.contains("&sv=app")){
            String[] url = surl.split("&sv=app");
            secondShareUrl = url[0];
        }

    }

    /**
     * 首页和二级子页面展示头
     *
     * @param toptype
     */
    private void showTopTitle(boolean toptype) {
        if (toptype) {
            tv_second_title.setText("四维购物");
            rl_home_title.setVisibility(GONE);
            rl_second_sharetop.setVisibility(VISIBLE);
            iv_top_right2.setVisibility(VISIBLE);

        } else {
            rl_home_title.setVisibility(VISIBLE);
            rl_second_sharetop.setVisibility(GONE);
            iv_top_right2.setVisibility(GONE);

        }
    }

    /**
     * 展示菜单popupwindow
     */
    private void showMenu() {
        sharePopupwindow = new SharePopupwindow((Activity) getContext(), this);
        sharePopupwindow.showPopupWindow(iv_top_right2, "1");
    }

    /**
     * 隐藏弹窗
     */
    private void hidMenu() {
        if (sharePopupwindow != null) {
            sharePopupwindow.dismiss();
        }
    }

    /**
     * 第三方分享
     */
    private void shareSDKPopupwindow() {
        scf.setTitleText("~分享到~");
        scf.setIndicatorVisibility(false);//指示点
        scf.setShareboardPostion(scf.SHAREBOARD_POSITION_BOTTOM);
        new ShareAction(getActivity())
                .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA,
                        SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE)
                .addButton("umeng_sharebutton_custom", "umeng_sharebutton_custom",
                        "umeng_socialize_copyurl", "umeng_socialize_share_web")
                // 自定义按钮
                .setShareboardclickCallback(shareBoardlistener)
                .open(scf);

    }

    private ShareBoardlistener shareBoardlistener = new ShareBoardlistener() {

        @Override
        public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
            if (share_media == null) {
                //根据key来区分自定义按钮的类型，并进行对应的操作
                if (snsPlatform.mKeyword.equals("umeng_sharebutton_custom")) {
                    //复制商品详情分享地址
                    if(!CommonUtils.isEmpty(secondShareUrl)){
                        CommonUtils.copy(secondShareUrl, getContext());
                    }
                }

            } else {
                //社交平台的分享行为
                shareData(share_media, R.drawable.ic_splash11, secondShareUrl, "四维购物", mShareContent);
            }
        }
    };

    /**
     * 社交平台的分享行为
     *
     * @param share_media
     * @param imageUrl    --网络图片
     * @param wapUrl      --跳转的链接地址
     * @param title       --标题
     * @param description --描述
     */
    private void shareData(SHARE_MEDIA share_media, int imageUrl, String wapUrl,
                           String title, String description) {
        UMWeb web = new UMWeb(wapUrl);//链接地址
        //    if (!CommonUtils.isEmpty(imageUrl)) {
        UMImage image = new UMImage(getContext(), imageUrl);//网络图片
        image.compressStyle = UMImage.CompressStyle.SCALE;//压缩方式
        web.setThumb(image);
        //     }
        if (!CommonUtils.isEmpty(title)) {
            web.setTitle(title);
        }
        if (!CommonUtils.isEmpty(description)) {
            web.setDescription(description);
        }
        new ShareAction(getActivity())
                .setPlatform(share_media)
                .setCallback(ushareListener)
                //   .withText("多平台分享")
                .withMedia(web)
                .share();
    }

    private UMShareListener ushareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            CustomToast.toast(getContext(), "分享成功了~");
            Log.d("whq", "onsuccess===");
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            CustomToast.toast(getContext(), "分享失败了~");
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            Log.d("whq", "share=========cancel");
            CustomToast.toast(getContext(), "分享取消了~");
        }
    };

    private void getHomeBean() {
        String url = "http://www.svgouwu.com/index.php?app=tjw&act=get_data";
        HashMap<String, String> map = new HashMap<>();
        map.put("id", "60");
        OkHttpUtils.post().url(url).params(map).build().execute(new CommonCallback<HomeBean.DataBean>() {
            @Override
            public void onError(Call call, Exception e) {
                Log.d("whq", "==onError==" + e);
            }

            @Override
            public void onResponse(HttpResult<HomeBean.DataBean> response) {
                Log.d("whq","---success=="+response.data.get_$1());
            }


        });
    }
}
