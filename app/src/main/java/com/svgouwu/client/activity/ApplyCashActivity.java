package com.svgouwu.client.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.kili.okhttp.OkHttpUtils;
import com.svgouwu.client.Api;
import com.svgouwu.client.BaseActivity;
import com.svgouwu.client.MyApplication;
import com.svgouwu.client.R;
import com.svgouwu.client.utils.CommonCallback;
import com.svgouwu.client.utils.CommonUtils;
import com.svgouwu.client.utils.CustomToast;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by whq on 2018/1/8.
 * 提现申请页面
 */

public class ApplyCashActivity extends BaseActivity {
    @BindView(R.id.tv_apply_name)
    TextView tv_apply_name;//账户名称
    @BindView(R.id.et_apply_pwd)
    EditText et_apply_pwd;//账户密码
    @BindView(R.id.et_apply_apwd)
    EditText et_apply_apwd;//确认密码
    @BindView(R.id.tv_apply_zhuan)
    TextView tv_apply_zhuan;//转账金额

    private Object applySure;
    String zhuanzhang;
    @Override
    protected int getContentView() {
        return R.layout.activity_apply_cash;
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        setTopBarViews(findViewById(R.id.rl_cash_title), true);
        if (MyApplication.getInstance().getPhone() != null) {
            tv_apply_name.setText(MyApplication.getInstance().getPhone());
        }
        zhuanzhang = getIntent().getStringExtra("applyMoney");
        tv_apply_zhuan.setText(zhuanzhang == null ? "0元": zhuanzhang+"元");
    }

    @OnClick({R.id.iv_cash_left, R.id.tv_apply_sure})
    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.iv_cash_left:
                finish();
                break;
            case R.id.tv_apply_sure:
                //确认提现
                CommonUtils.hideInputMode(this, true);
                //   getApplySure();
                CustomToast.showToasts(mContext,"开发中。。。。", 0);
                break;
            default:
                break;
        }
    }

    /**
     * 确认提现
     */
    private void getApplySure() {

        String pwd = et_apply_pwd.getText().toString();
        String apwd = et_apply_apwd.getText().toString();
        if (CommonUtils.isEmpty(pwd)) {
            CustomToast.showToasts(mContext, "账户密码不能为空！", 0);
            return;
        }
        if (CommonUtils.isEmpty(apwd)) {
            CustomToast.showToasts(mContext, "确认密码不能为空！", 0);
            return;
        }
        if (!pwd.equals(apwd)) {
            CustomToast.showToasts(mContext, "两次输入密码不一致！", 0);
            return;
        }
        String url = Api.BASE_URL;

        HashMap<String, String> map = new HashMap<>();
        map.put("key", pwd);
        map.put("key", apwd);
        OkHttpUtils.post().url(url).params(null).build().execute(new CommonCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(Object response) {
                finish();
            }
        });
    }
}
