package com.svgouwu.client.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kili.okhttp.OkHttpUtils;
import com.svgouwu.client.Api;
import com.svgouwu.client.BaseFragment;
import com.svgouwu.client.MyApplication;
import com.svgouwu.client.R;
import com.svgouwu.client.adapter.WealthNotesAdapter;
import com.svgouwu.client.bean.HttpResult;
import com.svgouwu.client.bean.WealthNotesBean;
import com.svgouwu.client.utils.CommonCallback;
import com.svgouwu.client.utils.ToastUtil;
import com.svgouwu.client.view.LoadPage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/11/17.
 * 提现记录
 */

public class WealthNotesFragment extends BaseFragment {

    private int page = 1;
    private List<WealthNotesBean.NotesItemBean> mList = new ArrayList<>();
    private WealthNotesAdapter notesAdapter;

    @BindView(R.id.tv_nodata_ll)
    View noData;
    @BindView(R.id.mLoadPage)
    LoadPage mLoadPage;
    @BindView(R.id.fragment_money_notes_xrecyclerView)
    XRecyclerView xrecyclerView;

    @Override
    protected int getContentView() {
        return R.layout.fragment_wealth_notes;
    }

    @Override
    public void initViews() {
        showTopBar();
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        xrecyclerView.setLayoutManager(layoutManager);
        notesAdapter = new WealthNotesAdapter(getContext(), R.layout.item_wealth_notes, mList);
        xrecyclerView.setAdapter(notesAdapter);
        xrecyclerView.setPullRefreshEnabled(false);
        mLoadPage.setGetDataListener(new LoadPage.GetDataListener() {
            @Override
            public void onGetData() {
                mLoadPage.switchPage(LoadPage.STATE_LOADING);
                jsonData();
            }
        });
        mLoadPage.switchPage(LoadPage.STATE_LOADING);
        xrecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                jsonData();
            }

            @Override
            public void onLoadMore() {
                page++;
                jsonData();
            }
        });
    }

    /**
     * 标题栏
     */
    private void showTopBar() {
        TextView tv_title = findView(R.id.tv_title);
        tv_title.setText("提现记录");
        ImageView iv_left = findView(R.id.iv_left);
        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        jsonData();
    }

    /**
     * 请求网络数据
     */
    private void jsonData() {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", MyApplication.getInstance().getLoginKey());
        params.put("page", page + "");
        try {
            OkHttpUtils.post().url(Api.URL_WEALTH_CLEAR).params(params).build().execute(new CommonCallback<WealthNotesBean>() {
                @Override
                public void onError(Call call, Exception e) {
                    mLoadPage.switchPage(LoadPage.STATE_NO_NET);
                }

                @Override
                public void onAfter() {
                    super.onAfter();
                    cancelWeiXinDialog();
                    xrecyclerView.loadMoreComplete();
                }

                @Override
                public void onResponse(HttpResult<WealthNotesBean> response) {
                    mLoadPage.switchPage(LoadPage.STATE_HIDE);
                    if (response.isSuccess()) {
                        if (response.data.list.size() != 0) {
                            mList.addAll(response.data.list);
                            xrecyclerView.setLoadingMoreEnabled(true);
                            notesAdapter.notifyDataSetChanged();
                        } else {
                            xrecyclerView.setLoadingMoreEnabled(false);
                            return;
                        }
                        if (mList.size() == 0) {
                            noData.setVisibility(View.VISIBLE);
                        } else {
                            noData.setVisibility(View.GONE);
                        }
                    } else {
                        ToastUtil.toast(response.msg);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            mLoadPage.switchPage(LoadPage.STATE_NO_NET);
        }
    }

    @Override
    public void processClick(View view) {

    }
}
