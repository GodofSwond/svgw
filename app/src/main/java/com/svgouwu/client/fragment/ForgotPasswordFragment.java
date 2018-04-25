package com.svgouwu.client.fragment;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.kili.okhttp.OkHttpUtils;
import com.svgouwu.client.Api;
import com.svgouwu.client.BaseFragment;
import com.svgouwu.client.CommonFragmentActivity;
import com.svgouwu.client.Constant;
import com.svgouwu.client.MyApplication;
import com.svgouwu.client.R;
import com.svgouwu.client.activity.LoginActivity;
import com.svgouwu.client.bean.HttpResult;
import com.svgouwu.client.utils.CommonCallback;
import com.svgouwu.client.utils.CommonUtils;
import com.svgouwu.client.utils.LogUtils;
import com.svgouwu.client.utils.ToastUtil;
import com.svgouwu.client.utils.UmengStat;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

/**
 * 忘记密码-找回密码页面
 * Created by melon on 2017/6/20.
 */

public class ForgotPasswordFragment extends BaseFragment {

    @BindView(R.id.et_forgot_pwd_phone)
    EditText etForgotPwdPhone;
    @BindView(R.id.tv_forget_pwd_get_code)
    TextView tvForgetPwdGetCode;
    @BindView(R.id.et_forgot_pwd_code)
    EditText etForgotPwdCode;

    private CountDownTimer countDownTimer = new CountDownTimer(Constant.SEND_CODE_TIME, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            tvForgetPwdGetCode.setEnabled(false);
            tvForgetPwdGetCode.setText(millisUntilFinished / 1000 + "s");
        }

        @Override
        public void onFinish() {
            tvForgetPwdGetCode.setEnabled(true);
            tvForgetPwdGetCode.setText("重新获取");
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        countDownTimer.cancel();
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_forgot_pwd;
    }

    @Override
    public void initViews() {
        initTopBar();
    }

    @Override
    public void initListener() {

    }

    public void initTopBar() {
        TextView tv_title = findView(R.id.tv_title);
        tv_title.setText("找回密码");
        TextView tv_right = findView(R.id.tv_right);
        tv_right.setText("登录");
        findView(R.id.iv_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.tv_right, R.id.tv_forget_pwd_get_code, R.id.bt_forget_pwd_ok})
    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
                getActivity().finish();
                CommonUtils.startAct(getContext(), LoginActivity.class);
                break;
            case R.id.bt_forget_pwd_ok:
                submit();
                break;
            case R.id.tv_forget_pwd_get_code:
                sendCode();
                break;
        }
    }

    private void submit() {
        if (!isPhoneOk()) return;
        String code = etForgotPwdCode.getText().toString().trim();
        if (CommonUtils.isEmpty(code)) {
            ToastUtil.toast("请输入验证码");
            return;
        }

        String url = Api.URL_CHECK_CODE;
        HashMap<String, String> params = new HashMap<>();
        params.put("code", code);
        params.put("mobile", mPhone);
        params.put("from", "findpwd");
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
                    if(!CommonUtils.isEmpty(response.msg))
                        ToastUtil.toast(response.msg);

                    if(response.isSuccess()){
                        //进入下一步
                        CommonUtils.startAct(getContext(), CommonFragmentActivity.FRAGMENT_FORGOT_PWD_SET);
                        MyApplication.phone = mPhone;
                        getActivity().finish();
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
        params.put("from", "findpwd");
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
                ToastUtil.toast(getString(R.string.text_net_response_error));
            }

            @Override
            public void onResponse(HttpResult response) {

                try {
                    if (!CommonUtils.isEmpty(response.msg)) {
                        ToastUtil.toast(response.msg);
                    }

                    if(response.isSuccess())
                        countDownTimer.start();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private String mPhone;

    private boolean isPhoneOk() {
        String phone = etForgotPwdPhone.getText().toString().trim();
        if (CommonUtils.isEmpty(phone)) {
            ToastUtil.toast("请输入手机号");
            return false;
        }
        mPhone = phone;
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(UmengStat.FINDPASSWORDPAGE);

    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(UmengStat.FINDPASSWORDPAGE);
    }
}
