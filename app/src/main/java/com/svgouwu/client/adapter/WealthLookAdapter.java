package com.svgouwu.client.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.svgouwu.client.R;
import com.svgouwu.client.bean.WealthLookBean;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/11/29.
 */

public class WealthLookAdapter extends CommonAdapter<WealthLookBean.LookListBean> {


    private List<WealthLookBean.LookListBean> datas;
    private Context context;

    public WealthLookAdapter(Context context, int layoutId, List<WealthLookBean.LookListBean> datas) {
        super(context, layoutId, datas);
        this.context = context;
        this.datas = datas;
    }

    @Override
    protected void convert(ViewHolder holder, WealthLookBean.LookListBean lookListBean, int position) {
        holder.setText(R.id.item_money_look_tvMoney, lookListBean.gsshare_rebate);
        holder.setText(R.id.item_money_look_tvTime, lookListBean.addtime);
        holder.setText(R.id.item_money_look_tvNumber, lookListBean.number);
        holder.setText(R.id.item_money_look_tvUser, lookListBean.username);

        final TextView tvNumber = holder.getView(R.id.item_money_look_tvNumber);
        tvNumber.setEllipsize(TextUtils.TruncateAt.END);//字体过长尾部以...显示-----------------------------
        tvNumber.setMaxWidth(500);
        tvNumber.setSingleLine(true);
        tvNumber.setOnClickListener(new View.OnClickListener() {
            Boolean flag = true;

            @Override
            public void onClick(View v) {
                if (flag) {
                    flag = false;
                    tvNumber.setEllipsize(null);// 展开
                    tvNumber.setSingleLine(flag);
                } else {
                    flag = true;
                    tvNumber.setEllipsize(TextUtils.TruncateAt.END); // 收缩
                    tvNumber.setSingleLine(flag);
                }
            }
        });
    }
}
