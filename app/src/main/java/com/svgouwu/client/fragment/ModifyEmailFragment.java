package com.svgouwu.client.fragment;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.kili.okhttp.OkHttpUtils;
import com.svgouwu.client.Api;
import com.svgouwu.client.BaseFragment;
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
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

/**
 * 修改邮箱
 * Created by melon on 2017/6/20.
 */

public class ModifyEmailFragment extends BaseFragment {


    @BindView(R.id.et_modify_email_pwd)
    EditText etModifyEmailPwd;
    @BindView(R.id.et_modify_email_email)
    EditText etModifyEmailEmail;

    @Override
    protected int getContentView() {
        return R.layout.fragment_modify_email;
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
        tv_title.setText("修改电子邮箱");

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

    @OnClick({R.id.tv_base_info_save})
    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.tv_base_info_save:
                saveEmail();
                break;
        }
    }

    private void saveEmail() {
        String pwd = etModifyEmailPwd.getText().toString().trim();
        String email = etModifyEmailEmail.getText().toString().trim();

        if(CommonUtils.isEmpty(pwd)) {
            ToastUtil.toast("请输入密码");
            return;
        }
        if(CommonUtils.isEmpty(email) || !CommonUtils.isEmail(email)) {
            ToastUtil.toast("请输入邮箱");
            return;
        }

        String url = Api.URL_MODIFY_EMAIL;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", MyApplication.getInstance().getLoginKey());
        params.put("password", pwd);
        params.put("email", email);
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
                    if(!CommonUtils.isEmpty(response.msg))
                        ToastUtil.toast(response.msg);

                    if(response.isSuccess()){
                        getActivity().finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtil.toast(R.string.text_tips_server_issue);

                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(UmengStat.CHANGEMAILPAGE);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(UmengStat.CHANGEMAILPAGE);
    }
}
