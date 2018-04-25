package com.bracode.ui;

import android.view.View;
import android.widget.TextView;

import com.kili.okhttp.OkHttpUtils;
import com.svgouwu.client.BaseActivity;
import com.svgouwu.client.MyApplication;
import com.svgouwu.client.R;
import com.svgouwu.client.bean.HttpResult;
import com.svgouwu.client.bean.SpreedData;
import com.svgouwu.client.utils.CommonCallback;
import com.svgouwu.client.utils.CustomToast;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

import static com.svgouwu.client.Api.URL_SCAN;
import static com.svgouwu.client.Api.URL_SCAN_LOGIN;

/**
 * Created by whq on 2017/11/13.
 * 扫描登陆页面
 */

public class ScanResultActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;//页面名称

    private String ScanState;
    String[] code;

    @Override
    protected int getContentView() {
        return R.layout.activity_scanresult;
    }

    @Override
    public void initViews() {
        ScanState = getIntent().getStringExtra("ScanState");
        code = ScanState.split("code=")[1].split("&");
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        tv_title.setText("扫码登陆");
        scan();
    }

    @OnClick({R.id.iv_left, R.id.tv_scan_login, R.id.tv_scan_cancel})
    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.tv_scan_login:
                //确认登录
                scanLogin("1");
                break;
            case R.id.tv_scan_cancel:
                //取消登陆
                scanLogin("2");

                break;
            default:
                break;
        }
    }

    /**
     * 扫码登陆接口
     */
    private void scan() {
        HashMap<String, String> map = new HashMap<>();
        map.put("token", MyApplication.getInstance().getLoginKey());
        map.put("code", code[0]);
        OkHttpUtils.post().url(URL_SCAN).params(map).build().execute(new CommonCallback<SpreedData>() {

            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(HttpResult<SpreedData> response) {
                try {
                    if (response.isSuccess()) {
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 确认/取消登陆接口
     */
    private void scanLogin(final String type) {
        final HashMap<String, String> map = new HashMap<>();
        map.put("token", MyApplication.getInstance().getLoginKey());
        map.put("code", code[0]);
        map.put("status", type);
        OkHttpUtils.post().url(URL_SCAN_LOGIN).params(map).build().execute(new CommonCallback<SpreedData>() {

            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(HttpResult<SpreedData> response) {
                try {
                    if (response.isSuccess()) {
                        if (type.equals("1")) {
                            CustomToast.toast(mContext, "授权登陆成功！");
                        }
                        finish();
                    }else{
                        //二维码过期
                        if(type.equals("1")){
                            CustomToast.toast(mContext,response.msg);
                        }
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
