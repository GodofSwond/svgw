package com.svgouwu.client.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kili.okhttp.OkHttpUtils;
import com.svgouwu.client.Api;
import com.svgouwu.client.BaseActivity;
import com.svgouwu.client.MyApplication;
import com.svgouwu.client.R;
import com.svgouwu.client.bean.HttpResult;
import com.svgouwu.client.bean.PayGetCoupon;
import com.svgouwu.client.utils.CommonCallback;
import com.svgouwu.client.utils.CommonUtils;
import com.svgouwu.client.utils.LogUtils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by topwolf on 2017/8/14.
 */

public class PayCallBackActivity extends BaseActivity {

    @BindView(R.id.iv_left)
    ImageView iv_left;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_order_status)
    TextView tv_order_status;
    @BindView(R.id.iv_pay_status)
    ImageView iv_pay_status;
    @BindView(R.id.rl_ps_coupon)
    RelativeLayout rl_ps_coupon;//获取优惠券rl
    @BindView(R.id.tv_ps_cp_title)
    TextView tv_ps_cp_title;//优惠券标题
    @BindView(R.id.tv_sp_cp_content)
    TextView tv_sp_cp_content;//优惠券内容

    private boolean isWxPay;
    private String paytradeno;
    private String orderList;
    PayGetCoupon payGetCoupon;

    @Override
    protected int getContentView() {
        return R.layout.activity_pay_callback;
    }

    @Override
    public void initViews() {
        tv_title.setText("付款成功");
    }

    @Override
    public void initListener() {
        rl_ps_coupon.setOnClickListener(this);
    }

    @Override
    public void initData() {
        //商品id
        if (getIntent().hasExtra("orderList")) {
            orderList = getIntent().getStringExtra("orderList");
        }
        if (getIntent().hasExtra("paytradeno")) {
            paytradeno = getIntent().getStringExtra("paytradeno");
        }
        isWxPay = getIntent().getBooleanExtra("isWxPay", false);
        if (TextUtils.isEmpty(paytradeno)) {
            return;
        }
        String url = Api.URL_ALIPAY_CALLBACK;
        LogUtils.e("isWxPay===" + isWxPay);
        if (isWxPay) {
            url = Api.URL_WX_CALLBACK;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("token", MyApplication.getInstance().getLoginKey());
        params.put("paytradeno", paytradeno);

        OkHttpUtils.post().url(url).params(params).build().execute(new CommonCallback<Object>() {
            @Override
            public void onError(Call call, Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(HttpResult<Object> response) {
                iv_pay_status.setVisibility(View.VISIBLE);
                try {
                    if (response.isSuccess()) {
                        iv_pay_status.setBackgroundResource(R.drawable.ic_pay_success);
                        tv_order_status.setText("付款成功");
                        getCoupon();
                    } else {
                        iv_pay_status.setBackgroundResource(R.drawable.ic_pay_error);
                        tv_order_status.setText("付款失败");
                    }
                } catch (Exception e) {
                    iv_pay_status.setBackgroundResource(R.drawable.ic_pay_error);
                    tv_order_status.setText("付款失败");
                    e.printStackTrace();
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

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.rl_ps_coupon:
                //点击领券跳转
                Log.d("whq","hahahh");
                startActivity(new Intent(mContext, CouponListActivity.class));
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 支付成功后获取优惠券
     */
    private void getCoupon() {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", MyApplication.getInstance().getLoginKey());
        params.put("orderList", orderList);
        String url = Api.URL_PAY_SUCCESS_GETCOUPON;
        OkHttpUtils.post().url(url).params(params).build().execute(new CommonCallback<PayGetCoupon>() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(HttpResult<PayGetCoupon> response) {
                try {
                    if (response.isSuccess()) {
                        rl_ps_coupon.setVisibility(View.VISIBLE);
                        if (response.data != null) {
                            payGetCoupon = response.data;
                            if (!CommonUtils.isEmpty(payGetCoupon.getTitle())) {
                                tv_ps_cp_title.setText("恭喜您获得一张" + payGetCoupon.getTitle());
                            }
                            if (!CommonUtils.isEmpty(payGetCoupon.getCoupon_value()) &&
                                    !CommonUtils.isEmpty(payGetCoupon.getMin_amount())) {
                                tv_sp_cp_content.setText("满" + payGetCoupon.getMin_amount() +
                                        "减" + payGetCoupon.getCoupon_value());
                            }
                        }

                    } else {
                        rl_ps_coupon.setVisibility(View.GONE);
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(mContext, MainActivity.class)
                                        .putExtra("index", MainActivity.FRAGMENT_HOME));
                                finish();
                            }
                        }, 3000);
                        Log.d("whq", "返回code码为：" + response.code);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
