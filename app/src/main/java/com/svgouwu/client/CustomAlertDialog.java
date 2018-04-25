package com.svgouwu.client;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.svgouwu.client.R;
import com.svgouwu.client.utils.CommonUtils;
import com.svgouwu.client.utils.ToastUtil;

/**
 * Created by Administrator on 2018/1/26.
 */

public class CustomAlertDialog extends Dialog implements View.OnClickListener {
    private View view;
    private TextView tvConfirm;
    private EditText etApplyMoney;
    /**
     * 一。内部接口，要求实现一个callback方法
     *
     * @author Administrator cuiweiyou.com
     */
    private ICallBack icb;

    /**
     * 初始化界面
     */
    private void initView() {
        view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_gold, null);
        etApplyMoney = (EditText) view.findViewById(R.id.dialog_gold_etApplyMoney);
        tvConfirm = (TextView) view.findViewById(R.id.dialog_gold_tvConfirm);
        tvConfirm.setOnClickListener(this);
        setContentView(view);
    }

    public CustomAlertDialog(Context context) {
        super(context);
        initView();
    }

    public CustomAlertDialog(Context context, boolean cancelable,
                             OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView();
    }

    public CustomAlertDialog(Context context, int theme) {
        super(context, theme);
        initView();
    }

    /**
     * 二。自定义的构造方法，需要一个ICallBack接口
     *
     * @param context
     * @param icb     回调器
     */
    public CustomAlertDialog(Context context, ICallBack icb) {
        super(context);
        this.icb = icb;
        initView();
    }

    /**
     * 三。设置回调变量
     *
     * @param cb
     */
// 如果不是使用上面接收icallback参数的(二)构造方法创建此自定义对话框，则需要使用此方法指定icb变量
    public void setCallBack(ICallBack cb) {
        icb = cb;
    }

    /**
     * 四。实现的监听器的方法，判断点击的控件
     * 当此自定义对话框点击按钮2时，向属性icb实例传一个参数
     * 这个参数会被界面接收使用
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.dialog_gold_tvConfirm:
                if (icb != null) {
                    // 这个方法在传入的回调器中实现
                    if (etApplyMoney.getText().length() == 0) {
                        ToastUtil.toast("四维币不能为空");
                    } else {
                        icb.callback(etApplyMoney.getText().toString());
                        dismiss();
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * 一。内部接口，要求实现一个callback方法
     *
     * @author Administrator cuiweiyou.com
     */
    public interface ICallBack {
        void callback(String str);
    }
}
