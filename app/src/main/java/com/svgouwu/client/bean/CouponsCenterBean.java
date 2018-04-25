package com.svgouwu.client.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/10/9.
 */

public class CouponsCenterBean implements Serializable {

        public List<UnreceivedBean> unreceived;    //未领取
        public List<ReceivedBean> received;      //已领取
        public List<OutofnumBean> outofnum;      //已领完

}
