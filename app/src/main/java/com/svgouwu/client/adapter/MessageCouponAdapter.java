package com.svgouwu.client.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.svgouwu.client.CommonFragmentActivity;
import com.svgouwu.client.R;
import com.svgouwu.client.bean.CouponsItemBean;
import com.svgouwu.client.fragment.MessageStoreFragment;
import com.svgouwu.client.bean.UnreceivedBean;
import com.svgouwu.client.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/13.
 */

public class MessageCouponAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CouponsItemBean> datas;
    private Context context;

    private boolean hasMore = true;
    private boolean fadeTips = false; // 变量，是否隐藏了底部的提示

    private int itemType = 0;     // 第一种ViewType，正常的item
    private int footType = 1;       // 第二种ViewType，底部的提示View
    private Handler handler = new Handler(Looper.getMainLooper());//开启主

    public MessageCouponAdapter(List<CouponsItemBean> datas, Context context, boolean hasMore) {
        this.datas = datas;
        this.context = context;
        this.hasMore = hasMore;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 根据返回的ViewType，绑定不同的布局文件，这里只有两种
        if (viewType == itemType) {
            return new ItemHolder(LayoutInflater.from(context).inflate(R.layout.item_message_coupon, null));//加载条目布局
        } else {
            return new FootHolder(LayoutInflater.from(context).inflate(R.layout.footview_message_coupon, null));//加载底部布局
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemHolder) {
            ((ItemHolder) holder).tvSubtract.setText("充话费领券满" + datas.get(position).minAmount + "减" + datas.get(position).couponValue);
            ((ItemHolder) holder).tvCount.setText(datas.get(position).couponName);
            ((ItemHolder) holder).tvTime.setText(datas.get(position).startTime);
            ((ItemHolder) holder).tvLook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommonUtils.startAct(context, CommonFragmentActivity.FRAGMENT_STORE);
                }
            });

        } else {
            ((FootHolder) holder).tips.setVisibility(View.VISIBLE);
            if (hasMore == true) {
                fadeTips = false;
                if (datas.size() > 10) {
                    ((FootHolder) holder).tips.setText("正在加载更多...");
                }
            } else {
                if (datas.size() > 0) {
                    ((FootHolder) holder).tips.setText("正在玩命加载中...");
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ((FootHolder) holder).tips.setVisibility(View.GONE);
                            fadeTips = true;
                            hasMore = true;
                        }
                    }, 500);
                }
            }
        }
    }

    /**
     * 加载条目布局
     */
    class ItemHolder extends RecyclerView.ViewHolder {
        private TextView tvSubtract;
        private TextView tvCount;
        private TextView tvTime;
        private TextView tvLook;
        private ImageView ivImg;

        public ItemHolder(View itemView) {
            super(itemView);
            tvSubtract = (TextView) itemView.findViewById(R.id.item_msg_coupon_tvSubtract);
            tvCount = (TextView) itemView.findViewById(R.id.item_msg_coupon_tvCount);
            tvTime = (TextView) itemView.findViewById(R.id.item_msg_coupon_tvTime);
            ivImg = (ImageView) itemView.findViewById(R.id.item_msg_coupon_ivImg);
            tvLook = (TextView) itemView.findViewById(R.id.item_msg_coupon_tvLook);
        }
    }

    /**
     * 加载底部布局
     */
    class FootHolder extends RecyclerView.ViewHolder {
        private TextView tips;

        public FootHolder(View itemView) {
            super(itemView);
            tips = (TextView) itemView.findViewById(R.id.footview_msg_coupon_tips);
        }
    }

    // 暴露接口，改变fadeTips的方法
    public boolean isFadeTips() {
        return fadeTips;
    }

    // 暴露接口，下拉刷新时，通过暴露方法将数据源置为空
    public void resetDatas() {
        datas = new ArrayList<>();
    }

    // 暴露接口，更新数据源，并修改hasMore的值，如果有增加数据，hasMore为true，否则为false
    public void updateList(List<CouponsItemBean> newDatas, boolean hasMore) {
        // 在原有的数据之上增加新数据
        if (newDatas != null) {
            datas.addAll(newDatas);
        }
        this.hasMore = hasMore;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return datas.size() + 1;
    }

    // 根据条目位置返回ViewType，以供onCreateViewHolder方法内获取不同的Holder
    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return footType;
        } else {
            return itemType;
        }
    }

    public int getRealLastPosition() {
        return datas.size();
    }
}
