package com.svgouwu.client.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.svgouwu.client.R;
import com.svgouwu.client.activity.GoodsDetailsWebView;
import com.svgouwu.client.bean.GoodsInfo;

import java.util.List;

import static com.svgouwu.client.Api.URL_GOODS_DETAILS;

/**
 * Created by whq on 2017/12/21.
 * 我的钱包adapter
 */

public class MyWalletAdapter extends RecyclerView.Adapter<MyWalletAdapter.ItemViewHolder> {

private List<GoodsInfo> datas;
    private Context mContext;
    public MyWalletAdapter(Context context, List<GoodsInfo> datas){
        this.datas = datas;
        this.mContext = context;
    }
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_mywt_tuijian, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        GoodsInfo goodsInfo = datas.get(position);
        Glide.with(mContext).load(goodsInfo.defaultImage).into(holder.iv_item_mywt);
        holder.tv_item_mywt_title.setText(goodsInfo.goodsName);
        holder.tv_item_mywt_money.setText("￥ " + goodsInfo.price + " 元");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, GoodsDetailsWebView.class);
                intent.putExtra("url", URL_GOODS_DETAILS + "14015");
                intent.putExtra("goodsId", "14015");//goodsInfo.goodsId
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {

        return datas.size() <= 8 ? datas.size(): 8;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{

        private ImageView iv_item_mywt;
        private TextView tv_item_mywt_title,tv_item_mywt_money;
        public ItemViewHolder(View itemView) {
            super(itemView);
            iv_item_mywt = (ImageView) itemView.findViewById(R.id.iv_item_mywt);//商品图片
            tv_item_mywt_title = (TextView) itemView.findViewById(R.id.tv_item_mywt_title);//商品描述
            tv_item_mywt_money = (TextView) itemView.findViewById(R.id.tv_item_mywt_money);//商品金额
        }
    }
}
