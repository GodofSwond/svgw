package com.svgouwu.client.activity;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.gxz.PagerSlidingTabStrip;
import com.svgouwu.client.BaseActivity;
import com.svgouwu.client.R;
import com.svgouwu.client.adapter.ItemTitlePagerAdapter;
import com.svgouwu.client.fragment.GoodsCommentFragment;
import com.svgouwu.client.fragment.GoodsDetailFragment;
import com.svgouwu.client.fragment.GoodsInfoFragment;
import com.svgouwu.client.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by topwolf on 2017/6/22.
 */

public class GoodsDetailsActivity extends BaseActivity {

    public PagerSlidingTabStrip psts_tabs;
    public NoScrollViewPager vp_content;
    public TextView tv_title;

    private List<Fragment> fragmentList = new ArrayList<>();
    private GoodsInfoFragment goodsInfoFragment;
    private GoodsDetailFragment goodsDetailFragment;
    private GoodsCommentFragment goodsCommentFragment;

    @Override
    protected int getContentView() {
        return R.layout.activity_goods_detail;
    }

    @Override
    public void initViews() {
        psts_tabs = (PagerSlidingTabStrip) findViewById(R.id.psts_tabs);
        vp_content = (NoScrollViewPager) findViewById(R.id.vp_content);
        tv_title = (TextView) findViewById(R.id.tv_title);

        fragmentList.add(goodsInfoFragment = new GoodsInfoFragment());
        fragmentList.add(goodsDetailFragment = new GoodsDetailFragment());
        fragmentList.add(goodsCommentFragment = new GoodsCommentFragment());
        vp_content.setAdapter(new ItemTitlePagerAdapter(getSupportFragmentManager(),
                fragmentList, new String[]{"商品", "详情", "评价"}));
        vp_content.setOffscreenPageLimit(3);
        psts_tabs.setViewPager(vp_content);
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
