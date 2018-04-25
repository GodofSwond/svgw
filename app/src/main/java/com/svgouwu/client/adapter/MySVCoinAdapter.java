package com.svgouwu.client.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.svgouwu.client.R;
import com.svgouwu.client.bean.WealthRebateBean;

import java.util.List;

/**
 * Created by whq on 2017/12/28.
 * 四维币adapter
 */

public class MySVCoinAdapter extends RecyclerView.Adapter<MySVCoinAdapter.ItemViewHolder> {

    List<WealthRebateBean.RebateListBean> list;
    public MySVCoinAdapter(List<WealthRebateBean.RebateListBean> list) {
        this.list = list;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_coin_item,
                 parent, false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        WealthRebateBean.RebateListBean bean = list.get(position);
        holder.tv_coin_item_name.setText(bean.productTitle);
        holder.tv_coin_item_time.setText(bean.addtime);
        holder.tv_coin_item_money.setText("+"+bean.gsrebate);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{

        TextView tv_coin_item_name, tv_coin_item_time, tv_coin_item_money;
        public ItemViewHolder(View itemView) {
            super(itemView);
            tv_coin_item_name = (TextView) itemView.findViewById(R.id.tv_coin_item_name);//商品名称
            tv_coin_item_time = (TextView) itemView.findViewById(R.id.tv_coin_item_time);//日期
            tv_coin_item_money = (TextView) itemView.findViewById(R.id.tv_coin_item_money);//金额
        }
    }
}
