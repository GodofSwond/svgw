package com.svgouwu.client;

import com.kili.okhttp.OkHttpUtils;
import com.svgouwu.client.bean.HttpResult;
import com.svgouwu.client.utils.CommonCallback;

import java.util.HashMap;

import okhttp3.Call;

/**
 * Created by topwolf on 2017/8/8.
 */

public class OrderManager {

    public static void cancelOrder(String token, final String reason, String orderIds, final OrderOperationCallBack callBack){
        String url = Api.URL_ORDER_CANCEL;
        HashMap<String, String> params = new HashMap<>();
        params.put("token",token);
        params.put("reason",reason);
        params.put("orderIds",orderIds);

        OkHttpUtils.post().url(url).params(params).build().execute(new CommonCallback<Object>() {
            @Override
            public void onError(Call call, Exception e) {
                e.printStackTrace();
                if(callBack!=null){
                    callBack.onError();
                }
            }

            @Override
            public void onResponse(HttpResult<Object> response) {
                try {
                    if (response.isSuccess()) {
                        if(callBack!=null){
                            callBack.onSuccess();
                        }
                    }else{
                        if(callBack!=null){
                            callBack.onFail(response.msg);
                        }
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                    if(callBack!=null){
                        callBack.onError();
                    }
                }
            }
        });
    }

    public static void receive(String orderId, final OrderOperationCallBack callBack) {
        String url = Api.URL_ORDER_RECEIVE;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", MyApplication.getInstance().getLoginKey());
        params.put("orderId", orderId);

        OkHttpUtils.post().url(url).params(params).build().execute(new CommonCallback<Object>() {
            @Override
            public void onError(Call call, Exception e) {
                e.printStackTrace();
                if(callBack!=null){
                    callBack.onError();
                }
            }

            @Override
            public void onResponse(HttpResult<Object> response) {
                try {
                    if (response.isSuccess()) {
                        if(callBack!=null){
                            callBack.onSuccess();
                        }
                    }else{
                        if(callBack!=null){
                            callBack.onFail(response.msg);
                        }
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                    if(callBack!=null){
                        callBack.onError();
                    }
                }
            }
        });
    }

    public interface OrderOperationCallBack {
        void onSuccess();
        void onFail(String msg);
        void onError();
    }
}
