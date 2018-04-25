package com.svgouwu.client.wxapi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.kili.okhttp.OkHttpUtils;
import com.svgouwu.client.Api;
import com.svgouwu.client.MyApplication;
import com.svgouwu.client.activity.PayActivity;
import com.svgouwu.client.activity.PayCallBackActivity;
import com.svgouwu.client.bean.HttpResult;
import com.svgouwu.client.bean.SpreedData;
import com.svgouwu.client.utils.CommonCallback;
import com.svgouwu.client.utils.CommonUtils;
import com.svgouwu.client.utils.LogUtils;
import com.svgouwu.client.utils.SpUtil;
import com.svgouwu.client.utils.ToastUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.HashMap;

import okhttp3.Call;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
	
	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	
    private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.pay_result);
        
    	api = WXAPIFactory.createWXAPI(this, "wxae8e90dc4d7fc100");
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		LogUtils.e(TAG, "onPayFinish, errCode = " + resp.errCode);

		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			if(resp.errCode == 0){
				ToastUtil.toast("支付成功");
				OrderRebate();
				Intent intent = new Intent(WXPayEntryActivity.this, PayCallBackActivity.class);
				intent.putExtra("paytradeno", SpUtil.getString(this, "paytradeno"));
				intent.putExtra("orderList", SpUtil.getString(this, "orderList"));
				intent.putExtra("isWxPay", true);
				startActivity(intent);
			}else{
				ToastUtil.toast("支付失败，请重试");
				finish();
			}

		}
	}
	private void OrderRebate(){
		HashMap<String, String> map = new HashMap<>();
		map.put("token", MyApplication.getInstance().getLoginKey());
		map.put("code", SpUtil.getString(WXPayEntryActivity.this,"coded"));
		map.put("paytradeno", SpUtil.getString(WXPayEntryActivity.this,"paytradeno"));
		OkHttpUtils.post().url(Api.URL_ORDER_REBATE).params(map).build().execute(new CommonCallback<SpreedData>() {
			@Override
			public void onError(Call call, Exception e) {

			}

			@Override
			public void onResponse(HttpResult<SpreedData> response) {
				try {
					Log.d("whq","--bindagency222222---"+response.msg);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		if(!CommonUtils.isEmpty(SpUtil.getString(WXPayEntryActivity.this, "coded"))){
			//支付成功脱离关系
			SpUtil.setString(WXPayEntryActivity.this, "coded", "");
		}
		finish();
	}
}