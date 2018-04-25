package com.svgouwu.client.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kili.okhttp.OkHttpUtils;
import com.svgouwu.client.Api;
import com.svgouwu.client.BaseActivity;
import com.svgouwu.client.CommonFragmentActivity;
import com.svgouwu.client.MyApplication;
import com.svgouwu.client.R;
import com.svgouwu.client.bean.CouponsCenterBean;
import com.svgouwu.client.bean.CouponsItemBean;
import com.svgouwu.client.bean.CouponsNumBean;
import com.svgouwu.client.bean.HttpResult;
import com.svgouwu.client.bean.TabBean;
import com.svgouwu.client.utils.CommonCallback;
import com.svgouwu.client.utils.CommonUtils;
import com.svgouwu.client.utils.CustomToast;
import com.svgouwu.client.utils.ToastUtil;
import com.svgouwu.client.utils.UmengStat;
import com.svgouwu.client.view.CouponExchangeDialog;
import com.svgouwu.client.view.LoadPage;
import com.umeng.analytics.MobclickAgent;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/12/6.
 * 优惠券
 */

public class CouponListActivity extends BaseActivity implements XRecyclerView.LoadingListener {

    private List<TabBean> tabs = new ArrayList<>();
    String[] titles = {"未使用", "已使用", "已过期"};
    private LoadPage mLoadPage;
    private String type = "0";
    private List<CouponsItemBean> couponlist = new ArrayList<>();//优惠券列表
    private int mPage = 1;
    private boolean isFirstLoad = true;
    private CommonAdapter<CouponsItemBean> adapter;
    private boolean isNodata;
    private boolean mIsRefreshing = true;

    @BindView(R.id.tv_right)
    TextView tv_right;
    @BindView(R.id.tv_coupons_center)//领券中心
    TextView tv_coupons_center;
    @BindView(R.id.tv_coupons_exchange)//券码兑换
    TextView tv_coupons_exchange;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.fragment_coupon_list_mTabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.fragment_coupon_list_mXRecyclerView)
    XRecyclerView mXRecyclerView;

    @Override
    protected int getContentView() {
        return R.layout.activity_coupon_list;
    }

    @Override
    public void initViews() {
        tvTitle.setText("优惠券");
        tv_right.setText("使用规则");
        tv_right.setVisibility(View.VISIBLE);
        mLoadPage = (LoadPage) findViewById(R.id.mLoadPage);

        mLoadPage.setGetDataListener(new LoadPage.GetDataListener() {
            @Override
            public void onGetData() {
                mLoadPage.switchPage(LoadPage.STATE_LOADING);
                mPage = 1;
                loadData();
            }
        });

        mLoadPage.switchPage(LoadPage.STATE_LOADING);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TabBean tabBean = tabs.get(tab.getPosition());
                type = tabBean.id;
                mPage = 1;
                loadData();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        LinearLayoutManager layoutManager = new LinearLayoutManager(CouponListActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mXRecyclerView.setLayoutManager(layoutManager);
        mXRecyclerView.setPullRefreshEnabled(true);
        mXRecyclerView.setNoMore(false);

        adapter = new CommonAdapter<CouponsItemBean>(CouponListActivity.this, R.layout.item_coupon_list, couponlist) {
            @Override
            protected void convert(final ViewHolder holder, final CouponsItemBean couponsItemBean, int position) {
                holder.setText(R.id.item_coupon_list_tvName, couponsItemBean.couponName);
                holder.setText(R.id.item_coupon_list_tvMoney, couponsItemBean.couponValue);
                holder.setText(R.id.item_coupon_list_tvTime, couponsItemBean.startTime + "至" + couponsItemBean.endTime);
                holder.setText(R.id.item_coupon_list_tvFullness, "满" + couponsItemBean.minAmount + "使用");

                //已使用优惠券type：0
                if (type.equals("0")) {
                    holder.setBackgroundRes(R.id.item_coupon_list_llBg, R.drawable.biankuang);
                    holder.setTextColorRes(R.id.item_coupon_list_tvMoney, R.color.color_red);
                    holder.setTextColorRes(R.id.item_coupon_list_tvSign, R.color.color_red);
                    holder.getView(R.id.item_coupon_list_tvUse).setVisibility(View.VISIBLE);
                    holder.setVisible(R.id.item_coupon_list_ivGuoqi, false);
                    holder.setVisible(R.id.item_coupon_list_ivShiyong, false);

                    //是否即将过期，1：即将过期      0：未使用
                    if (couponsItemBean.being_expired.equals("1")) {
                        holder.getView(R.id.item_coupon_list_bg).setVisibility(View.VISIBLE);
                    } else if (couponsItemBean.being_expired.equals("0")) {
                        holder.getView(R.id.item_coupon_list_bg).setVisibility(View.GONE);
                    }
                    //去使用的点击事件
                    holder.getView(R.id.item_coupon_list_tvUse).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (CommonUtils.checkLogin()) {
                                if (couponsItemBean.is_begin.equals("0")) {
                                    ToastUtil.toast("活动暂未开始，请按指定时间使用");
                                } else {
                                    MobclickAgent.onEvent(CouponListActivity.this, UmengStat.COUPONSUSE);//ym
                                    Intent intentC = new Intent(CouponListActivity.this, CommonFragmentActivity.class);
                                    intentC.putExtra(CommonFragmentActivity.TARGET, CommonFragmentActivity.FRAGMENT_USE_COUPONS);
                                    intentC.putExtra("useMobileUrl", couponsItemBean.useMobileUrl);
                                    startActivity(intentC);
                                }
                            }
                        }
                    });
                }
                //已使用优惠券type：1
                if (type.equals("1")) {
                    holder.setBackgroundRes(R.id.item_coupon_list_llBg, R.drawable.bk_yilingqu);
                    holder.setTextColorRes(R.id.item_coupon_list_tvMoney, R.color.grey_color3);
                    holder.setTextColorRes(R.id.item_coupon_list_tvSign, R.color.grey_color3);
                    holder.getView(R.id.item_coupon_list_tvUse).setVisibility(View.GONE);
                    holder.setVisible(R.id.item_coupon_list_bg, false);
                    holder.setVisible(R.id.item_coupon_list_ivShiyong, true);
                    holder.setVisible(R.id.item_coupon_list_ivGuoqi, false);
                }
                //已过期优惠券type：2
                if (type.equals("2")) {
                    holder.setBackgroundRes(R.id.item_coupon_list_llBg, R.drawable.bk_yilingqu);
                    holder.setTextColorRes(R.id.item_coupon_list_tvMoney, R.color.grey_color3);
                    holder.setTextColorRes(R.id.item_coupon_list_tvSign, R.color.grey_color3);
                    holder.getView(R.id.item_coupon_list_tvUse).setVisibility(View.GONE);
                    holder.setVisible(R.id.item_coupon_list_bg, false);
                    holder.setVisible(R.id.item_coupon_list_ivGuoqi, true);
                    holder.setVisible(R.id.item_coupon_list_ivShiyong, false);
                }
            }
        };
        mXRecyclerView.setAdapter(adapter);
    }

    private void jsonNum() {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", MyApplication.getInstance().getLoginKey());
        try {
            OkHttpUtils.post().url(Api.URL_DIS_NUM).params(params).build().execute(new CommonCallback<CouponsNumBean>() {
                @Override
                public void onError(Call call, Exception e) {
                    mLoadPage.switchPage(LoadPage.STATE_NO_NET);

                }

                @Override
                public void onResponse(HttpResult<CouponsNumBean> response) {
                    if (response.isSuccess()) {
                        mLoadPage.switchPage(LoadPage.STATE_HIDE);
                        if (response.data != null) {
                            tabs.add(new TabBean("0", "未使用" + " " + "（" + response.data.ongoing + "）"));
                            tabs.add(new TabBean("1", "已使用" + " " + "（" + response.data.used + "）"));
                            tabs.add(new TabBean("2", "已过期" + " " + "（" + response.data.expired + "）"));
                            for (TabBean tab : tabs) {
                                mTabLayout.addTab(mTabLayout.newTab().setText(tab.title));
                            }
                        }
                    } else {
                        ToastUtil.toast(response.msg);
                    }
                }
            });
        } catch (Exception e) {
            mLoadPage.switchPage(LoadPage.STATE_NO_NET);
            e.printStackTrace();
        }
    }

    private void loadData() {
        final HashMap<String, String> params = new HashMap<>();
        params.put("token", MyApplication.getInstance().getLoginKey());
        params.put("page", mPage + "");
        params.put("type", type);

        OkHttpUtils.post().url(Api.URL_DISCOUNTS_LIST).params(params).build().execute(new CommonCallback<CouponsItemBean>() {
            @Override
            public void onError(Call call, Exception e) {
                mLoadPage.switchPage(LoadPage.STATE_NO_NET);
                mXRecyclerView.refreshComplete();
                mXRecyclerView.loadMoreComplete();
            }

            @Override
            public void onResponse(HttpResult<CouponsItemBean> response) {
                try {
                    mXRecyclerView.refreshComplete();
                    mXRecyclerView.loadMoreComplete();

                    if (response.isSuccess()) {
                        mLoadPage.switchPage(LoadPage.STATE_HIDE);
                        if (response.data != null && response.data.couponlist != null && response.data.couponlist.size() > 0) {
                            if (mPage == 1) couponlist.clear();
                            couponlist.addAll(response.data.couponlist);
                            mXRecyclerView.setLoadingMoreEnabled(true);
                            adapter.notifyDataSetChanged();
                            isNodata = false;

                        } else {
                            if (mPage == 1) {
                                mLoadPage.switchPage(LoadPage.STATE_NO_DATA);
                                isNodata = true;
                            } else {
                                isNodata = false;
                                mXRecyclerView.setLoadingMoreEnabled(false);
                            }
                        }
                    } else {
                        ToastUtil.toast(response.msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    mLoadPage.switchPage(LoadPage.STATE_NO_NET);
                }
            }
        });


    }

    @Override
    public void initListener() {
        mXRecyclerView.setLoadingListener(this);
        tv_coupons_center.setOnClickListener(this);
        tv_coupons_exchange.setOnClickListener(this);
    }

    @Override
    public void initData() {
        jsonNum();
        loadData();
    }

    @OnClick({R.id.iv_left, R.id.tv_right})
    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left://返回
                finish();
                break;
            case R.id.tv_right://优惠券使用规则
                if (CommonUtils.checkLogin())
                    CommonUtils.startAct(CouponListActivity.this, CommonFragmentActivity.FRAGMENT_DO_RULE);
                break;
            case R.id.tv_coupons_center://跳转到领券中心
                startActivity(new Intent(mContext, CouponsCenterActivity.class));
                break;
            case R.id.tv_coupons_exchange://券码兑换
                showExchangePopupwindow();
                break;
            default:
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (!isFirstLoad) {
            mPage = 1;
            loadData();
        }
        isFirstLoad = false;
    }

    @Override
    public void onRefresh() {
        mPage = 1;
        loadData();
    }

    @Override
    public void onLoadMore() {
        mPage++;
        loadData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mTabLayout.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(mTabLayout, 10, 10);
            }
        });
    }

    /**
     * 设置Tablayout下划线的方法
     *
     * @param tabs
     * @param leftDip
     * @param rightDip
     */
    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }

    /**
     * 兑换码弹窗
     */
    CouponExchangeDialog ce_dialog;
    CouponExchangeDialog.Builder builder;
    View viewDialog;

    private void showExchangePopupwindow() {
        viewDialog = LayoutInflater.from(mContext).inflate(R.layout.coupon_center_exchange_dialog, null, false);
        builder = new CouponExchangeDialog.Builder(mContext);
        ce_dialog = builder.setContentView(viewDialog)
                .setCancelListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //关闭弹窗
                        dialog.dismiss();
                    }
                }).setReceiveListener(new DialogInterface.OnClickListener() {
                    //兑换
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CommonUtils.hideInputMode(CouponListActivity.this, true);
                        showCodeToast();

                    }
                }).setVerificationCode(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
        ce_dialog.setCancelable(false);
        ce_dialog.show();
        ce_dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        ce_dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    }

    /**
     * 兑换提示
     */
    private void showCodeToast() {
        //立即兑换
        switch (builder.inPutCode(builder.changeIV())) {
            case 1://立即兑换
                getRedeemCode();
                break;
            case 2:
                CustomToast.toast(mContext, "验证码错误！");
                break;
            case 3:
                CustomToast.toast(mContext, "兑换码不能为空！");
                break;
            case 4:
                CustomToast.toast(mContext, "验证码不能为空！");
                break;
            case 5:
                break;
            default:
                break;

        }
    }


    /**
     * 立即兑换接口
     */

    private void getRedeemCode() {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", MyApplication.getInstance().getLoginKey());
        params.put("redeemcode", builder.mredcode);
        params.put("code", builder.mver);
        OkHttpUtils.post().url(Api.URL_COUPONS_REDEEM).params(params).build().execute(new CommonCallback<CouponsCenterBean>() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(HttpResult<CouponsCenterBean> response) {
                CustomToast.toast(mContext, response.msg);
                if (!CommonUtils.isEmpty(response.code) && "0066".equals(response.code)) {
                    if (ce_dialog != null) {
                        ce_dialog.dismiss();
                    }
                    onrefreshdata();
                }
            }
        });

    }

    /**
     * 从新刷新未使用和优惠券个数几口
     */
    private void onrefreshdata() {
        couponlist = new ArrayList<>();
        mLoadPage.switchPage(LoadPage.STATE_LOADING);
        jsonNum();
        loadData();
    }
}
