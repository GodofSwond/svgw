package com.svgouwu.client.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.svgouwu.client.BaseFragment;
import com.svgouwu.client.CommonFragmentActivity;
import com.svgouwu.client.MyApplication;
import com.svgouwu.client.R;
import com.svgouwu.client.activity.LoginActivity;
import com.svgouwu.client.utils.CommonUtils;
import com.svgouwu.client.utils.SpUtil;
import com.svgouwu.client.utils.ToastUtil;
import com.svgouwu.client.utils.UmengStat;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by melon on 2017/6/20.
 * 用户设置页面
 */

public class SettingsFragment extends BaseFragment {

    @BindView(R.id.tv_settings_logout)
    TextView tvSettingsLogout;

    @Override
    protected int getContentView() {
        return R.layout.fragment_settings;
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
        tv_title.setText("用户设置");

        findView(R.id.iv_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (CommonUtils.isLogin()) {
            tvSettingsLogout.setVisibility(View.VISIBLE);
        }else {
            tvSettingsLogout.setVisibility(View.GONE);
        }
        MobclickAgent.onPageStart(UmengStat.SETINFOPAGE);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(UmengStat.SETINFOPAGE);
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.tv_settings_logout, R.id.tv_settings_base, R.id.tv_settings_modify_email, R.id.tv_settings_modify_phone, R.id.tv_settings_modify_pwd})
    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.tv_settings_logout:
                MyApplication.getInstance().logout();
                SpUtil.setBoolean(getContext(),"isout",true);
                getActivity().finish();
                CommonUtils.startAct(getContext(), LoginActivity.class);
                break;
            case R.id.tv_settings_modify_email:
                if (CommonUtils.checkLogin())
                    CommonUtils.startAct(getContext(), CommonFragmentActivity.FRAGMENT_SETTINGS_MODIFY_EMAIL);
                break;
            case R.id.tv_settings_modify_phone:
                if (CommonUtils.checkLogin())
                    CommonUtils.startAct(getContext(), CommonFragmentActivity.FRAGMENT_SETTINGS_MODIFY_PHONE);
                break;
            case R.id.tv_settings_modify_pwd:
                if (CommonUtils.checkLogin())
                    CommonUtils.startAct(getContext(), CommonFragmentActivity.FRAGMENT_SETTINGS_MODIFY_PWD);
                break;
            case R.id.tv_settings_base:
                if (CommonUtils.checkLogin())
                    CommonUtils.startAct(getContext(), CommonFragmentActivity.FRAGMENT_SETTINGS_BASE_INFO);
                break;
        }
    }


}
