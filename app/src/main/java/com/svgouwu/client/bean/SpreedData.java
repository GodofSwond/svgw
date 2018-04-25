package com.svgouwu.client.bean;

import java.io.Serializable;

/**
 * Created by whq on 2017/8/31.
 * 0052：活动未开始，0053：活动已结束，0054：活动进行中
 * 0055：您已领取，0056：领取成功
 */

public class SpreedData implements Serializable {
    public String code;//0052
    public String msg;//活动未开始

    @Override
    public String toString() {
        return "SpreedData{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
