package com.svgouwu.client.fragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.kili.okhttp.OkHttpUtils;
import com.svgouwu.client.Api;
import com.svgouwu.client.BaseFragment;
import com.svgouwu.client.CommonFragmentActivity;
import com.svgouwu.client.MyApplication;
import com.svgouwu.client.R;
import com.svgouwu.client.adapter.MessageCouponAdapter;
import com.svgouwu.client.bean.CouponsBean;
import com.svgouwu.client.bean.CouponsCenterBean;
import com.svgouwu.client.bean.CouponsItemBean;
import com.svgouwu.client.bean.HttpResult;
import com.svgouwu.client.bean.UnreceivedBean;
import com.svgouwu.client.utils.CommonCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/11/10.
 * 消息--优惠
 */

public class MessageCouponFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private int PAGE_COUNT = 10;
    private int lastVisibleItem = 0;
    private MessageCouponAdapter couponAdapter;
    private Handler handler = new Handler(Looper.getMainLooper());
    private GridLayoutManager layoutManager;
    private List<CouponsItemBean> couponlist = new ArrayList<>();

    @BindView(R.id.aty_msg_coupon_refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.aty_msg_rvCoupon)
    RecyclerView recyclerView;

    @Override
    protected int getContentView() {
        return R.layout.fragment_message_coupon;
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        jsonCouponData();
        initRefreshLayout();
        initRecyclerView();
        refreshLayout.setEnabled(false);//禁止下拉

    }

    /**
     * 下拉刷新的进度条颜色
     */
    private void initRefreshLayout() {
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        refreshLayout.setOnRefreshListener(this);
    }

    /**
     * 初始化上拉刷新
     * SCROLL_STATE_IDLE     停止滚动
     * SCROLL_STATE_DRAGGING     正在被外部拖拽,一般为用户正在用手指滚动
     * SCROLL_STATE_SETTLING     自动滚动开始
     */
    private void initRecyclerView() {
        layoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //newState目前状态
                if (newState == recyclerView.SCROLL_STATE_IDLE) {
                    if (couponAdapter.isFadeTips() == false && lastVisibleItem + 1 == couponAdapter.getItemCount()) {
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateRecyclerView(couponAdapter.getRealLastPosition(), couponAdapter.getRealLastPosition() + PAGE_COUNT);
                            }
                        }, 500);
                    }
                    if (couponAdapter.isFadeTips() == true && lastVisibleItem + 2 == couponAdapter.getItemCount()) {
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateRecyclerView(couponAdapter.getRealLastPosition(), couponAdapter.getRealLastPosition() + PAGE_COUNT);
                            }
                        }, 500);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }
        });
    }

    private void updateRecyclerView(int realLastPosition, int i) {
        List<CouponsItemBean> newDatas = getDatas(realLastPosition, i);
        if (newDatas.size() > 0) {
            couponAdapter.updateList(newDatas, true);
        } else {
            couponAdapter.updateList(null, false);
        }
    }

    private List<CouponsItemBean> getDatas(int firstIndex, int page_count) {
        List<CouponsItemBean> copList = new ArrayList<>();
        for (int i = firstIndex; i < page_count; i++) {
            if (i < couponlist.size()) {
                copList.add(couponlist.get(i));
            }
        }
        return copList;
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        couponAdapter.resetDatas();
        updateRecyclerView(0, PAGE_COUNT);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
            }
        }, 1000);
    }

    private void jsonCouponData() {
        HashMap<String, String> params = new HashMap<String, String>();
        String loginKey = MyApplication.getInstance().getLoginKey();
        params.put("token", loginKey);
        params.put("type", "0");
        params.put("page", "1");
        OkHttpUtils.post().url(Api.URL_DISCOUNTS_LIST).params(params).build().execute(new CommonCallback<CouponsBean>() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(HttpResult<CouponsBean> response) {
                couponlist = response.data.couponlist;
                couponAdapter = new MessageCouponAdapter(getDatas(0, PAGE_COUNT), getContext(), getDatas(0, PAGE_COUNT).size() > 0 ? true : false);
                recyclerView.setAdapter(couponAdapter);
                couponAdapter.notifyDataSetChanged();
            }
        });
    }


    @Override
    public void processClick(View view) {
        switch (view.getId()) {

        }
    }

    public static Fragment newInstance() {
        return new MessageCouponFragment();
    }
}
