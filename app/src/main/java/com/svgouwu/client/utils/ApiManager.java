package com.svgouwu.client.utils;

import com.svgouwu.client.Constant;
import com.svgouwu.client.MyApplication;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by topwolf on 2016/4/27.
 */
public class ApiManager {
    public static Map<String, String> simpleHeader = new HashMap<String, String>();
    static {
        simpleHeader.put("client", "android");
        String code = SystemHelper.getAppVersionCode(MyApplication.getInstance())+"";
        simpleHeader.put("version", code);
        simpleHeader.put("channel", Constant.CHANNEL);
    }
}
