package com.svgouwu.client.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.svgouwu.client.R;
import com.svgouwu.client.utils.LogUtils;
import com.svgouwu.client.utils.ModelRecyclerAdapter;
import com.svgouwu.client.utils.RecyclerItemViewId;

import butterknife.BindView;

/**
 * 订单列表Item ViewHolder
 * Created by melon on 2017/6/27.
 * Email 530274554@qq.com
 */

@RecyclerItemViewId(R.layout.item_order_list)
public class OrderListViewHolder extends ModelRecyclerAdapter.ModelViewHolder<String> {

    public OrderListViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void convert(String item, ModelRecyclerAdapter adapter, Context context, int position) {
    }
}
