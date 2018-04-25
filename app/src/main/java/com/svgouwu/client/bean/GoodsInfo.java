package com.svgouwu.client.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by topwolf on 2017/6/7.
 */

public class GoodsInfo implements Serializable{

    public String goodsId;//商品ID
    public String goodsName;//商品名称
    @SerializedName(value = "defaultImage", alternate = {"goodsImage","goodsImg"})
    public String defaultImage; //商品图片
    public int sale; //销量
    public int comments; //评论销量
    public double price; //价格  实际价格
    public double spelPrice; //价格  优惠价格

    public String desc;
    public double prime_price;
    public int postion;
    public int count;
    public String color;
    public String size;
    public int selected;//是否选中（要购买的为1）
    public int quantity;//购买数量
    public Double subtotal;//商品小计
    public int is_overseas;//是否海外商品（1是，0不是）
    public int specId;//货号ID
    @SerializedName(value = "recId", alternate = {"recid"})
    public int recId;//记录ID（购物车）
    public int isSpel;//是否优惠（0不优惠，1优惠）
    public String specification;//特殊说明
    public String storeId;//店铺ID
    public String storeName;//店铺名称
    public int evaluation;//0为未评价
    public String shareRebate;//购买后返利
//    public GoodsInfo(String storeId, String goodsName, String desc, double price, double prime_price,
//                     String color, String size, int goodsImg, int count) {
//        this.goodsId = storeId;
//        this.goodsName = goodsName;
//        this.desc = desc;
//        this.price = price;
//        this.prime_price = prime_price;
//        this.count = count;
//        this.color = color;
//        this.size = size;
//        this.goodsImg = goodsImg;
//    }
}
