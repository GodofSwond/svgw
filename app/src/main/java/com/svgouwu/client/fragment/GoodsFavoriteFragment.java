package com.svgouwu.client.fragment;

import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kili.okhttp.OkHttpUtils;
import com.svgouwu.client.Api;
import com.svgouwu.client.BaseFragment;
import com.svgouwu.client.Constant;
import com.svgouwu.client.MyApplication;
import com.svgouwu.client.R;
import com.svgouwu.client.activity.AddressListActivity;
import com.svgouwu.client.adapter.FavoriteGoodsAdapter;
import com.svgouwu.client.adapter.GoodsFavoriteAdapter;
import com.svgouwu.client.bean.GoodsFavResult;
import com.svgouwu.client.bean.GoodsInfo;
import com.svgouwu.client.bean.HttpResult;
import com.svgouwu.client.utils.CommonCallback;
import com.svgouwu.client.utils.CommonUtils;
import com.svgouwu.client.utils.DialogUtil;
import com.svgouwu.client.utils.LogUtils;
import com.svgouwu.client.utils.MyGlideRoundImageTarget;
import com.svgouwu.client.utils.ToastUtil;
import com.svgouwu.client.utils.UmengStat;
import com.svgouwu.client.view.LoadPage;
import com.umeng.analytics.MobclickAgent;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

/**
 * 商品收藏
 * Created by melon on 2017/8/1.
 * Email 530274554@qq.com
 */

public class GoodsFavoriteFragment extends BaseFragment implements XRecyclerView.LoadingListener {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.xrv_goods_fav)
    XRecyclerView xrv_goods_fav;
    @BindView(R.id.mLoadPage)
    LoadPage mLoadPage;

    private int mPage = 1;
    private List<GoodsInfo> goodsz = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;

    @Override
    protected int getContentView() {
        return R.layout.fragment_goods_fav;
    }

    @Override
    public void initViews() {
        tv_title.setText("收藏宝贝");

        initLoadPage();

        mAdapter = new GoodsFavoriteAdapter(this, R.layout.item_favorite_goods, goodsz);
        xrv_goods_fav.setAdapter(mAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xrv_goods_fav.setLayoutManager(layoutManager);

        xrv_goods_fav.setLoadingListener(this);
    }

    private void initLoadPage() {
        mLoadPage.setGetDataListener(new LoadPage.GetDataListener() {
            @Override
            public void onGetData() {
                mLoadPage.switchPage(LoadPage.STATE_LOADING);
                initData();
            }
        });
        mLoadPage.switchPage(LoadPage.STATE_LOADING);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        getData();
    }

    private void getData() {
        String url = Api.URL_GOODS_FAV_LIST;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", MyApplication.getInstance().getLoginKey());
        params.put("page", mPage + "");
        params.put("limit", Constant.PAGE_LIMIT + "");
        OkHttpUtils.post().url(url).params(params).build().execute(new CommonCallback<GoodsFavResult>() {
            @Override
            public void onBefore(Request request) {
                super.onBefore(request);
            }

            @Override
            public void onAfter() {
                super.onAfter();
                xrv_goods_fav.refreshComplete();
                xrv_goods_fav.loadMoreComplete();
            }

            @Override
            public void onError(Call call, Exception e) {
                LogUtils.e(e.toString());
                mLoadPage.switchPage(LoadPage.STATE_NO_NET);
            }

            @Override
            public void onResponse(HttpResult<GoodsFavResult> response) {
                mLoadPage.switchPage(LoadPage.STATE_HIDE);
                try {
                    if (response.isSuccess()) {
                        List<GoodsInfo> goodsList = response.data.goodsList;
                        if (goodsList != null) {
                            if (mPage == 1) goodsz.clear();
                            goodsz.addAll(goodsList);
                            mAdapter.notifyDataSetChanged();
                        }

                        if (mPage == 1 && goodsList != null && goodsList.size() == 0) {
                            mLoadPage.switchPage(LoadPage.STATE_NO_DATA);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    mLoadPage.switchPage(LoadPage.STATE_NO_NET);
                }
            }
        });
    }

    @OnClick({R.id.iv_left})
    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                getActivity().finish();
                break;
        }
    }

    public void onItemDelete(final GoodsInfo info) {

        DialogUtil.show(getActivity(), "确定要删除该宝贝吗？", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                delFavGoods(info);
            }
        });
    }

    //删除收藏
    private void delFavGoods(final GoodsInfo info) {
        String url = Api.URL_GOODS_FAV_CANCEL;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", MyApplication.getInstance().getLoginKey());
        params.put("goodsId", info.goodsId);
        OkHttpUtils.post().url(url).params(params).build().execute(new CommonCallback<GoodsFavResult>() {
            @Override
            public void onBefore(Request request) {
                super.onBefore(request);
            }

            @Override
            public void onAfter() {
                super.onAfter();
            }

            @Override
            public void onError(Call call, Exception e) {
                LogUtils.e(e.toString());
            }

            @Override
            public void onResponse(HttpResult<GoodsFavResult> response) {
                try {
                    if (!CommonUtils.isEmpty(response.msg)) {
                        ToastUtil.toast(response.msg);
                    }

                    if (response.isSuccess()) {
                        goodsz.remove(info);
                        mAdapter.notifyDataSetChanged();

                        if (goodsz.size() == 0) {
                            mLoadPage.switchPage(LoadPage.STATE_NO_DATA);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        mPage = 1;
        getData();
    }

    @Override
    public void onLoadMore() {
        mPage++;
        getData();
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(UmengStat.COLLECTIONLISTPAGE);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(UmengStat.COLLECTIONLISTPAGE);
    }
}
