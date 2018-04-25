package com.svgouwu.client.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kili.okhttp.OkHttpUtils;
import com.svgouwu.client.Api;
import com.svgouwu.client.BaseFragment;
import com.svgouwu.client.MyApplication;
import com.svgouwu.client.R;
import com.svgouwu.client.bean.HttpResult;
import com.svgouwu.client.bean.UserBean;
import com.svgouwu.client.utils.CommonCallback;
import com.svgouwu.client.utils.CommonUtils;
import com.svgouwu.client.utils.DateTimePickDialogUtils;
import com.svgouwu.client.utils.LogUtils;
import com.svgouwu.client.utils.ToastUtil;
import com.svgouwu.client.utils.UmengStat;
import com.umeng.analytics.MobclickAgent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Request;

/**
 * 个人资料-基本信息
 * Created by melon on 2017/6/20.
 */

public class BaseInfoFragment extends BaseFragment {
    @BindView(R.id.et_base_info_name)
    EditText etBaseInfoName;
    @BindView(R.id.tv_base_info_birthday)
    TextView tvBaseInfoBirthday;
    @BindView(R.id.rb_base_info_man)
    RadioButton rb_base_info_man;
    @BindView(R.id.rb_base_info_woman)
    RadioButton rb_base_info_woman;

    @Override
    protected int getContentView() {
        return R.layout.fragment_base_info;
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
        tv_title.setText("个人资料");

        findView(R.id.iv_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    @Override
    public void initData() {
        getBaseInfo();
    }

    private void getBaseInfo() {
        String url = Api.URL_USER_BASE_INFO;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", MyApplication.getInstance().getLoginKey());
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
                    UserBean userBean = response.data;
                    if(userBean!=null){
                        String name = CommonUtils.isEmpty(userBean.realName) ? userBean.userName : userBean.realName;
                        if(!CommonUtils.isEmpty(name)){
                            etBaseInfoName.setText(name);
                        }else {
                            etBaseInfoName.setHint("请输入名称");
                        }

                        if(userBean.sex == 2) {
                            rb_base_info_woman.setChecked(true);
                        }else {
                            rb_base_info_man.setChecked(true);
                        }

                        if(!CommonUtils.isEmpty(userBean.birthday)){
                            tvBaseInfoBirthday.setText(userBean.birthday);
                        } else {
                            tvBaseInfoBirthday.setHint("请选择生日");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtil.toast(R.string.text_tips_server_issue);

                }
            }
        });

    }

    @OnClick({R.id.tv_base_info_save, R.id.rl_base_info_birthday})
    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.tv_base_info_save:
                saveInfo();
                break;
            case R.id.rl_base_info_birthday:
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd ");
                Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                String currentDate = format.format(curDate);
                new DateTimePickDialogUtils(getActivity(), currentDate).show(new DateTimePickDialogUtils.DataPickCallback() {
                    @Override
                    public void onPositive(String date) {
                        tvBaseInfoBirthday.setText(date);
                    }

                    @Override
                    public void onNegative() {

                    }
                });
                break;
        }
    }

    private void saveInfo() {
        String name = etBaseInfoName.getText().toString().trim();
        String birthday = tvBaseInfoBirthday.getText().toString().trim();
        boolean isWoman = rb_base_info_woman.isChecked();
        int sex = 1;
        if(isWoman) {
            sex = 2;
        }

        String url = Api.URL_USER_BASE_INFO_EDIT;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", MyApplication.getInstance().getLoginKey());
        if(!CommonUtils.isEmpty(name))
            params.put("realName", name);
        if(!CommonUtils.isEmpty(birthday))
            params.put("birthday", birthday);
        params.put("gender", sex+"");
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
        MobclickAgent.onPageStart(UmengStat.PERSONALINFOPAGE);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(UmengStat.PERSONALINFOPAGE);
    }
}
