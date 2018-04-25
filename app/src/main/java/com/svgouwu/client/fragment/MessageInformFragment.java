package com.svgouwu.client.fragment;

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
import com.svgouwu.client.MyApplication;
import com.svgouwu.client.R;
import com.svgouwu.client.adapter.MessageInformAdapter;
import com.svgouwu.client.bean.CouponsBean;
import com.svgouwu.client.bean.CouponsCenterBean;
import com.svgouwu.client.bean.CouponsItemBean;
import com.svgouwu.client.bean.HttpResult;
import com.svgouwu.client.bean.UnreceivedBean;
import com.svgouwu.client.utils.CommonCallback;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/11/10.
 * 消息--通知
 */

public class MessageInformFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private GridLayoutManager layoutManager;
    private int PAGE_COUNT = 10;
    private MessageInformAdapter informAdapter;
    private int lastVisibleItem = 0;
    private Handler handler = new Handler(Looper.getMainLooper());
    private List<CouponsItemBean> couponlist = new ArrayList<>();

    @BindView(R.id.aty_msg_rvInform_refreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.aty_msg_recyclerView)
    RecyclerView recyclerView;

    @Override
    protected int getContentView() {
        return R.layout.fragment_message_inform;
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initData() {
        jsonInformData();
        initRefreshLayout();
        initRecyclerView();
        swipeRefreshLayout.setEnabled(false);
    }

    @Override
    public void initListener() {

    }

    /**
     * 初始化initRecyclerView
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
                    if (informAdapter.isFadeTips() == false && lastVisibleItem + 1 == informAdapter.getItemCount()) {
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateRecyclerView(informAdapter.getRealLastPosition(), informAdapter.getRealLastPosition() + PAGE_COUNT);
                            }
                        }, 500);
                    }
                    if (informAdapter.isFadeTips() == true && lastVisibleItem + 2 == informAdapter.getItemCount()) {
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateRecyclerView(informAdapter.getRealLastPosition(), informAdapter.getRealLastPosition() + PAGE_COUNT);
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
            informAdapter.updateList(newDatas, true);
        } else {
            informAdapter.updateList(null, false);
        }
    }

    private List<CouponsItemBean> getDatas(int firstIndex, int page_count) {
        List<CouponsItemBean> informList = new ArrayList<>();
        for (int i = firstIndex; i < page_count; i++) {
            if (i < couponlist.size()) {
                informList.add(couponlist.get(i));
            }
        }
        return informList;
    }

    /**
     * 下拉颜色设置
     */
    private void initRefreshLayout() {
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(this);
    }


    private void jsonInformData() {
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
                if (response.isSuccess()) {
                    if (response.data != null) {
                        couponlist = response.data.couponlist;
                        Log.i("TAG", "onResponse: " + response.data.couponlist);
                        informAdapter = new MessageInformAdapter(getDatas(0, PAGE_COUNT), getContext(), getDatas(0, PAGE_COUNT).size() > 0 ? true : false);
                        recyclerView.setAdapter(informAdapter);
                        informAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    @Override
    public void processClick(View view) {

    }

    public static Fragment newInstance() {
        return new MessageInformFragment();
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        informAdapter.resetDatas();
        updateRecyclerView(0, PAGE_COUNT);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 1000);
    }
}
