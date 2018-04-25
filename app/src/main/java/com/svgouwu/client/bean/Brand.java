package com.svgouwu.client.bean;

import java.io.Serializable;

/**
 * Created by topwolf on 2017/5/11.
 */

public class Brand implements Serializable{

    public String brandName;
    public String brandLogo;
    public String brand; //品牌名
    public String num;//数量

    @Override
    public String toString() {
        return "Brand{" +
                "brandName='" + brandName + '\'' +
                ", brandLogo='" + brandLogo + '\'' +
                '}';
    }
}
