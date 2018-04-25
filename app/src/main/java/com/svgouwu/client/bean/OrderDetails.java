package com.svgouwu.client.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 订单详情
 * Created by melon on 2017/7/28.
 * Email 530274554@qq.com
 */

public class OrderDetails implements Serializable {
    public String orderId;
    public String orderSn;
    public String addTime;
    public String payTime;
    public String shipTime;
    public String finishedTime;
    public int status;//0 已取消 11待付款 20 已付款 30 已发货 40 已完成
    public String paymentName;
    public String discount;
    public String orderAmount;
    public String postscript;
    public String postage;
    public String consignee; //收货人
    public String phoneMob;
    public String address;
    public String zipcode;
    public String shippingName;
    public String tradenum;
    public String subtotal;
    public int allGoodsNum;
    public int evaluation; //1 已评论
    public Invoice invoiceinfo;
    public List<GoodsInfo> goods;
    public List<Express> logisticsinfo;
    public String minusAmount;//立减金额总值
    public String type;//0:无活动    1: 优惠券    2:立减
}
