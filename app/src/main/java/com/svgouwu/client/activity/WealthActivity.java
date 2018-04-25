package com.svgouwu.client.activity;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kili.okhttp.OkHttpUtils;
import com.svgouwu.client.Api;
import com.svgouwu.client.BaseActivity;
import com.svgouwu.client.CommonFragmentActivity;
import com.svgouwu.client.MyApplication;
import com.svgouwu.client.R;
import com.svgouwu.client.bean.HttpResult;
import com.svgouwu.client.bean.WealthApplyBean;
import com.svgouwu.client.utils.CommonCallback;
import com.svgouwu.client.utils.CommonUtils;
import com.svgouwu.client.utils.NetWorkUtils;
import com.svgouwu.client.utils.ToastUtil;

import java.io.IOException;
import java.util.HashMap;

import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/11/16.
 * 我的财富
 */

public class WealthActivity extends BaseActivity {
    private String code;
    private String msg;

    @Override
    protected int getContentView() {
        return R.layout.activity_wealth;
    }

    @Override
    public void initViews() {
        showTopBar();
    }

    private void showTopBar() {
        TextView tv_title = findView(R.id.tv_title);
        tv_title.setText("我的财富");
        ImageView iv_left = findView(R.id.iv_left);
        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initListener() {

    }

    /**
     * 刷新页面
     */
    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    /**
     * 提取金额根据code码判断msg状态
     */
    @Override
    public void initData() {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", MyApplication.getInstance().getLoginKey());
        try {
            OkHttpUtils.post().url(Api.URL_WEALTH_CLEARMONEY).params(params).build().execute(new CommonCallback<WealthApplyBean>() {
                @Override
                public void onError(Call call, Exception e) {

                }

                @Override
                public void onResponse(HttpResult<WealthApplyBean> response) {
                    code = response.code;
                    msg = response.msg;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.fragment_money_rebate_rl, R.id.fragment_money_look_rl, R.id.fragment_money_apply_rl, R.id.fragment_money_notes_rl})
    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_money_rebate_rl:
                CommonUtils.startAct(WealthActivity.this, CommonFragmentActivity.FRAGMENT_REBATE);//我的返利
                break;
            case R.id.fragment_money_look_rl:
                CommonUtils.startAct(WealthActivity.this, CommonFragmentActivity.FRAGMENT_LOOK);//查看佣金
                break;
            case R.id.fragment_money_apply_rl:
                if (code != null) {
                    if (code.equals("0070") || code.equals("0069")) { //0070:金额不足    0069：不是代理人
                        ToastUtil.toast(msg);
                    } else {
                        CommonUtils.startAct(WealthActivity.this, CommonFragmentActivity.FRAGMENT_APPLY);//提现申请
                    }
                } else {
                    ToastUtil.toast("请连接网络");
                }

                break;
            case R.id.fragment_money_notes_rl:
                CommonUtils.startAct(WealthActivity.this, CommonFragmentActivity.FRAGMENT_NOTES);//提现记录
                break;
        }
    }
}
