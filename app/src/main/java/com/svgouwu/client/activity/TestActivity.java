package com.svgouwu.client.activity;

import android.support.v4.app.Fragment;
import android.view.View;

import com.svgouwu.client.R;
import com.svgouwu.client.SingleFragmentActivity;
import com.svgouwu.client.fragment.HomeFragment;

/**
 * Created by topwolf on 2017/6/6.
 */

public class TestActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return HomeFragment.newInstance();
    }

    @Override
    public void initViews() {
        super.initViews();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View view) {

    }
}
