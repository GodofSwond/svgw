package com.svgouwu.client.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kili.okhttp.OkHttpUtils;
import com.svgouwu.client.Api;
import com.svgouwu.client.BaseActivity;
import com.svgouwu.client.CommonFragmentActivity;
import com.svgouwu.client.Constant;
import com.svgouwu.client.CustomAlertDialog;
import com.svgouwu.client.MyApplication;
import com.svgouwu.client.R;
import com.svgouwu.client.bean.AddedValueInvoice;
import com.svgouwu.client.bean.AddedValueInvoice2;
import com.svgouwu.client.bean.Address;
import com.svgouwu.client.bean.GoodsInfo;
import com.svgouwu.client.bean.HttpResult;
import com.svgouwu.client.bean.ListDialogItem;
import com.svgouwu.client.bean.OrderConfirmResult;
import com.svgouwu.client.bean.PlaceOrderResult;
import com.svgouwu.client.fragment.SimpleListDialogFragment;
import com.svgouwu.client.utils.CommonCallback;
import com.svgouwu.client.utils.CommonUtils;
import com.svgouwu.client.utils.ImageLoader;
import com.svgouwu.client.utils.ToastUtil;
import com.svgouwu.client.utils.UmengStat;
import com.svgouwu.client.view.LoadPage;
import com.umeng.analytics.MobclickAgent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by topwolf on 2017/7/26.
 * 订单确认页--入口是购物车结算
 */

public class OrderConfirmActivity extends BaseActivity implements SimpleListDialogFragment.DialogItemClickListener {

    private static final int CHANGE_ADDRESS = 101;
    private static final int GET_INVOICE = 100;

    @BindView(R.id.tv_order_gold)
    TextView tv_orderGold;
    @BindView(R.id.iv_left)
    ImageView iv_left;//退出
    @BindView(R.id.tv_title)
    TextView tv_title;//页面标题
    @BindView(R.id.mLoadPage)
    LoadPage mLoadPage;//加载
    @BindView(R.id.tv_consignee)
    TextView tv_consignee;//收件人姓名
    @BindView(R.id.tv_phone)
    TextView tv_phone;//收件人电话
    @BindView(R.id.tv_address)
    TextView tv_address;//收件人地址
    @BindView(R.id.ll_container)
    LinearLayout ll_container;//商品
    @BindView(R.id.rl_delivery)
    RelativeLayout rl_delivery;//配送方式
    @BindView(R.id.tv_delivery_type)
    TextView tv_delivery_type;//快递免邮
    @BindView(R.id.rl_invoice)//发票rl
            RelativeLayout rl_invoice;
    @BindView(R.id.tv_invoice_type)
    TextView tv_invoice_type;
    @BindView(R.id.tv_total_price)
    TextView tv_total_price;//小计XX元
    @BindView(R.id.tv_count)
    TextView tv_count;//商品共记XX件
    @BindView(R.id.tv_total)
    TextView tv_total;//总金额
    @BindView(R.id.tv_submit)
    TextView tv_submit;//提交按钮
    @BindView(R.id.ll_address)
    LinearLayout ll_address;//地址ll
    @BindView(R.id.tv_order_coupon)
    TextView tv_order_coupon;//优惠券跳转
    @BindView(R.id.rl_order_coupon)
    RelativeLayout rl_order_coupon;//优惠券rl
    @BindView(R.id.tv_coupon_money)
    TextView tv_coupon_money;//优惠金额
    @BindView(R.id.tv_order_all_rebate)
    TextView tv_order_all_rebate;//返利总金额

    private String recid;//要购买的商品记录ID（多个用,分隔）
    private String addressid;//
    List<ListDialogItem> items = new ArrayList<>();//明细 办公用品 电脑配件 耗材
    List<GoodsInfo> goods = new ArrayList<>();
    private AddedValueInvoice invoiceinfo;
    private Address addressInfo;
    private String invoiceStr;
    private String amount;//总金额
    private String total_minus = "0";//订单总共立减金额
    private double coupon;//优惠后价格（立减，优惠券）
    private String cValue, couponid;//返回的优惠券价格和id
    private boolean hasCoupon;//是否有可用优惠券
    private String mIntentRebate;
    private List<OrderConfirmResult.OrderCoupon> couponlist;
    private double couponOver;
    private String fourdcAll;//四维币总数
    private double strDouble;
    private double fourdcDouble;

    @Override
    protected int getContentView() {
        return R.layout.activity_order_confirm;
    }

    @Override
    public void initViews() {
        items.add(new ListDialogItem(0, "明细"));
        items.add(new ListDialogItem(1, "办公用品"));
        items.add(new ListDialogItem(2, "电脑配件"));
        items.add(new ListDialogItem(3, "耗材"));

        tv_title.setText(getString(R.string.title_order_confirm));
        mLoadPage.setGetDataListener(new LoadPage.GetDataListener() {
            @Override
            public void onGetData() {
                mLoadPage.switchPage(LoadPage.STATE_LOADING);
                initData();
            }
        });
        mLoadPage.switchPage(LoadPage.STATE_LOADING);
        if (getIntent().hasExtra("recid")) {
            recid = getIntent().getStringExtra("recid");
        }
        mIntentRebate = getIntent().getStringExtra("intentRebate");
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    private void setAddressInfo(Address addressInfo) {
        if (addressInfo != null) {
            addressid = addressInfo.addrId;
            tv_consignee.setText("收货人：" + addressInfo.consignee);
            tv_phone.setText(addressInfo.phoneMob);
            tv_address.setText("收货地址：" + addressInfo.address);
        }
    }

    private void showGoods(List<OrderConfirmResult.OrderItem> orderList) {
        if (goods != null) {
            goods.clear();
        }
        if (ll_container != null) {
            ll_container.removeAllViews();
        }
        for (OrderConfirmResult.OrderItem item : orderList) {
            if (item.items != null) {
                goods.addAll(item.items);
            }
        }
        tv_count.setText("共" + goods.size() + "件商品 小计");
        TextView tv_gd_order_rebate = null;
        for (GoodsInfo good : goods) {
            if (good != null) {
                View itemView = View.inflate(OrderConfirmActivity.this, R.layout.item_order_confirm_goods, null);
                ImageView iv_pic = (ImageView) itemView.findViewById(R.id.iv_pic);
                TextView tv_goods_name = (TextView) itemView.findViewById(R.id.tv_goods_name);
                TextView tv_specification = (TextView) itemView.findViewById(R.id.tv_specification);
                TextView tv_goods_price = (TextView) itemView.findViewById(R.id.tv_goods_price);
                TextView tv_goods_num = (TextView) itemView.findViewById(R.id.tv_goods_num);
                tv_gd_order_rebate = (TextView) itemView.findViewById(R.id.tv_gd_order_rebate);//购买返后利
                ImageLoader.with(OrderConfirmActivity.this, good.defaultImage, iv_pic);
                tv_goods_name.setText(good.goodsName);
                tv_specification.setText(good.specification);
                tv_goods_price.setText("￥" + good.price);
                tv_goods_num.setText("x" + good.quantity);
                if (!CommonUtils.isEmpty(good.shareRebate) && !"0".equals(good.shareRebate)
                        && !"0.0".equals(good.shareRebate) && !"0.00".equals(good.shareRebate)) {
                    tv_gd_order_rebate.setVisibility(View.VISIBLE);
                    tv_gd_order_rebate.setText("购物返利" + good.shareRebate + "元");
                }
                ll_container.addView(itemView);
            }
        }
        if (!CommonUtils.isEmpty(mIntentRebate)) {
            tv_order_all_rebate.setVisibility(View.VISIBLE);
            tv_order_all_rebate.setText(Html.fromHtml("返利总金额:  " + "<font color=#df3031>" + " ￥" + mIntentRebate + "元" + "</font>"));
        }
    }


    @OnClick({R.id.iv_left, R.id.rl_invoice, R.id.tv_submit, R.id.ll_address, R.id.tv_order_coupon, R.id.rl_order_gold})
    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.rl_invoice:
                List<ListDialogItem> items = new ArrayList<>();
                items.add(new ListDialogItem(1, "个人"));
                items.add(new ListDialogItem(2, "增值税"));
                SimpleListDialogFragment dialog = new SimpleListDialogFragment(items);
                dialog.show(getSupportFragmentManager(), "invoice");
                break;
            case R.id.ll_address:
                Intent intent = new Intent(OrderConfirmActivity.this, AddressListActivity.class);
                intent.putExtra("isValid", true);
                startActivityForResult(intent, CHANGE_ADDRESS);
                break;
            case R.id.tv_submit://提交订单------------------------------------------------------------------------
                if (!TextUtils.isEmpty(addressid)) {
                    MobclickAgent.onEvent(OrderConfirmActivity.this, UmengStat.CONFIRMTHEORDER);
                    submitOrder();
                }
                break;
            case R.id.tv_order_coupon:
                //优惠券
                if (hasCoupon) {
                    //有可用点击无效
                    MobclickAgent.onEvent(OrderConfirmActivity.this, UmengStat.COUPONS);//UM
                    Intent intent1 = new Intent(OrderConfirmActivity.this, CommonFragmentActivity.class);
                    intent1.putExtra(CommonFragmentActivity.TARGET, CommonFragmentActivity.FRAGMENT_USE_DIS);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Coupon", (Serializable) couponlist);
                    intent1.putExtras(bundle);
                    startActivityForResult(intent1, 1);
                } else {
                    //无可用点击不跳转
                }
                break;
            case R.id.rl_order_gold:
                if (!fourdcAll.equals("0.00")) {
                    showGold();//展示四维币
                } else {
//                    ToastUtil.toast("不提示，不跳转");
                }
                break;
            default:
                break;
        }
    }

    /**
     * 四维币
     */
    private void showGold() {

        CustomAlertDialog dialog = new CustomAlertDialog(this, new CustomAlertDialog.ICallBack() {
            @Override
            public void callback(String str) {
                Log.i("TAG", "str: " + str);

                strDouble = Double.parseDouble(str);
                fourdcDouble = Double.parseDouble(fourdcAll);
                if (strDouble <= coupon) {

                    if (strDouble <= fourdcDouble) {
                        tv_orderGold.setText(str);
                        tv_orderGold.setTextColor(getResources().getColor(R.color.color_red));
                        //所有优惠之后的价格
                        couponOver = coupon - Double.parseDouble(str);//优惠后价格
                        tv_total.setText("￥" + couponOver);
                        tv_total_price.setText("￥" + couponOver);

                    } else {
                        ToastUtil.toast("现拥有" + fourdcAll + "四维币");
                    }
                } else {
                    ToastUtil.toast("四维币不能超过" + couponOver);
                }
            }
        });

        dialog.show();
    }


    @Override
    public void onItemClick(ListDialogItem item) {
        if ("个人".equals(item.name)) {
            SimpleListDialogFragment dialog = new SimpleListDialogFragment(items);
            dialog.show(getSupportFragmentManager(), "personal");
        } else if ("增值税".equals(item.name)) {
            Intent intent = new Intent(OrderConfirmActivity.this, AddedValueInvoiceActivity.class);
//            if(invoiceinfo!=null){
//                intent.putExtra("id",invoiceinfo.id);
//            }
            startActivityForResult(intent, GET_INVOICE);

        } else {
            invoiceStr = "{\"type\":1,\"invoice_content\":" + item.id + "}";
            tv_invoice_type.setText(item.name);
        }
    }

    /**
     * 提交订单接口
     */
    public void submitOrder() {

        String url = Api.URL_SUBMIT_ORDER;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", MyApplication.getInstance().getLoginKey());
        params.put("recid", recid);
        params.put("addressid", addressid);
        params.put("appChannel", Constant.CHANNEL);
        params.put("fourdc", strDouble + "");
        params.put("payprice", couponOver + "");

        if (!CommonUtils.isEmpty(couponid)) {
            params.put("coupon_id", couponid);
        }
        if (!TextUtils.isEmpty(invoiceStr)) {
            params.put("invoice", invoiceStr);//json
        }

        OkHttpUtils.post().url(url).params(params).build().execute(new CommonCallback<PlaceOrderResult>() {
            @Override
            public void onError(Call call, Exception e) {
                mLoadPage.switchPage(LoadPage.STATE_NO_NET);
                cancelWeiXinDialog();
            }

            @Override
            public void onBefore(Request request) {
                super.onBefore(request);
                weixinDialogInit("订单提交中...");
            }

            @Override
            public void onAfter() {
                super.onAfter();
                cancelWeiXinDialog();
            }

            @Override
            public void onResponse(HttpResult<PlaceOrderResult> response) {
                try {
                    if (response.isSuccess()) {
                        mLoadPage.switchPage(LoadPage.STATE_HIDE);
                        StringBuilder sb = new StringBuilder();//要购买的商品记录ID
                        if (response.data.orderidlist != null && response.data.orderidlist.orderIdlist != null) {
                            for (int i = 0; i < response.data.orderidlist.orderIdlist.size(); i++) {
                                sb.append(response.data.orderidlist.orderIdlist.get(i) + ",");
                            }
                        }
                        String orderIds = sb.substring(0, sb.lastIndexOf(","));

                        StringBuilder sb2 = new StringBuilder();//要购买的商品记录ID
                        if (response.data.orderidlist != null && response.data.orderidlist.orderSnlist != null) {
                            for (int i = 0; i < response.data.orderidlist.orderSnlist.size(); i++) {
                                sb2.append(response.data.orderidlist.orderSnlist.get(i) + ",");
                            }
                        }
                        String orderSns = sb2.substring(0, sb2.lastIndexOf(","));

//                        Intent intent = new Intent(OrderConfirmActivity.this,PayActivity.class);
//                        intent.putExtra("orderIds",orderIds);
//                        intent.putExtra("orderSns",orderSns);
//                        startActivity(intent);
                        //    CommonUtils.go2pay(orderIds, orderSns, amount);
                        CommonUtils.go2pay(orderIds, orderSns, couponOver + "");
                        finish();
                    } else {
                        ToastUtil.toast(response.msg);
                    }
                } catch (Exception e) {
                    mLoadPage.switchPage(LoadPage.STATE_NO_NET);
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == GET_INVOICE) {
                if (data.hasExtra("invoice")) {
                    AddedValueInvoice2 invoiceinfo2 = (AddedValueInvoice2) data.getSerializableExtra("invoice");
                    if (invoiceinfo2 != null) {
                        tv_invoice_type.setText("增值税发票");
                    }
                    invoiceStr = new Gson().toJson(invoiceinfo2);
                }
            } else if (requestCode == CHANGE_ADDRESS) {
                if (data.hasExtra("address")) {
                    addressInfo = (Address) data.getSerializableExtra("address");
                    setAddressInfo(addressInfo);
                }
            }
        }
        if (resultCode == 1) {
            //优惠券页面返回优惠数值
            if (data != null && data.hasExtra("cValue")) {
                cValue = (String) data.getSerializableExtra("cValue");
                coupon = Double.parseDouble(amount) - Double.parseDouble(cValue);
                tv_order_coupon.setText("￥" + cValue);
                tv_order_coupon.setTextColor(getResources().getColor(R.color.color_red));
                tv_total.setText("￥" + coupon);
                tv_total_price.setText("￥" + coupon);
            }
            if (data != null && data.hasExtra("id")) {
                couponid = (String) data.getSerializableExtra("id");
            }
        }
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(UmengStat.CONFIRMTHEORDERPAGE); //统计页面
        String url = Api.URL_ORDER_CONFIRM;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", MyApplication.getInstance().getLoginKey());
        params.put("recid", recid);

        OkHttpUtils.post().url(url).params(params).build().execute
                (new CommonCallback<OrderConfirmResult>() {
                     @Override
                     public void onError(Call call, Exception e) {
                         mLoadPage.switchPage(LoadPage.STATE_NO_NET);
                     }

                     @Override
                     public void onResponse(HttpResult<OrderConfirmResult> response) {
                         try {
                             if (response.isSuccess()) {
                                 mLoadPage.switchPage(LoadPage.STATE_HIDE);
                                 if (response.data != null) {
                                     if (response.data.address != null) {
                                         addressInfo = response.data.address;
                                         setAddressInfo(addressInfo);
                                     }
                                     //    invoiceinfo = response.data.invoiceinfo;
                                     if (response.data.orderinfo != null) {
                                         //接口返回立减价格
                                         total_minus = response.data.orderinfo.total_minus;
                                         if (!CommonUtils.isEmpty(total_minus) && !"0".equals(total_minus)
                                                 && !"0.0".equals(total_minus) && !"0.00".equals(total_minus)) {
                                             rl_order_coupon.setVisibility(View.VISIBLE);
                                             tv_coupon_money.setText("-￥" + total_minus.toString());
                                         }
                                         //接口返回总金额
                                         amount = response.data.orderinfo.amount;
                                         tv_total_price.setText("￥" + amount);

                                         coupon = Double.parseDouble(amount) - Double.parseDouble(total_minus);//优惠后价格
                                         tv_total.setText("￥" + couponOver);

                                         if (response.data.orderinfo.orderList != null && response.data.orderinfo.orderList.size() > 0) {
                                             showGoods(response.data.orderinfo.orderList);
                                         }
                                         if (response.data.orderinfo.coupon != null && response.data.orderinfo.coupon.size() > 0) {
                                             couponlist = response.data.orderinfo.coupon;
                                             hasCoupon = true;
                                             tv_order_coupon.setText("有可用");
                                             tv_order_coupon.setTextColor(getResources().getColor(R.color.color_red));

                                         } else {
                                             hasCoupon = false;
                                             tv_order_coupon.setText("无可用");
                                         }
                                         if (response.data.wealthinfo != null) {
                                             fourdcAll = response.data.wealthinfo.fourdc;
                                             if (!fourdcAll.equals("0.00")) {
                                                 tv_orderGold.setText("有可用");
                                                 tv_orderGold.setTextColor(getResources().getColor(R.color.color_red));
                                             } else {
                                                 tv_orderGold.setText("无可用");
                                             }
                                         }
                                     }
                                 } else {
                                 }
                             } else if ("0028".equals(response.code)) {//没有地址
                                 ToastUtil.toast("当前账号没有绑定地址，即将跳转添加地址页面");
                                 MyApplication.getHandler().postDelayed(new Runnable() {
                                     @Override
                                     public void run() {
                                         startActivity(new Intent(mContext, AddressListActivity.class));
                                     }
                                 }, 1500);

                             }
                         } catch (Exception e) {
                             mLoadPage.switchPage(LoadPage.STATE_NO_NET);
                             e.printStackTrace();
                         }
                     }
                 }

                );
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(UmengStat.CONFIRMTHEORDERPAGE); //
    }
}
