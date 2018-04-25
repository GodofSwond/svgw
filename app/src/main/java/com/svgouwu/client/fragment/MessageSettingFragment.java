package com.svgouwu.client.fragment;

import android.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kili.okhttp.OkHttpUtils;
import com.svgouwu.client.Api;
import com.svgouwu.client.BaseFragment;
import com.svgouwu.client.R;
import com.svgouwu.client.utils.CommonCallback;
import com.svgouwu.client.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/11/6.
 * 消息--设置
 */

public class MessageSettingFragment extends BaseFragment {

    private AlertDialog sDialog;
    private AlertDialog cDialog;
    private boolean b = true;

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.frg_msg_setting_ivInform)
    ImageView ivInform;
    @BindView(R.id.frg_msg_setting_ivCoupon)
    ImageView ivCoupon;
    @BindView(R.id.frg_msg_setting_tvClear)
    TextView tvClear;

    @Override
    protected int getContentView() {
        return R.layout.fragment_message_setting;
    }

    @Override
    public void initViews() {
        tv_title.setText("消息设置");
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        jsonData();
    }

    private void jsonData() {
        OkHttpUtils.post().url(Api.URL_DISCOUNTS_LIST).params(null).build().execute(new CommonCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(Object response) {

            }
        });
    }

    @OnClick({R.id.iv_left, R.id.frg_msg_setting_tvClear, R.id.frg_msg_setting_ivInform, R.id.frg_msg_setting_ivCoupon})
    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                getActivity().finish();
                break;
            case R.id.frg_msg_setting_tvClear:
                showDialog();
                break;
            case R.id.frg_msg_setting_ivInform:
                if (b) {
                    ivInform.setImageResource(R.drawable.icon_on);
                    b = false;
                } else {
                    ivInform.setImageResource(R.drawable.icon_off);
                    b = true;
                }
                break;
            case R.id.frg_msg_setting_ivCoupon:
                if (b) {
                    ivCoupon.setImageResource(R.drawable.icon_on);
                    b = false;
                } else {
                    ivCoupon.setImageResource(R.drawable.icon_off);
                    b = true;
                }
                break;
        }
    }

    /**
     * Dialog展示并延时取消
     */
    private void showDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        LinearLayout relativeLayout = (LinearLayout) layoutInflater.inflate(R.layout.dialog_message, null);
        sDialog = new AlertDialog.Builder(getContext(), R.style.MessageDialog).create();
        Window dialogWindow = sDialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//        dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
//        lp.x = 100; // 新位置X坐标
//        lp.y = 300; // 新位置Y坐标
        dialogWindow.setAttributes(lp);
        sDialog.show();
        sDialog.getWindow().setContentView(relativeLayout);
        sDialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        View tvCancel = sDialog.findViewById(R.id.dialog_message_tvCancel);
        View tvConfirm = sDialog.findViewById(R.id.dialog_message_tvConfirm);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sDialog.dismiss();
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sDialog.dismiss();
                showConfirmDialog();
            }
        });
    }

    /**
     * Dialog展示并延时取消
     */
    private void showConfirmDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        RelativeLayout relativeLayout = (RelativeLayout) layoutInflater.inflate(R.layout.dialog_message_clear, null);
        cDialog = new AlertDialog.Builder(getContext(), R.style.MessageDialogClear).create();
        Window dialogWindow = cDialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
//        lp.x = 100; // 新位置X坐标
//        lp.y = 300; // 新位置Y坐标
        dialogWindow.setAttributes(lp);
        cDialog.show();
        cDialog.getWindow().setContentView(relativeLayout);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);//让他显示1秒后，取消Dialog
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                cDialog.dismiss();
            }
        });
        thread.start();
    }
}
