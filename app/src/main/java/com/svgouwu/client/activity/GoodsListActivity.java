package com.svgouwu.client.activity;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kili.okhttp.OkHttpUtils;
import com.svgouwu.client.Api;
import com.svgouwu.client.BaseActivity;
import com.svgouwu.client.CommonFragmentActivity;
import com.svgouwu.client.Constant;
import com.svgouwu.client.R;
import com.svgouwu.client.adapter.GoodsFilterBrandAdapter;
import com.svgouwu.client.adapter.GoodsFilterCategoryAdapter;
import com.svgouwu.client.adapter.GoodsFilterPriceAdapter;
import com.svgouwu.client.adapter.GoodsListAdapter;
import com.svgouwu.client.bean.Brand;
import com.svgouwu.client.bean.Classify;
import com.svgouwu.client.bean.GoodsFilterResult;
import com.svgouwu.client.bean.GoodsInfo;
import com.svgouwu.client.bean.HttpResult;
import com.svgouwu.client.bean.PriceFilter;
import com.svgouwu.client.event.NewSearchEvent;
import com.svgouwu.client.utils.CommonCallback;
import com.svgouwu.client.utils.CommonUtils;
import com.svgouwu.client.utils.LogUtils;
import com.svgouwu.client.utils.ToastUtil;
import com.svgouwu.client.utils.UmengStat;
import com.svgouwu.client.view.MyGridView;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

import static com.svgouwu.client.MyApplication.getIPAddress;

/**
 * Created by topwolf on 2017/6/22.
 * 项目分类页(商品搜索列表页)--入口搜索页/分类页
 */

public class GoodsListActivity extends BaseActivity {

    @BindView(R.id.rv_goods_list)
    XRecyclerView rv_goods_list;
    @BindView(R.id.cb_goods_list_show_way)
    CheckBox cb_goods_list_show_way;
    @BindView(R.id.rb_goods_list_sort_price)
    RadioButton rb_goods_list_sort_price;
    @BindView(R.id.iv_goods_list_go_top)
    ImageView iv_goods_list_go_top;
    @BindView(R.id.rg_goods_list_tab)
    RadioGroup rg_goods_list_tab;
    @BindView(R.id.mgv_goods_filter_cate)
    MyGridView mgv_goods_filter_cate;
    @BindView(R.id.mgv_goods_filter_price)
    MyGridView mgv_goods_filter_price;
    @BindView(R.id.mgv_goods_filter_brand)
    MyGridView mgv_goods_filter_brand;
    @BindView(R.id.tv_goods_list_filter_cate_more)
    TextView tv_goods_list_filter_cate_more;
    @BindView(R.id.tv_goods_list_filter_price_more)
    TextView tv_goods_list_filter_price_more;
    @BindView(R.id.tv_goods_list_filter_brand_more)
    TextView tv_goods_list_filter_brand_more;
    @BindView(R.id.tv_goods_list_no_content_tips)
    TextView tv_goods_list_no_content_tips;
    @BindView(R.id.rl_goods_list_filter_cate)
    RelativeLayout rl_goods_list_filter_cate;
    @BindView(R.id.rl_goods_list_filter_price)
    RelativeLayout rl_goods_list_filter_price;
    @BindView(R.id.rl_goods_list_filter_brand)
    RelativeLayout rl_goods_list_filter_brand;
    @BindView(R.id.dl_goods_list)
    DrawerLayout dl_goods_list;
    @BindView(R.id.et_goods_list_search)
    TextView et_goods_list_search;

    private GoodsListAdapter mAdapter;
    private GridLayoutManager mGridLayoutManager;

    private List<GoodsInfo> mGoodsInfos = new ArrayList<>();

    private String category, keyword, brand, lowPrice, highPrice,category2, brand2, lowPrice2, highPrice2; //搜索
    private int address; //地址搜索

    private int mPage = 1;
    private int mPriceSortState; //0 默认 1降序 2升序     价格
    private int mSalesSortState; //0 默认 1降序 2升序     销量
    private int mNewSortState; //0 默认 1降序 2升序    新品
    private GoodsFilterCategoryAdapter mFilterCateAdapter;
    private GoodsFilterPriceAdapter mFilterPriceAdapter;
    private GoodsFilterBrandAdapter mFilterBrandAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_goods_list;
    }

    @Override
    public void initViews() {
        initTopBar();
        dl_goods_list.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);//关闭侧滑
        mGridLayoutManager = new GridLayoutManager(this, 1);
        rv_goods_list.setLayoutManager(mGridLayoutManager);
        rv_goods_list.setEmptyView(tv_goods_list_no_content_tips);
        rv_goods_list.setPullRefreshEnabled(false);
        mAdapter = new GoodsListAdapter(this, mGoodsInfos, mGridLayoutManager);
        rv_goods_list.setAdapter(mAdapter);
        rv_goods_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int pos = mGridLayoutManager.findFirstVisibleItemPosition();

                if (pos > 7) {
                    iv_goods_list_go_top.setVisibility(View.VISIBLE);
                } else {
                    iv_goods_list_go_top.setVisibility(View.GONE);
                }
            }
        });

        rv_goods_list.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mPage = 1;
                getGoodsList();
            }

            @Override
            public void onLoadMore() {
                mPage++;
                getGoodsList();

            }
        });
        cb_goods_list_show_way.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mGridLayoutManager.setSpanCount(2);
                } else {
                    mGridLayoutManager.setSpanCount(1);
                }
            }
        });
        rg_goods_list_tab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                clearSortState();
                switch (checkedId) {
                    case R.id.rb_goods_list_default:
                        //不传排序参数
                        mPage = 1;
                        getGoodsList();
                        break;
                    case R.id.rb_goods_list_new:
                        mPage = 1;
                        mNewSortState = 1;
                        getGoodsList();
                        break;
                    case R.id.rb_goods_list_sales:
                        mPage = 1;
                        mSalesSortState = 1;
                        getGoodsList();
                        break;
                    case R.id.rb_goods_list_sort_price:
                        //由点击事件处理
                        break;
                }
            }
        });

        mgv_goods_filter_cate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mFilterCateAdapter != null)
                    mFilterCateAdapter.check(position);
            }
        });
        mgv_goods_filter_brand.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mFilterBrandAdapter != null)
                    mFilterBrandAdapter.check(position);
            }
        });
        mgv_goods_filter_price.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mFilterPriceAdapter != null)
                    mFilterPriceAdapter.check(position);
            }
        });
    }

    private void clearSortState() {
        mPriceSortState = 0;
        mSalesSortState = 0;
        mNewSortState = 0;
        rb_goods_list_sort_price.setText("价格");
        mPage = 1;
    }

    public void initTopBar() {
        findView(R.id.iv_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initListener() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void initData() {
        Intent intent = getIntent();
        category = intent.getStringExtra("category");
        keyword = intent.getStringExtra("keyword");
        brand = intent.getStringExtra("brand");
        lowPrice = intent.getStringExtra("lowPrice");
        highPrice = intent.getStringExtra("highPrice");
        address = intent.getIntExtra("address", -1);
        category2 = category;
        brand2 = brand;
        lowPrice2 = lowPrice;
        highPrice2 = highPrice;
        if (!CommonUtils.isEmpty(keyword)) {
            et_goods_list_search.setText(keyword);
        }

        getGoodsList();
    }

    /**
     * 商品列表接口
     */
    private void getGoodsList() {
        String url = Api.URL_GOODS_LIST;
        HashMap<String, String> params = new HashMap<>();

        //筛选
        if (!CommonUtils.isEmpty(category)) {
            params.put("cate_id", category);
        }
        if (!CommonUtils.isEmpty(keyword)) {
            params.put("keyword", keyword);
        }
        if (!CommonUtils.isEmpty(brand)) {
            params.put("brand", brand);
        }
        if (!CommonUtils.isEmpty(lowPrice)) {
            params.put("low_price", lowPrice);
        }
        if (!CommonUtils.isEmpty(highPrice)) {
            params.put("high_price", highPrice);
        }
        if (address != -1) {
            params.put("address", address + "");
        }

        //排序
        if (mPriceSortState != 0) {
            params.put("price", mPriceSortState + "");
        }

        if (mSalesSortState != 0) {
            params.put("sales", mSalesSortState + "");
        }

        if (mNewSortState != 0) {
            params.put("created_at", mNewSortState + "");
        }

        params.put("page", mPage + "");
        params.put("limit", Constant.PAGE_LIMIT + "");
        OkHttpUtils.get().url(url).params(params).build().execute(new CommonCallback<List<GoodsInfo>>() {
            @Override
            public void onBefore(Request request) {
                super.onBefore(request);
                if (mPage == 1)
                    weixinDialogInit(getString(R.string.dialog_process));
            }

            @Override
            public void onAfter() {
                super.onAfter();
                cancelWeiXinDialog();
                rv_goods_list.loadMoreComplete();
            }

            @Override
            public void onError(Call call, Exception e) {
                LogUtils.e(e.toString());
                ToastUtil.toast(R.string.text_tips_net_issue);
            }

            @Override
            public void onResponse(HttpResult<List<GoodsInfo>> response) {

                try {

                    if (response.data != null) {
                        if (mPage == 1) {
                            mGoodsInfos.clear();
                            rv_goods_list.setLoadingMoreEnabled(true);
                        } else {
                            if (response.data.size() == 0) {
                                rv_goods_list.setLoadingMoreEnabled(false);
                                ToastUtil.toast("亲，没有更多了哟～");
                                return;
                            }
                        }
                        mGoodsInfos.addAll(response.data);
                        mAdapter.notifyDataSetChanged();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtil.toast(R.string.text_tips_server_issue);
                }
            }
        });
    }

    @OnClick({R.id.tv_goods_list_filter_ok, R.id.tv_goods_list_filter_reset, R.id.tv_goods_list_filter_brand_more, R.id.et_goods_list_search, R.id.tv_right, R.id.iv_goods_list_go_top, R.id.rb_goods_list_sort_price, R.id.tv_goods_list_filter_cate_more, R.id.tv_goods_list_filter_price_more})
    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.tv_goods_list_filter_ok:
                filterGoods();
                break;
            case R.id.tv_goods_list_filter_reset:
                if (mFilterBrandAdapter != null)
                    mFilterBrandAdapter.check(-1);
                if (mFilterCateAdapter != null)
                    mFilterCateAdapter.check(-1);
                if (mFilterPriceAdapter != null)
                    mFilterPriceAdapter.check(-1);
                break;
            case R.id.tv_goods_list_filter_brand_more:
                if (mFilterBrandAdapter != null) {
                    mFilterBrandAdapter.showAll(tv_goods_list_filter_brand_more);
                }
                break;
            case R.id.tv_goods_list_filter_price_more:
                if (mFilterPriceAdapter != null) {
                    mFilterPriceAdapter.showAll(tv_goods_list_filter_price_more);
                }
                break;
            case R.id.tv_goods_list_filter_cate_more:
                if (mFilterCateAdapter != null) {
                    mFilterCateAdapter.showAll(tv_goods_list_filter_cate_more);
                }
                break;
            case R.id.iv_goods_list_go_top:
                rv_goods_list.scrollToPosition(0);
                break;
            case R.id.et_goods_list_search:
                CommonUtils.startAct(this, CommonFragmentActivity.FRAGMENT_SEARCH);
                break;
            case R.id.tv_right:
                //请求筛选条件 ：URL_GOODS_FILTER
                if (isDrawerOpened) {
                    dl_goods_list.openDrawer(Gravity.RIGHT);
                } else {
                    getFilterInfo();
                }
                break;
            case R.id.rb_goods_list_sort_price:
                if (mPriceSortState == 0) {
                    mPriceSortState = 1;
                    rb_goods_list_sort_price.setText("价格↓");
                } else if (mPriceSortState == 1) {
                    mPriceSortState = 2;
                    rb_goods_list_sort_price.setText("价格↑");
                } else if (mPriceSortState == 2) {
                    mPriceSortState = 1;
                    rb_goods_list_sort_price.setText("价格↓");
                }

                mPage = 1;
                getGoodsList();
                break;
        }
    }

    private void filterGoods() {
        try {
            dl_goods_list.closeDrawer(Gravity.RIGHT);

            //获取筛选参数
            if (mFilterCateAdapter != null) {
                String tempCategory = mFilterCateAdapter.getSelectedCate();
                if (!CommonUtils.isEmpty(tempCategory)) {
                    category = tempCategory;
                }else{
                    category = category2;
                }
            }

            if (mFilterBrandAdapter != null) {
                String tempBrand = mFilterBrandAdapter.getSelectedBrand();
                if (!CommonUtils.isEmpty(tempBrand)) {
                    brand = tempBrand;
                }else{
                    brand = brand2;
                }
            }

            if (mFilterPriceAdapter != null) {
                PriceFilter selectedPriceArea = mFilterPriceAdapter.getSelectedPriceArea();
                if (selectedPriceArea != null) {
                    lowPrice = selectedPriceArea.minprice;
                    highPrice = selectedPriceArea.maxprice;
                } else {
                    lowPrice = lowPrice2;
                    highPrice = highPrice2;
                }
            }

            //发起请求
            mPage = 1;
            getGoodsList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    boolean isDrawerOpened;//侧滑是否打开过

    private void getFilterInfo() {
        String url = Api.URL_GOODS_FILTER;
        HashMap<String, String> params = new HashMap<>();
        if (!CommonUtils.isEmpty(category)) {
            params.put("cate_id", category);
        }
        if (!CommonUtils.isEmpty(keyword)) {
            params.put("keyword", keyword);
        }
        if (!CommonUtils.isEmpty(brand)) {
            params.put("brand", brand);
        }
        if (!CommonUtils.isEmpty(lowPrice)) {
            params.put("low_price", lowPrice);
        }
        if (!CommonUtils.isEmpty(highPrice)) {
            params.put("high_price", highPrice);
        }
        OkHttpUtils.get().url(url).params(params).build().execute(new CommonCallback<GoodsFilterResult>() {
            @Override
            public void onBefore(Request request) {
                super.onBefore(request);
                weixinDialogInit(getString(R.string.dialog_process));
            }

            @Override
            public void onAfter() {
                super.onAfter();
                cancelWeiXinDialog();
            }

            @Override
            public void onError(Call call, Exception e) {
                LogUtils.e(e.toString());
                ToastUtil.toast(R.string.text_tips_net_issue);
            }

            @Override
            public void onResponse(HttpResult<GoodsFilterResult> response) {

                try {
                    if (!response.isSuccess()) {
                        ToastUtil.toast(response.msg);
                    } else {
                        //分类
                        List<Classify> cates = response.data.by_cate;
                        if (cates != null) {
                            if (cates.size() <= 4) {
                                tv_goods_list_filter_cate_more.setVisibility(View.GONE);
                            } else {
                                tv_goods_list_filter_cate_more.setVisibility(View.VISIBLE);
                            }
                            mFilterCateAdapter = new GoodsFilterCategoryAdapter(cates);
                            mgv_goods_filter_cate.setAdapter(mFilterCateAdapter);

                            rl_goods_list_filter_cate.setVisibility(View.VISIBLE);
                            mgv_goods_filter_cate.setVisibility(View.VISIBLE);
                        } else {
                            rl_goods_list_filter_cate.setVisibility(View.GONE);
                            mgv_goods_filter_cate.setVisibility(View.GONE);
                        }

                        //价格区间
                        List<PriceFilter> prices = response.data.by_price;
                        if (prices != null) {
                            if (prices.size() <= 4) {
                                tv_goods_list_filter_price_more.setVisibility(View.GONE);
                            } else {
                                tv_goods_list_filter_price_more.setVisibility(View.VISIBLE);
                            }
                            mFilterPriceAdapter = new GoodsFilterPriceAdapter(prices);
                            mgv_goods_filter_price.setAdapter(mFilterPriceAdapter);

                            rl_goods_list_filter_price.setVisibility(View.VISIBLE);
                            mgv_goods_filter_price.setVisibility(View.VISIBLE);
                        } else {
                            rl_goods_list_filter_price.setVisibility(View.GONE);
                            mgv_goods_filter_price.setVisibility(View.GONE);
                        }

                        //品牌
                        List<Brand> brands = response.data.by_brand;
                        if (brands != null) {
                            if (brands.size() <= 4) {
                                tv_goods_list_filter_brand_more.setVisibility(View.GONE);
                            } else {
                                tv_goods_list_filter_brand_more.setVisibility(View.VISIBLE);
                            }
                            mFilterBrandAdapter = new GoodsFilterBrandAdapter(brands);
                            mgv_goods_filter_brand.setAdapter(mFilterBrandAdapter);

                            rl_goods_list_filter_brand.setVisibility(View.VISIBLE);
                            mgv_goods_filter_brand.setVisibility(View.VISIBLE);
                        } else {
                            rl_goods_list_filter_brand.setVisibility(View.GONE);
                            mgv_goods_filter_brand.setVisibility(View.GONE);
                        }
                    }

                    // 打开侧滑
                    dl_goods_list.openDrawer(Gravity.RIGHT);
                    isDrawerOpened = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(NewSearchEvent event) {
        finish();
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(UmengStat.CLASSIFICATIONLISTPAGE); //统计页面
        CommonUtils.umTongJi(mContext, getIPAddress(), "6", "0");
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(UmengStat.CLASSIFICATIONLISTPAGE);
    }
}
