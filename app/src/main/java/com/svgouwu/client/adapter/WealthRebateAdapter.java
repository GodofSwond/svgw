package com.svgouwu.client.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.svgouwu.client.R;
import com.svgouwu.client.bean.WealthRebateBean;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/11/28.
 */

public class WealthRebateAdapter extends CommonAdapter<WealthRebateBean.RebateListBean> {

    private Context context;
    private List<WealthRebateBean.RebateListBean> datas;

    public WealthRebateAdapter(Context context, int layoutId, List<WealthRebateBean.RebateListBean> datas) {
        super(context, layoutId, datas);
        this.context = context;
        this.datas = datas;
    }

    @Override
    protected void convert(ViewHolder holder, WealthRebateBean.RebateListBean rebateListBean, int position) {
        holder.setText(R.id.item_rebate_tvTitle, rebateListBean.productTitle);
        holder.setText(R.id.item_rebate_tvMoney, rebateListBean.gsrebate);
        holder.setText(R.id.item_rebate_tvTime, rebateListBean.addtime);

        final TextView tvTitle = holder.getView(R.id.item_rebate_tvTitle);
        tvTitle.setEllipsize(TextUtils.TruncateAt.END);//字体过长尾部以...显示-----------------------------
        tvTitle.setMaxWidth(100);
        tvTitle.setSingleLine(true);
        tvTitle.setOnClickListener(new View.OnClickListener() {
            Boolean flag = true;

            @Override
            public void onClick(View v) {
                if (flag) {
                    flag = false;
                    tvTitle.setEllipsize(null);// 展开
                    tvTitle.setSingleLine(flag);
                } else {
                    flag = true;
                    tvTitle.setEllipsize(TextUtils.TruncateAt.END); // 收缩
                    tvTitle.setSingleLine(flag);
                }
            }
        });
    }
}
