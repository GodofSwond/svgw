package com.svgouwu.client.bean;

/**
 * Created by topwolf on 2017/6/29.
 */

public class UserCenter {
    public String userName;
    public String lastLogin;
    public String lastIp;
    public String portrait;
    public int waitForPayNum; //待支付
    public int waitForConfirmNum; //待收货
    public int waitForCommentNum; //待评论
    public int waitForDeliverGoodsNum; //待发货
    public int returnGoodsNum; //退货
}
