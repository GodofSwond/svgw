package com.svgouwu.client.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by whq on 2017/10/20.
 */

public class PayOrderList implements Serializable {

    /**
     * code : 0000
     * msg : 成功
     * data : {"orderSn":["17291457064096","17291078621102"],"orderId":"152573,152572","totalFee":"674.00"}
     */

    /**
     * orderSn : ["17291457064096","17291078621102"]
     * orderId : 152573,152572
     * totalFee : 674.00
     */

    private String orderId;
    private String totalFee;
    private List<String> orderSn;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public List<String> getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(List<String> orderSn) {
        this.orderSn = orderSn;
    }
}
