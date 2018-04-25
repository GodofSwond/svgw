package com.svgouwu.client.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bracode.ui.CaptureActivity;
import com.bumptech.glide.Glide;
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
import com.svgouwu.client.bean.GoodDetailsInfo;
import com.svgouwu.client.bean.GoodDetailsShareInfo;
import com.svgouwu.client.bean.GoodsSpec;
import com.svgouwu.client.bean.HttpResult;
import com.svgouwu.client.bean.JsBridgeParam;
import com.svgouwu.client.bean.SpreedData;
import com.svgouwu.client.utils.CommonCallback;
import com.svgouwu.client.utils.CommonUtils;
import com.svgouwu.client.utils.CustomToast;
import com.svgouwu.client.utils.LogUtils;
import com.svgouwu.client.utils.SpUtil;
import com.svgouwu.client.utils.SystemHelper;
import com.svgouwu.client.utils.ToastUtil;
import com.svgouwu.client.utils.UmengStat;
import com.svgouwu.client.view.GoodsSpecDialog;
import com.svgouwu.client.view.LoadPage;
import com.svgouwu.client.view.MyBridgeWebViewClient;
import com.svgouwu.client.view.MyJsBridgeWebView;
import com.svgouwu.client.view.SharePopupwindow;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.OnClick;
import cn.magicwindow.mlink.annotation.MLinkRouter;
import okhttp3.Call;
import okhttp3.Request;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.svgouwu.client.Api.URL_GOODS_DETAILS;
import static com.svgouwu.client.MyApplication.getIPAddress;

/**
 * Created by whq on 2017/9/28.
 * 商品详情页头部原生，底部html
 */

@MLinkRouter(keys={"goodsDetailKey"})
public class GoodsDetailsWebView extends BaseActivity implements View.OnClickListener {

    private HashMap<String, String> headMap = new HashMap<String, String>();
    private MyJsBridgeWebView mWebView;
    private LinearLayout ll_web;
    private MyBridgeWebViewClient mBridgeWebViewClient;
    private ProgressBar mProgressBar;
    private GoodsSpec mGoodsSpec; //商品规格
    private LinearLayout ll_webview_goods_details_bottom;
    private WebSettings settings;
    private Dialog down2UpDialog;
    private CheckBox cb_goods_fav;
    private LoadPage web_mLoadPage;
    private ConvenientBanner web_bannerView;
    private GoodDetailsInfo gooddatainfo;
    private ImageView iv_right, iv_top_right2;

    private String url, from, mGoodsId, verCode,mRebate;
    private TextView tv_title, tv_gooddetail_name, tv_gooddetail_money, tv_gooddetail_number,
            tv_gooddetail_spelPrice, tv_gooddetail_ad, tv_gd_rebate;

    private List<GoodDetailsInfo.ImgsBean> imageMini;
    private List<String> imageList = new ArrayList<>();

    SharePopupwindow sharePopupwindow;
    ShareBoardConfig scf = new ShareBoardConfig();//分享
    boolean isSplash;//分享首页跳过来的
    @Override
    protected int getContentView() {
        return R.layout.activity_goodwebview;
    }

    @Override
    public void initViews() {

        ll_web = (LinearLayout) findViewById(R.id.ll_web);
        mWebView = (MyJsBridgeWebView) findViewById(R.id.webviewID);
        ll_webview_goods_details_bottom = (LinearLayout) findViewById(R.id.ll_webview_goods_details_bottom);//底部ll
        cb_goods_fav = findView(R.id.cb_goods_fav);//收藏状态按钮
        web_mLoadPage = (LoadPage) findViewById(R.id.web_mLoadPage);
        web_bannerView = (ConvenientBanner) findViewById(R.id.web_bannerView);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_gooddetail_name = (TextView) findViewById(R.id.tv_gooddetail_name);//商品名称
        tv_gooddetail_money = (TextView) findViewById(R.id.tv_gooddetail_money);//价格
        tv_gooddetail_spelPrice = (TextView) findViewById(R.id.tv_gooddetail_spelPrice);//活动价格
        tv_gooddetail_number = (TextView) findViewById(R.id.tv_gooddetail_number);//销售件数
        tv_gooddetail_ad = (TextView) findViewById(R.id.tv_gooddetail_ad);//地址
        iv_right = (ImageView) findViewById(R.id.iv_right);//分享
        iv_top_right2 = (ImageView) findViewById(R.id.iv_top_right2);//分享new
        tv_gd_rebate = (TextView) findViewById(R.id.tv_gd_rebate);//购物返利
    }

    @Override
    public void initData() {
        isSplash = getIntent().getBooleanExtra("isSplash",false);
        if(isSplash){
            GoBindagence();
        }
        tv_title.setText("商品详情");
        iv_right.setVisibility(VISIBLE);
        iv_top_right2.setVisibility(GONE);
        url = getIntent().getStringExtra("url");
        mGoodsId = getIntent().getStringExtra("goodsId");
        mProgressBar = mWebView.getProgressBar();
        verCode = SystemHelper.getAppVersionCode(getApplicationContext()) + "";
        headMap.put("version", verCode);
        headMap.put("client", "android");
        settings = mWebView.getSettings();
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

        mWebView.setWebChromeClient(new GoodsDetailsWebView.MyWebChromeClient());
        mBridgeWebViewClient = new MyBridgeWebViewClient(mWebView);
        mWebView.setWebViewClient(mBridgeWebViewClient);
        if (!CommonUtils.isEmpty(mGoodsId)) {
            ll_webview_goods_details_bottom.setVisibility(VISIBLE);

            //获取商品规格
            getGoodsSpec();
        } else {
            ll_webview_goods_details_bottom.setVisibility(GONE);
        }
        web_mLoadPage.setGetDataListener(new LoadPage.GetDataListener() {
            @Override
            public void onGetData() {
                web_mLoadPage.switchPage(LoadPage.STATE_LOADING);
                getGoodDetailsInfo();
            }
        });
        web_mLoadPage.switchPage(LoadPage.STATE_LOADING);
        getGoodDetailsInfo();
    }

    @Override
    public void initListener() {
        mWebView.registerHandler("submitFromWeb", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                try {
                    function.onCallBack("submitFromWeb exe, response url 中文 from Java");
                    data = data.replace("/", "");
                    JsBridgeParam params = new Gson().fromJson(data, new TypeToken<JsBridgeParam>() {
                    }.getType());

                    //login.search,goodsDetail,storeDetail,buyVoucher,orderList,buyMore,verifyPhone,html5,userCenter
                    if (params != null) {
                        if (("barcode").equals(params.action)) {
                            Intent loginIntent = new Intent(GoodsDetailsWebView.this, CaptureActivity.class);
                            startActivity(loginIntent);
                        } else if ("getSpec".equals(params.action)) {
                            //商品规格
                            showSpecDialog();
                        } else if (("goodsDetail").equals(params.action)) {
                            Intent intent2 = new Intent(GoodsDetailsWebView.this, WebViewActivity.class);
                            intent2.putExtra("url", URL_GOODS_DETAILS + params.ref_id);
                            intent2.putExtra("goodsId", params.ref_id);
                            startActivity(intent2);
                        } else if (("category").equals(params.action)) {
                            Intent intent = new Intent(GoodsDetailsWebView.this, GoodsListActivity.class);
                            intent.putExtra("category", params.ref_id);
                            startActivity(intent);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

    }

    private void showSpecDialog() {
        //弹出规格选择对话框
        if (mGoodsSpec == null) {
            ToastUtil.toast("商品规格异常，请稍后再试");
            return;
        }
        if (down2UpDialog == null) {
            down2UpDialog = new GoodsSpecDialog(GoodsDetailsWebView.this, mGoodsSpec, mGoodsId, mRebate);
        }
        down2UpDialog.show();
    }

    /**
     * 收藏状态接口
     */
    private void getFavState() {
        if (!CommonUtils.isLogin() || CommonUtils.isEmpty(mGoodsId)) {
            return;
        }
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


    /**
     * 收藏商品接口
     */
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

    /**
     * 获取商品规格接口
     */
    private void getGoodsSpec() {
        String url = Api.URL_GOODS_SPEC;
        HashMap<String, String> params = new HashMap<>();
        params.put("id", mGoodsId);
        OkHttpUtils.get().url(url).params(params).build().execute(new CommonCallback<GoodsSpec>() {
            @Override
            public void onBefore(Request request) {
                super.onBefore(request);
                //    weixinDialogInit(getString(R.string.dialog_process));
            }

            @Override
            public void onAfter() {
                super.onAfter();
                //    cancelWeiXinDialog();
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
                        if(!"0".equals(mGoodsSpec.shareRebate) && !"0.0".equals(mGoodsSpec.shareRebate)
                                && !"0.00".equals(mGoodsSpec.shareRebate)){
                            mRebate = mGoodsSpec.shareRebate;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtil.toast(R.string.text_tips_server_issue);
                }
            }
        });
    }

    @OnClick({R.id.iv_web_view_back, R.id.cb_goods_fav, R.id.tv_goods_detail_add_cart,
            R.id.tv_goods_detail_buy, R.id.iv_left, R.id.iv_right, R.id.iv_top_right2,})
    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.iv_web_view_back:
                finish();
                break;
            case R.id.cb_goods_fav:
                //收藏
                if (!CommonUtils.checkLogin()) {
                    cb_goods_fav.setChecked(false);
                    return;
                }
                favGoods(cb_goods_fav.isChecked());
                break;
            case R.id.iv_left:
                if(getIntent().getBooleanExtra("mlink", false)){
                    startActivity(new Intent(mContext, MainActivity.class).putExtra("index", 1));
                }
                finish();
                break;
            case R.id.tv_goods_detail_add_cart:
            case R.id.tv_goods_detail_buy:
                //购买
                showSpecDialog();
                break;
            case R.id.iv_right:
                //登陆条件下，分享弹窗
                if (CommonUtils.checkLogin()) {
                    shareSDKPopupwindow();
                }
                break;
            case R.id.iv_top_right2:
                //展示菜单弹窗
                //   showMenu();
                break;
            case R.id.ll_share_menu_news:
                //消息跳转
               /* CustomToast.toast(mContext, "消息跳转");
                hidMenu();*/
                break;
            case R.id.ll_share_menu_home:
                //跳转首页
              /*  CustomToast.toast(mContext, "首页");
                hidMenu();
                startActivity(new Intent(mContext, MainActivity.class).putExtra("index", 1));
                finish();*/
                break;
            default:
                break;
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

        @Override
        public void onCloseWindow(WebView window) {

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
            ll_web.addView(mCustomView);
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
            ll_web.removeView(mCustomView);
            mCustomViewCallback.onCustomViewHidden();
            mCustomView = null;
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            super.onHideCustomView();
        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();// 返回前一个页面
            return true;
        }
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
        //防止内存泄漏
        UMShareAPI.get(GoodsDetailsWebView.this).release();
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
            getGoodDetailsInfo();
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
        mWebView.onPause();
        MobclickAgent.onPageStart(UmengStat.STAYGOODSDETAILS);
    }

    @Override
    public void onResume() {
        super.onResume();
        mWebView.onResume();
        //获取收藏状态
        getFavState();
        MobclickAgent.onPageEnd(UmengStat.STAYGOODSDETAILS);
        CommonUtils.umTongJi(mContext, getIPAddress(), "5", "0");
        getGoodDetailsShare();

    }

    /**
     * 商品详情接口
     */
    private void getGoodDetailsInfo() {
        OkHttpUtils.get().url(url).params(null).build().execute(new CommonCallback<GoodDetailsInfo>() {
            @Override
            public void onError(Call call, Exception e) {
                web_mLoadPage.switchPage(LoadPage.STATE_NO_NET);
            }

            @Override
            public void onResponse(HttpResult<GoodDetailsInfo> response) {
                try {
                    if (response.isSuccess()) {
                        web_mLoadPage.switchPage(LoadPage.STATE_HIDE);
                        gooddatainfo = response.data;
                        imageMini = gooddatainfo.getImgs();
                        if (null != imageMini && !imageMini.isEmpty()) {
                            for (int i = 0; i < imageMini.size(); i++) {
                                GoodDetailsInfo.ImgsBean imgsBean = imageMini.get(i);
                                imageList.add(imgsBean.getMini_imageUrl());
                            }
                        }
                        fillData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    web_mLoadPage.switchPage(LoadPage.STATE_LOADING);
                }
            }
        });
    }

    /**
     * 商品详情分享接口
     */
    GoodDetailsShareInfo shareInfo;
    private String mImageUrl, mWapUrl, mMhareGoodName;
    private String mShareContent = "天天低价，全场包邮，全网精选好货，尽在四维购物！";

    private void getGoodDetailsShare() {
        HashMap<String, String> map = new HashMap<>();
        map.put("goodid", mGoodsId);
        map.put("token", MyApplication.getInstance().getLoginKey());
        OkHttpUtils.post().url(Api.URL_GOODSHARE).params(map).build().execute(new CommonCallback<GoodDetailsShareInfo>() {
            @Override
            public void onError(Call call, Exception e) {
            }

            @Override
            public void onResponse(HttpResult<GoodDetailsShareInfo> response) {
                try {
                    if (response.isSuccess()) {
                        shareInfo = response.data;
                        if (shareInfo != null) {
                            fillShareData();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 分享绑定关系
     */
    private void GoBindagence(){
        HashMap<String, String> map = new HashMap<>();
        map.put("token", MyApplication.getInstance().getLoginKey());
        map.put("code", SpUtil.getString(mContext,"coded"));
        OkHttpUtils.post().url(Api.URL_BINDAGENCY).params(map).build().execute(new CommonCallback<SpreedData>() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(HttpResult<SpreedData> response) {
                try {
                    Log.d("whq","--bindagency---"+response.msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 商品详情data
     */
    private void fillData() {
        if (imageList != null && imageList.size() > 0) {
            web_bannerView.setPages(new CBViewHolderCreator() {
                @Override
                public Object createHolder() {
                    return new GoodsDetailsWebView.LocalImagesHolderView();
                }
            }, imageList).setPageIndicator(new int[]{R.drawable.web_circular_selected_focus, R.drawable.web_circular_selected});
        }
        web_bannerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //  Toast.makeText(mContext, "第" + position + "张", Toast.LENGTH_LONG).show();
            }
        });
        mWebView.loadDataWithBaseURL(null, getNetContent(gooddatainfo.getDescription() + gooddatainfo.getQrcode()), "text/html", "utf-8", null);
        tv_gooddetail_name.setText(gooddatainfo.getGoodsName());
        if (!CommonUtils.isEmpty(gooddatainfo.getPrice().getActivePrice())) {
            //如果有活动价格
            tv_gooddetail_money.setText(CommonUtils.getCurrencySign() + gooddatainfo.getPrice().getActivePrice());
            tv_gooddetail_spelPrice.setText(CommonUtils.getCurrencySign() + gooddatainfo.getPrice().getPrice());
            tv_gooddetail_spelPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            tv_gooddetail_money.setText(CommonUtils.getCurrencySign() + gooddatainfo.getPrice().getPrice());
        }
        tv_gooddetail_number.setText("销售件数: " + gooddatainfo.getSales());
        tv_gooddetail_ad.setText(gooddatainfo.getRegionName());

        if(gooddatainfo != null && !CommonUtils.isEmpty(gooddatainfo.getShareRebate())
                &&!"0".equals(gooddatainfo.getShareRebate()) && !"0.0".equals(gooddatainfo.getShareRebate())
                && !"0.00".equals(gooddatainfo.getShareRebate())){
            tv_gd_rebate.setVisibility(VISIBLE);
            tv_gd_rebate.setText("购物返四维币" + gooddatainfo.getShareRebate() + "元");
        }
    }

    /**
     * 商品详情分享data
     */
    private void fillShareData() {
        mImageUrl = shareInfo.getDefaultImage();//图片地址
        mWapUrl = shareInfo.getUrl();//分享链接地址
        mMhareGoodName = shareInfo.getGoodsName();//分享商品名称

    }

    private class LocalImagesHolderView implements Holder<String> {

        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            Glide.with(mContext).load(data).into(imageView);
        }
    }

    private String getNetContent(String ss) {
        Document doc = Jsoup.parse(ss);
        Elements elements = doc.getElementsByTag("img");
        for (Element element : elements) {
            element.attr("width", "100%").attr("height", "auto");
        }
        return doc.toString();
    }

    /**
     * 第三方分享
     */
    private void shareSDKPopupwindow() {
        scf.setTitleText("~分享到~");
        scf.setIndicatorVisibility(false);//指示点
        new ShareAction(GoodsDetailsWebView.this)
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
                    CommonUtils.copy(mWapUrl, mContext);
                }

            } else {
                //社交平台的分享行为
                shareData(share_media, mImageUrl, mWapUrl, mMhareGoodName, mShareContent);
            }
        }
    };
    private UMShareListener ushareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            CustomToast.toast(mContext, "分享成功了~");
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            CustomToast.toast(mContext, "分享失败了~");
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            CustomToast.toast(mContext, "分享取消了~");
            Log.d("whq","sharedetail=========cancel");
        }
    };

    /**
     * 展示菜单popupwindow
     */
    private void showMenu() {
        sharePopupwindow = new SharePopupwindow(GoodsDetailsWebView.this, this);
        sharePopupwindow.showPopupWindow(iv_top_right2, "0");
    }

    /**
     * 隐藏弹窗
     */
    private void hidMenu() {
        if (sharePopupwindow != null) {
            sharePopupwindow.dismiss();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 社交平台的分享行为
     *
     * @param share_media
     * @param imageUrl    --网络图片
     * @param wapUrl      --跳转的链接地址
     * @param title       --标题
     * @param description --描述
     */
    private void shareData(SHARE_MEDIA share_media, String imageUrl, String wapUrl,
                           String title, String description) {
        UMWeb web = new UMWeb(wapUrl);//链接地址
        if (!CommonUtils.isEmpty(imageUrl)) {
            UMImage image = new UMImage(mContext, imageUrl);//网络图片
            image.compressStyle = UMImage.CompressStyle.SCALE;//压缩方式
            web.setThumb(image);
        }
        if (!CommonUtils.isEmpty(title)) {
            web.setTitle(title);
        }
        if (!CommonUtils.isEmpty(description)) {
            web.setDescription(description);
        }
        new ShareAction(GoodsDetailsWebView.this)
                .setPlatform(share_media)
                .setCallback(ushareListener)
                //   .withText("多平台分享")
                .withMedia(web)
                .share();
    }
}
