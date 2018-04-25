package com.svgouwu.client.activity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.kili.okhttp.OkHttpUtils;
import com.svgouwu.client.Api;
import com.svgouwu.client.BaseActivity;
import com.svgouwu.client.CommonFragmentActivity;
import com.svgouwu.client.MyApplication;
import com.svgouwu.client.R;
import com.svgouwu.client.bean.HttpResult;
import com.svgouwu.client.bean.UserBean;
import com.svgouwu.client.utils.CommonCallback;
import com.svgouwu.client.utils.CommonUtils;
import com.svgouwu.client.utils.LogUtils;
import com.svgouwu.client.utils.SpUtil;
import com.svgouwu.client.utils.ToastUtil;
import com.svgouwu.client.utils.UmengStat;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.Config;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_login_name)
    EditText etLoginName;
    @BindView(R.id.et_login_pwd)
    EditText etLoginPwd;
    @BindView(R.id.cb_login_save)
    CheckBox cbLoginSave;
    private UMShareAPI umShareAPI;

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    public void initViews() {
        initTopBar();
    }

    @Override
    public void initListener() {
    }

    @Override
    public void initData() {
        UMShareConfig umShareConfig = new UMShareConfig();
        umShareConfig.isNeedAuthOnGetUserInfo(true);
        umShareAPI = UMShareAPI.get(this);
        umShareAPI.setShareConfig(umShareConfig);
    }

    @OnClick({R.id.tv_right, R.id.tv_login_register_2, R.id.tv_login_find_pwd, R.id.iv_login_wx, R.id.iv_login_wb, R.id.iv_login_qq, R.id.bt_login_ok})
    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login_register_2:
            case R.id.tv_right:
                finish();
                CommonUtils.startAct(this, RegisterActivity.class);
                break;
            case R.id.tv_login_find_pwd:
                CommonUtils.startAct(this, CommonFragmentActivity.FRAGMENT_FORGOT_PWD);
                break;
            case R.id.iv_login_wx:
                umShareAPI.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.WEIXIN, umAuthListener);
                break;
            case R.id.iv_login_wb:
                Config.DEBUG = true;
                umShareAPI.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.SINA, umAuthListener);
                break;
            case R.id.iv_login_qq:
                umShareAPI.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.QQ, umAuthListener);
                break;
            case R.id.bt_login_ok:
                login();
                break;
        }
    }

    private void login() {
        String name = etLoginName.getText().toString().trim();
        String pwd = etLoginPwd.getText().toString().trim();

        if (CommonUtils.isEmpty(name)) {
            ToastUtil.toast("请输入用户名");
            return;
        }
        if (CommonUtils.isEmpty(pwd)) {
            ToastUtil.toast("请输入密码");
            return;
        }

        boolean isSevenDay = cbLoginSave.isChecked();

        String url = Api.URL_LOGIN;
        HashMap<String, String> params = new HashMap<>();
        params.put("user", name);
        params.put("pwd", pwd);
        params.put("free_landing", isSevenDay ? "1" : "0");
        OkHttpUtils.post().url(url).params(params).build().execute(new CommonCallback<UserBean>() {
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
            public void onResponse(HttpResult<UserBean> response) {

                ToastUtil.toast(response.msg);
                try {
                    if (!CommonUtils.isEmpty(response.msg))
//                        ToastUtil.toast(response.msg);

                        if (response.isSuccess()) {
                            setResult(RESULT_OK);
                            //登陆成功记录大礼包
                            SpUtil.setBoolean(mContext, "coupon", true);
                            finish();
                            MyApplication.getInstance().setLoginKey(response.data.token);
                            CommonUtils.hideInputMode(LoginActivity.this, true);
                        }

                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtil.toast(R.string.text_tips_server_issue);
                }
            }
        });

    }

    public void initTopBar() {
        TextView tv_title = findView(R.id.tv_title);
        TextView tv_right = findView(R.id.tv_right);
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText("注册");
        tv_title.setText("会员登录");

        findView(R.id.iv_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //授权开始的回调
            LogUtils.e("UMAuthListener onStart");
            ToastUtil.toast("授权开始");
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            ToastUtil.toast("授权成功");
            LogUtils.e("Authorize succeed" + platform.name() + "--action: " + action + "---" + data.toString());

            if (data != null) {
                if (platform != null && !CommonUtils.isEmpty(platform.name())) {
                    thirdLogin(data, platform.name().toLowerCase());
                }
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            LogUtils.e("Authorize fail: " + platform.name() + "--action: " + action);
            if ("QQ".equals(platform.name()) && t.getMessage().contains("没有安装应用")) {
                ToastUtil.toast("请安装QQ");
            } else if ("WEIXIN".equals(platform.name()) && t.getMessage().contains("没有安装应用")) {
                ToastUtil.toast("请安装微信");
            } else {
                ToastUtil.toast("授权失败");
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            ToastUtil.toast("登录取消");
        }
    };

    private void thirdLogin(Map<String, String> data, final String type) {
        final String openid = data.get("openid");

        String url = Api.URL_THIRD_LOGIN;
        HashMap<String, String> params = new HashMap<>();
        params.put("openid", openid);
        params.put("type", type);

        OkHttpUtils.post().url(url).params(params).build().execute(new CommonCallback<UserBean>() {
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
            public void onResponse(HttpResult<UserBean> response) {

                try {
                    if (!CommonUtils.isEmpty(response.msg))
                        ToastUtil.toast(response.msg);

                    if (response.isSuccess()) {
                        MyApplication.getInstance().setLoginKey(response.data.token);
                        setResult(RESULT_OK);
                        SpUtil.setBoolean(mContext, "coupon", true);//首页礼包相关
                        finish();
                    }

                    if (response.isNotBindPone()) {
                        finish();
                        // 授权成功，跳至绑定手机号界面
                        CommonUtils.startAct(getApplicationContext(), CommonFragmentActivity.FRAGMENT_BIND_PHONE);

                        Intent intent = new Intent(getApplicationContext(), CommonFragmentActivity.class);
                        intent.putExtra(CommonFragmentActivity.TARGET, CommonFragmentActivity.FRAGMENT_BIND_PHONE);
                        intent.putExtra("openid", openid);
                        intent.putExtra("type", type);
                        startActivity(intent);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtil.toast(R.string.text_tips_server_issue);
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(UmengStat.LOGINPAGE); //统计页面
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(UmengStat.LOGINPAGE);
    }
}
