package com.svgouwu.client.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/31.
 */

public class CouponsNumBean implements Serializable {

    public String total;              //总数
    public String used;               //已使用
    public String ongoing;            //进行中
    public String expired;            //已过期
    public String being_expired;     //即将过期

}
