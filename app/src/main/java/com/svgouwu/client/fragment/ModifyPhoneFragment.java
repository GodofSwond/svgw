package com.svgouwu.client.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.kili.okhttp.OkHttpUtils;
import com.svgouwu.client.Api;
import com.svgouwu.client.BaseFragment;
import com.svgouwu.client.Constant;
import com.svgouwu.client.MyApplication;
import com.svgouwu.client.R;
import com.svgouwu.client.bean.HttpResult;
import com.svgouwu.client.utils.CommonCallback;
import com.svgouwu.client.utils.CommonUtils;
import com.svgouwu.client.utils.LogUtils;
import com.svgouwu.client.utils.ToastUtil;
import com.svgouwu.client.utils.UmengStat;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Request;

/**
 * 修改手机号码
 * Created by melon on 2017/6/20.
 */

public class ModifyPhoneFragment extends BaseFragment {
    @BindView(R.id.tv_modify_phone_get_code)
    TextView tv_modify_phone_get_code;
    @BindView(R.id.et_modify_phone_pwd)
    EditText etModifyPhonePwd;
    @BindView(R.id.et_modify_phone_mobile)
    EditText etModifyPhoneMobile;
    @BindView(R.id.et_modify_phone_code)
    EditText et_modify_phone_code;
    private CountDownTimer countDownTimer = new CountDownTimer(Constant.SEND_CODE_TIME, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            tv_modify_phone_get_code.setEnabled(false);
            tv_modify_phone_get_code.setText(millisUntilFinished / 1000 + "s");
        }

        @Override
        public void onFinish() {
            tv_modify_phone_get_code.setEnabled(true);
            tv_modify_phone_get_code.setText("重新获取");
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        countDownTimer.cancel();
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_modify_phone;
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
        tv_title.setText("修改手机号");

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

    @OnClick({R.id.tv_modify_phone_save, R.id.tv_modify_phone_get_code})
    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.tv_modify_phone_save:
                saveMobile();
                break;
            case R.id.tv_modify_phone_get_code:
                sendCode();
                break;
        }
    }

    private void sendCode() {
        String pwd = etModifyPhonePwd.getText().toString().trim();
        String phone = etModifyPhoneMobile.getText().toString().trim();

        if (CommonUtils.isEmpty(pwd)) {
            ToastUtil.toast("请输入密码");
            return;
        }
        if (CommonUtils.isEmpty(phone)) {
            ToastUtil.toast("请输入手机号");
            return;
        }
        if (phone.length() < 7) {
            ToastUtil.toast("请输入正确的手机号");
            return;
        }

        String url = Api.URL_SEND_CODE;
        HashMap<String, String> params = new HashMap<>();
        params.put("from", "changemobile");
        params.put("mobile", phone);
        params.put("password", pwd);
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

    private void saveMobile() {
        if (!isInputOk()) return;
        String url = Api.URL_MODIFY_PHONE;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", MyApplication.getInstance().getLoginKey());
        params.put("password", pwd);
        params.put("phone", phone);
        params.put("code", code);
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
                ToastUtil.toast(R.string.text_tips_net_issue);
            }

            @Override
            public void onResponse(HttpResult response) {

                try {
                    if (!CommonUtils.isEmpty(response.msg))
                        ToastUtil.toast(response.msg);

                    if ("0000".equals(response.code)) {
                        getActivity().finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtil.toast(R.string.text_tips_server_issue);

                }
            }
        });
    }

    private String pwd, phone, code;
    private boolean isInputOk() {
        boolean result = true;
        String code = et_modify_phone_code.getText().toString().trim();
        String pwd = etModifyPhonePwd.getText().toString().trim();
        String phone = etModifyPhoneMobile.getText().toString().trim();

        if (CommonUtils.isEmpty(pwd)) {
            ToastUtil.toast("请输入密码");
            return false;
        }
        if (CommonUtils.isEmpty(phone)) {
            ToastUtil.toast("请输入手机号");
            return false;
        }
        if (phone.length() < 7) {
            ToastUtil.toast("请输入正确的手机号");
            return false;
        }
        if (CommonUtils.isEmpty(code)) {
            ToastUtil.toast("请输入验证码");
            return false;
        }

        this.pwd = pwd;
        this.phone = phone;
        this.code = code;
        return result;
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(UmengStat.CHANGEPHONEPAGE);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(UmengStat.CHANGEPHONEPAGE);
    }
}
