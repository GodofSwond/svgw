package com.svgouwu.client.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.svgouwu.client.R;
import com.svgouwu.client.bean.CouponsItemBean;
import com.svgouwu.client.bean.UnreceivedBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/14.
 */

public class MessageInformAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CouponsItemBean> datas;
    private Context context;

    private boolean hasMore = true;
    private boolean fadeTips = false; // 变量，是否隐藏了底部的提示

    private int itemType = 0;     // 第一种ViewType，正常的item
    private int footType = 1;       // 第二种ViewType，底部的提示View
    private Handler handler = new Handler(Looper.getMainLooper());//开启主

    public MessageInformAdapter(List<CouponsItemBean> datas, Context context, boolean b) {
        this.datas = datas;
        this.context = context;
        this.hasMore = b;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 根据返回的ViewType，绑定不同的布局文件，这里只有两种
        if (viewType == itemType) {
            return new ItemHolder(LayoutInflater.from(context).inflate(R.layout.item_message_inform, null));//加载条目布局
        } else {
            return new FootHolder(LayoutInflater.from(context).inflate(R.layout.footview_message_inform, null));//加载底部布局
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemHolder) {
            ((ItemHolder) holder).tvTitle.setText(datas.get(position).couponName);
            Log.i("TAG", "onBindViewHolder: " + datas.get(position).couponName);
            ((ItemHolder) holder).tvTime.setText(datas.get(position).startTime);
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
        private TextView tvTitle;
        private TextView tvTime;

        public ItemHolder(View itemView) {
            super(itemView);
            tvTime = (TextView) itemView.findViewById(R.id.item_inform_tvTime);
            tvTitle = (TextView) itemView.findViewById(R.id.item_inform_tvTitle);
        }
    }

    /**
     * 加载底部布局
     */
    class FootHolder extends RecyclerView.ViewHolder {
        private TextView tips;

        public FootHolder(View itemView) {
            super(itemView);
            tips = (TextView) itemView.findViewById(R.id.footview_msg_inform_tips);
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

    @Override
    public int getItemCount() {
        return datas.size() + 1;
    }
}
