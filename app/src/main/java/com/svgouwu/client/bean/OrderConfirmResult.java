package com.svgouwu.client.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 筛选信息
 * Created by melon on 2017/7/20.
 */

public class OrderConfirmResult implements Serializable {
    public OrderInfo orderinfo;
    public Address address;
    public AddedValueInvoice invoiceinfo;
    public Wealthinfo wealthinfo;

    public class OrderInfo implements Serializable {
        public String amount;
        public String available_coupon;//1--有优惠券，0--没有优惠券
        public String type;
        public String otype;
        public List<String> storeIds; //结算的商品所有店铺ID
        public List<OrderItem> orderList;//
        public String total_minus;//总订单立减金额
        public List<OrderCoupon> coupon;//优惠券详情
    }

    public class OrderItem implements Serializable {
        public List<GoodsInfo> items;//一个订单里的所有商品
        public String amount;//该订单的订单金额（一个店铺一个订单）
        public String storeId;
        public String storeName;
        public String imqq;
        public String minus_amount;//单个商品立减金额
    }

    public class OrderCoupon implements Serializable {
        public String couponId;
        public String couponName;
        public String couponSn;
        public String couponValue;
        public String endTime;
        public String id;
        public String minAmount;
        public String rate;
        public String startTime;
        public String type;
        public String useRule;
        public String useTime;
    }

    @Override
    public String toString() {
        return "OrderConfirmResult{" +
                "orderinfo=" + orderinfo +
                ", address=" + address +
                ", invoiceinfo=" + invoiceinfo +
                '}';
    }
}
