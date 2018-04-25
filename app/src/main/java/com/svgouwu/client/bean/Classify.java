package com.svgouwu.client.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by topwolf on 2017/5/11.
 */

public class Classify implements Serializable{

    public String cateName;
    public String cateImg;
    public int cateId;
    public String cateImage;
    public int classify_rank;
    public List<Brand> brand;
    public List<Classify> children;
    public String num; //商品数量

    @Override
    public String toString() {
        return "Classify{" +
                "cateName='" + cateName + '\'' +
                ", cateImg='" + cateImg + '\'' +
                ", cateId=" + cateId +
                ", cateImage='" + cateImage + '\'' +
                ", classify_rank=" + classify_rank +
                ", brand=" + brand +
                ", children=" + children +
                '}';
    }
}
