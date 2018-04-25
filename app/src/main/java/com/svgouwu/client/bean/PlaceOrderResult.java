package com.svgouwu.client.bean;

import java.util.List;

/**
 * 筛选信息
 * Created by melon on 2017/7/20.
 */

public class PlaceOrderResult {
    public PlaceOrder orderidlist;

    public class PlaceOrder{
        public List<String> orderIdlist;
        public List<String> orderSnlist;
    }
}
