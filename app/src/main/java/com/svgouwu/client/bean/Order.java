package com.svgouwu.client.bean;

import android.view.View;

import com.kili.okhttp.OkHttpUtils;
import com.svgouwu.client.Api;
import com.svgouwu.client.MyApplication;
import com.svgouwu.client.utils.CommonCallback;
import com.svgouwu.client.utils.ToastUtil;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;

/**
 * 订单列表的order对象
 * Created by topwolf on 2017/5/11.
 */

public class Order implements Serializable{

    public String goodsImg;
    public String goodsName;
    public int quantity;
    public String price;
    public String goodsId;
    public String specification;
    public String postage;
    public int evaluationStatus;//是否评价（1：已评价，0未评价）
    public int allGoodsNum;
    public int orderId;
    public String orderSn;
    public List<Express> invoiceinfo;
    public String addTime;
    public String orderAmount;
    public int status;//0 已取消 11待付款 20 已付款 30 已发货 40 已完成
    public String shareRebate;//返利

}
