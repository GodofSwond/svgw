package com.svgouwu.client.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.DividerItemDecoration;
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
import com.svgouwu.client.MyApplication;
import com.svgouwu.client.R;
import com.svgouwu.client.bean.Address;
import com.svgouwu.client.bean.AddressResult;
import com.svgouwu.client.bean.HttpResult;
import com.svgouwu.client.bean.Region;
import com.svgouwu.client.bean.RegionResult;
import com.svgouwu.client.fragment.CommonSureDialogFragment;
import com.svgouwu.client.utils.CommonCallback;
import com.svgouwu.client.utils.LogUtils;
import com.svgouwu.client.utils.ToastUtil;
import com.svgouwu.client.utils.UmengStat;
import com.svgouwu.client.view.LoadPage;
import com.umeng.analytics.MobclickAgent;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by topwolf on 2017/6/6.
 * 地址管理列表页
 */

public class AddressListActivity extends BaseActivity {


    private static final int NEED_REFRESH = 100 ;
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.rl_topbar_title)
    RelativeLayout rlTopbarTitle;
    @BindView(R.id.ll_topbar)
    LinearLayout llTopbar;
    @BindView(R.id.mXRecyclerView)
    XRecyclerView mXRecyclerView;
    @BindView(R.id.tv_add)
    TextView tv_add;
    @BindView(R.id.tv_add2)
    TextView tv_add2;
    @BindView(R.id.ll_no_address)
    LinearLayout ll_no_address;
    @BindView(R.id.mLoadPage)
    LoadPage mLoadPage;

    public List<Address> datas;
    private int request_code;
    private boolean isValid=false;
    @Override
    protected int getContentView() {
        return R.layout.activity_address_list;
    }

    @Override
    public void initViews() {
        isValid = getIntent().getBooleanExtra("isValid",false);
        tvTitle.setText(getString(R.string.title_address_list));
        mLoadPage.setGetDataListener(new LoadPage.GetDataListener() {
            @Override
            public void onGetData() {
                mLoadPage.switchPage(LoadPage.STATE_LOADING);
                initData();
            }
        });
        mLoadPage.switchPage(LoadPage.STATE_LOADING);
        LinearLayoutManager layoutManager = new LinearLayoutManager(AddressListActivity.this);
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
        String url = Api.URL_ADDRESS_LIST;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", MyApplication.getInstance().getLoginKey());

        OkHttpUtils.post().url(url).params(params).build().execute(new CommonCallback<AddressResult>() {
            @Override
            public void onError(Call call, Exception e) {
                mLoadPage.switchPage(LoadPage.STATE_NO_NET);
            }

            @Override
            public void onResponse(HttpResult<AddressResult> response) {
                try {
                    if (response.isSuccess()) {
                        mLoadPage.switchPage(LoadPage.STATE_HIDE);
                        if (response.data != null && response.data.list != null && response.data.list.size() > 0) {
                            ll_no_address.setVisibility(View.GONE);
                            mXRecyclerView.setVisibility(View.VISIBLE);
                            tv_add2.setVisibility(View.VISIBLE);
                            datas = response.data.list;
                            CommonAdapter<Address> mAdapter = new CommonAdapter<Address>(AddressListActivity.this, R.layout.item_address_list, datas)
                            {
                                @Override
                                protected void convert(final ViewHolder holder, final Address address, final int position) {
                                    holder.setText(R.id.tv_name, address.consignee);
                                    holder.setText(R.id.tv_phone, address.phoneMob);
                                    holder.setText(R.id.tv_address, address.address);
//                                    holder.setChecked(R.id.cb_default,address.setdefault == 1? true: false);
//                                    holder.setText(R.storeId.cb_default, address.address);//tv_edit.tv_del
                                    holder.setOnClickListener(R.id.tv_edit, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(AddressListActivity.this,AddressActivity.class);
                                            intent.putExtra("address",address);
                                            intent.putExtra("isValid",isValid);
                                            startActivityForResult(intent,NEED_REFRESH);
                                        }
                                    });
                                    holder.setOnClickListener(R.id.tv_del, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            CommonSureDialogFragment dialog = new CommonSureDialogFragment("确定要删除该地址吗？",new CommonSureDialogFragment.SureListener() {
                                                @Override
                                                public void onSureListener() {
                                                    delete(position,address);
                                                }
                                            });
                                            dialog.show(getSupportFragmentManager(), "SureDialog");
                                        }
                                    });
                                    CheckBox cb_default = holder.getView(R.id.cb_default);
                                    if(address.setdefault == 1){
                                        cb_default.setChecked(true);
                                        cb_default.setEnabled(false);
                                    }else{
                                        cb_default.setEnabled(true);
                                        cb_default.setChecked(false);
                                        holder.setOnClickListener(R.id.cb_default, new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                setDefault(position,address);
                                            }
                                        });
                                    }

                                }
                            };
                            mAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                                    setResult(RESULT_OK,new Intent().putExtra("address",datas.get(position -XRECYCLER_HEAD_DEFAULT_COUNT)));
                                }

                                @Override
                                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                                    return false;
                                }
                            });
                            mXRecyclerView.setAdapter(mAdapter);
                        }else{
                            ll_no_address.setVisibility(View.VISIBLE);
                            tv_add2.setVisibility(View.GONE);
                        }
                    }
                }catch (Exception e) {
                    mLoadPage.switchPage(LoadPage.STATE_NO_NET);
                    e.printStackTrace();
                }
            }
        });
    }

    private void setDefault(final int position, Address address) {
        String url = Api.URL_ADDRESS_SET_DEFAULT;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", MyApplication.getInstance().getLoginKey());
        params.put("addrId", address.addrId);

        OkHttpUtils.post().url(url).params(params).build().execute(new CommonCallback<AddressResult>() {
            @Override
            public void onError(Call call, Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(HttpResult<AddressResult> response) {
                try {
                    if (response.isSuccess()) {
                        for(int i =0;i<datas.size();i++){
                            if(i == position-XRECYCLER_HEAD_DEFAULT_COUNT){
                                datas.get(i).setdefault = 1;
                            }else{
                                datas.get(i).setdefault = 0;
                            }
                        }
                        mXRecyclerView.getAdapter().notifyDataSetChanged();
                        ToastUtil.toast("设置成功");
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void delete(final int position,final Address address) {
        String url = Api.URL_ADDRESS_DELETE;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", MyApplication.getInstance().getLoginKey());
        params.put("addrId", address.addrId);

        OkHttpUtils.post().url(url).params(params).build().execute(new CommonCallback<AddressResult>() {
            @Override
            public void onError(Call call, Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(HttpResult<AddressResult> response) {
                try {
                    if (response.isSuccess()) {
                        datas.remove(address);
                        mXRecyclerView.getAdapter().notifyItemRemoved(position);
                        ToastUtil.toast("删除成功");
                        if(datas.size() == 0){
                            mXRecyclerView.setVisibility(View.GONE);
                            ll_no_address.setVisibility(View.VISIBLE);
                            tv_add2.setVisibility(View.GONE);
                        }
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @OnClick({R.id.iv_left,R.id.tv_add,R.id.tv_add2})
    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.tv_add:
            case R.id.tv_add2:
                Intent intent = new Intent(AddressListActivity.this,AddressActivity.class);
                intent.putExtra("isValid",isValid);
                startActivityForResult(intent,NEED_REFRESH);
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == NEED_REFRESH){
            initData();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(UmengStat.ADDRESSLISTPAGE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(UmengStat.ADDRESSLISTPAGE);
    }
}
