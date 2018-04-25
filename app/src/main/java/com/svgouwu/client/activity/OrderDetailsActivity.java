package com.svgouwu.client.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kili.okhttp.OkHttpUtils;
import com.svgouwu.client.Api;
import com.svgouwu.client.BaseActivity;
import com.svgouwu.client.CommonFragmentActivity;
import com.svgouwu.client.MyApplication;
import com.svgouwu.client.OrderManager;
import com.svgouwu.client.R;
import com.svgouwu.client.bean.GoodsInfo;
import com.svgouwu.client.bean.HttpResult;
import com.svgouwu.client.bean.ListDialogItem;
import com.svgouwu.client.bean.OrderDetails;
import com.svgouwu.client.bean.OrderDetailsResult;
import com.svgouwu.client.fragment.CancelOrderDialogFragment;
import com.svgouwu.client.fragment.CommonSureDialogFragment;
import com.svgouwu.client.utils.CommonCallback;
import com.svgouwu.client.utils.CommonUtils;
import com.svgouwu.client.utils.ImageLoader;
import com.svgouwu.client.utils.ToastUtil;
import com.svgouwu.client.utils.UmengStat;
import com.svgouwu.client.view.LoadPage;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

/**
 * 订单详情
 * Created by melon on 2017/7/28.
 * Email 530274554@qq.com
 */

public class OrderDetailsActivity extends BaseActivity implements CancelOrderDialogFragment.OnConfirmListener {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_left)
    ImageView iv_left;
    @BindView(R.id.mLoadPage)
    LoadPage mLoadPage;
    @BindView(R.id.tv_view_logistics)
    TextView tv_view_logistics;
    @BindView(R.id.tv_order_desc)
    TextView tv_order_desc;
    @BindView(R.id.ll_delivery)
    LinearLayout ll_delivery;
    @BindView(R.id.tv_delivery_company)
    TextView tv_delivery_company;
    @BindView(R.id.tv_delivery_order_no)
    TextView tv_delivery_order_no;
    @BindView(R.id.tv_consignee)
    TextView tv_consignee;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.tv_subtotal)
    TextView tv_subtotal;
    @BindView(R.id.tv_goods_count)
    TextView tv_goods_count;
    @BindView(R.id.tv_voucher)
    TextView tv_voucher;
    @BindView(R.id.tv_delivery_type)
    TextView tv_delivery_type;
    @BindView(R.id.tv_payed)
    TextView tv_payed;
    @BindView(R.id.tv_postage)
    TextView tv_postage;
    @BindView(R.id.ll_container)
    LinearLayout ll_container;
    @BindView(R.id.ll_invoice)
    LinearLayout ll_invoice;
    @BindView(R.id.tv_invoice_type)
    TextView tv_invoice_type;
    @BindView(R.id.tv_invoice_head)
    TextView tv_invoice_head;
    @BindView(R.id.tv_invoice_content)
    TextView tv_invoice_content;

    @BindView(R.id.tv_order_no)
    TextView tv_order_no;
    @BindView(R.id.tv_transation_no)
    TextView tv_transation_no;
    @BindView(R.id.tv_pay_type)
    TextView tv_pay_type;
    @BindView(R.id.tv_create_time)
    TextView tv_create_time;
    @BindView(R.id.tv_pay_time)
    TextView tv_pay_time;
    @BindView(R.id.tv_operation_left)
    TextView tv_operation_left;
    @BindView(R.id.tv_operation_right)
    TextView tv_operation_right;

    @BindView(R.id.rl_voucher)
    RelativeLayout rl_voucher;
    @BindView(R.id.rl_transation_no)
    RelativeLayout rl_transation_no;
    @BindView(R.id.rl_pay_type)
    RelativeLayout rl_pay_type;
    @BindView(R.id.rl_pay_time)
    RelativeLayout rl_pay_time;
    @BindView(R.id.rl_orderd_coupon)
    RelativeLayout rl_orderd_coupon;//立减金额rl
    @BindView(R.id.tv_orderd_coupon)
    TextView tv_orderd_coupon;//立减金额

    private String mOrderId;
    private OrderDetails mOrderDetails;

    @Override
    protected int getContentView() {
        return R.layout.activity_order_detail;
    }

    @Override
    public void initViews() {
        mOrderId = getIntent().getStringExtra("orderId");
        if (CommonUtils.isEmpty(mOrderId)) {
            ToastUtil.toast("订单不存在");
            finish();
        }
        tv_title.setText("订单详情");
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
        String url = Api.URL_ORDER_DETAILS;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", MyApplication.getInstance().getLoginKey());
        params.put("orderId", mOrderId);
        OkHttpUtils.post().url(url).params(params).build().execute(new CommonCallback<OrderDetailsResult>() {
            @Override
            public void onBefore(Request request) {
                super.onBefore(request);
            }

            @Override
            public void onAfter() {
                super.onAfter();
            }

            @Override
            public void onError(Call call, Exception e) {
                mLoadPage.switchPage(LoadPage.STATE_NO_NET);
                ToastUtil.toast(R.string.text_tips_net_issue);
            }

            @Override
            public void onResponse(HttpResult<OrderDetailsResult> response) {
                try {
                    if (response.isSuccess()) {
                        mLoadPage.switchPage(LoadPage.STATE_HIDE);
                        if (response.data != null) {
                            mOrderDetails = response.data.orderinfo;
                            showDetail(mOrderDetails);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    mLoadPage.switchPage(LoadPage.STATE_NO_NET);
                    ToastUtil.toast(R.string.text_tips_server_issue);
                }
            }
        });
    }

    private void showDetail(final OrderDetails orderInfo) {
        if (orderInfo != null) {
            ll_delivery.setVisibility(View.GONE);
            //0 已取消 11待付款 20 已付款 30 已发货 40 已完成
            if (orderInfo.status == 0) {
//                tv_order_desc.setText(getString(R.string.order_status_cancel));
            } else if (orderInfo.status == 11) {
                tv_order_desc.setText(getString(R.string.order_desc_un_pay));
            } else if (orderInfo.status == 20) {
                tv_order_desc.setText(getString(R.string.order_desc_un_delivery));
            } else if (orderInfo.status == 30) {
                tv_order_desc.setText(getString(R.string.order_desc_un_receive));
                if (orderInfo.logisticsinfo != null && orderInfo.logisticsinfo.size() > 0) {
                    ll_delivery.setVisibility(View.VISIBLE);
                    tv_delivery_company.setText(orderInfo.logisticsinfo.get(0).express_name);
                    tv_delivery_order_no.setText(orderInfo.logisticsinfo.get(0).danhao);
                }
            } else if (orderInfo.status == 40) {
                tv_order_desc.setText(getString(R.string.order_desc_complete));
                if (orderInfo.logisticsinfo != null && orderInfo.logisticsinfo.size() > 0) {
                    ll_delivery.setVisibility(View.VISIBLE);
                    tv_delivery_company.setText(orderInfo.logisticsinfo.get(0).express_name);
                    tv_delivery_order_no.setText(orderInfo.logisticsinfo.get(0).danhao);
                }
            }
            //收货人
            tv_consignee.setText(orderInfo.consignee);
            tv_phone.setText(orderInfo.phoneMob);
            tv_address.setText(orderInfo.address);

            //goods
            if (orderInfo.goods != null) {
                for (GoodsInfo good : orderInfo.goods) {
                    if (good != null) {
                        View itemView = View.inflate(OrderDetailsActivity.this, R.layout.item_order_confirm_goods, null);
                        ImageView iv_pic = (ImageView) itemView.findViewById(R.id.iv_pic);
                        TextView tv_goods_name = (TextView) itemView.findViewById(R.id.tv_goods_name);
                        TextView tv_specification = (TextView) itemView.findViewById(R.id.tv_specification);
                        TextView tv_goods_price = (TextView) itemView.findViewById(R.id.tv_goods_price);
                        TextView tv_goods_num = (TextView) itemView.findViewById(R.id.tv_goods_num);
                        ImageLoader.with(OrderDetailsActivity.this, good.defaultImage, iv_pic);
                        tv_goods_name.setText(good.goodsName);
                        tv_specification.setText(good.specification);
                        tv_goods_price.setText("￥" + good.price);
                        tv_goods_num.setText("x" + good.quantity);
                        TextView tv_gd_order_rebate = (TextView) itemView.findViewById(R.id.tv_gd_order_rebate);//返利
                        if(!CommonUtils.isEmpty(good.shareRebate) && !"0".equals(good.shareRebate) &&
                                !"0.0".equals(good.shareRebate) && !"0.00".equals(good.shareRebate)){
                            tv_gd_order_rebate.setVisibility(View.VISIBLE);
                            tv_gd_order_rebate.setText("购物返利"+ good.shareRebate + "元");
                        }
                        ll_container.addView(itemView);
                    }
                }
            }

            tv_goods_count.setText("共" + orderInfo.allGoodsNum + "件商品");
            tv_subtotal.setText("￥" + orderInfo.subtotal);
            tv_voucher.setText("￥" + orderInfo.discount);
            tv_postage.setText("￥" + orderInfo.postage == null ? "0" : orderInfo.postage);

            if (orderInfo.type.equals("0")) {
                tv_payed.setText("￥" + orderInfo.orderAmount);
            } else if (orderInfo.type.equals("1")) {
                rl_orderd_coupon.setVisibility(View.GONE);
                tv_payed.setText("￥" + orderInfo.orderAmount);
            } else if (orderInfo.type.equals("2")) {
                rl_voucher.setVisibility(View.GONE);
                tv_payed.setText("￥" + orderInfo.orderAmount);
            }
            //发票
            if (orderInfo.invoiceinfo != null) {
                //1普通发票，2增值税发票
                //0 明细 1 办公用品 2 电脑器材 3 耗材，如果是增值税发票：默认带0
                ll_invoice.setVisibility(View.VISIBLE);
                if (orderInfo.invoiceinfo.type == 1) {
                    tv_invoice_type.setText("普通发票");
                    tv_invoice_head.setText("个人");
                    if (orderInfo.invoiceinfo.content == 0) {
                        tv_invoice_content.setText("明细");
                    } else if (orderInfo.invoiceinfo.content == 1) {
                        tv_invoice_content.setText("办公用品");
                    } else if (orderInfo.invoiceinfo.content == 2) {
                        tv_invoice_content.setText("电脑器材");
                    } else if (orderInfo.invoiceinfo.content == 3) {
                        tv_invoice_content.setText("耗材");
                    }
                } else if (orderInfo.invoiceinfo.type == 2) {
                    tv_invoice_type.setText("增值税发票");
                    tv_invoice_head.setText(orderInfo.invoiceinfo.header);
                    tv_invoice_content.setText("明细");
                }
            } else {
                ll_invoice.setVisibility(View.GONE);
            }

            //订单号 交易号 支付方式 创建时间 付款时间
            if (orderInfo.status == 0) {
//                tv_order_desc.setText(getString(R.string.order_status_cancel));
            } else if (orderInfo.status == 11) {
                rl_transation_no.setVisibility(View.GONE);
                rl_pay_type.setVisibility(View.GONE);
                rl_pay_time.setVisibility(View.GONE);
                tv_order_no.setText(orderInfo.orderSn);
                tv_create_time.setText(orderInfo.addTime);
                tv_operation_left.setVisibility(View.VISIBLE);
                tv_operation_right.setText(getString(R.string.order_operation_pay));
            } else if (orderInfo.status == 20) {
                tv_order_no.setText(orderInfo.orderSn);
                tv_transation_no.setText(orderInfo.tradenum);
                tv_create_time.setText(orderInfo.addTime);
//                tv_pay_time.setText(orderInfo.p);
//                tv_order_no.setText(orderInfo.orderSn);
                tv_operation_right.setText(getString(R.string.order_operation_remind));
            } else if (orderInfo.status == 30) {
                tv_order_no.setText(orderInfo.orderSn);
                tv_transation_no.setText(orderInfo.tradenum);
                tv_create_time.setText(orderInfo.addTime);
//                tv_pay_time.setText(orderInfo.p);
//                tv_order_no.setText(orderInfo.orderSn);
                tv_operation_right.setText(getString(R.string.order_operation_received));
            } else if (orderInfo.status == 40) {
                tv_order_no.setText(orderInfo.orderSn);
                tv_transation_no.setText(orderInfo.tradenum);
                tv_create_time.setText(orderInfo.addTime);
//                tv_pay_time.setText(orderInfo.p);
//                tv_order_no.setText(orderInfo.orderSn);
                tv_operation_right.setText(getString(R.string.order_operation_evaluate));
            }
            tv_operation_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (orderInfo.status == 11) {
                        CommonUtils.go2pay(orderInfo.orderId, orderInfo.orderSn, orderInfo.orderAmount);
                    } else if (orderInfo.status == 20) {
                        weixinDialogInit("提醒中...");
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ToastUtil.toast("提醒成功");
                                        cancelWeiXinDialog();
                                    }
                                });
                            }
                        }, 300);
                    } else if (orderInfo.status == 30) {
                        //确认收货
                        CommonSureDialogFragment dialog = new CommonSureDialogFragment("是否确认收货？", new CommonSureDialogFragment.SureListener() {
                            @Override
                            public void onSureListener() {
                                OrderManager.receive(mOrderId, new OrderManager.OrderOperationCallBack() {
                                    @Override
                                    public void onSuccess() {
                                        ToastUtil.toast("确定收货成功");
                                        finish();
                                    }

                                    @Override
                                    public void onFail(String msg) {
                                        ToastUtil.toast(msg);
                                    }

                                    @Override
                                    public void onError() {
                                        ToastUtil.toast("服务器异常");
                                    }
                                });
                            }
                        });
                        dialog.show(getSupportFragmentManager(), "SureDialog");
                    } else if (orderInfo.status == 40 && orderInfo.evaluation != 1) {
                        //评论
                        Intent intent = new Intent(getApplicationContext(), CommonFragmentActivity.class);
                        intent.putExtra("target", CommonFragmentActivity.FRAGMENT_ORDER_COMMENT);
                        intent.putExtra("orderId", orderInfo.orderId + "");
                        startActivity(intent);
                    }
                }
            });
            if (orderInfo.minusAmount != null) {
                if (!orderInfo.minusAmount.equals("0.00") && !orderInfo.minusAmount.equals("0.0")
                        && !orderInfo.minusAmount.equals("0")) {
                    //立减金额值不为0
                    rl_orderd_coupon.setVisibility(View.VISIBLE);
                    tv_orderd_coupon.setText("-￥" + orderInfo.minusAmount);
                }
            }

        }
    }

    @OnClick({R.id.iv_left, R.id.tv_view_logistics, R.id.tv_operation_left})
    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.tv_view_logistics:
                Intent intent = new Intent(OrderDetailsActivity.this, OrderExpressListActivity.class);
                intent.putExtra("orderid", mOrderId);
                startActivity(intent);
                break;
            case R.id.tv_operation_left:
                CancelOrderDialogFragment dialog = new CancelOrderDialogFragment();
                dialog.show(getSupportFragmentManager(), "cancelOrder");
                break;
        }
    }

    @Override
    public void onConfirm(ListDialogItem item) {
        //取消订单
        OrderManager.cancelOrder(MyApplication.getInstance().getLoginKey(), item.id + "", mOrderId, new OrderManager.OrderOperationCallBack() {
            @Override
            public void onSuccess() {
                ToastUtil.toast("取消成功");
                finish();
            }

            @Override
            public void onFail(String msg) {
                ToastUtil.toast(msg);
            }

            @Override
            public void onError() {
                ToastUtil.toast("服务器异常");
            }
        });
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(UmengStat.PAYMENTINFOPAGE); //统计页面
        MobclickAgent.onResume(this);          //统计时长
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(UmengStat.PAYMENTINFOPAGE); //
        MobclickAgent.onPause(this);
    }
}
