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
 * 忘记密码 --- 设置新密码
 * Created by melon on 2017/6/20.
 */

public class ForgotPasswordSetFragment extends BaseFragment {

    @BindView(R.id.et_forgot_pwd_set_pwd)
    EditText etForgotPwdSetPwd;
    @BindView(R.id.et_forgot_pwd_set_re_pwd)
    EditText etForgetPwdSetRePwd;


    @Override
    protected int getContentView() {
        return R.layout.fragment_forgot_pwd_set;
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
        if (CommonUtils.isEmpty(MyApplication.phone)) {
            ToastUtil.toast("号码不存在，请重试");
            getActivity().finish();
        }
    }

    @OnClick({R.id.tv_right, R.id.bt_forget_pwd_set_ok})
    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
                getActivity().finish();
                CommonUtils.startAct(getContext(), LoginActivity.class);
                break;
            case R.id.bt_forget_pwd_set_ok:
                submit();
                break;
        }
    }

    private void submit() {
        String pwd = etForgotPwdSetPwd.getText().toString().trim();
        String rePwd = etForgetPwdSetRePwd.getText().toString().trim();
        if (CommonUtils.isEmpty(pwd) || CommonUtils.isEmpty(rePwd)) {
            ToastUtil.toast("请输入密码");
            return;
        }

        if (!pwd.equals(rePwd)) {
            ToastUtil.toast("两次密码不一致");
            return;
        }

        String url = Api.URL_FORGOT_PWD_SET;
        HashMap<String, String> params = new HashMap<>();
        params.put("pwd1", pwd);
        params.put("pwd2", rePwd);
        params.put("mobile", MyApplication.phone);
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
                        MyApplication.getInstance().logout();
                        getActivity().finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(UmengStat.CONFIRMPASSWORDPAGE);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(UmengStat.CONFIRMPASSWORDPAGE);
    }
}
