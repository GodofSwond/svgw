package com.svgouwu.client.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.svgouwu.client.R;

/**
 * Created by whq on 2017/12/28.
 */

public class MysvCoinDialog extends Dialog {

    public MysvCoinDialog(Context context) {
        super(context);
    }

    public MysvCoinDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public MysvCoinDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public static class Builder {
        private Context mContext;
        private View mContentView;

        private OnClickListener mPositiveListener, mNegativeListener;

        public Builder(Context context) {
            mContext = context;
        }

        public Builder setOntentView(View view) {
            mContentView = view;
            return this;
        }

        public Builder setPositivite(OnClickListener listener) {
            mPositiveListener = listener;
            return this;
        }

        public Builder setNegative(OnClickListener listener) {
            mNegativeListener = listener;
            return this;
        }

        TextView tv_coin_sure, tv_coin_cancel;

        public MysvCoinDialog create() {

            final MysvCoinDialog dialog = new MysvCoinDialog(mContext, R.style.hf_dialog);
            dialog.setContentView(mContentView, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            tv_coin_sure = (TextView) mContentView.findViewById(R.id.tv_coin_sure);
            tv_coin_cancel = (TextView) mContentView.findViewById(R.id.tv_coin_cancel);
            if (mPositiveListener != null) {
                tv_coin_sure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPositiveListener.onClick(dialog, BUTTON_POSITIVE);
                    }
                });
            }
            if (mNegativeListener != null) {
                tv_coin_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mNegativeListener.onClick(dialog, BUTTON_NEGATIVE);
                    }
                });
            }
            return dialog;

        }


    }
}
