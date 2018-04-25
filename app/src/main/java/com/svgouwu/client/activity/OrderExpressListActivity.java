package com.svgouwu.client.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kili.okhttp.OkHttpUtils;
import com.svgouwu.client.Api;
import com.svgouwu.client.BaseActivity;
import com.svgouwu.client.Constant;
import com.svgouwu.client.MyApplication;
import com.svgouwu.client.R;
import com.svgouwu.client.bean.Address;
import com.svgouwu.client.bean.AddressResult;
import com.svgouwu.client.bean.HttpResult;
import com.svgouwu.client.bean.OrderExpressListResult;
import com.svgouwu.client.fragment.CommonSureDialogFragment;
import com.svgouwu.client.utils.CommonCallback;
import com.svgouwu.client.utils.ImageLoader;
import com.svgouwu.client.utils.ToastUtil;
import com.svgouwu.client.utils.UmengStat;
import com.svgouwu.client.view.LoadPage;
import com.umeng.analytics.MobclickAgent;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 订单物流列表
 * Created by topwolf on 2017/6/6.
 */

public class OrderExpressListActivity extends BaseActivity {

    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.mXRecyclerView)
    XRecyclerView mXRecyclerView;
    @BindView(R.id.mLoadPage)
    LoadPage mLoadPage;

    private String orderid;
    public List<OrderExpressListResult.ExpressItem> datas;
    @Override
    protected int getContentView() {
        return R.layout.activity_order_express_list;
    }

    @Override
    public void initViews() {
        if(getIntent().hasExtra("orderid")){
            orderid = getIntent().getStringExtra("orderid");
        }
        tvTitle.setText(getString(R.string.title_express_list));
        mLoadPage.setGetDataListener(new LoadPage.GetDataListener() {
            @Override
            public void onGetData() {
                mLoadPage.switchPage(LoadPage.STATE_LOADING);
                initData();
            }
        });
        mLoadPage.switchPage(LoadPage.STATE_LOADING);
        LinearLayoutManager layoutManager = new LinearLayoutManager(OrderExpressListActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mXRecyclerView.setLayoutManager(layoutManager);
        mXRecyclerView.setPullRefreshEnabled(false);
        mXRecyclerView.setNoMore(false);
//        mXRecyclerView.addItemDecoration(new DividerItemDecoration(AddressListActivity.this, DividerItemDecoration.VERTICAL));
//        mXRecyclerView.setEmptyView(tv_add);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        datas =null;
        String url = Api.URL_ORDER_EXPRESS_LIST;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", MyApplication.getInstance().getLoginKey());
        params.put("orderid", orderid);

        OkHttpUtils.post().url(url).params(params).build().execute(new CommonCallback<OrderExpressListResult>() {
            @Override
            public void onError(Call call, Exception e) {
                mLoadPage.switchPage(LoadPage.STATE_NO_NET);
            }

            @Override
            public void onResponse(HttpResult<OrderExpressListResult> response) {
                try {
                    if (response.isSuccess()) {
                        mLoadPage.switchPage(LoadPage.STATE_HIDE);
                        if (response.data != null && response.data.list != null && response.data.list.size() > 0) {
                            mXRecyclerView.setVisibility(View.VISIBLE);
                            datas = response.data.list;
                            CommonAdapter<OrderExpressListResult.ExpressItem> mAdapter = new CommonAdapter<OrderExpressListResult.ExpressItem>(OrderExpressListActivity.this, R.layout.item_order_express, datas)
                            {
                                @Override
                                protected void convert(final ViewHolder holder, final OrderExpressListResult.ExpressItem item, final int position) {
                                    ImageLoader.with(OrderExpressListActivity.this,item.imageurl,holder.getView(R.id.iv_pic));
                                    holder.setText(R.id.tv_express_no, "运单号："+item.nu);
                                    holder.setText(R.id.tv_express_name, "承运公司："+item.comname);
                                    holder.setOnClickListener(R.id.ll_item, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(OrderExpressListActivity.this,WebViewActivity.class);
                                            intent.putExtra("url", Constant.H5_DELIVERY_DETAIL+"type="+item.com+"&postid="+item.nu);
                                            startActivity(intent);
                                        }
                                    });

                                }
                            };

                            mXRecyclerView.setAdapter(mAdapter);
                        }else{
                        }
                    }
                }catch (Exception e) {
                    mLoadPage.switchPage(LoadPage.STATE_NO_NET);
                    e.printStackTrace();
                }
            }
        });
    }


    @OnClick({R.id.iv_left})
    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(UmengStat.LOGISTICSPAGE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(UmengStat.LOGISTICSPAGE);
    }
}
