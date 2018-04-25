package com.svgouwu.client.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 筛选信息
 * Created by melon on 2017/7/20.
 */

public class CartResult implements Serializable{
    public int isRealname;//是否实名认证（购买海外商品需要实名认证1是，0不是）
    public CartGoods cart_goods;
    public CartSubTotal cart;//当前购物车的总计情况（quantity：总数量，amount：总金额，kinds：总品种）



    public class CartGoods{
        public List<StoreList> storelist;
    }

    public class StoreList{
        public List<GoodsInfo> goods;//该店铺商品
        public String storeName;
        public String storeId;
        public int kinds;//该店铺种类
    }
    public class CartSubTotal{
        public int quantity;//总数量
        public String amount;//总金额
        public int kinds;//总品种
    }
}
