package com.svgouwu.client.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kili.okhttp.OkHttpUtils;
import com.svgouwu.client.Api;
import com.svgouwu.client.BaseFragment;
import com.svgouwu.client.MyApplication;
import com.svgouwu.client.R;
import com.svgouwu.client.SimpleFragment;
import com.svgouwu.client.activity.GoodsListActivity;
import com.svgouwu.client.activity.RegionActivity;
import com.svgouwu.client.bean.Classify;
import com.svgouwu.client.bean.HttpResult;
import com.svgouwu.client.bean.Region;
import com.svgouwu.client.bean.RegionResult;
import com.svgouwu.client.event.GotRegionEvent;
import com.svgouwu.client.utils.CommonCallback;
import com.svgouwu.client.utils.CommonUtils;
import com.svgouwu.client.utils.LogUtils;
import com.svgouwu.client.utils.ToastUtil;
import com.svgouwu.client.view.LoadPage;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * Created by topwolf on 2017/6/6.
 */

public class RegionFragment extends BaseFragment {

    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.mLoadPage)
    LoadPage mLoadPage;

    public static final String ARGUMENT = "regionId";
    public static final String LEVEL = "level";
    public static final String ISLASTLEVEL = "islastlevel";
    private String regionId;
    private int level;
    private boolean islastlevel;
    private XRecyclerView mXRecyclerView;
    public List<Region> datas;
    public RegionFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            regionId = bundle.getString(ARGUMENT);
            islastlevel = bundle.getBoolean(ISLASTLEVEL);
            level = bundle.getInt(LEVEL,1);
        }
    }

    public static RegionFragment newInstance(String regionId,int level,boolean islastlevel) {
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT,regionId);
        bundle.putInt(LEVEL,level);
        bundle.putBoolean(ISLASTLEVEL,islastlevel);
        RegionFragment fragment = new RegionFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_region;
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
        mXRecyclerView = findView(R.id.mXRecyclerView);
    }

    @Override
    public void initListener() {
    }

    @Override
    public void initData() {
        String url = Api.URL_AREA_LIST;
        HashMap<String, String> params = new HashMap<>();
        params.put("parentid", regionId);

        OkHttpUtils.post().url(url).params(params).build().execute(new CommonCallback<RegionResult>() {

            @Override
            public void onError(Call call, Exception e) {
                e.printStackTrace();
                mLoadPage.switchPage(LoadPage.STATE_NO_NET);
            }

            @Override
            public void onResponse(final HttpResult<RegionResult> response) {

                try {
                    if (response.isSuccess()) {
                        mLoadPage.switchPage(LoadPage.STATE_HIDE);
                        if(response.data!= null && response.data.list !=null && response.data.list.size()>0){
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            mXRecyclerView.setLayoutManager(layoutManager);
                            mXRecyclerView.setPullRefreshEnabled(false);
                            mXRecyclerView.setNoMore(true);
                            mXRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
                            datas = response.data.list;
                            if (datas.size() == 0) {
                                mLoadPage.switchPage(LoadPage.STATE_NO_DATA);
                            }
                            final CommonAdapter<Region> mAdapter = new CommonAdapter<Region>(getContext(), R.layout.item_region, datas)
                            {
                                @Override
                                protected void convert(ViewHolder holder, Region region, int position) {
                                    if((level ==2 && islastlevel) || level >=3){
                                        holder.setVisible(R.id.iv_next,false);
                                    }else{
                                        holder.setVisible(R.id.iv_next,true);
                                    }
                                    holder.setText(R.id.tv_region, region.regionName);
                                }
                            };
                            mAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

                                    if((level ==2 && islastlevel) || level >=3){
                                        EventBus.getDefault().post(new GotRegionEvent(datas.get(position-XRECYCLER_HEAD_DEFAULT_COUNT).regionId, datas.get(position-XRECYCLER_HEAD_DEFAULT_COUNT).regionName));
//                                        ToastUtil.toast("last level");
                                        getActivity().finish();
                                        return;
                                    }
                                    if(position>=1 && position <= response.data.list.size()){
                                        int nextLevel = level+1;
                                        Intent intent = new Intent(getActivity(), RegionActivity.class);
                                        intent.putExtra("regionId",datas.get(position-XRECYCLER_HEAD_DEFAULT_COUNT).regionId);
                                        intent.putExtra("level",nextLevel);
                                        if((nextLevel == 2 && isLastLevel(datas.get(position-XRECYCLER_HEAD_DEFAULT_COUNT).regionName)) ||level >=3){
                                            intent.putExtra("islastlevel",true);
                                        }
                                        startActivity(intent);
                                    }
                                }

                                @Override
                                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                                    return false;
                                }
                            });
                            mXRecyclerView.setAdapter(mAdapter);
                        }
                    }else{
                        ToastUtil.toast(response.msg);
                    }

                } catch (Exception e) {
                    mLoadPage.switchPage(LoadPage.STATE_NO_NET);
                    e.printStackTrace();
                    ToastUtil.toast(R.string.text_tips_server_issue);
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

    private boolean isLastLevel(String regionName){
        if("北京市".equals(regionName) || "天津市".equals(regionName) || "重庆市".equals(regionName) || "上海市".equals(regionName)){
            return true;
        }
        return false;
    }
}
