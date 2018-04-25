package com.svgouwu.client.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 详情页默认规格
 * Created by melon on 2017/7/26.
 * Email 530274554@qq.com
 */

public class GoodsSpec implements Serializable {
    public String goodsname;
    public int stock;
    public String defalut_pic;
    public Price price;
    public List<Spec> standard;
    public String specId;
    public String shareRebate;


    public class Price {
        public String price;
        public String spelPrice;//10.28上线后废弃
        public int isSpel;
        public String activePrice;//活动价格节点
    }

    public class Spec {
        public String specName;//属性名
        public List<String> specValues;//属性值
    }
}
