package com.svgouwu.client.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.svgouwu.client.R;
import com.svgouwu.client.utils.CommonUtils;

/**
 * Created by whq on 2017/11/17.
 */

public class CouponExchangeDialog extends Dialog {

    public CouponExchangeDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public CouponExchangeDialog(Context context) {
        super(context);
    }

    public CouponExchangeDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public static class Builder {

        private Context mContext;
        private View mContentView;
        private boolean mCancelable = true;

        private OnClickListener mPositiveListener, mNegativeListener, mVerification;

        public Builder(Context context) {
            mContext = context;
        }

        public Builder setContentView(View v) {
            mContentView = v;
            return this;
        }

        public Builder setCancelListener(final OnClickListener listener) {
            mNegativeListener = listener;
            return this;
        }

        public Builder setReceiveListener(final OnClickListener listener) {
            mPositiveListener = listener;
            return this;
        }

        public Builder setVerificationCode(final OnClickListener listener) {
            mVerification = listener;
            return this;
        }

        ImageView iv_coupon_excancle;//, iv_coupon_vc;
        RelativeLayout rl_coupon_vc;
        TextView tv_coupon_change;
        EditText et_coupon_redeem, et_coupon_vc;
        CodeView cv_verification;

        public CouponExchangeDialog create() {

            final CouponExchangeDialog dialog = new CouponExchangeDialog(mContext, R.style.hf_dialog);
            dialog.setContentView(mContentView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));

            iv_coupon_excancle = (ImageView) mContentView.findViewById(R.id.iv_coupon_excancle);//关闭
            rl_coupon_vc = (RelativeLayout) mContentView.findViewById(R.id.rl_coupon_vc);//看不清
            tv_coupon_change = (TextView) mContentView.findViewById(R.id.tv_coupon_change);//立即兑换
            cv_verification = (CodeView) mContentView.findViewById(R.id.cv_verification);
            et_coupon_redeem = (EditText) mContentView.findViewById(R.id.et_coupon_redeem);//兑换码
            et_coupon_vc = (EditText) mContentView.findViewById(R.id.et_coupon_vc);//验证码
            if (mNegativeListener != null) {
                iv_coupon_excancle.setOnClickListener(new View.OnClickListener() {
                    //取消弹窗
                    @Override
                    public void onClick(View v) {
                        mNegativeListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                    }
                });
            }
            if (mPositiveListener != null) {
                tv_coupon_change.setOnClickListener(new View.OnClickListener() {
                    //兑换礼券
                    @Override
                    public void onClick(View v) {
                        mPositiveListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                    }
                });
            }
            if (mVerification != null) {
                rl_coupon_vc.setOnClickListener(new View.OnClickListener() {
                    //看不清，换一张
                    @Override
                    public void onClick(View v) {
                        mVerification.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                        //重新初始化验证码
                        //   res = cv_verification.getValidataAndSetImage();
                        cv_verification.code = cv_verification.createCode();
                        cv_verification.invalidate();
                    }
                });
            }

            dialog.setCancelable(mCancelable);
            return dialog;

        }

        public String changeIV() {
            return cv_verification.code;
        }

        public CouponExchangeDialog show() {
            CouponExchangeDialog dialog = create();
            dialog.show();
            return dialog;
        }

        /**
         * 双码输入情况
         *
         * @return
         */
        public String mredcode, mver;

        public int inPutCode(String ver) {
            mredcode = et_coupon_redeem.getText().toString();//兑换码
            mver = et_coupon_vc.getText().toString();//验证码

            if (!CommonUtils.isEmpty(mredcode) && !CommonUtils.isEmpty(mver)) {
                if (ver.equals(mver)) {
                    return 1;//验证码正确
                } else {
                    return 2;//验证码错误
                }
            } else {
                if (CommonUtils.isEmpty(mredcode)) {
                    return 3;//兑换码空的
                } else if (CommonUtils.isEmpty(mver)) {
                    return 4;//验证码空的的
                } else {
                    return 3;//都空的
                }
            }
        }


    }

}
