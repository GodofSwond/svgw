package com.svgouwu.client.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kili.okhttp.OkHttpUtils;
import com.svgouwu.client.Api;
import com.svgouwu.client.BaseActivity;
import com.svgouwu.client.MyApplication;
import com.svgouwu.client.R;
import com.svgouwu.client.bean.AddedValueInvoice;
import com.svgouwu.client.bean.AddedValueInvoice2;
import com.svgouwu.client.bean.Address;
import com.svgouwu.client.bean.AddressResult;
import com.svgouwu.client.bean.HttpResult;
import com.svgouwu.client.utils.CommonCallback;
import com.svgouwu.client.utils.ToastUtil;
import com.svgouwu.client.utils.UmengStat;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

/**
 * 增值税发票
 * Created by topwolf on 2017/7/28.
 */

public class AddedValueInvoiceActivity extends BaseActivity {


    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_company)
    EditText etCompany;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_bank)
    EditText etBank;
    @BindView(R.id.et_bank_account)
    EditText etBankAccount;
    @BindView(R.id.btn_submit)
    Button btnSubmit;

    private AddedValueInvoice2 invoice;
    private int id;
    @Override
    protected int getContentView() {
        return R.layout.activity_added_value_invoice;
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        getInvoice();
//        getIntent().getIntExtra("id",0);
    }

    @OnClick({R.id.iv_left,R.id.btn_submit})
    @Override
    public void processClick(View view) {
        switch (view.getId()){
            case R.id.iv_left:
                finish();
                break;
            case R.id.btn_submit:
                invoice = new AddedValueInvoice2();
                String company = etCompany.getText().toString();
                String code = etCode.getText().toString();
                String address = etAddress.getText().toString();
                String phone = etPhone.getText().toString();
                String bank = etBank.getText().toString();
                String account = etBankAccount.getText().toString();

                invoice.type = 2;
                invoice.invoice_content = 0;
                invoice.unit_name = company;
                invoice.ident_code = code;
                invoice.register_addr = address;
                invoice.register_tel = phone;
                invoice.open_bank = bank;
                invoice.bank_account = account;
                invoice.id = id;
                checkInvoice(new Gson().toJson(invoice));

                break;
        }
    }

    private void checkInvoice(String invoiceInfo) {

        String url = Api.URL_CHECK_INVOICE;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", MyApplication.getInstance().getLoginKey());
        params.put("invoice", invoiceInfo);

        OkHttpUtils.post().url(url).params(params).build().execute(new CommonCallback<AddedValueInvoice2>() {
            @Override
            public void onBefore(Request request) {
                super.onBefore(request);
                weixinDialogInit("校验中...");
            }

            @Override
            public void onError(Call call, Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onAfter() {
                super.onAfter();
                cancelWeiXinDialog();
            }

            @Override
            public void onResponse(HttpResult<AddedValueInvoice2> response) {
                try {
                    if (response.isSuccess()) {
                        ToastUtil.toast("保存成功");
                        setResult(RESULT_OK,new Intent().putExtra("invoice",response.data));
                        finish();
                    }else{
                        ToastUtil.toast(response.msg);
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void getInvoice() {

        String url = Api.URL_GET_INVOICE;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", MyApplication.getInstance().getLoginKey());

        OkHttpUtils.post().url(url).params(params).build().execute(new CommonCallback<AddedValueInvoice>() {
            @Override
            public void onBefore(Request request) {
                super.onBefore(request);
                weixinDialogInit("加载中...");
            }

            @Override
            public void onError(Call call, Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onAfter() {
                super.onAfter();
                cancelWeiXinDialog();
            }

            @Override
            public void onResponse(HttpResult<AddedValueInvoice> response) {
                try {
                    if (response.isSuccess()) {
                        if(response.data!=null){
                            etCompany.setText(response.data.unit_name);
                            etCode.setText(response.data.ident_code);
                            etAddress.setText(response.data.register_addr);
                            etPhone.setText(response.data.register_tel);
                            etBank.setText(response.data.open_bank);
                            etBankAccount.setText(response.data.bank_account);
                            id = response.data.id;
                        }
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(UmengStat.THEVATTICKETPAGE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(UmengStat.THEVATTICKETPAGE);
    }
}
