package com.svgouwu.client.bean;

import java.io.Serializable;

/**
 * Created by whq on 2017/10/23.
 */

public class PayGetCoupon implements Serializable {

    /**
     * coupon_value : 20
     * min_amount : 100
     * title : 双11主场券
     */

    private String coupon_value;
    private String min_amount;
    private String title;

    public String getCoupon_value() {
        return coupon_value;
    }

    public void setCoupon_value(String coupon_value) {
        this.coupon_value = coupon_value;
    }

    public String getMin_amount() {
        return min_amount;
    }

    public void setMin_amount(String min_amount) {
        this.min_amount = min_amount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
