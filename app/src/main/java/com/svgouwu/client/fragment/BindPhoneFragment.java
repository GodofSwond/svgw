package com.svgouwu.client.fragment;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.kili.okhttp.OkHttpUtils;
import com.svgouwu.client.Api;
import com.svgouwu.client.BaseFragment;
import com.svgouwu.client.Constant;
import com.svgouwu.client.MyApplication;
import com.svgouwu.client.R;
import com.svgouwu.client.bean.HttpResult;
import com.svgouwu.client.bean.UserBean;
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
 * 绑定手机
 * Created by melon on 2017/6/20.
 */

public class BindPhoneFragment extends BaseFragment {
    @BindView(R.id.et_bind_phone_mobile)
    EditText et_bind_phone_mobile;
    @BindView(R.id.et_bind_phone_code)
    EditText et_bind_phone_code;
    @BindView(R.id.tv_bind_phone_get_code)
    TextView tv_bind_phone_get_code;
    @BindView(R.id.et_bindphone_excode)
    EditText et_bindphone_excode;
    private CountDownTimer countDownTimer = new CountDownTimer(Constant.SEND_CODE_TIME, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            tv_bind_phone_get_code.setEnabled(false);
            tv_bind_phone_get_code.setText(millisUntilFinished / 1000 + "s");
        }

        @Override
        public void onFinish() {
            tv_bind_phone_get_code.setEnabled(true);
            tv_bind_phone_get_code.setText("重新获取");
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        countDownTimer.cancel();
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_bind_phone;
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
        tv_title.setText("账号绑定");

        findView(R.id.iv_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    private String openid, type;
    @Override
    public void initData() {
        openid = getActivity().getIntent().getStringExtra("openid");
        type = getActivity().getIntent().getStringExtra("type");

        if(CommonUtils.isEmpty(openid)) {
            LogUtils.e("三方登录信息不存在");
            getActivity().finish();
        }
    }

    @OnClick({R.id.tv_bind_phone_save, R.id.tv_bind_phone_get_code})
    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.tv_bind_phone_save:
                bindMobile();
                break;
            case R.id.tv_bind_phone_get_code:
                sendCode();
                break;
        }
    }

    private void sendCode() {
        String phone = et_bind_phone_mobile.getText().toString().trim();
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
        params.put("from", "bind");
        params.put("mobile", phone);
        params.put("extensionCode", excode);
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
                ToastUtil.toast("请求失败，请检查网络");
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

    private void bindMobile() {
        if (!isInputOk()) return;
        String url = Api.URL_BIND_PHONE;
        HashMap<String, String> params = new HashMap<>();
        params.put("mobile", phone);
        params.put("code", code);
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
                        getActivity().finish();
                        MyApplication.getInstance().setLoginKey(response.data.token);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtil.toast(R.string.text_tips_server_issue);

                }
            }
        });
    }

    private String phone, code, excode;
    private boolean isInputOk() {
        boolean result = true;
        String code = et_bind_phone_code.getText().toString().trim();
        String phone = et_bind_phone_mobile.getText().toString().trim();
        String excode = et_bindphone_excode.getText().toString().trim();

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

        this.phone = phone;
        this.code = code;
        this.excode = excode;
        return result;
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(UmengStat.LOGINRETURNPAGE);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(UmengStat.LOGINRETURNPAGE);
    }
}
