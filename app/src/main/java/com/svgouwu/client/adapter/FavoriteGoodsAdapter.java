package com.svgouwu.client.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.svgouwu.client.R;
import com.svgouwu.client.utils.DialogUtil;
import com.svgouwu.client.utils.MyViewHolder;
import com.svgouwu.client.utils.ToastUtil;

/**
 * Created by melon on 2017/6/23.
 */

public class FavoriteGoodsAdapter extends BaseAdapter {
    Activity act;

    public FavoriteGoodsAdapter(Activity act) {
        this.act = act;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(act, R.layout.item_favorite_goods, null);
        }

        ImageView ivDel = MyViewHolder.get(convertView, R.id.iv_item_favorite_goods_del);
        ivDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除商品
                DialogUtil.show(act, "确定要删除吗？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ToastUtil.toast("删除中...");
                    }
                });
            }
        });
        return convertView;
    }
}
