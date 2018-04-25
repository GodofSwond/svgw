package com.svgouwu.client.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kili.okhttp.OkHttpUtils;
import com.svgouwu.client.Api;
import com.svgouwu.client.BaseActivity;
import com.svgouwu.client.R;
import com.svgouwu.client.adapter.MyWalletAdapter;
import com.svgouwu.client.bean.GoodsInfo;
import com.svgouwu.client.bean.HttpResult;
import com.svgouwu.client.utils.CommonCallback;
import com.svgouwu.client.view.LoadPage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by whq on 2017/12/21.
 * 我的钱包页，入口--个人中心页
 */

public class MyWalletActivity extends BaseActivity {

    private GridLayoutManager manager;
    private List<GoodsInfo> list = new ArrayList<>();
    private MyWalletAdapter mAdapter;
    @BindView(R.id.mLoadPage_mywt)
    LoadPage mLoadPage_mywt;//加载动画
    @BindView(R.id.tv_mywt_pre_money)
    TextView tv_mywt_pre_money;//预存款金额
    @BindView(R.id.tv_mywt_coupon_num)
    TextView tv_mywt_coupon_num;//优惠券张数
    @BindView(R.id.tv_mywt_coin_num)
    TextView tv_mywt_coin_num;//四维币金额
    @BindView(R.id.rv_mywt)
    XRecyclerView rv_mywt;//recyclev


    @Override
    protected int getContentView() {
        return R.layout.activity_mywallet;
    }

    @Override
    public void initViews() {
        manager = new GridLayoutManager(mContext, 2);
        rv_mywt.setPullRefreshEnabled(false);
        rv_mywt.setNoMore(false);
        rv_mywt.setLoadingMoreEnabled(false);
        rv_mywt.setLayoutManager(manager);
        setTopBarViews(findViewById(R.id.rl_mywt),true);
    }

    @Override
    public void initListener() {
        mLoadPage_mywt.setGetDataListener(new LoadPage.GetDataListener() {
            @Override
            public void onGetData() {
                mLoadPage_mywt.switchPage(LoadPage.STATE_LOADING);
                getRecommend();
            }
        });
        mLoadPage_mywt.switchPage(LoadPage.STATE_LOADING);
        getRecommend();
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.iv_mywallet_back, R.id.tv_mywt_coupon, R.id.tv_mywt_wealth,
            R.id.tv_mywt_coin, R.id.tv_mywt_Recharge})
    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.iv_mywallet_back://返回
                finish();
                break;
            case R.id.tv_mywt_coupon://优惠券
                startActivity(new Intent(mContext, CouponListActivity.class));
                break;
            case R.id.tv_mywt_wealth://我的财富
                startActivity(new Intent(mContext, WealthActivity.class));
                break;
            case R.id.tv_mywt_coin://四维币
                startActivity(new Intent(mContext, MysvCoinActivity.class));
                break;
            case R.id.tv_mywt_Recharge://预存款充值
                startActivity(new Intent(mContext, PredepositActivity.class));
                break;
            default:
                break;
        }
    }

    private void getRecommend() {
        String url = Api.URL_GOODS_LIST;
        HashMap<String, String> map = new HashMap<>();
        map.put("page", "1");
        map.put("limit", "15");
        OkHttpUtils.get().url(url).params(map).build().execute(new CommonCallback<List<GoodsInfo>>() {

            @Override
            public void onError(Call call, Exception e) {
                mLoadPage_mywt.switchPage(LoadPage.STATE_NO_NET);
            }

            @Override
            public void onResponse(HttpResult<List<GoodsInfo>> response) {
                try {

                    if (response.data != null) {
                        mLoadPage_mywt.switchPage(LoadPage.STATE_HIDE);
                        fillData(response.data);
                    } else {
                        mLoadPage_mywt.switchPage(LoadPage.STATE_NO_DATA);
                    }

                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        });
    }

    private void fillData(List<GoodsInfo> data) {
        list.addAll(data);
        mAdapter = new MyWalletAdapter(mContext, list);
        rv_mywt.setAdapter(mAdapter);
    }
}
