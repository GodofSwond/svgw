package com.svgouwu.client;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

/**
 * Created by topwolf on 2017/5/10.
 * 通用的包含单个fragment的activity
 */

public abstract class SingleFragmentActivity extends BaseActivity {


   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fl_container);
        if(fragment == null){
            fragment = createFragment();
            fm.beginTransaction().add(R.id.fl_container,fragment).commit();
        }

    }*/

    @Override
    protected int getContentView() {
        return R.layout.activity_single_fragment;
    }

    @Override
    public void initViews() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fl_container);
        if(fragment == null){
            fragment = createFragment();
            fm.beginTransaction().add(R.id.fl_container,fragment).commit();
        }
    }

    protected abstract Fragment createFragment();

}
