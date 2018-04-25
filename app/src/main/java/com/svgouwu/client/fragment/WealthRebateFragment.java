package com.svgouwu.client.fragment;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kili.okhttp.OkHttpUtils;
import com.lzy.imagepicker.view.SystemBarTintManager;
import com.svgouwu.client.Api;
import com.svgouwu.client.BaseFragment;
import com.svgouwu.client.CommonFragmentActivity;
import com.svgouwu.client.MyApplication;
import com.svgouwu.client.R;
import com.svgouwu.client.adapter.WealthRebateAdapter;
import com.svgouwu.client.bean.HttpResult;
import com.svgouwu.client.bean.WealthApplyBean;
import com.svgouwu.client.bean.WealthRebateBean;
import com.svgouwu.client.utils.CommonCallback;
import com.svgouwu.client.utils.CommonUtils;
import com.svgouwu.client.utils.ToastUtil;
import com.svgouwu.client.view.LoadPage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/11/17.
 * 我的返利
 */

public class WealthRebateFragment extends BaseFragment {

    private int page = 1;//页
    private List<WealthRebateBean.RebateListBean> mList = new ArrayList<>();
    private WealthRebateAdapter rebateAdapter;
    private String dong_jie_money, ke_tixian_money, total_money, have_tixian_money;
    private String code;
    private String msg;

    @BindView(R.id.rl_topbar_title)
    RelativeLayout rlTopbar;
    @BindView(R.id.vXian)
    View vXian;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.fragment_money_rebate_rl)
    LinearLayout linearLayout;
    @BindView(R.id.fragment_rebate_tvAlreadyMoney)
    TextView tvAlreadyMoney;
    @BindView(R.id.fragment_rebate_tvCanMoney)
    TextView tvCanMoney;
    @BindView(R.id.fragment_rebate_tvFreezeMoney)
    TextView tvFreezeMoney;
    @BindView(R.id.mLoadPage)
    LoadPage mLoadPage;
    @BindView(R.id.fragment_money_rebate_xrecycleView)
    XRecyclerView xrecycleView;

    @Override
    protected int getContentView() {
        return R.layout.fragment_wealth_rebate;
    }

    @Override
    public void initViews() {
        myStatusBar();//状态栏设置
        tvTitle.setText("我的返利");
        tvTitle.setTextColor(getResources().getColor(R.color.color_bg));
        rlTopbar.setBackgroundResource(R.color.color_red);
        vXian.setBackgroundResource(R.color.color_red);
        ivLeft.setImageResource(R.drawable.icon_back_btn_normal);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        xrecycleView.setLayoutManager(layoutManager);
        rebateAdapter = new WealthRebateAdapter(getContext(), R.layout.item_wealth_rebate, mList);
        xrecycleView.setAdapter(rebateAdapter);
        xrecycleView.setPullRefreshEnabled(false);
        mLoadPage.setGetDataListener(new LoadPage.GetDataListener() {
            @Override
            public void onGetData() {
                mLoadPage.switchPage(LoadPage.STATE_LOADING);
                jsonXData();
            }
        });
        mLoadPage.switchPage(LoadPage.STATE_LOADING);
        xrecycleView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                jsonXData();
            }

            @Override
            public void onLoadMore() {
                page++;
                jsonXData();
            }
        });
    }

    /**
     * 状态栏设置跟随颜色
     */
    private void myStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(getActivity());
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.color_red);//状态栏所需颜色

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        jsonSData();
        jsonAgencyData();
    }

    /**
     * 请求接口数据
     */
    private void jsonSData() {
        final HashMap<String, String> params = new HashMap<>();
        params.put("token", MyApplication.getInstance().getLoginKey());
        params.put("page", page + "");
        try {
            OkHttpUtils.post().url(Api.URL_WEALTH_REBATE).params(params).build().execute(new CommonCallback<WealthRebateBean>() {
                @Override
                public void onError(Call call, Exception e) {
                    mLoadPage.switchPage(LoadPage.STATE_NO_NET);

                }

                @Override
                public void onAfter() {
                    super.onAfter();
                    cancelWeiXinDialog();
                    xrecycleView.loadMoreComplete();
                }

                @Override
                public void onResponse(HttpResult<WealthRebateBean> response) {
                    mLoadPage.switchPage(LoadPage.STATE_HIDE);
                    if (response.isSuccess()) {
                        WealthRebateBean.Account account = response.data.account;
                        total_money = account.total_money;//总金额
                        ke_tixian_money = account.ke_tixian_money;//可提现金额
                        have_tixian_money = account.have_tixian_money;//已提现金额
                        dong_jie_money = account.dong_jie_money;//冻结金额

                        tvAlreadyMoney.setText(have_tixian_money);
                        tvCanMoney.setText(ke_tixian_money);
                        tvFreezeMoney.setText(dong_jie_money);

                    } else {
                        ToastUtil.toast(response.msg);//请求失败提示信息
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            mLoadPage.switchPage(LoadPage.STATE_NO_NET);
        }
    }

    /**
     * 请求接口数据
     */
    private void jsonXData() {
        final HashMap<String, String> params = new HashMap<>();
        params.put("token", MyApplication.getInstance().getLoginKey());
        params.put("page", page + "");
        try {
            OkHttpUtils.post().url(Api.URL_WEALTH_REBATE).params(params).build().execute(new CommonCallback<WealthRebateBean>() {
                @Override
                public void onError(Call call, Exception e) {
                    mLoadPage.switchPage(LoadPage.STATE_NO_NET);
                }

                @Override
                public void onAfter() {
                    super.onAfter();
                    cancelWeiXinDialog();
                    xrecycleView.loadMoreComplete();
                }

                @Override
                public void onResponse(HttpResult<WealthRebateBean> response) {
                    mLoadPage.switchPage(LoadPage.STATE_HIDE);
                    if (response.isSuccess()) {
                        WealthRebateBean.Account account = response.data.account;
                        total_money = account.total_money;//总金额
                        ke_tixian_money = account.ke_tixian_money;//可提现金额
                        have_tixian_money = account.have_tixian_money;//已提现金额
                        dong_jie_money = account.dong_jie_money;//冻结金额

                        tvAlreadyMoney.setText(have_tixian_money);
                        tvCanMoney.setText(ke_tixian_money);
                        tvFreezeMoney.setText(dong_jie_money);

                        if (response.data.list.size() != 0) {
                            mList.addAll(response.data.list);
                            xrecycleView.setLoadingMoreEnabled(true);
                            rebateAdapter.notifyDataSetChanged();
                        } else {
                            xrecycleView.setLoadingMoreEnabled(false);
                            return;
                        }
                        if (mList.size() == 0) {//如果数据为空则显示无数据界面提示，反之隐藏
                            linearLayout.setVisibility(View.VISIBLE);
                        } else {
                            linearLayout.setVisibility(View.GONE);
                        }
                    } else {
                        ToastUtil.toast(response.msg);//请求失败提示信息
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            mLoadPage.switchPage(LoadPage.STATE_NO_NET);
        }
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        jsonAgencyData();
        jsonXData();
    }

    /**
     * 解析数据金额和代理提示数据
     */
    private void jsonAgencyData() {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", MyApplication.getInstance().getLoginKey());
        OkHttpUtils.post().url(Api.URL_WEALTH_CLEARMONEY).params(params).build().execute(new CommonCallback<WealthApplyBean>() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(HttpResult<WealthApplyBean> response) {
                code = response.code;
                msg = response.msg;
            }
        });
    }

    @OnClick({R.id.fragment_money_rebate_tvApply, R.id.iv_left})
    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_money_rebate_tvApply:
                if (code != null) {
                    if (code.equals("0070") || code.equals("0069")) {
                        ToastUtil.toast(msg);
                        Log.i("TAG", "processClick: " + code);
                    } else {
                        CommonUtils.startAct(getContext(), CommonFragmentActivity.FRAGMENT_APPLY);//提现申请
                    }
                } else {
                    ToastUtil.toast("请连接网络");
                }
                break;
            case R.id.iv_left:
                getActivity().finish();
                break;
        }
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getActivity().getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

}
