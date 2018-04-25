package com.svgouwu.client.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kili.okhttp.OkHttpUtils;
import com.svgouwu.client.Api;
import com.svgouwu.client.BaseActivity;
import com.svgouwu.client.MyApplication;
import com.svgouwu.client.R;
import com.svgouwu.client.bean.Address;
import com.svgouwu.client.bean.HttpResult;
import com.svgouwu.client.event.GotRegionEvent;
import com.svgouwu.client.utils.CommonCallback;
import com.svgouwu.client.utils.ToastUtil;
import com.svgouwu.client.utils.UmengStat;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

import static com.ta.utdid2.android.utils.StringUtils.isEmpty;

/**
 * Created by topwolf on 2017/6/23.
 * 编辑收货人--新建地址页
 */

public class AddressActivity extends BaseActivity {

    @BindView(R.id.et_consignee)
    EditText etConsignee;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_region)
    TextView tvRegion;
    @BindView(R.id.rl_region)
    RelativeLayout rlRegion;
    @BindView(R.id.et_zip_code)
    EditText etZipCode;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_sfz_code)
    EditText etSfzCode;
    @BindView(R.id.cb_default)
    CheckBox cbDefault;
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.tv_right)
    TextView tvRight;

    private String addid;
    private Address address = null;
    private String regionid;
    public static final int GET_REGION_ID = 100;
    private boolean isEdit = false;
    private boolean isValid = false;

    @Override
    protected int getContentView() {
        return R.layout.activity_address;
    }

    @Override
    public void initViews() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        isValid = getIntent().getBooleanExtra("isValid", false);
        if (getIntent().hasExtra("address")) {
            address = (Address) getIntent().getSerializableExtra("address");
        }
        if (address != null) {
            isEdit = true;
        }
        tvTitle.setText(getString(R.string.title_address_detail));
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(getString(R.string.address_save));


        if (isEdit) {
            addid = address.addrId;
            etConsignee.setText(address.consignee);
            etPhone.setText(address.phoneMob);
            regionid = address.regionId + "";
            tvRegion.setText(address.regionName);
            etZipCode.setText(address.zipcode);
            etAddress.setText(address.address);
            etName.setText(address.realName);
            etSfzCode.setText(address.idNumber);
        }
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.iv_left, R.id.tv_right, R.id.rl_region})
    @Override
    public void processClick(View view) {

        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.rl_region:
                Intent intent = new Intent();
                intent.setClass(AddressActivity.this, RegionActivity.class);
                startActivityForResult(intent, GET_REGION_ID);
                break;
            case R.id.tv_right:
                String consignee = etConsignee.getText().toString();
                String phonemob = etPhone.getText().toString();
                String regionName = tvRegion.getText().toString();
                if (TextUtils.isEmpty(regionName)) {
                    ToastUtil.toast("请选择地址");
                }
                String zip = etZipCode.getText().toString();
                String address = etAddress.getText().toString();
                String realname = etName.getText().toString();
                String id = etSfzCode.getText().toString();

                if (!consignee.equals("")) {

                } else {
                    ToastUtil.toast("请输入收货人");
                    return;
                }
                if (phonemob.length() == 11 && isMobileNO(phonemob)) {

                } else {
                    ToastUtil.toast("请输入正确的手机号");
                    return;
                }
                if (!regionName.equals("")) {

                } else {
                    ToastUtil.toast("请选择收货地址");
                    return;
                }
                if (zip.length() == 6 && isZipNO(zip)) {

                } else {
                    ToastUtil.toast("请输入正确的邮编");
                    return;
                }
                if (!address.equals("")) {

                } else {
                    ToastUtil.toast("请输入详细地址");
                    return;
                }
                if (!realname.isEmpty() && isChiese(realname)) {

                } else {
                    ToastUtil.toast("请输入正确的姓名");
                    return;
                }
                if (isID(id)) {

                } else {
                    ToastUtil.toast("请输入正确的身份证号");
                    return;
                }
                String operationType;
                if (isEdit) {
                    operationType = "update";
                } else {
                    operationType = "add";
                }
                process(consignee, address, phonemob, regionid, zip, realname, id, operationType, addid);
                break;
        }
    }

    /**
     * 判断手机号
     */
    public static boolean isMobileNO(String phonemob) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(phonemob);
        return m.matches();
    }

    /**
     * 判断邮编
     */
    public static boolean isZipNO(String zipString) {
        String str = "^[0-9][0-9]{5}$";
        return Pattern.compile(str).matcher(zipString).matches();
    }

    /**
     * 是否是中文:是为true，否则false
     */
    public static Boolean isChiese(String realname) {
        Boolean isChinese = true;
        String chinese = "[\u0391-\uFFE5]";
        if (!isEmpty(realname)) {
            //获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
            for (int i = 0; i < realname.length(); i++) {
                //获取一个字符
                String temp = realname.substring(i, i + 1);
                //判断是否为中文字符
                if (temp.matches(chinese)) {
                } else {
                    isChinese = false;
                }
            }
        }
        return isChinese;
    }

    /**
     * 验证身份证号是否符合规则
     */
    public boolean isID(String id) {
        String regx = "[0-9]{17}x";
        String reg1 = "[0-9]{15}";
        String regex = "[0-9]{18}";
        return id.matches(regx) || id.matches(reg1) || id.matches(regex);
    }

    /**
     * 个人中心收货地址操作（添加，更新）
     *
     * @param consignee 收货人姓名
     * @param address   详细地址
     * @param phonemob  收货人电话
     * @param regionid  地区ID（最后一级地址ID）
     * @param zip       邮政编码
     * @param realname  真实姓名
     * @param id        身份证号码
     * @param type      操作类型：添加：add 更新：update
     * @param addid     地址ID，只有在更新操作是才为必填
     */
    private void process(String consignee, String address, String phonemob, String regionid, String zip, final String realname, String id, String type, String addid) {
        String url = Api.URL_ADDRESS_PROCESS;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", MyApplication.getInstance().getLoginKey());
        params.put("consignee", consignee);
        params.put("address", address);
        params.put("phonemob", phonemob);
        params.put("regionid", regionid);
        params.put("zip", zip);
        params.put("realname", realname);
        params.put("id", id);
        params.put("type", type);
        if ("update".equals(type)) {
            params.put("addid", addid);
        }
        //是否是实名认证，0：否，1：是
        if (isValid) {
            params.put("isvalid", "1");
        } else {
            params.put("isvalid", "0");
        }
        if (cbDefault.isChecked()) {
            params.put("setdefault", "1");
        } else {
            params.put("setdefault", "0");
        }
        OkHttpUtils.post().url(url).params(params).build().execute(new CommonCallback<Object>() {

            @Override
            public void onError(Call call, Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(HttpResult<Object> response) {
                if (response.isSuccess()) {
                    ToastUtil.toast("保存成功！");
                    setResult(RESULT_OK);
                    finish();
                } else {
                    ToastUtil.toast(response.msg);
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(GotRegionEvent event) {
//        ToastUtil.toast(event.regionId+"==="+event.regionName);
        if (event != null) {
            regionid = event.regionId;
            tvRegion.setText(event.regionName);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(UmengStat.NEWADDRESSPAGE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(UmengStat.NEWADDRESSPAGE);
    }
}
