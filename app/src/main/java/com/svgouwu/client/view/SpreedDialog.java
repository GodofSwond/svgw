package com.svgouwu.client.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.svgouwu.client.R;

/**
 * Created by whq on 2017/8/30.
 */

public class SpreedDialog extends Dialog {

    public SpreedDialog(Context context) {
        super(context);
    }

    public SpreedDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected SpreedDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public static class Builder {
        private Context mContext;
        private View mContentView;
        private String stateNum;//0054--未领取，0056--领取成功，0055--已领取
        private boolean mCancelable = true;


        private DialogInterface.OnClickListener mPositiveListener, mNegativeListener;


        public Builder(Context context) {
            mContext = context;
        }

        public Builder setContentView(View v, String num) {
            mContentView = v;
            stateNum = num;
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

        public SpreedDialog create() {
            final SpreedDialog dialog = new SpreedDialog(mContext, R.style.hf_dialog);
            dialog.setContentView(mContentView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));

            ImageView iv_hf_cancle = (ImageView) mContentView.findViewById(R.id.iv_hf_cancle);
            ImageView iv_hf_money = (ImageView) mContentView.findViewById(R.id.iv_hf_money);
            ImageView iv_hf_receive = (ImageView) mContentView.findViewById(R.id.iv_hf_receive);

            if (mNegativeListener != null) {
                iv_hf_cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mNegativeListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                    }
                });
            }
            if (mPositiveListener != null) {
                iv_hf_receive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPositiveListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                    }
                });
            }
           /* if ("0055".equals(stateNum)) {
                //已领取
                iv_hf_money.setImageResource(R.drawable.hf_rb_receive);
        //        iv_hf_receive.setImageResource(R.drawable.hf_redbag_btuse);

            } else if("0056".equals(stateNum)){
                //领取成功
        //        iv_hf_money.setImageResource(R.drawable.hf_redbag_success);
        //        iv_hf_receive.setImageResource(R.drawable.hf_redbag_btuse);
            } else{*/
                //未领取,0054
                iv_hf_money.setImageResource(R.drawable.hf_rb_noreceive);
                iv_hf_receive.setImageResource(R.drawable.hf_rb_btreceive);
            //}
            dialog.setCancelable(mCancelable);
            return dialog;

        }

        public SpreedDialog show() {
            SpreedDialog dialog = create();
            dialog.show();
            return dialog;
        }
    }

}
