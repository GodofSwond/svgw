package com.svgouwu.client.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kili.okhttp.OkHttpUtils;
import com.svgouwu.client.Api;
import com.svgouwu.client.BaseActivity;
import com.svgouwu.client.CommonFragmentActivity;
import com.svgouwu.client.MyApplication;
import com.svgouwu.client.R;
import com.svgouwu.client.bean.CouponsCenterBean;
import com.svgouwu.client.bean.HttpResult;
import com.svgouwu.client.bean.OutofnumBean;
import com.svgouwu.client.bean.ReceivedBean;
import com.svgouwu.client.bean.UnreceivedBean;
import com.svgouwu.client.utils.CommonCallback;
import com.svgouwu.client.utils.CommonUtils;
import com.svgouwu.client.utils.ToastUtil;
import com.svgouwu.client.view.LoadPage;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/9/27.
 * 领券中心
 */

public class CouponsCenterActivity extends BaseActivity {

    private String code;
    private CommonAdapter<UnreceivedBean> uAdapter;
    private CommonAdapter<OutofnumBean> oAdapter;
    private CommonAdapter<ReceivedBean> rAdapter;
    private AlertDialog dialog;
    private HashMap<String, String> params;
    private String couponId;
    private boolean b = true;

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_nodata_ll)
    LinearLayout llNodata;
    @BindView(R.id.mLoadPage)
    LoadPage mLoadPage;
    @BindView(R.id.activity_couCenter_xrvUnreceived)
    XRecyclerView xrvUnreceived;
    @BindView(R.id.activity_couCenter_xrvReceived)
    XRecyclerView xrvReceived;
    @BindView(R.id.activity_couCenter_xrvOutofnum)
    XRecyclerView xrvOutofnum;


    @Override
    protected int getContentView() {
        return R.layout.activity_coupons_center;
    }

    @Override
    public void initViews() {
        tv_title.setText("领券中心");
        LinearLayoutManager uLayoutManager = new LinearLayoutManager(this);
        uLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xrvUnreceived.setLayoutManager(uLayoutManager);
        xrvUnreceived.setPullRefreshEnabled(false);
        xrvUnreceived.setLoadingMoreEnabled(false);
        LinearLayoutManager rLayoutManager = new LinearLayoutManager(this);
        rLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xrvReceived.setLayoutManager(rLayoutManager);
        xrvReceived.setPullRefreshEnabled(false);
        xrvReceived.setLoadingMoreEnabled(false);
        LinearLayoutManager oLayoutManager = new LinearLayoutManager(this);
        oLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xrvOutofnum.setLayoutManager(oLayoutManager);
        xrvOutofnum.setPullRefreshEnabled(false);
        xrvOutofnum.setLoadingMoreEnabled(false);

        mLoadPage.setGetDataListener(new LoadPage.GetDataListener() {
            @Override
            public void onGetData() {
                mLoadPage.switchPage(LoadPage.STATE_LOADING);
                initData();
            }
        });
        mLoadPage.switchPage(LoadPage.STATE_LOADING);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        jsonData();
    }

//  @Override
//    protected void onResume() {
//        super.onResume();
//        showSuccData();
//        jsonData();
//    }

    @Override
    protected void onRestart() {
        super.onRestart();
        showSuccData();
        jsonData();
    }

    private void showSuccData() {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", MyApplication.getInstance().getLoginKey());
        params.put("couponid", couponId);
        OkHttpUtils.post().url(Api.URL_COUPONS_GETCOUPON).params(params).build().execute(new CommonCallback<CouponsCenterBean>() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(HttpResult<CouponsCenterBean> response) {
                code = response.code;
                if (code.equals("0056")) {
                    if (b && couponId != null) {
                        ToastUtil.toast(response.msg);
                        jsonData();
                        b = false;
                    }
                }
                if (code.equals("0055")) {
                    if (b && couponId != null) {
                        ToastUtil.toast(response.msg);
                        b = false;
                    }
                }
                if (code.equals("0060")) {
                    if (b && couponId != null) {
                        ToastUtil.toast(response.msg);
                        b = false;
                    }
                }
            }
        });
    }


    private void jsonData() {
        params = new HashMap<String, String>();
        String loginKey = MyApplication.getInstance().getLoginKey();
        params.put("token", loginKey);
        OkHttpUtils.post().url(Api.URL_COUPONS_CENTER).params(params).build().execute(new CommonCallback<CouponsCenterBean>() {
            @Override
            public void onError(Call call, Exception e) {
                mLoadPage.switchPage(LoadPage.STATE_NO_NET);
            }

            @Override
            public void onResponse(final HttpResult<CouponsCenterBean> response) {
                MyApplication.getInstance().unreceived = response.data.unreceived;
                MyApplication.getInstance().received = response.data.received;
                MyApplication.getInstance().outofnum = response.data.outofnum;
                mLoadPage.switchPage(LoadPage.STATE_HIDE);
                if (response.isSuccess()) {
                    if (response.data != null) {
                        unreceivedData();
                        receivedData();
                        outofnumData();
                    } else {
                        mLoadPage.switchPage(LoadPage.STATE_NO_DATA);
                    }
                    if (response.data.unreceived.size() == 0 && response.data.outofnum.size() == 0 && response.data.received.size() == 0) {
                        llNodata.setVisibility(View.VISIBLE);
                    } else {
                        llNodata.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    private void unreceivedData() {
        uAdapter = new CommonAdapter<UnreceivedBean>(CouponsCenterActivity.this, R.layout.item_coupons_unreceived, MyApplication.getInstance().unreceived) {
            @Override
            protected void convert(final ViewHolder holder, final UnreceivedBean unreceivedBean, final int position) {
                holder.setText(R.id.item_couCenter_tvMoney, unreceivedBean.couponValue);
                holder.setText(R.id.item_couCenter_tvName, unreceivedBean.couponName);
                holder.setText(R.id.item_couCenter_tvTime, unreceivedBean.startTime + "至" + unreceivedBean.endTime);
                holder.setText(R.id.item_couCenter_tvHundred, "满" + unreceivedBean.minAmount + "使用");
                holder.getView(R.id.item_couCenter_tvDraw).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {//登陆：0021
                        postData(unreceivedBean, holder);
                        couponId = unreceivedBean.couponId;
                    }
                });

                holder.getView(R.id.item_couCenter_tvUse).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intentU = new Intent(CouponsCenterActivity.this, CommonFragmentActivity.class);
                        intentU.putExtra(CommonFragmentActivity.TARGET, CommonFragmentActivity.FRAGMENT_USE);
                        intentU.putExtra("useMobileUrl", unreceivedBean.useMobileUrl);
                        startActivity(intentU);
                    }
                });
            }
        };
        xrvUnreceived.setAdapter(uAdapter);
        uAdapter.notifyDataSetChanged();
    }

    private void receivedData() {
        rAdapter = new CommonAdapter<ReceivedBean>(CouponsCenterActivity.this, R.layout.item_coupons_received, MyApplication.getInstance().received) {
            @Override
            protected void convert(final ViewHolder holder, final ReceivedBean receivedBean, int position) {
                holder.setText(R.id.item_couCenter_tvMoney, receivedBean.couponValue);
                holder.setText(R.id.item_couCenter_tvName, receivedBean.couponName);
                holder.setText(R.id.item_couCenter_tvTime, receivedBean.startTime + "至" + receivedBean.endTime);
                holder.setText(R.id.item_couCenter_tvHundred, "满" + receivedBean.minAmount + "使用");
                holder.getView(R.id.item_couCenter_tvUse).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intentR = new Intent(CouponsCenterActivity.this, CommonFragmentActivity.class);
                        intentR.putExtra(CommonFragmentActivity.TARGET, CommonFragmentActivity.FRAGMENT_USE);
                        intentR.putExtra("useMobileUrl", receivedBean.useMobileUrl);
                        startActivity(intentR);
                    }
                });
            }
        };
        xrvReceived.setAdapter(rAdapter);
        rAdapter.notifyDataSetChanged();
    }

    private void outofnumData() {
        oAdapter = new CommonAdapter<OutofnumBean>(CouponsCenterActivity.this, R.layout.item_coupons_outofnum, MyApplication.getInstance().outofnum) {
            @Override
            protected void convert(final ViewHolder holder, final OutofnumBean outofnumBean, int position) {
                holder.setText(R.id.item_couCenter_tvMoney, outofnumBean.couponValue);
                holder.setText(R.id.item_couCenter_tvName, outofnumBean.couponName);
                holder.setText(R.id.item_couCenter_tvTime, outofnumBean.startTime + "至" + outofnumBean.endTime);
                holder.setText(R.id.item_couCenter_tvHundred, "满" + outofnumBean.minAmount + "使用");

                holder.getView(R.id.item_couCenter_tvCome).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intentO = new Intent(CouponsCenterActivity.this, CommonFragmentActivity.class);
                        intentO.putExtra(CommonFragmentActivity.TARGET, CommonFragmentActivity.FRAGMENT_USE);
                        intentO.putExtra("useMobileUrl", outofnumBean.useMobileUrl);
                        startActivity(intentO);
                    }
                });
            }
        };
        xrvOutofnum.setAdapter(oAdapter);
        oAdapter.notifyDataSetChanged();
    }


    private void postData(UnreceivedBean unreceivedBean, final ViewHolder holder) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", MyApplication.getInstance().getLoginKey());
        params.put("couponid", unreceivedBean.couponId);
        OkHttpUtils.post().url(Api.URL_COUPONS_GETCOUPON).params(params).build().execute(new CommonCallback<CouponsCenterBean>() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(HttpResult<CouponsCenterBean> response) {
                code = response.code;

                if (code.equals("0021")) {
                    CommonUtils.checkLogin();
                }
                if (code.equals("0056")) {
                    holder.setVisible(R.id.item_couCenter_tvUse, true);
                    holder.setVisible(R.id.item_couCenter_tvDraw, false);
                    ToastUtil.toast(response.msg);
                }
                if (code.equals("0055")) {
//                    ToastUtil.toast(response.msg);
                }
                if (code.equals("0060")) {
//                    ToastUtil.toast(response.msg);
                }
            }
        });
    }

    @OnClick({R.id.iv_left})
    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
        }
    }

    /**
     * Dialog展示并延时取消
     */
    private void showDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(CouponsCenterActivity.this);
        RelativeLayout relativeLayout = (RelativeLayout) layoutInflater.inflate(R.layout.item_coupons_center_dialog, null);
        dialog = new AlertDialog.Builder(CouponsCenterActivity.this, R.style.add_coupons_center_dialog).create();
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
//        lp.x = 100; // 新位置X坐标
//        lp.y = 300; // 新位置Y坐标
        dialogWindow.setAttributes(lp);
        dialog.show();
        dialog.getWindow().setContentView(relativeLayout);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);//让他显示1.5秒后，取消Dialog
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        });
        thread.start();
    }

}