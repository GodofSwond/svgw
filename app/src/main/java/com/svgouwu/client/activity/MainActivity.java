package com.svgouwu.client.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;

import com.kili.okhttp.OkHttpUtils;
import com.svgouwu.client.Api;
import com.svgouwu.client.BaseActivity;
import com.svgouwu.client.Constant;
import com.svgouwu.client.MyApplication;
import com.svgouwu.client.R;
import com.svgouwu.client.bean.HttpResult;
import com.svgouwu.client.bean.VersionInfo;
import com.svgouwu.client.fragment.CartFragment;
import com.svgouwu.client.fragment.CategoryFragment;
import com.svgouwu.client.fragment.HomeFragment;
import com.svgouwu.client.fragment.PersonalFragment;
import com.svgouwu.client.fragment.VideoFragment;
import com.svgouwu.client.utils.CommonCallback;
import com.svgouwu.client.utils.CommonUtils;
import com.svgouwu.client.utils.DeviceUuidUtil;
import com.svgouwu.client.utils.LogUtils;
import com.svgouwu.client.utils.NetWorkUtils;
import com.svgouwu.client.utils.SpUtil;
import com.svgouwu.client.utils.SystemHelper;
import com.svgouwu.client.utils.ToastUtil;
import com.svgouwu.client.view.UpdateManager;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.utils.Log;

import java.util.HashMap;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import cn.magicwindow.MLinkAPIFactory;
import cn.magicwindow.mlink.annotation.MLinkDefaultRouter;
import okhttp3.Call;

@MLinkDefaultRouter
public class MainActivity extends BaseActivity {

    public static final String TAG = "MainActivity";

    public static final int FRAGMENT_HOME = 1;
    private static final int FRAGMENT_CATEGORY = 2;
    public static final int FRAGMENT_VIDEO = 3;
    public static final int FRAGMENT_CART = 4;
    public static final int FRAGMENT_PERSONAL = 5;

    public int current_page = 1;
    private FragmentManager fragmentManager;

    private Fragment homeFragment;
    private Fragment categoryFragment;
    private Fragment videoFragment;
    private Fragment cartFragment;
    private Fragment personalFragment;

    private RadioButton rb_home, rb_category, rb_video, rb_cart, rb_personal;

    // 定义一个变量，来标识是否退出
    private static boolean isExit = false;
    private String act;//分享参数

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews() {
        fragmentManager = getSupportFragmentManager();

        rb_home = findView(R.id.rb_home);
        rb_category = findView(R.id.rb_category);
        rb_video = findView(R.id.rb_video);
        rb_cart = findView(R.id.rb_cart);
        rb_personal = findView(R.id.rb_personal);
        MLinkAPIFactory.createAPI(this).deferredRouter();
        act = getIntent().getStringExtra("act");
        initState();

        jpushinit();//极光推送
    }

    /**
     * 极光推送设置
     */
    private void jpushinit() {
        JPushInterface.setDebugMode(false);
        JPushInterface.init(this);

        JPushInterface.setAlias(this, "svgw", //别名
                new TagAliasCallback() {//回调接口,i=0表示成功,其它设置失败
                    @Override
                    public void gotResult(int i, String s, Set<String> set) {
                        Log.d("alias", "set alias result is" + i);
                    }
                });
    }


    @Override
    public void initListener() {
        rb_home.setOnClickListener(this);
        rb_category.setOnClickListener(this);
        rb_video.setOnClickListener(this);
        rb_cart.setOnClickListener(this);
        rb_personal.setOnClickListener(this);
    }

    @Override
    public void initData() {
        checkAppVersion();
        showFragment(FRAGMENT_HOME);
    }

    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.rb_home:
                showFragment(FRAGMENT_HOME);
//                rb_home.setChecked(true);
                break;
            case R.id.rb_category:
                showFragment(FRAGMENT_CATEGORY);
//                rb_category.setChecked(true);
                break;
            case R.id.rb_video:
                showFragment(FRAGMENT_VIDEO);
//                rb_video.setChecked(true);
                break;
            case R.id.rb_cart:
                showFragment(FRAGMENT_CART);
//                rb_cart.setChecked(true);
                break;
            case R.id.rb_personal:
                showFragment(FRAGMENT_PERSONAL);
//                rb_personal.setChecked(true);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            ToastUtil.toast(R.string.text_click_again_exit);
            // 利用handler延迟发送更改状态信息
            MyApplication.getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isExit = false;
                }
            }, 2000);
        } else {
            finish();
            SpUtil.setBoolean(mContext, "coupon", false);//礼包相关
            SpUtil.setBoolean(mContext, "isout", false);
            Log.d("whq", "-----coded--" + SpUtil.getString(mContext, "coded"));
            if (!CommonUtils.isEmpty(SpUtil.getString(mContext, "coded"))) {
                SpUtil.setString(mContext, "coded", "");
            }
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    public void showFragment(int type) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        current_page = type;
        Bundle bundle = new Bundle();
        switch (type) {
            case FRAGMENT_HOME:
                if (homeFragment == null) {
                    homeFragment = HomeFragment.newInstance();
                    if (act != null) {
                        bundle.putString("act", act);
                        act = null;
                        homeFragment.setArguments(bundle);
                    }
                    transaction.add(R.id.fl_container, homeFragment);
                } else {
                    if (act != null) {
                        homeFragment = HomeFragment.newInstance();
                        bundle.putString("act", act);
                        act = null;
                        homeFragment.setArguments(bundle);
                        transaction.add(R.id.fl_container, homeFragment);
                    }
                    transaction.show(homeFragment);
                }
                rb_home.setChecked(true);
                break;
            case FRAGMENT_CATEGORY:
                if (categoryFragment == null) {
                    categoryFragment = CategoryFragment.newInstance();
                    transaction.add(R.id.fl_container, categoryFragment);
                } else {
                    transaction.show(categoryFragment);
                }
                rb_category.setChecked(true);
                break;
            case FRAGMENT_VIDEO:
                if (videoFragment == null) {
                    videoFragment = VideoFragment.newInstance();
                    transaction.add(R.id.fl_container, videoFragment);
                } else {
                    transaction.show(videoFragment);
                }
                rb_video.setChecked(true);
                break;
            case FRAGMENT_CART:
                if (CommonUtils.checkLogin()) {
                    if (cartFragment == null) {
                        cartFragment = CartFragment.newInstance();
                        transaction.add(R.id.fl_container, cartFragment);
                    } else {
                        transaction.show(cartFragment);
                    }
                }
                rb_cart.setChecked(true);
                break;
            case FRAGMENT_PERSONAL:
                if (personalFragment == null) {
                    personalFragment = PersonalFragment.newInstance();
                    transaction.add(R.id.fl_container, personalFragment);
                } else {
//                    personalFragment.isCheckLogin();
                    transaction.show(personalFragment);
                }
                rb_personal.setChecked(true);
                break;
        }

//        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.commitAllowingStateLoss();
    }

    /**
     * 隐藏所有的FRAGMENT
     *
     * @param transaction 用于对FRAGMENT进行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (categoryFragment != null) {
            transaction.hide(categoryFragment);
        }
        if (videoFragment != null) {
            transaction.hide(videoFragment);
        }
        if (cartFragment != null) {
            transaction.hide(cartFragment);
        }
        if (personalFragment != null) {
            transaction.hide(personalFragment);
        }
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        if (homeFragment == null && fragment instanceof HomeFragment)
            homeFragment = fragment;
        if (categoryFragment == null && fragment instanceof CategoryFragment)
            categoryFragment = fragment;
        if (videoFragment == null && fragment instanceof VideoFragment)
            videoFragment = fragment;
        if (cartFragment == null && fragment instanceof CartFragment)
            cartFragment = fragment;
        if (personalFragment == null && fragment instanceof PersonalFragment)
            personalFragment = fragment;
        super.onAttachFragment(fragment);
    }


    /**
     * check update
     */
    public void checkAppVersion() {
        String url = Api.URL_CHECK_VERSION;
        HashMap<String, String> params = new HashMap<>();
        params.put("build", SystemHelper.getAppVersionCode(this) + "");
        params.put("appVersion", SystemHelper.getAppVersionName(getApplicationContext()));
        params.put("appChannel", Constant.CHANNEL);
        params.put("networkModel", NetWorkUtils.getAPNType(MainActivity.this) + "");
        params.put("os", "android");
        params.put("osVersion", Build.VERSION.SDK);//系统版本"
        params.put("model", Build.MODEL);//"手机型号",
        //Devices info
        params.put("deviceId", DeviceUuidUtil.getDeviceId(getApplicationContext()));
        params.put("androidId", DeviceUuidUtil.getAndroidId(getApplicationContext()));
        params.put("uuid", DeviceUuidUtil.getRandomUuid(getApplicationContext()));
        params.put("key", MyApplication.getInstance().getLoginKey());

        OkHttpUtils.post().url(url).params(params).build().execute(new CommonCallback<VersionInfo>() {
            @Override
            public void onError(Call call, Exception e) {
                LogUtils.e(e.toString());
            }

            @Override
            public void onResponse(HttpResult<VersionInfo> response) {

                try {
                    if (!response.isSuccess()) {
                        ToastUtil.toast(response.msg);
                        return;
                    }
                    final VersionInfo result = response.data;
                    if (result != null) {
                        //TODO
//                        result.isForce = 0;
                        if (result.isForce == 1 || result.isUpdate == 1) {
                            UpdateManager mUpdateManager = new UpdateManager(MainActivity.this, result);
                            mUpdateManager.checkUpdateInfo();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int index = intent.getIntExtra("index", -1);
        act = intent.getStringExtra("act");
        if (index == FRAGMENT_HOME) {
            showFragment(FRAGMENT_HOME);
            rb_home.setChecked(true);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(mContext).onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 沉浸式状态栏
     */
    private void initState() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS); //透明导航栏
            //    getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        }
    }
}
