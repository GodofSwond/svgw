package com.svgouwu.client.adapter;

import android.content.Intent;
import android.view.View;

import com.svgouwu.client.Api;
import com.svgouwu.client.MyApplication;
import com.svgouwu.client.R;
import com.svgouwu.client.activity.WebViewActivity;
import com.svgouwu.client.bean.GoodsInfo;
import com.svgouwu.client.fragment.GoodsFavoriteFragment;
import com.svgouwu.client.utils.CommonUtils;
import com.svgouwu.client.utils.ImageLoader;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * 商品收藏
 * Created by melon on 2017/6/23.
 */

public class GoodsFavoriteAdapter extends CommonAdapter<GoodsInfo> {
    private GoodsFavoriteFragment fragment;
    private List datas;

    public GoodsFavoriteAdapter(GoodsFavoriteFragment fragment, int layoutId, List datas) {
        super(fragment.getContext(), layoutId, datas);
        this.fragment = fragment;
        this.datas = datas;
    }

    @Override
    protected void convert(ViewHolder holder, final GoodsInfo info, int position) {
        holder.setText(R.id.tv_goods_fav_name, info.goodsName);
        holder.setText(R.id.tv_goods_fav_money, CommonUtils.getCurrencySign() + info.price + "元");

        ImageLoader.with(MyApplication.getInstance(), info.defaultImage, holder.getView(R.id.iv_goods_fav_pic));

        holder.getView(R.id.iv_item_favorite_goods_del).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.onItemDelete(info);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入商品详情
                CommonUtils.enterGoodsDetails(info.goodsId);
            }
        });
    }

}
