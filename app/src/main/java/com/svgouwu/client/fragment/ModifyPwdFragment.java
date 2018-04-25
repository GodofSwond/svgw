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
 * 修改密码
 * Created by melon on 2017/6/20.
 */

public class ModifyPwdFragment extends BaseFragment {

    @BindView(R.id.et_modify_pwd_cur)
    EditText etModifyPwdCur;
    @BindView(R.id.et_modify_pwd_new)
    EditText etModifyPwdNew;
    @BindView(R.id.et_modify_pwd_re_new)
    EditText etModifyPwdReNew;

    @Override
    protected int getContentView() {
        return R.layout.fragment_modify_pwd;
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
        tv_title.setText("修改密码");

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
                savePwd();
                break;
        }
    }

    private void savePwd() {
        String curPwd = etModifyPwdCur.getText().toString().trim();
        String newPwd = etModifyPwdNew.getText().toString().trim();
        String reNewPwd = etModifyPwdReNew.getText().toString().trim();

        if (CommonUtils.isEmpty(curPwd) || CommonUtils.isEmpty(newPwd) || CommonUtils.isEmpty(reNewPwd)) {
            ToastUtil.toast("密码不能为空");
            return;
        }

        if(!newPwd.equals(reNewPwd)){
            ToastUtil.toast("两次新密码不一致");
            return;
        }
        if(curPwd.equals(newPwd)){
            ToastUtil.toast("原密码与新密码不能相同");
            return;
        }

        String url = Api.URL_MODIFY_PWD;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", MyApplication.getInstance().getLoginKey());
        params.put("oldpassword", curPwd);
        params.put("password", newPwd);
        params.put("repassword", reNewPwd);
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

                    if(response.isSuccess()){
                        getActivity().finish();
                        MyApplication.getInstance().logout();
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
        MobclickAgent.onPageStart(UmengStat.CHANGEPASSWORDPAGE);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(UmengStat.CHANGEPASSWORDPAGE);
    }
}
