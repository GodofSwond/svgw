package com.svgouwu.client.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.svgouwu.client.R;
import com.svgouwu.client.bean.CouponsItemBean;
import com.svgouwu.client.fragment.MessageStoreFragment;
import com.svgouwu.client.bean.UnreceivedBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/8.
 */

public class MessageStoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CouponsItemBean> datas;
    private Context context;
    private boolean hasMore;// 变量，是否有更多数据
    private int normalType = 0;     // 第一种ViewType，正常的item
    private int footType = 1;       // 第二种ViewType，底部的提示View
    private boolean fadeTips = false; // 变量，是否隐藏了底部的提示
    private Handler mHandler = new Handler(Looper.getMainLooper()); //获取主线程的Handler

    public MessageStoreAdapter(List<CouponsItemBean> datas, Context context, boolean hasMore) {
        // 初始化变量
        this.datas = datas;
        this.context = context;
        this.hasMore = hasMore;
    }

    // // 底部footView的ViewHolder，用以缓存findView操作
    class FootHolder extends RecyclerView.ViewHolder {
        private TextView tips;

        public FootHolder(View itemView) {
            super(itemView);
            tips = (TextView) itemView.findViewById(R.id.footview_msg_store_tips);
        }
    }

    // 正常item的ViewHolder，用以缓存findView操作
    class NormalHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvCount;
        private TextView tvTime;
        private ImageView ivImg;

        public NormalHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.item_message_store_tvName);
            tvCount = (TextView) itemView.findViewById(R.id.item_message_store_tvCount);
            tvTime = (TextView) itemView.findViewById(R.id.item_message_store_tvTime);
            ivImg = (ImageView) itemView.findViewById(R.id.item_message_store_ivImg);
            tvCount.setEllipsize(TextUtils.TruncateAt.END);//字体过长尾部以...显示-----------------------------
            tvCount.setMaxWidth(100);
            tvCount.setSingleLine(true);
            tvCount.setOnClickListener(new View.OnClickListener() {
                Boolean flag = true;

                @Override
                public void onClick(View v) {
                    if (flag) {
                        flag = false;
                        tvCount.setEllipsize(null);// 展开
                        tvCount.setSingleLine(flag);
                    } else {
                        flag = true;
                        tvCount.setEllipsize(TextUtils.TruncateAt.END); // 收缩
                        tvCount.setSingleLine(flag);
                    }
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 根据返回的ViewType，绑定不同的布局文件，这里只有两种
        if (viewType == normalType) {
            return new NormalHolder(LayoutInflater.from(context).inflate(R.layout.item_message_store, null));
        } else {
            return new FootHolder(LayoutInflater.from(context).inflate(R.layout.footview_message_store, null));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {//条目发生改变时调用
        // 如果是正常的imte，直接设置TextView的值
        if (holder instanceof NormalHolder) {
            ((NormalHolder) holder).tvName.setText(datas.get(position).couponName);
            ((NormalHolder) holder).tvTime.setText(datas.get(position).startTime);

        } else {
            // 之所以要设置可见，是因为我在没有更多数据时会隐藏了这个footView
            ((FootHolder) holder).tips.setVisibility(View.VISIBLE);
            // 只有获取数据为空时，hasMore为false，所以当我们拉到底部时基本都会首先显示“正在加载更多...”
            if (hasMore == true) {
                // 不隐藏footView提示
                fadeTips = false;
                if (datas.size() > 10) {
                    // 如果查询数据发现增加之后，就显示正在加载更多
                    ((FootHolder) holder).tips.setText("正在加载更多...");
                }
            } else {
                if (datas.size() > 0) {
                    // 如果查询数据发现并没有增加时，就显示没有更多数据了
                    ((FootHolder) holder).tips.setText("正在玩命加载中...");

                    // 然后通过延时加载模拟网络请求的时间，在500ms后执行
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // 隐藏提示条
                            ((FootHolder) holder).tips.setVisibility(View.GONE);
                            // 将fadeTips设置true
                            fadeTips = true;
                            // hasMore设为true是为了让再次拉到底时，会先显示正在加载更多
                            hasMore = true;
                        }
                    }, 500);
                }
            }
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
        // 获取条目数量，之所以要加1是因为增加了一条footView
        return datas.size() + 1;
    }

    // 自定义方法，获取列表中数据源的最后一个位置，比getItemCount少1，因为不计上footView
    public int getRealLastPosition() {
        return datas.size();
    }

    // 根据条目位置返回ViewType，以供onCreateViewHolder方法内获取不同的Holder
    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return footType;
        } else {
            return normalType;
        }
    }
}
