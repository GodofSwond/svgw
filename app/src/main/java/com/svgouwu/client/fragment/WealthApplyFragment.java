package com.svgouwu.client.fragment;

import android.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kili.okhttp.OkHttpUtils;
import com.svgouwu.client.Api;
import com.svgouwu.client.BaseFragment;
import com.svgouwu.client.IEditTextChangeListener;
import com.svgouwu.client.MyApplication;
import com.svgouwu.client.R;
import com.svgouwu.client.bean.HttpResult;
import com.svgouwu.client.bean.WealthApplyBean;
import com.svgouwu.client.utils.CommonCallback;
import com.svgouwu.client.utils.CommonUtils;
import com.svgouwu.client.utils.ToastUtil;
import com.svgouwu.client.utils.WorksSizeCheckUtil;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

import static com.svgouwu.client.utils.CommonUtils.isEmpty;

/**
 * Created by Administrator on 2017/11/17.
 * 申请提现
 */

public class WealthApplyFragment extends BaseFragment {

    private String name, bank, card, money;

    @BindView(R.id.frg_money_apply_tvSubmit)
    TextView tvSubmit;
    @BindView(R.id.frg_money_apply_etName)
    EditText etName;
    @BindView(R.id.frg_money_apply_etBank)
    EditText etBank;
    @BindView(R.id.frg_money_apply_etFee)
    TextView tvFee;
    @BindView(R.id.frg_money_apply_etMoney)
    TextView tvMoney;
    @BindView(R.id.frg_money_apply_etCard)
    EditText etCard;

    @Override
    protected int getContentView() {
        return R.layout.fragment_wealth_apply;
    }

    @Override
    public void initViews() {
        showTopBar();
    }

    private void showTopBar() {
        TextView tv_title = findView(R.id.tv_title);
        tv_title.setText("提现申请");
        ImageView iv_left = findView(R.id.iv_left);
        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        showButton();//姓名、卡号、银行输入完展示红色按钮
        getMoney();
//        etCard.addTextChangedListener(watcher);
    }

    /**
     * 请求数据金额提示
     */
    private void getMoney() {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", MyApplication.getInstance().getLoginKey());
        OkHttpUtils.post().url(Api.URL_WEALTH_CLEARMONEY).params(params).build().execute(new CommonCallback<WealthApplyBean>() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(HttpResult<WealthApplyBean> response) {
                money = response.data.money;
                tvFee.setText("0.0");
                tvMoney.setText(money);
            }
        });
    }

    /**
     * 银行卡输入框实现自动加空格
     */
    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String str = s.toString().trim().replace(" ", "");
            String result = "";
            if (str.length() >= 4) {
                etCard.removeTextChangedListener(watcher);
                for (int i = 0; i < str.length(); i++) {
                    result += str.charAt(i);
                    if ((i + 1) % 4 == 0) {
                        result += " ";
                    }
                }
                if (result.endsWith(" ")) {
                    result = result.substring(0, result.length() - 1);
                }
                etCard.setText(result);
                etCard.addTextChangedListener(watcher);
                etCard.setSelection(etCard.getText().toString().length());//焦点到输入框最后位置
            }
        }
    };


    /**
     * 通过输入内容判断，只有当全部内容填完，才能显示红色按钮
     */
    private void showButton() {
        //创建工具类对象 把要改变颜色的tv先传过去
        WorksSizeCheckUtil.textChangeListener textChangeListener = new WorksSizeCheckUtil.textChangeListener(tvSubmit);
        //把所有要监听的edittext都添加进去
        textChangeListener.addAllEditText(etName, etBank, etCard);
        //接口回调 在这里拿到boolean变量 根据isHasContent的值决定 tv 应该设置什么颜色
        WorksSizeCheckUtil.setChangeListener(new IEditTextChangeListener() {
            @Override
            public void textChange(boolean isHasContent) {
                if (isHasContent) {
                    tvSubmit.setBackgroundResource(R.drawable.fragment_wealth_apply_btn);
                } else {
                    tvSubmit.setBackgroundResource(R.drawable.fragment_wealth_apply_40btn);
                }
            }
        });
    }

    /**
     * 请求网络数据
     */
    private void process() {
        name = etName.getText().toString().trim();
        if (isChiese(name) && !CommonUtils.isEmpty(name)) {
        } else {
            ToastUtil.toast("请输入正确姓名");
            return;
        }
        card = etCard.getText().toString().trim();
        if (checkBankCard(card) && !CommonUtils.isEmpty(card)) {
        } else {
            ToastUtil.toast("请输入正确卡号");
            return;
        }
        bank = etBank.getText().toString().trim();
        if (isChiese(bank) && !CommonUtils.isEmpty(bank)) {
        } else {
            ToastUtil.toast("请输入正确支行名称");
            return;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("token", MyApplication.getInstance().getLoginKey());
        params.put("name", name);
        params.put("card_number", card);
        params.put("bankname", bank);
        params.put("money", money);
        params.put("brokerage", "0.0");

        try {
            OkHttpUtils.post().url(Api.URL_WEALTH_APPLYCLEAR).params(params).build().execute(new CommonCallback<WealthApplyBean>() {
                @Override
                public void onError(Call call, Exception e) {

                }

                @Override
                public void onResponse(HttpResult<WealthApplyBean> response) {
                    if (response.isSuccess()) {
                        getActivity().finish();//支付成功后返回
                        ToastUtil.toast(response.msg);
                    } else {
                        ToastUtil.toast(response.msg);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 校验银行卡卡号
     *
     * @param cardId
     * @return
     */
    public static boolean checkBankCard(String cardId) {
        char bit = getBankCardCheckCode(cardId
                .substring(0, cardId.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return cardId.charAt(cardId.length() - 1) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     *
     * @param nonCheckCodeCardId
     * @return
     */
    public static char getBankCardCheckCode(String nonCheckCodeCardId) {
        if (nonCheckCodeCardId == null
                || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            // 如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }

    /**
     * 是否是中文:是为true，否则false
     */
    public static Boolean isChiese(String name) {
        Boolean isChinese = true;
        String chinese = "[\u0391-\uFFE5]";
        if (!isEmpty(name)) {
            //获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
            for (int i = 0; i < name.length(); i++) {
                //获取一个字符
                String temp = name.substring(i, i + 1);
                //判断是否为中文字符
                if (temp.matches(chinese)) {
                } else {
                    isChinese = false;
                }
            }
        }
        return isChinese;
    }

    @OnClick({R.id.frg_money_apply_tvSubmit})
    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.frg_money_apply_tvSubmit:
                process();
                break;
        }
    }
}
