package com.svgouwu.client.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kili.okhttp.OkHttpUtils;
import com.svgouwu.client.Api;
import com.svgouwu.client.BaseActivity;
import com.svgouwu.client.MyApplication;
import com.svgouwu.client.R;
import com.svgouwu.client.adapter.MySVCoinAdapter;
import com.svgouwu.client.bean.HttpResult;
import com.svgouwu.client.bean.WealthRebateBean;
import com.svgouwu.client.utils.CommonCallback;
import com.svgouwu.client.utils.CommonUtils;
import com.svgouwu.client.utils.CustomToast;
import com.svgouwu.client.view.LoadPage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by whq on 2017/12/28.
 * 我的四维币页面
 */

public class MysvCoinActivity extends BaseActivity {

    @BindView(R.id.tv_mycoin_money)
    TextView tv_mycoin_money;//总金额
    @BindView(R.id.tv_mycoin_take)
    TextView tv_mycoin_take;//可提取金额
    @BindView(R.id.tv_mycoin_frozen)
    TextView tv_mycoin_frozen;//冻结金额
    @BindView(R.id.rv_mycoin)
    XRecyclerView rv_mycoin;
    @BindView(R.id.mLoadPage_mycoin)
    LoadPage mLoadPage_mycoin;//加载
    @BindView(R.id.tv_mycoin_apply)
    TextView tv_mycoin_apply;//提取申请

    private LinearLayoutManager manager;
    private List<WealthRebateBean.RebateListBean> list = new ArrayList<>();
    private int mPage = 1;
    MySVCoinAdapter adapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_svcoin;
    }

    @Override
    public void initViews() {
        setTopBarViews(findViewById(R.id.rl_coin), true);
        manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_mycoin.setPullRefreshEnabled(false);
        rv_mycoin.setNoMore(false);
        rv_mycoin.setLayoutManager(manager);
        mLoadPage_mycoin.setGetDataListener(new LoadPage.GetDataListener() {
            @Override
            public void onGetData() {
                mLoadPage_mycoin.switchPage(LoadPage.STATE_LOADING);
                mPage = 1;
                getSVCoinData();
            }
        });
        mLoadPage_mycoin.switchPage(LoadPage.STATE_LOADING);
        getSVCoinData();
    }

    @Override
    public void initListener() {
        rv_mycoin.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //下拉刷新
                mPage = 1;
                getSVCoinData();
            }

            @Override
            public void onLoadMore() {
                //加载更多
                mPage++;
                getSVCoinData();
            }
        });
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.iv_mycoin_back, R.id.tv_mycoin_apply})
    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.iv_mycoin_back:
                finish();
                break;
            case R.id.tv_mycoin_apply:
                //提取申请
            //    showDialog();
                //解冻四维币
            //    getThaw();
                CustomToast.showToasts(mContext, "开发中...", 0);
                break;
            default:
                break;
        }
    }

    private void getSVCoinData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("token", MyApplication.getInstance().getLoginKey());
        map.put("page", mPage + "");
        OkHttpUtils.post().url(Api.URL_WEALTH_REBATE).params(map).build().execute(new CommonCallback<WealthRebateBean>() {
            @Override
            public void onError(Call call, Exception e) {
                mLoadPage_mycoin.switchPage(LoadPage.STATE_NO_NET);
            }

            @Override
            public void onResponse(HttpResult<WealthRebateBean> response) {
                try {
                    if (response.isSuccess()) {
                        if (response.data != null) {
                            fillData(response.data);
                        } else {
                            CustomToast.toast(mContext, response.msg);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("whq", "error==" + e);
                }
            }

        });
    }

    private void fillData(WealthRebateBean data) {
        if (data.account != null && mPage == 1) {
            if (!CommonUtils.isEmpty(data.account.total_money)) {
                //总金额
                tv_mycoin_money.setText(data.account.total_money);
            }
            if (!CommonUtils.isEmpty(data.account.ke_tixian_money)) {
                //可提金额
                tv_mycoin_take.setText(data.account.ke_tixian_money);
            }
            if (!CommonUtils.isEmpty(data.account.dong_jie_money)) {
                //冻结金额
                tv_mycoin_frozen.setText(data.account.dong_jie_money);
            }
        }
        if (data.list != null) {
            mLoadPage_mycoin.switchPage(LoadPage.STATE_HIDE);
            if (mPage == 1) {
                list.clear();
                if (data.list.size() > 5) {
                    rv_mycoin.setLoadingMoreEnabled(true);
                } else {
                    rv_mycoin.setLoadingMoreEnabled(false);
                }

            } else {
                if (data.list.size() == 0) {
                    rv_mycoin.setLoadingMoreEnabled(false);
                    CustomToast.toast(mContext, "亲，没有更多了哟～");
                    return;
                }
            }
            list.addAll(data.list);
            adapter = new MySVCoinAdapter(list);
            rv_mycoin.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            if (mPage == 1) {
                mLoadPage_mycoin.switchPage(LoadPage.STATE_NO_DATA);
            }
        }
    }

   /* View view;
    MysvCoinDialog.Builder builder;
    MysvCoinDialog dialog;

    private void showDialog() {
        view = LayoutInflater.from(mContext).inflate(R.layout.dialog_coin, null, false);
        builder = new MysvCoinDialog.Builder(mContext);
        dialog = builder.setOntentView(view)
                .setPositivite(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startActivityForResult(new Intent(mContext, CommonFragmentActivity.class)
                                .putExtra(CommonFragmentActivity.TARGET, 28), 1001);
                    }
                }).setNegative(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        dialog.setCancelable(false);
        dialog.show();
    }*/

  /*  @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1001:
                //提取申请
                mLoadPage_mycoin.switchPage(LoadPage.STATE_LOADING);
                mPage = 1;
                getSVCoinData();
                break;
            default:
                break;
        }
    }*/

    /**
     * 解冻四维币
     */
    private void getThaw(){
        String url = Api.BASE_URL;
        HashMap<String, String> map = new HashMap<>();
        OkHttpUtils.post().url(url).params(null).build().execute(new CommonCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(Object response) {

            }
        });
    }
}
