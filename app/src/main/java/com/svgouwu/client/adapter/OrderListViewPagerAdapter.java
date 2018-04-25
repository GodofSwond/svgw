package com.svgouwu.client.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.svgouwu.client.fragment.BaseInfoFragment;
import com.svgouwu.client.fragment.OrderListFragment;
import com.svgouwu.client.fragment.PersonalFragment;

/**
 * Created by melon on 2017/6/26.
 * Email 530274554@qq.com
 */

public class OrderListViewPagerAdapter extends FragmentPagerAdapter {
    private String[] titles;
    public OrderListViewPagerAdapter(FragmentManager fm, String[] titles) {
        super(fm);
        this.titles  = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return createFragment(position);
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    private Fragment createFragment(int position) {
        OrderListFragment fragment = new OrderListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("orderState", position);
        fragment.setArguments(bundle);
        return fragment;
    }
}
