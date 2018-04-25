package com.svgouwu.client.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by topwolf on 2017/8/8.
 */

public class WxPayResult implements Serializable{

    public String appid;
    public String partnerid;
    public String prepayid;
    @SerializedName("package")
    public String packageValue;
    public String noncestr;
    public String timestamp;
    public String sign;
    public String paytradeno;
}
