package com.svgouwu.client.bean;

/**
 * Created by topwolf on 2017/6/7.
 */

public class StoreInfo {
    public String storeId;
    public String storeName;
    public boolean isChoosed;
    public boolean isEditor; //自己对该组的编辑状态
    public boolean ActionBarEditor;// 全局对该组的编辑状态
    public int flag;

    public StoreInfo(String storeId, String storeName,boolean isChoosed) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.isChoosed = isChoosed;
    }
    public StoreInfo(String storeName) {
        this.storeName = storeName;
    }
}
