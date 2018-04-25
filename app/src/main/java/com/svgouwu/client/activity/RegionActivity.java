package com.svgouwu.client.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.svgouwu.client.SingleFragmentActivity;
import com.svgouwu.client.event.GotRegionEvent;
import com.svgouwu.client.fragment.HomeFragment;
import com.svgouwu.client.fragment.RegionFragment;
import com.svgouwu.client.utils.LogUtils;
import com.svgouwu.client.utils.ToastUtil;
import com.svgouwu.client.utils.UmengStat;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by topwolf on 2017/6/6.
 * 收货地址选择--详情
 */

public class RegionActivity extends SingleFragmentActivity {

    private String regionId ="1";
    private int level =1;
    private boolean islastlevel ;
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {

        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected Fragment createFragment() {
        if(getIntent().hasExtra("regionId")){
            regionId = getIntent().getStringExtra("regionId");
            level = getIntent().getIntExtra("level",1);
            islastlevel = getIntent().getBooleanExtra("islastlevel",false);
        }
        return RegionFragment.newInstance(regionId,level,islastlevel);
    }

    @Override
    public void initViews() {
        super.initViews();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(GotRegionEvent event) {
        if(RegionActivity.this!=null && !RegionActivity.this.isFinishing()){
        LogUtils.e(event.regionId+"=="+event.regionName);
            finish();
        }
//        ToastUtil.toast(event.regionId+"==="+event.regionName);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(UmengStat.ADDRESSPAGE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(UmengStat.ADDRESSPAGE);
    }
}
