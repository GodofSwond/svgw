package com.svgouwu.client.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.svgouwu.client.R;
import com.svgouwu.client.bean.WealthNotesBean;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/11/24.
 */

public class WealthNotesAdapter extends CommonAdapter<WealthNotesBean.NotesItemBean> {

    private Context context;
    private List<WealthNotesBean.NotesItemBean> datas;

    public WealthNotesAdapter(Context context, int layoutId, List<WealthNotesBean.NotesItemBean> datas) {
        super(context, layoutId, datas);
        this.context = context;
        this.datas = datas;
    }

    @Override
    protected void convert(ViewHolder holder, WealthNotesBean.NotesItemBean notesItemBean, int position) {
        holder.setText(R.id.item_notes_tvNumber, notesItemBean.number);
        holder.setText(R.id.item_notes_tvState, notesItemBean.status);
        holder.setText(R.id.item_notes_tvMoney, notesItemBean.amount);
        holder.setText(R.id.item_notes_tvTime, notesItemBean.addtime);
        TextView tvState = holder.getView(R.id.item_notes_tvState);
        if (notesItemBean.status.equals("打款成功")) {
            holder.setTextColorRes(R.id.item_notes_tvState, R.color.grey_color3F);
        } else if (notesItemBean.status.equals("未通过审核")) {
            holder.setTextColorRes(R.id.item_notes_tvState, R.color.color_rebate_btn);
        } else if (tvState.equals("审核中")) {
            tvState.setTextColor(context.getResources().getColor(R.color.text_click));
        } else {

        }

        final TextView tvNumber = holder.getView(R.id.item_notes_tvNumber);
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
