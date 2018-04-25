package com.svgouwu.client.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kili.okhttp.OkHttpUtils;
import com.svgouwu.client.Api;
import com.svgouwu.client.BaseActivity;
import com.svgouwu.client.CommonFragmentActivity;
import com.svgouwu.client.MyApplication;
import com.svgouwu.client.OrderManager;
import com.svgouwu.client.R;
import com.svgouwu.client.bean.HttpResult;
import com.svgouwu.client.bean.ListDialogItem;
import com.svgouwu.client.bean.Order;
import com.svgouwu.client.bean.OrderListResult;
import com.svgouwu.client.bean.TabBean;
import com.svgouwu.client.fragment.CancelOrderDialogFragment;
import com.svgouwu.client.fragment.CommonSureDialogFragment;
import com.svgouwu.client.utils.CommonCallback;
import com.svgouwu.client.utils.CommonUtils;
import com.svgouwu.client.utils.ImageLoader;
import com.svgouwu.client.utils.ToastUtil;
import com.svgouwu.client.utils.UmengStat;
import com.svgouwu.client.view.LoadPage;
import com.umeng.analytics.MobclickAgent;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 订单列表
 * Created by melon on 2017/6/26.
 * Email 530274554@qq.com
 */

public class OrderListActivity extends BaseActivity implements CancelOrderDialogFragment.OnConfirmListener, XRecyclerView.LoadingListener {


    String[] titles = {"全部", "待付款", "待发货", "待收货", "待评价"};
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.mXRecyclerView)
    XRecyclerView mXRecyclerView;
    @BindView(R.id.mLoadPage)
    LoadPage mLoadPage;
    @BindView(R.id.mTabLayout)
    TabLayout mTabLayout;

    private int page = 1;
    private String type = "1";//1：所有订单2：待付款3：待发货4：待收货5：待评价
    private List<TabBean> tabs = new ArrayList<>();
    public List<Order> datas = new ArrayList<>();
    private String orderId;
    private boolean isFirstLoad = true;
    private CommonAdapter<Order> mAdapter;
    private boolean isNodata;

    @Override
    protected int getContentView() {
        return R.layout.activity_order_list;
    }

    @Override
    public void initViews() {
        tvTitle.setText("我的订单");
        mLoadPage = (LoadPage) findViewById(R.id.mLoadPage);
        mLoadPage.setGetDataListener(new LoadPage.GetDataListener() {
            @Override
            public void onGetData() {
                mLoadPage.switchPage(LoadPage.STATE_LOADING);
                loadData();
            }
        });
        mLoadPage.switchPage(LoadPage.STATE_LOADING);

        tabs.add(new TabBean("1", "全部"));
        tabs.add(new TabBean("2", "待付款"));
        tabs.add(new TabBean("3", "待发货"));
        tabs.add(new TabBean("4", "待收货"));
        tabs.add(new TabBean("5", "待评价"));
        for (TabBean tab : tabs) {
            mTabLayout.addTab(mTabLayout.newTab().setText(tab.title));
        }
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mXRecyclerView.setLoadingMoreEnabled(true);
                TabBean bean = tabs.get(tab.getPosition());
                type = bean.id;
                page = 1;
                loadData();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        String type2 = getIntent().getStringExtra("type");
        if (!CommonUtils.isEmpty(type2)) {
            this.type = type2;

            try {
                mTabLayout.getTabAt(Integer.parseInt(type2) - 1).select();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            loadData();
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(OrderListActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mXRecyclerView.setLayoutManager(layoutManager);
        mXRecyclerView.setPullRefreshEnabled(false);
        mXRecyclerView.setNoMore(false);

        mAdapter = new CommonAdapter<Order>(OrderListActivity.this, R.layout.item_order_list, datas) {
            @Override
            protected void convert(final ViewHolder holder, final Order order, final int position) {

                ImageLoader.with(OrderListActivity.this, order.goodsImg, holder.getView(R.id.iv_goods_pic));
                holder.setText(R.id.tv_goods_name, order.goodsName);
                holder.setText(R.id.tv_goods_spec, order.specification);
                holder.setText(R.id.tv_goods_price, order.price);
                holder.setText(R.id.tv_goods_num, "x " + order.quantity);
                if(!"0".equals(order.shareRebate) && !"0.0".equals(order.shareRebate) && !"0.00".equals(order.shareRebate)){
                    holder.setVisible(R.id.tv_item_order_rebate, true);
                    holder.setText(R.id.tv_item_order_rebate,"购物返利"+order.shareRebate + "元");
                }
                if (TextUtils.isEmpty(order.postage) || TextUtils.equals(order.postage, "0") || TextUtils.equals(order.postage, "0.00")) {
                    holder.setText(R.id.tv_order_total, "共" + order.quantity + "件商品 合计：￥" + order.orderAmount + "（包邮）");
                } else {
                    holder.setText(R.id.tv_order_total, "共" + order.quantity + "件商品 合计：￥" + order.orderAmount + "（含运费￥" + (TextUtils.isEmpty(order.postage) ? 0 : order.postage) + "）");
                }
//                                    holder.setChecked(R.id.cb_default,address.setdefault == 1? true: false);
//                                    holder.setText(R.storeId.cb_default, address.address);//tv_edit.tv_del
                //0 已取消 11待付款 20 已付款 30 已发货 40 已完成
                if (order.status == 0) {
                    holder.setText(R.id.tv_order_status, getString(R.string.order_status_cancel));
                    holder.setVisible(R.id.tv_left, false);
                    holder.setVisible(R.id.tv_right, false);
                } else if (order.status == 11) {
                    holder.setText(R.id.tv_order_status, getString(R.string.order_status_un_pay));
                    holder.setVisible(R.id.tv_left, true);
                    holder.setText(R.id.tv_left, getString(R.string.order_operation_cancel));
                    holder.setVisible(R.id.tv_right, true);
                    holder.setText(R.id.tv_right, getString(R.string.order_operation_pay));
                } else if (order.status == 20) {
                    holder.setText(R.id.tv_order_status, getString(R.string.order_status_un_delivery));
                    holder.setVisible(R.id.tv_left, true);
                    holder.setText(R.id.tv_left, getString(R.string.order_operation_remind));
                    holder.setVisible(R.id.tv_right, false);
                } else if (order.status == 30) {
                    holder.setText(R.id.tv_order_status, getString(R.string.order_status_un_receive));
                    holder.setVisible(R.id.tv_left, true);
                    holder.setText(R.id.tv_left, getString(R.string.order_operation_view_delivery));
                    holder.setVisible(R.id.tv_right, true);
                    holder.setText(R.id.tv_right, getString(R.string.order_operation_received));
                } else if (order.status == 40) {
                    holder.setText(R.id.tv_order_status, getString(R.string.order_status_complete));
                    holder.setVisible(R.id.tv_left, false);
                    if (order.evaluationStatus == 1) {
                        holder.setVisible(R.id.tv_right, false);
                    } else {
                        holder.setVisible(R.id.tv_right, true);
                        holder.setText(R.id.tv_right, getString(R.string.order_operation_evaluate));
                    }

                }
                holder.setOnClickListener(R.id.tv_left, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        orderId = order.orderId + "";
                        if (order.status == 11) {
                            CancelOrderDialogFragment dialog = new CancelOrderDialogFragment();
                            dialog.show(getSupportFragmentManager(), "cancelOrder");
                        } else if (order.status == 20) {
                            weixinDialogInit("提醒中...");
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ToastUtil.toast("提醒成功");
                                            cancelWeiXinDialog();
                                        }
                                    });
                                }
                            },300);
                        } else if (order.status == 30) {
                            Intent intent = new Intent(OrderListActivity.this, OrderExpressListActivity.class);
                            intent.putExtra("orderid", orderId);
                            startActivity(intent);
                        }
                    }
                });
                holder.setOnClickListener(R.id.tv_right, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            //TODO
                            if (order.status == 11) {
                                CommonUtils.go2pay(order.orderId+"",order.orderSn,order.orderAmount);
                            } else if (order.status == 40 && order.evaluationStatus != 1) {
                                //评价
                                Intent intent = new Intent(getApplicationContext(), CommonFragmentActivity.class);
                                intent.putExtra("target", CommonFragmentActivity.FRAGMENT_ORDER_COMMENT);
                                intent.putExtra("orderId", order.orderId + "");
                                startActivity(intent);
                            }else if(order.status == 30){

                                //确认收货
                                CommonSureDialogFragment dialog = new CommonSureDialogFragment("是否确认收货？",new CommonSureDialogFragment.SureListener() {
                                    @Override
                                    public void onSureListener() {
                                        OrderManager.receive(order.orderId+"", new OrderManager.OrderOperationCallBack() {
                                            @Override
                                            public void onSuccess() {
                                                ToastUtil.toast("确定收货成功");
                                                page = 1;
                                                loadData();
                                            }

                                            @Override
                                            public void onFail(String msg) {
                                                ToastUtil.toast(msg);
                                            }

                                            @Override
                                            public void onError() {
                                                ToastUtil.toast("服务器异常");
                                            }
                                        });
                                    }
                                });
                                dialog.show(getSupportFragmentManager(), "SureDialog");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        };
        mAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                try {
                    if(!isNodata){
                        Intent intent = new Intent(OrderListActivity.this, OrderDetailsActivity.class);
                        intent.putExtra("orderId", datas.get(position - XRECYCLER_HEAD_DEFAULT_COUNT).orderId + "");
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        mXRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initListener() {
        mXRecyclerView.setLoadingListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isFirstLoad) {
            page=1;
            loadData();
        }
        isFirstLoad = false;
        MobclickAgent.onPageStart(UmengStat.ALLORDERSPAGE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(UmengStat.ALLORDERSPAGE);
    }

    @Override
    public void initData() {

    }

    public void loadData() {
        String url = Api.URL_ORDER_LIST;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", MyApplication.getInstance().getLoginKey());
        params.put("type", type);
        params.put("page", page + "");

        OkHttpUtils.post().url(url).params(params).build().execute(new CommonCallback<OrderListResult>() {
            @Override
            public void onError(Call call, Exception e) {
                mLoadPage.switchPage(LoadPage.STATE_NO_NET);
                mXRecyclerView.refreshComplete();
                mXRecyclerView.loadMoreComplete();
            }

            @Override
            public void onResponse(HttpResult<OrderListResult> response) {
                try {
                    mXRecyclerView.refreshComplete();
                    mXRecyclerView.loadMoreComplete();

                    if (response.isSuccess()) {
                        mLoadPage.switchPage(LoadPage.STATE_HIDE);
                        if (response.data != null && response.data.orderlist != null && response.data.orderlist.size() > 0) {
                            if (page == 1) datas.clear();
                            datas.addAll(response.data.orderlist);
                            mAdapter.notifyDataSetChanged();
                            isNodata = false;
                        } else {
                            if (page == 1) {
                                mLoadPage.switchPage(LoadPage.STATE_NO_DATA);
                                isNodata = true;
                            }else {
                                isNodata = false;
                                mXRecyclerView.setLoadingMoreEnabled(false);
                            }
                        }
                    }
                } catch (Exception e) {
                    mLoadPage.switchPage(LoadPage.STATE_NO_NET);
                    e.printStackTrace();
                }
            }
        });
    }

    @OnClick(R.id.iv_left)
    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
        }
    }

    @Override
    public void onConfirm(ListDialogItem item) {
        //取消订单
        OrderManager.cancelOrder(MyApplication.getInstance().getLoginKey(), item.id + "", orderId, new OrderManager.OrderOperationCallBack() {
            @Override
            public void onSuccess() {
                ToastUtil.toast("取消成功");
                page = 1;
                loadData();
            }

            @Override
            public void onFail(String msg) {
                ToastUtil.toast(msg);
            }

            @Override
            public void onError() {
                ToastUtil.toast("服务器异常");
            }
        });
    }

    @Override
    public void onRefresh() {
        page = 1;
        loadData();
    }

    @Override
    public void onLoadMore() {
        page++;
        loadData();
    }
}