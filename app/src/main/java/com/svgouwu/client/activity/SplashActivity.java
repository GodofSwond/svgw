package com.svgouwu.client.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.svgouwu.client.BaseActivity;
import com.svgouwu.client.MyApplication;
import com.svgouwu.client.R;
import com.svgouwu.client.utils.SpUtil;
import com.umeng.socialize.Config;
import com.umeng.socialize.UMShareAPI;

import java.util.Map;

import cn.magicwindow.MLinkAPIFactory;
import cn.magicwindow.MWConfiguration;
import cn.magicwindow.MagicWindowSDK;
import cn.magicwindow.mlink.MLinkCallback;
import cn.magicwindow.mlink.YYBCallback;

import static com.svgouwu.client.Api.URL_GOODS_DETAILS;
import static com.svgouwu.client.Constant.H5_BASE_URL;

/**
 * Created by topwolf on 2017/6/6.
 */

public class SplashActivity extends BaseActivity {

    private ImageView iv_welcome;
    //使用SharedPreferences来记录程序的使用次数
    SharedPreferences sharedPreferences;
    private int count;

    @Override
    protected int getContentView() {
        return R.layout.activity_splash;
    }

    @Override
    public void initViews() {
        Config.DEBUG = true;
        UMShareAPI.get(this);
        MWConfiguration config = new MWConfiguration(this);
        config.setLogEnable(true);//打开魔窗信息
        MagicWindowSDK.initSDK(config);
        //      MLinkAPIFactory.createAPI(this).registerWithAnnotation(this);
        register(SplashActivity.this);
        //跳转router 调用
        if (getIntent().getData() != null) {
            //qq跳转
            MLinkAPIFactory.createAPI(this).router(this, getIntent().getData());
            Log.d("whq", "qq====" + getIntent().getStringExtra("id"));
            finish();
        } else {
            //微信跳转
            MLinkAPIFactory.createAPI(this).checkYYB(SplashActivity.this, new YYBCallback() {
                @Override
                public void onFailed(Context context) {
                    jumpNext();
                    Log.d("whq", "weixin===1111=onFailed" + getIntent().getStringExtra("param"));
                }

                @Override
                public void onSuccess() {
                    Log.d("whq", "weixin===2222=success");
                    finish();
                }
            });
        }


        iv_welcome = (ImageView) findViewById(R.id.iv_welcome);
        iv_welcome.setImageDrawable(getResources().getDrawable(R.drawable.ic_splash1801));
        //读取SharedPreferences中需要的数据
        sharedPreferences = getSharedPreferences("count", MODE_WORLD_READABLE);
        count = sharedPreferences.getInt("count", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //存入数据
        editor.putInt("count", ++count);
        Log.i("TAG", "进入次数: " + count);
        //提交修改
        editor.commit();
        MLinkAPIFactory.createAPI(this).deferredRouter();
    }

    private void register(Context context) {

        MLinkAPIFactory.createAPI(context).registerDefault(new MLinkCallback() {
            @Override
            public void execute(Map<String, String> paramMap, Uri uri, Context context) {
                //HomeActivity 为你的首页
                if (count != 1) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            .putExtra("index", 1)
                            .putExtra("spCode", paramMap.get("coded"))
                            .putExtra("act", paramMap.get("id")));

                    //      MLinkIntentBuilder.buildIntent(paramMap, context, MainActivity.class);
                    finish();
                }


            }
        });
        // mLinkKey: mLink 的 key, mLink 的唯一标识
        MLinkAPIFactory.createAPI(context).register("goodsDetailKey", new MLinkCallback() {
            public void execute(Map<String, String> paramMap, Uri uri, Context context) {
                startActivity(new Intent(SplashActivity.this, GoodsDetailsWebView.class)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        .putExtra("isSplash", true)
                        .putExtra("goodsId", paramMap.get("id"))
                        .putExtra("url", URL_GOODS_DETAILS + paramMap.get("id"))
                        .putExtra("mlink", true));
                for (Map.Entry<String, String> param : paramMap.entrySet()) {
                    String s = param.getKey() + "----" + param.getValue();
                    Log.d("whq", "-----0000--" + s);
                }
                SpUtil.setString(mContext, "coded", paramMap.get("coded") != null ? paramMap.get("coded") : "");
                Log.d("whq", "-----0000--" + SpUtil.getString(mContext, "coded"));
                finish();

            }
        });
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
//        checkAppVersion();  //检测新版本

       /* MyApplication.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //判断程序与第几次运行，如果是第一次运行则跳转到引导页面
                if (count == 1) {
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), GuideActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent it = new Intent();
                    //        it.setClass(SplashActivity.this, WebViewActivity.class);
                    it.setClass(SplashActivity.this, MainActivity.class);
                    //        it.putExtra("url", "https://wxn.qq.com/cmsid/WXN2017062803464500");
                    it.putExtra("url", H5_BASE_URL);
                    startActivity(it);
                    finish();
                }
            }
        }, 2000);*/
    }

    @Override
    public void processClick(View view) {

    }

    private void enterMain() {
        Intent it = new Intent();
//        it.setClass(SplashActivity.this, WebViewActivity.class);
        it.setClass(SplashActivity.this, GuideActivity.class);
//        it.putExtra("url", "https://wxn.qq.com/cmsid/WXN2017062803464500");
        it.putExtra("url", H5_BASE_URL);
        startActivity(it);
        finish();
    }

    private void jumpNext() {
        MyApplication.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //判断程序与第几次运行，如果是第一次运行则跳转到引导页面
                if (count == 1) {
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), GuideActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent it = new Intent();
                    //        it.setClass(SplashActivity.this, WebViewActivity.class);
                    it.setClass(SplashActivity.this, MainActivity.class);
                    //        it.putExtra("url", "https://wxn.qq.com/cmsid/WXN2017062803464500");
                    it.putExtra("url", H5_BASE_URL);
                    startActivity(it);
                    finish();
                }
            }
        }, 1000);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.setIntent(intent);
        Uri mLink = intent.getData();
        if (mLink != null) {
            MLinkAPIFactory.createAPI(this).router(mLink);
        } else {
            MLinkAPIFactory.createAPI(this).checkYYB();
        }
    }
}
