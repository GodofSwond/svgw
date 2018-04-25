package com.svgouwu.client.bean;

import java.io.Serializable;

/**
 * Created by whq on 2017/11/16.
 */

public class GoodDetailsShareInfo implements Serializable {


    /**
     * url : https://www.svgouwu.com/index.php?app=goods&act=index&id= 813&coded=S4RD7CHX
     * goodsName : 威克士家用电动工具套组
     * defaultImage : https://www.svgouwu.com/data/files/store_1087/goods_107/small_201703031311479356.jpg
     */

    private String url;
    private String goodsName;
    private String defaultImage;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getDefaultImage() {
        return defaultImage;
    }

    public void setDefaultImage(String defaultImage) {
        this.defaultImage = defaultImage;
    }

    @Override
    public String toString() {
        return "GoodDetailsShareInfo{" +
                "url='" + url + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", defaultImage='" + defaultImage + '\'' +
                '}';
    }
}

