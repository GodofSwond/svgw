package com.svgouwu.client.fragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kili.okhttp.OkHttpUtils;
import com.svgouwu.client.Api;
import com.svgouwu.client.BaseFragment;
import com.svgouwu.client.CommonFragmentActivity;
import com.svgouwu.client.MyApplication;
import com.svgouwu.client.R;
import com.svgouwu.client.adapter.MessageStoreAdapter;
import com.svgouwu.client.bean.CouponsBean;
import com.svgouwu.client.bean.CouponsItemBean;
import com.svgouwu.client.bean.HttpResult;
import com.svgouwu.client.utils.CommonCallback;
import com.svgouwu.client.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/11/6.
 * 消息--店铺
 */

public class MessageStoreFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private int PAGE_COUNT = 10;//分页加载数量
    private int lastVisibleItem = 0;
    private Handler mHandler = new Handler(Looper.getMainLooper());//开启主线程
    private GridLayoutManager mLayoutManager;
    private MessageStoreAdapter storeAdapter;
    private List<CouponsItemBean> couponlist = new ArrayList<>();

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_right)
    ImageView iv_right;
    @BindView(R.id.iv_top_right2)
    ImageView iv_right2;
    @BindView(R.id.frg_msg_store_refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.frg_msg_store_rvStore)
    RecyclerView recyclerView;

    @Override
    protected int getContentView() {
        return R.layout.fragment_message_store;
    }

    @Override
    public void initViews() {
        iv_right2.setVisibility(View.VISIBLE);
        iv_right.setVisibility(View.VISIBLE);
        iv_right.setImageResource(R.drawable.icon_shezhi);
        tv_title.setText("消息");
        jsonData();
        initRefreshLayout();
        initRecyclerView();
        refreshLayout.setEnabled(false);
    }

    /**
     * 初始化initRecyclerView
     */
    private void initRecyclerView() {
        mLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // 实现上拉加载重要步骤，设置滑动监听器，RecyclerView自带的ScrollListener
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                // 在newState为滑到底部时
                if (newState == recyclerView.SCROLL_STATE_IDLE) {
                    // 如果没有隐藏footView，那么最后一个条目的位置就比我们的getItemCount少1，自己可以算一下
                    if (storeAdapter.isFadeTips() == false && lastVisibleItem + 1 == storeAdapter.getItemCount()) {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateRecyclerView(storeAdapter.getRealLastPosition(), storeAdapter.getRealLastPosition() + PAGE_COUNT);
                            }
                        }, 500);
                    }
                    if (storeAdapter.isFadeTips() == true && lastVisibleItem + 2 == storeAdapter.getItemCount()) {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateRecyclerView(storeAdapter.getRealLastPosition(), storeAdapter.getRealLastPosition() + PAGE_COUNT);
                            }
                        }, 500);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    private List<CouponsItemBean> getDatas(int firstIndex, int lastIndex) {
        List<CouponsItemBean> resList = new ArrayList<>();
        for (int i = firstIndex; i < lastIndex; i++) {
            if (i < couponlist.size()) {
                resList.add(couponlist.get(i));
            }
        }
        return resList;
    }

    private void updateRecyclerView(int fromIndex, int toIndex) {
        List<CouponsItemBean> newDatas = getDatas(fromIndex, toIndex);
        if (newDatas.size() > 0) {
            storeAdapter.updateList(newDatas, true);
        } else {
            storeAdapter.updateList(null, false);
        }
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        storeAdapter.resetDatas();
        updateRecyclerView(0, PAGE_COUNT);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
            }
        }, 1000);
    }

    /**
     * 下拉刷新的进度条颜色
     */
    private void initRefreshLayout() {
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        refreshLayout.setOnRefreshListener(this);
    }

    private void showTopBar() {
        TextView textView = findView(R.id.tv_title);
        ImageView ivRight = findView(R.id.iv_right);
        ImageView right2 = findView(R.id.iv_top_right2);
        right2.setVisibility(View.VISIBLE);
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(R.drawable.icon_shezhi);
        textView.setText("消息");
        findView(R.id.iv_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        ivRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CommonFragmentActivity.class);
                intent.putExtra(CommonFragmentActivity.TARGET, CommonFragmentActivity.FRAGMENT_MESSAGE_SETTING);
                startActivity(intent);
            }
        });
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    private void jsonData() {
        HashMap<String, String> params = new HashMap<String, String>();
        String loginKey = MyApplication.getInstance().getLoginKey();
        params.put("token", loginKey);
        params.put("type", "2");
        params.put("page", "1");
        OkHttpUtils.post().url(Api.URL_DISCOUNTS_LIST).params(params).build().execute(new CommonCallback<CouponsBean>() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(HttpResult<CouponsBean> response) {
                couponlist = response.data.couponlist;
                // 第一个参数为数据，上拉加载的原理就是分页，所以我设置常量PAGE_COUNT=10，即每次加载10个数据
                // 第二个参数为Context
                // 第三个参数为hasMore，是否有新数据
                storeAdapter = new MessageStoreAdapter(getDatas(0, PAGE_COUNT), getContext(), getDatas(0, PAGE_COUNT).size() > 0 ? true : false);
                recyclerView.setAdapter(storeAdapter);
                storeAdapter.notifyDataSetChanged();
            }
        });
    }

    @OnClick({R.id.iv_left, R.id.frg_msg_store_rlInform, R.id.frg_msg_store_rlCoupon})
    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.frg_msg_store_rlInform:
                ToastUtil.toast("通知");
                break;
            case R.id.frg_msg_store_rlCoupon:
                ToastUtil.toast("优惠");
                break;
            case R.id.iv_left:
                getActivity().finish();
                break;
            case R.id.iv_top_right2:
                Intent intent = new Intent(getContext(), CommonFragmentActivity.class);
                intent.putExtra(CommonFragmentActivity.TARGET, CommonFragmentActivity.FRAGMENT_MESSAGE_SETTING);
                startActivity(intent);
                break;
        }
    }
}
