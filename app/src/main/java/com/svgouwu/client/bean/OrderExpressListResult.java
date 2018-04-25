package com.svgouwu.client.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by topwolf on 2017/8/3.
 */

public class OrderExpressListResult implements Serializable {
    public List<ExpressItem> list;

    public class ExpressItem{
        public String imageurl;//商品图片url
        public String comname;//物流公司名称
        public String com;//物流公司代码
        public String nu;//物流单号
    }
}
