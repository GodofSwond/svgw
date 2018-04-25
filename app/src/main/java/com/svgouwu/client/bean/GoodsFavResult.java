package com.svgouwu.client.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 商品收藏结果
 * Created by melon on 2017/8/1.
 * Email 530274554@qq.com
 */

public class GoodsFavResult implements Serializable {
    public List<GoodsInfo> goodsList;
    public int totalNum;
}
