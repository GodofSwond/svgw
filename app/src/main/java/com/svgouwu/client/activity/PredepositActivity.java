package com.svgouwu.client.activity;

import android.view.View;

import com.svgouwu.client.BaseActivity;
import com.svgouwu.client.R;

import butterknife.OnClick;

/**
 * Created by whq on 2018/1/8.
 * 预存款页面
 */

public class PredepositActivity extends BaseActivity {

    @Override
    protected int getContentView() {
        return R.layout.activity_predeposit;
    }

    @Override
    public void initViews() {
        setTopBarViews(findViewById(R.id.rl_predeposit_title), true);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.iv_pre_left})
    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.iv_pre_left:
                finish();
                break;
            default:
                break;
        }
    }
}
