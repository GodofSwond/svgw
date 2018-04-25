package com.svgouwu.client.bean;

import java.io.Serializable;

/**
 * Created by topwolf on 2017/5/11.
 */

public class Address implements Serializable{

    public String userId;
    public String consignee;//收件人姓名
    public int regionId;
    public String regionName;
    public String address;
    public String zipcode;
    public String phoneTel;
    public String phoneMob;//手机号
    public int setdefault;
    public String idNumber;//身份证
    public String realName;//真实姓名
    public String addrId;

}
