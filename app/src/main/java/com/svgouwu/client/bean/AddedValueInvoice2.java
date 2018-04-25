package com.svgouwu.client.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 增值税发票bean
 * Created by topwolf on 2017/5/11.
 */

public class AddedValueInvoice2 implements Serializable{

    public int type;//1是普通发票，2是增值税发票
    public int invoice_content;//针对普通发票有四个选项：0 明细 1办公用品 2电脑器材 3耗材，如果是增值税发票：默认带0
    public String unit_name;//必填：单位名称
    public String ident_code; //必填：纳税人识别吗
    public String register_addr; //必填：注册地址
    public String register_tel; //必填：注册电话
    public String open_bank; //必填：开户银行
    public String bank_account; //必填：银行账户
    public int id; //必填：如果进入订单页面有返回信息，必须带返回的那个ID值，如果之前没有开过增值税发票，这个值带0

}
