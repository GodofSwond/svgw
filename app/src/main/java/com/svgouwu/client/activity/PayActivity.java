package com.svgouwu.client.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.kili.okhttp.OkHttpUtils;
import com.svgouwu.client.Api;
import com.svgouwu.client.BaseActivity;
import com.svgouwu.client.Constant;
import com.svgouwu.client.MyApplication;
import com.svgouwu.client.R;
import com.svgouwu.client.bean.AlipayResult;
import com.svgouwu.client.bean.HttpResult;
import com.svgouwu.client.bean.PayOrderList;
import com.svgouwu.client.bean.PayResult;
import com.svgouwu.client.bean.SpreedData;
import com.svgouwu.client.bean.WxPayResult;
import com.svgouwu.client.utils.CommonCallback;
import com.svgouwu.client.utils.CommonUtils;
import com.svgouwu.client.utils.LogUtils;
import com.svgouwu.client.utils.SpUtil;
import com.svgouwu.client.utils.ToastUtil;
import com.svgouwu.client.utils.UmengStat;
import com.svgouwu.client.view.LoadPage;
import com.svgouwu.client.view.MyRadioGroup;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by topwolf on 2017/8/8.
 * 支付页面
 */

public class PayActivity extends BaseActivity {

    private static final int SDK_PAY_FLAG = 1;
    private static final String PAY_ALI = "pay_ali";
    private static final String PAY_WX = "pay_wx";
    private String orderIds;
    private String orderSns;
    PayOrderList payOrderInfo;
    @BindView(R.id.iv_left)
    ImageView iv_left;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.rg_payment)
    MyRadioGroup rg_payment;
    @BindView(R.id.rl_ali)
    RelativeLayout rl_ali;
    @BindView(R.id.rl_wx)
    RelativeLayout rl_wx;
    @BindView(R.id.tv_money)
    TextView tv_money;
    @BindView(R.id.tv_order_desc)
    TextView tv_order_desc;
    @BindView(R.id.rb_ali)
    RadioButton rb_ali;
    @BindView(R.id.rb_wx)
    RadioButton rb_wx;
    @BindView(R.id.btn_pay)
    Button btn_pay;
    @BindView(R.id.mLoadPage)
    LoadPage mLoadPage;

    private String payment = PAY_WX;
    private String paytradeno;
    private String amount;
    private IWXAPI api;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//                        ToastUtil.toast("支付成功");
                        Intent intent = new Intent(PayActivity.this, PayCallBackActivity.class);
                        intent.putExtra("paytradeno", paytradeno);
                        intent.putExtra("orderList", orderIds);
                        startActivity(intent);
                        OrderRebate();
                        // finish();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        ToastUtil.toast("支付失败");
                    }
                    break;
                }
            }
        }
    };


    @Override
    protected int getContentView() {
        return R.layout.activity_pay;
    }

    @Override
    public void initViews() {

        api = WXAPIFactory.createWXAPI(this, null);
        api.registerApp(Constant.APP_ID_WX);

        if (getIntent().hasExtra("orderIds")) {
            orderIds = getIntent().getStringExtra("orderIds");
        }
       /*
       10.28上线，订单编号从接口中获取，不再从上个页面传
       if (getIntent().hasExtra("orderSns")) {
            orderSns = getIntent().getStringExtra("orderSns");
        }*/
        if (getIntent().hasExtra("amount")) {
            amount = getIntent().getStringExtra("amount");
            Log.i("TAGPAY", "amount: " + amount);

        }
        tv_title.setText("支付订单");
        mLoadPage.setGetDataListener(new LoadPage.GetDataListener() {
            @Override
            public void onGetData() {
                mLoadPage.switchPage(LoadPage.STATE_LOADING);
                //    initData();
                getPayInfo();
            }
        });
        mLoadPage.switchPage(LoadPage.STATE_LOADING);
        getPayInfo();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
       /* tv_money.setText("￥" + amount);
        tv_order_desc.setText("四维购物 - 订单编号:" + orderSns);*/
    }

    @OnClick({R.id.rl_ali, R.id.iv_left, R.id.rb_ali, R.id.rb_wx, R.id.btn_pay})
    @Override
    public void processClick(View view) {

        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.rb_ali:
                payment = PAY_ALI;
                break;
            case R.id.rb_wx:
                payment = PAY_WX;
                break;
            case R.id.btn_pay:
                MobclickAgent.onEvent(PayActivity.this, UmengStat.CONFIRMTHEPAYMENT);
                if (payment.equals(PAY_ALI)) {
                    getAlipayInfo();
                } else if (payment.equals(PAY_WX)) {
                    getWxPayInfo();
                }
                break;
        }
    }

    /**
     * 支付宝支付
     */
    private void getAlipayInfo() {
        String url = Api.URL_ALIPAY;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", MyApplication.getInstance().getLoginKey());
        params.put("orderList", orderIds);

        OkHttpUtils.post().url(url).params(params).build().execute(new CommonCallback<AlipayResult>() {
            @Override
            public void onError(Call call, Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(HttpResult<AlipayResult> response) {
                try {
                    if (response.isSuccess()) {
                        paytradeno = response.data.paytradeno;
                        alipay(response.data.orderString);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void alipay(final String orderInfo) {
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(PayActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                LogUtils.e("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 微信支付
     */
    private void getWxPayInfo() {
        btn_pay.setEnabled(false);
        String url = Api.URL_WX;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", MyApplication.getInstance().getLoginKey());
        params.put("orderList", orderIds);

        OkHttpUtils.post().url(url).params(params).build().execute(new CommonCallback<WxPayResult>() {
            @Override
            public void onError(Call call, Exception e) {
                e.printStackTrace();
                btn_pay.setEnabled(true);
            }

            @Override
            public void onResponse(HttpResult<WxPayResult> response) {
                try {
                    if (response.isSuccess()) {
                        WxPayResult result = response.data;
                        if (result != null) {
                            SpUtil.setString(PayActivity.this, "paytradeno", result.paytradeno);
                            SpUtil.setString(PayActivity.this, "orderList", orderIds);
                            PayReq request = new PayReq();
                            request.appId = result.appid;
                            request.partnerId = result.partnerid;
                            request.prepayId = result.prepayid;
                            request.packageValue = result.packageValue;
                            request.nonceStr = result.noncestr;
                            request.timeStamp = result.timestamp;
                            request.sign = result.sign;
                            api.sendReq(request);
                            Log.i("result", "result: " + response.msg);
                            finish();
                        }
                    } else {
                        ToastUtil.toast(response.msg);
                    }
                } catch (Exception e) {
                    btn_pay.setEnabled(true);
                    Log.i("result", "result: " + response.msg);
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        btn_pay.setEnabled(true);
        MobclickAgent.onPageStart(UmengStat.PAYMENTPAGE); //统计页面
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(UmengStat.PAYMENTPAGE);
    }

    /**
     * 支付订单页，订单信息
     */
    private void getPayInfo() {
        String url = Api.URL_PAY_ORDERLIST;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", MyApplication.getInstance().getLoginKey());
        params.put("orderList", orderIds);
        OkHttpUtils.post().url(url).params(params).build().execute(new CommonCallback<PayOrderList>() {

            @Override
            public void onError(Call call, Exception e) {
                mLoadPage.switchPage(LoadPage.STATE_NO_NET);
            }

            @Override
            public void onResponse(HttpResult<PayOrderList> response) {
                try {
                    if (response.isSuccess()) {
                        mLoadPage.switchPage(LoadPage.STATE_HIDE);
                        if (response.data != null) {
                            payOrderInfo = response.data;
                            fillData();
                        }
                    } else {
                        Log.d("whq", "==" + response.code);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    mLoadPage.switchPage(LoadPage.STATE_NO_NET);
                    Log.d("whq", "----" + e.toString());
                }
            }
        });

    }

    private void fillData() {
        StringBuilder sb2 = new StringBuilder();//要购买的商品记录ID
        if (payOrderInfo.getOrderSn() != null) {
            for (int i = 0; i < payOrderInfo.getOrderSn().size(); i++) {
                sb2.append(payOrderInfo.getOrderSn().get(i) + ",");
            }
        }
        String orderSns = sb2.substring(0, sb2.lastIndexOf(","));
        tv_money.setText("￥" + payOrderInfo.getTotalFee());
        tv_order_desc.setText("四维购物 - 订单编号:" + orderSns);
    }

    /**
     * 订单返利
     * paytradeno--交易号
     * code--绑定关系
     */
    private void OrderRebate() {
        HashMap<String, String> map = new HashMap<>();
        map.put("token", MyApplication.getInstance().getLoginKey());
        map.put("code", SpUtil.getString(mContext, "coded"));
        map.put("paytradeno", paytradeno);
        OkHttpUtils.post().url(Api.URL_ORDER_REBATE).params(map).build().execute(new CommonCallback<SpreedData>() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(HttpResult<SpreedData> response) {
                try {
                    Log.d("whq", "--bindagency---" + response.msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        if (!CommonUtils.isEmpty(SpUtil.getString(mContext, "coded"))) {
            //支付成功脱离关系
            SpUtil.setString(mContext, "coded", "");
        }
        finish();
    }
}
