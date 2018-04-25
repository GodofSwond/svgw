package com.svgouwu.client.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/11/27.
 */

public class WealthLookBean implements Serializable {

    public Account account;
    public List<LookListBean> list;

    public static class LookListBean {
        public String addtime;            //": "2017-09-22 ",
        public String productTitle;      //": "四维原产五常大米稻花香特惠组",
        public String username;           //": "15647448007",
        public String gsrebate;           //": "50.00",
        public String gsshare_rebate;    //": "50.00"
        public String number;

    }

    public static class Account {
        public String total_money;//": "50.00",
        public String ke_tixian_money;//": 0,
        public String dong_jie_money;//": "50"
        public String have_tixian_money;
    }
}
