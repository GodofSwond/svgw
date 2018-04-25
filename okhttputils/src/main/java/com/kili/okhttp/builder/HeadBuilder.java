package com.kili.okhttp.builder;

import com.kili.okhttp.OkHttpUtils;
import com.kili.okhttp.request.OtherRequest;
import com.kili.okhttp.request.RequestCall;

/**
 * Created by zhy on 16/3/2.
 */
public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers).build();
    }
}
