package com.svgouwu.client.utils;

import android.util.Log;

import com.kili.okhttp.callback.Callback;
import com.svgouwu.client.MyApplication;
import com.svgouwu.client.bean.HttpResult;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * 基础回调
 * Created by topwolf on 2016/5/5.
 */
public abstract class CommonCallback<T> extends Callback<HttpResult<T>> {
    @Override
    public HttpResult parseNetworkResponse(Response response) throws IOException {
        HttpResult result = new HttpResult();
        String body = response.body().string();
        Log.d("zybody","result = " + body);
        JSONObject jsonObj;
        try {
            jsonObj = new JSONObject(body);
            String data = jsonObj.optString("data");
            if (!CommonUtils.isEmpty(data)) {
                if(data.startsWith("{")){
                    Type genType = getClass().getGenericSuperclass();
                    Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
                    result = (HttpResult<T>) GsonUtils.fromJsonObject(new StringReader(body), (Class) params[0]);
                }else if(data.startsWith("[")){
                    Type genType = getClass().getGenericSuperclass();
                    Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
                    result = GsonUtils.fromJsonArray(new StringReader(body), params[0]);
                }
                if(result.isTokenInvalid()){
                    MyApplication.getInstance().logout();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.data = null;
            result.error = "Server is busy now, please hold on.";
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }

}
