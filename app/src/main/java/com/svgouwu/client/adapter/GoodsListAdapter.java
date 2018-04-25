package com.svgouwu.client.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.svgouwu.client.Api;
import com.svgouwu.client.R;
import com.svgouwu.client.activity.GoodsDetailsWebView;
import com.svgouwu.client.activity.WebViewActivity;
import com.svgouwu.client.bean.GoodsInfo;
import com.svgouwu.client.utils.CommonUtils;
import com.svgouwu.client.utils.ImageLoader;
import com.svgouwu.client.utils.ToastUtil;
import com.svgouwu.client.utils.UmengStat;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.List;

import static com.svgouwu.client.Api.URL_GOODS_DETAILS;

/**
 * 商品列表
 * Created by melon on 2017/7/14.
 * Email 530274554@qq.com
 */

public class GoodsListAdapter extends RecyclerView.Adapter<GoodsListAdapter.ItemViewHolder> {
    private Context context;
    private GridLayoutManager mLayoutManager;
    private static final int VIEW_TYPE_GRID = 1;
    private static final int VIEW_TYPE_LIST = 2;
    private List<GoodsInfo> datas;

    public GoodsListAdapter(Context context, List<GoodsInfo> datas, GridLayoutManager layoutManager) {
        this.context = context;
        this.datas = datas;
        this.mLayoutManager = layoutManager;
    }

    @Override
    public GoodsListAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_LIST) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goods_list, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goods_list_grid, parent, false);
        }
        return new ItemViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(GoodsListAdapter.ItemViewHolder holder, int position) {
        final GoodsInfo goodsInfo = datas.get(position);

        holder.tvTitle.setText(goodsInfo.goodsName);
        holder.tvMoney.setText(CommonUtils.getCurrencySign() + goodsInfo.price + " 元");
        ImageLoader.with(context, goodsInfo.defaultImage, holder.ivImage);
        holder.tvSaleNum.setText("已售 " + goodsInfo.sale + " 件, " + goodsInfo.comments + " 评论");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,String> map = new HashMap<String,String>();
                map.put("goodid",goodsInfo.goodsId);
                Intent intent = new Intent(context, GoodsDetailsWebView.class);
                intent.putExtra("url", URL_GOODS_DETAILS + goodsInfo.goodsId);
                intent.putExtra("goodsId", goodsInfo.goodsId);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                MobclickAgent.onEvent(context, UmengStat.CLASSIFICATIONTODETAILSCLICKNUMBER,map);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        int spanCount = mLayoutManager.getSpanCount();
        if (spanCount == 1) {
            return VIEW_TYPE_LIST;
        } else {
            return VIEW_TYPE_GRID;
        }
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView tvTitle;
        TextView tvSaleNum;
        TextView tvMoney;

        ItemViewHolder(View itemView, int viewType) {
            super(itemView);
            ivImage = (ImageView) itemView.findViewById(R.id.iv_item_goods_list_pic);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_item_goods_list_name);
            tvMoney = (TextView) itemView.findViewById(R.id.tv_item_goods_list_money);
            tvSaleNum = (TextView) itemView.findViewById(R.id.tv_item_goods_list_sell_num);
        }
    }
}
