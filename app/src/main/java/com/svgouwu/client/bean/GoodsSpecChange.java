package com.svgouwu.client.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 切换规格后的信息
 * Created by melon on 2017/7/26.
 * Email 530274554@qq.com
 */

public class GoodsSpecChange implements Serializable {
    public String specId;
    public int stock;
    public GoodsSpec.Price price;
    public String sku;
    public String shareRebate;//返利金额
}
