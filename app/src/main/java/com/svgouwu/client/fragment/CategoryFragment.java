package com.svgouwu.client.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kili.okhttp.OkHttpUtils;
import com.svgouwu.client.Api;
import com.svgouwu.client.BaseTopFragment;
import com.svgouwu.client.CommonFragmentActivity;
import com.svgouwu.client.R;
import com.svgouwu.client.activity.GoodsListActivity;
import com.svgouwu.client.bean.Brand;
import com.svgouwu.client.bean.Classify;
import com.svgouwu.client.bean.HttpResult;
import com.svgouwu.client.utils.CommonCallback;
import com.svgouwu.client.utils.CommonUtils;
import com.svgouwu.client.utils.LogUtils;
import com.svgouwu.client.utils.ToastUtil;
import com.svgouwu.client.utils.UmengStat;
import com.svgouwu.client.view.LoadPage;
import com.umeng.analytics.MobclickAgent;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

import static com.svgouwu.client.MyApplication.getIPAddress;

/**
 * Created by topwolf on 2017/6/6.
 */

public class CategoryFragment extends BaseTopFragment {

    @BindView(R.id.xrv_left)
    XRecyclerView xrvLeft;
    @BindView(R.id.xrv_right)
    XRecyclerView xrvRight;
    @BindView(R.id.ll_head)
    LinearLayout ll_head;
    @BindView(R.id.bannerView)
    ConvenientBanner bannerView;
    @BindView(R.id.mLoadPage)
    LoadPage mLoadPage;
    //    private View headerView;
    private CommonAdapter<Classify> leftAdapter;
    private CommonAdapter<Classify> rightAdapter;
    private Map<Integer, List<Classify>> cateMap;
    private Map<Integer, List<Brand>> brandMap;
    private int oneCatePostion = 1;//一级分类位置
    private int oneCateId;//当前选中一级分类id
    private List<Classify> twoCates;//二级分类数据
    private List<Brand> brands;//

    public CategoryFragment() {
    }

    public static CategoryFragment newInstance() {
        return new CategoryFragment();
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_category;
    }

    @Override
    public void initViews() {
        mLoadPage.setGetDataListener(new LoadPage.GetDataListener() {
            @Override
            public void onGetData() {
                mLoadPage.switchPage(LoadPage.STATE_LOADING);
                initData();
            }
        });
        mLoadPage.switchPage(LoadPage.STATE_LOADING);
        mStatusBarView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        String url = Api.URL_CLASSIFY;
        OkHttpUtils.get().url(url).params(null).build().execute(new CommonCallback<List<Classify>>() {

            @Override
            public void onError(Call call, Exception e) {
                mLoadPage.switchPage(LoadPage.STATE_NO_NET);
                LogUtils.e(e.toString());
            }

            @Override
            public void onResponse(HttpResult<List<Classify>> response) {
                mLoadPage.switchPage(LoadPage.STATE_HIDE);
                try {
                    if (response.isSuccess()) {
                        if (response.data != null && response.data.size() > 0) {
                            showCategory(response.data);
                        } else {
                            mLoadPage.switchPage(LoadPage.STATE_NO_DATA);
                        }
                    } else {
                        ToastUtil.toast(response.msg);
                    }

                } catch (Exception e) {
                    mLoadPage.switchPage(LoadPage.STATE_NO_NET);
                    e.printStackTrace();
                }
            }
        });

    }

    private void showCategory(List<Classify> datas) {
        showLeft(datas);
        cateMap = new HashMap<>();
        brandMap = new HashMap<>();
        for (Classify data : datas) {
            cateMap.put(data.cateId, data.children);
            brandMap.put(data.cateId, data.brand);
        }
        showRight(oneCateId);
    }

    private void showLeft(final List<Classify> data) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xrvLeft.setLayoutManager(layoutManager);
        xrvLeft.setPullRefreshEnabled(false);
        xrvLeft.setLoadingMoreEnabled(false);
        xrvLeft.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        oneCateId = data.get(0).cateId;
        leftAdapter = new CommonAdapter<Classify>(getContext(), R.layout.item_category_left, data) {
            @Override
            protected void convert(ViewHolder holder, Classify classify, int position) {
                holder.setText(R.id.tv_cate_name, classify.cateName);
                if (oneCatePostion == position) {
                    holder.setBackgroundColor(R.id.v_divider, Color.RED);
                } else {
                    holder.setBackgroundColor(R.id.v_divider, Color.TRANSPARENT);
                }
            }

        };
        leftAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (oneCatePostion != position) {
                    oneCateId = data.get(position - XRECYCLER_HEAD_DEFAULT_COUNT).cateId;
                    oneCatePostion = position;
                    switchCategory(oneCateId);
                    leftAdapter.notifyDataSetChanged();
                    TextView tv = (TextView) view.findViewById(R.id.tv_cate_name);
                    String s =  tv.getText()+"";
                    HashMap<String,String> map = new HashMap<String,String>();
                    map.put("goodClassification",s);
                    MobclickAgent.onEvent(getContext(), UmengStat.ONELEVELCLASSIFICATION, map);
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        xrvLeft.setAdapter(leftAdapter);
    }

    private void switchCategory(int cateId) {
        twoCates = cateMap.get(cateId);
        brands = brandMap.get(cateId);
        xrvRight.removeAllViews();
        if (brands != null && brands.size() > 0) {
            bannerView.setVisibility(View.VISIBLE);
            bannerView.setPages(new CBViewHolderCreator<LocalImageHolderView>() {
                @Override
                public LocalImageHolderView createHolder() {
                    return new LocalImageHolderView();
                }
            }, brands)
                    //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
//                    .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                    //设置指示器的方向
                    .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
            bannerView.startTurning(5000);
        } else {
            bannerView.setVisibility(View.GONE);
        }
        rightAdapter = null;
        rightAdapter = new RightAdapter(getContext(), R.layout.item_category_right, twoCates);
        rightAdapter.setOnItemClickListener(listener);
        xrvRight.setAdapter(rightAdapter);
        xrvRight.getAdapter().notifyDataSetChanged();
    }

    private void showRight(int cateId) {
        xrvRight.setLayoutManager(new GridLayoutManager(getContext(), 3));
        xrvRight.setPullRefreshEnabled(false);
//        xrvRight.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        switchCategory(cateId);
    }

    private class RightAdapter extends CommonAdapter<Classify> {
        public RightAdapter(Context context, int layoutId, List<Classify> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, Classify classify, int position) {
            holder.setText(R.id.tv_cate_name, classify.cateName);
            Glide.with(getContext()).load(classify.cateImg).into((ImageView) holder.getView(R.id.iv_pic));
        }
    }

    private CommonAdapter.OnItemClickListener listener = new CommonAdapter.OnItemClickListener() {

        @Override
        public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
            int pos = position - XRECYCLER_HEAD_DEFAULT_COUNT;
            if (pos >= 0 && pos < twoCates.size()) {
                String category = twoCates.get(pos).cateId + "_" + twoCates.get(pos).classify_rank;
                Intent intent = new Intent(getActivity(), GoodsListActivity.class);
                intent.putExtra("category", category);
                getActivity().startActivity(intent);

                TextView tv = (TextView) view.findViewById(R.id.tv_cate_name);
                String s =  tv.getText()+"";
                HashMap<String,String> map = new HashMap<String,String>();
                map.put("goodClassificationList",s);
                MobclickAgent.onEvent(getContext(), UmengStat.TWOLEVELCLASSIFICATION,map);
            }
        }

        @Override
        public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
            return false;
        }
    };

    public class LocalImageHolderView implements Holder<Brand> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, Brand data) {
            Glide.with(getContext()).load(data.brandLogo).into(imageView);
//            Glide.with(getContext()).load("http://image.kili.co/mobile/special/s0/s0_05418650600313459.png").into(imageView);
//            onImageViewClick(imageView, data.getType(), data.getData(), data.getTitle());
//            onImageViewClick(imageView, "url", "http://www.baidu.com");
        }
    }

    @OnClick({R.id.ll_head})
    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.ll_head:
                MobclickAgent.onEvent(getContext(), UmengStat.searchBox);
                CommonUtils.startAct(getActivity(), CommonFragmentActivity.FRAGMENT_SEARCH);
                break;
        }
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(UmengStat.CATEGORYPAGE); //统计页面，"MainScreen"为页面名称，可自定义
        CommonUtils.umTongJi(getContext(),getIPAddress(),"2", "0");
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(UmengStat.CATEGORYPAGE);
    }
}
