package com.kili.okhttp.request;


import android.text.TextUtils;
import android.util.Log;

import com.kili.okhttp.OkHttpUtils;
import com.kili.okhttp.callback.Callback;
import com.kili.okhttp.utils.Exceptions;
import com.kili.okhttp.utils.L;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by zhy on 15/11/6.
 */
public abstract class OkHttpRequest
{
    private static final String TAG = "OkHttpRequest";
    protected String url;
    protected Object tag;
    protected Map<String, String> params;
    protected Map<String, String> headers;

    protected Request.Builder builder = new Request.Builder();

    protected OkHttpRequest(String url, Object tag,
                            Map<String, String> params, Map<String, String> headers)
    {
        this.url = url;
        this.tag = tag;
        this.params = params;
        this.headers = headers;
        OkHttpUtils okHttpUtils = OkHttpUtils.getInstance();
        if(!TextUtils.isEmpty(okHttpUtils.getOriginalUrl()) && !TextUtils.isEmpty(okHttpUtils.getReplaceUrl())){
//            if(okHttpUtils.getReplaceUrl().startsWith("http://") || okHttpUtils.getReplaceUrl().startsWith("https://")){
                this.url = url.replace(okHttpUtils.getOriginalUrl(),okHttpUtils.getReplaceUrl());
//            }
        }
        if (okHttpUtils.getCommonHeaders() != null) {
            if(this.headers == null){
                this.headers = new HashMap<String, String>();
            }
            this.headers.putAll(okHttpUtils.getCommonHeaders());
        }
        if (url == null)
        {
            Exceptions.illegalArgument("url can not be null.");
        }

        if(params!=null){
            printUrlLog(this.url, params);
        }else{
            L.e(url);
        }
        initBuilder();
    }

    private void printUrlLog(String url, Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry:params.entrySet()
             ) {
            String key = entry.getKey();
            String value = entry.getValue();

            sb.append("&"+key+"="+value);
        }
        L.e(url+sb.toString());
    }


    /**
     * 初始化一些基本参数 url , tag , headers
     */
    private void initBuilder()
    {
        builder.url(url).tag(tag);
        appendHeaders();
    }

    protected abstract RequestBody buildRequestBody();

    protected RequestBody wrapRequestBody(RequestBody requestBody, final Callback callback)
    {
        return requestBody;
    }

    protected abstract Request buildRequest(RequestBody requestBody);

    public RequestCall build()
    {
        return new RequestCall(this);
    }


    public Request generateRequest(Callback callback)
    {
        RequestBody requestBody = buildRequestBody();
        RequestBody wrappedRequestBody = wrapRequestBody(requestBody, callback);
        Request request = buildRequest(wrappedRequestBody);
        return request;
    }


    protected void appendHeaders()
    {
        Headers.Builder headerBuilder = new Headers.Builder();
        if (headers == null || headers.isEmpty()) return;

        for (String key : headers.keySet())
        {
            headerBuilder.add(key, headers.get(key));
        }
        builder.headers(headerBuilder.build());
    }

}
