package com.svgouwu.client.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 增值税发票bean
 * Created by topwolf on 2017/5/11.
 */

public class Invoice implements Serializable{

    public int type;//1普通发票，2增值税发票
    public int content;//针对普通发票有四个选项：0 明细 1办公用品 2电脑器材 3耗材，如果是增值税发票：默认带0
    public String header;//1普通发票，2增值税发票
    public String identcode;//纳税人识别码 只有type为2时有该字段

}
