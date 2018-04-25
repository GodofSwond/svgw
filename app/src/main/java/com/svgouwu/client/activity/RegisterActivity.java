package com.svgouwu.client.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.kili.okhttp.OkHttpUtils;
import com.svgouwu.client.Api;
import com.svgouwu.client.BaseActivity;
import com.svgouwu.client.CommonFragmentActivity;
import com.svgouwu.client.Constant;
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

/**
 * 用户注册页面
 */
public class RegisterActivity extends BaseActivity {
    @BindView(R.id.tv_register_get_code)
    TextView tvRegisterGetCode;
    @BindView(R.id.et_register_phone)
    EditText etRegisterPhone;
    @BindView(R.id.et_register_code)
    EditText etRegisterCode;
    @BindView(R.id.et_register_pwd)
    EditText etRegisterPwd;
    @BindView(R.id.et_register_pwd_again)
    EditText etRegisterPwdAgain;
    @BindView(R.id.et_register_excode)
    EditText et_register_excode;

    private UMShareAPI umShareAPI;
    private CountDownTimer countDownTimer = new CountDownTimer(Constant.SEND_CODE_TIME, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            tvRegisterGetCode.setEnabled(false);
            tvRegisterGetCode.setText(millisUntilFinished / 1000 + "s");
        }

        @Override
        public void onFinish() {
            tvRegisterGetCode.setEnabled(true);
            tvRegisterGetCode.setText("重新获取");
        }
    };

    @Override
    protected int getContentView() {
        return R.layout.activity_register;
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

    @OnClick({R.id.tv_right, R.id.iv_login_wx, R.id.iv_login_wb, R.id.iv_login_qq, R.id.tv_register_get_code, R.id.bt_register_ok})
    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
                finish();
                CommonUtils.startAct(getApplicationContext(), LoginActivity.class);
                break;
            case R.id.iv_login_wx:
                umShareAPI.getPlatformInfo(RegisterActivity.this, SHARE_MEDIA.WEIXIN, umAuthListener);
                break;
            case R.id.iv_login_wb:
                Config.DEBUG = true;
                UMShareAPI.get(RegisterActivity.this).getPlatformInfo(RegisterActivity.this, SHARE_MEDIA.SINA, umAuthListener);
                break;
            case R.id.iv_login_qq:
                umShareAPI.getPlatformInfo(RegisterActivity.this, SHARE_MEDIA.QQ, umAuthListener);
                break;
            case R.id.tv_register_get_code:
                sendCode();
                break;
            case R.id.bt_register_ok:
                register();
                break;
        }
    }

    private void register() {
        if (!isPhoneOk()) return;
        String code = etRegisterCode.getText().toString().trim();
        String pwd = etRegisterPwd.getText().toString().trim();
        String excode = et_register_excode.getText().toString().trim();
        final String rePwd = etRegisterPwdAgain.getText().toString().trim();

        if (CommonUtils.isEmpty(code)) {
            ToastUtil.toast("请输入验证码");
            return;
        }
        if (CommonUtils.isEmpty(pwd)) {
            ToastUtil.toast("请输入密码");
            return;
        }
        if (CommonUtils.isEmpty(rePwd)) {
            ToastUtil.toast("请输入确认密码");
            return;
        }

        if (!pwd.equals(rePwd)) {
            ToastUtil.toast("两次密码不一致");
            return;
        }

        if (pwd.length() < 6 || pwd.length() > 32) {
            ToastUtil.toast("请输入6-32位密码");
            return;
        }

        String url = Api.URL_REGISTER;
        HashMap<String, String> params = new HashMap<>();
        params.put("pwd1", pwd);
        params.put("pwd2", rePwd);
        params.put("code", code);
        params.put("mobile", mPhone);
        params.put("coded", excode);
        OkHttpUtils.post().url(url).params(params).build().execute(new CommonCallback<Object>() {
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
            }

            @Override
            public void onResponse(HttpResult response) {

                try {
                    if (!CommonUtils.isEmpty(response.msg))
                        ToastUtil.toast(response.msg);

                    if (response.isSuccess()) {
                        finish();
                        MobclickAgent.onEvent(mContext, UmengStat.REGISTRATIONSTATISTICS);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

    private void sendCode() {
        if (!isPhoneOk()) return;

        String url = Api.URL_SEND_CODE;
        HashMap<String, String> params = new HashMap<>();
        params.put("from", "register");
        params.put("mobile", mPhone);
        OkHttpUtils.post().url(url).params(params).build().execute(new CommonCallback<Object>() {
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
            }

            @Override
            public void onResponse(HttpResult response) {

                try {
                    if (!response.isSuccess()) {
                        ToastUtil.toast(response.msg);
                        return;
                    }

                    countDownTimer.start();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private String mPhone;

    private boolean isPhoneOk() {
        String phone = etRegisterPhone.getText().toString().trim();
        if (CommonUtils.isEmpty(phone)) {
            ToastUtil.toast("请输入手机号");
            return false;
        }
        mPhone = phone;
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTimer.cancel();
    }

    public void initTopBar() {
        TextView tv_title = findView(R.id.tv_title);
        TextView tv_right = findView(R.id.tv_right);
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText("登录");
        tv_title.setText("账号注册");
        et_register_excode.setText(SpUtil.getString(mContext,"coded") != null ? SpUtil.getString(mContext,"coded"):"");

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
                    if (!response.isNotBindPone() && !CommonUtils.isEmpty(response.msg))
                        ToastUtil.toast(response.msg);
                    if (response.isSuccess()) {
                        MyApplication.getInstance().setLoginKey(response.data.token);
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

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(UmengStat.REGISTRATIONPAGE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(UmengStat.REGISTRATIONPAGE);
    }
}
