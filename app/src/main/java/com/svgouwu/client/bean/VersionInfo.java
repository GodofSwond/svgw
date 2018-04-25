package com.svgouwu.client.bean;

/**
 * 应用版本
 * Created by topwolf on 2016/4/20.
 */
public class VersionInfo {

//    public int mobile_apk_status; //0：强制升级，2：非强制升级
    public String version; //
    public int build; //
    public int isForce;//是否强制更新（0：否，1：是）
    public int isUpdate;//是否弹框更新（0：否，1：是）
    public String downloadUrl;//最新版本SDK下载地址URL
    public String content; //更新内容
    public String loadimageUrl;//APP的load图下载地址 URL
}
