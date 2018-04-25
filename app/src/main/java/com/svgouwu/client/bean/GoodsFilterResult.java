package com.svgouwu.client.bean;

import java.util.List;

/**
 * 筛选信息
 * Created by melon on 2017/7/20.
 */

public class GoodsFilterResult {
    public List<Classify> by_cate;//分类
    public List<Brand> by_brand; //品牌
    public List<PriceFilter> by_price;//价格区间
}
