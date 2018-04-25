package com.svgouwu.client.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.svgouwu.client.BaseFragment;
import com.svgouwu.client.R;
import com.svgouwu.client.holder.OrderListViewHolder;
import com.svgouwu.client.utils.ModelRecyclerAdapter;

import java.util.ArrayList;
import java.util.concurrent.RunnableFuture;

import butterknife.BindView;

/**
 * 订单列表Fragment
 */
public class OrderListFragment extends BaseFragment {
    public static int orderState = 0;
    @BindView(R.id.recycleView)
    XRecyclerView recycleView;

    @Override
    protected int getContentView() {
        return R.layout.fragment_order_list;
    }

    @Override
    public void initViews() {
        ArrayList<String> datas = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            datas.add("item " + i);
        }
        recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ModelRecyclerAdapter adapter = new ModelRecyclerAdapter(OrderListViewHolder.class, datas);
        recycleView.setAdapter(adapter);
        recycleView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recycleView.refreshComplete();
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recycleView.loadMoreComplete();
                    }
                }, 2000);

            }
        });
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        if (getArguments() != null) {
            orderState = getArguments().getInt("orderState");
            switch (orderState) {
                case 0:
//                    tv_text_content.setText("全部订单");
                    break;
                case 1:
//                    tv_text_content.setText("待付款");
                    break;
                case 2:
//                    tv_text_content.setText("待发货");
                    break;
                case 3:
//                    tv_text_content.setText("待收货");
                    break;
                case 4:
//                    tv_text_content.setText("待评价");
                    break;
            }
        }
    }

    @Override
    public void processClick(View view) {

    }
}
