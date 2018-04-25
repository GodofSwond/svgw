package com.svgouwu.client.fragment;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kili.okhttp.OkHttpUtils;
import com.svgouwu.client.Api;
import com.svgouwu.client.BaseFragment;
import com.svgouwu.client.MyApplication;
import com.svgouwu.client.R;
import com.svgouwu.client.activity.GoodsListActivity;
import com.svgouwu.client.bean.HttpResult;
import com.svgouwu.client.bean.SearchResult;
import com.svgouwu.client.event.NewSearchEvent;
import com.svgouwu.client.utils.CommonCallback;
import com.svgouwu.client.utils.CommonUtils;
import com.svgouwu.client.utils.LogUtils;
import com.svgouwu.client.utils.MyGlideRoundImageTarget;
import com.svgouwu.client.utils.ToastUtil;
import com.svgouwu.client.utils.UmengStat;
import com.svgouwu.client.view.FlowLayout;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

import static com.svgouwu.client.MyApplication.getIPAddress;

/**
 * Created by melon on 2017/7/23.
 * 搜索页
 */

public class SearchFragment extends BaseFragment {

    @BindView(R.id.iv_search_back)
    ImageView ivSearchBack;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tv_search_search)
    TextView tvSearchSearch;
    @BindView(R.id.flow_search)
    FlowLayout flowSearch;

    @Override
    protected int getContentView() {
        return R.layout.fragment_search;
    }

    @Override
    public void initViews() {
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    String keyword = etSearch.getText().toString();
                    enterGoodsList(keyword);
                    return true;
                }
                return false;
            }
        });

        //TODO 加载过渡页
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        getHotSearch();
    }

    private void getHotSearch() {
        String url = Api.URL_HOT_SEARCH;
        OkHttpUtils.get().url(url).build().execute(new CommonCallback<SearchResult>() {
            @Override
            public void onBefore(Request request) {
                super.onBefore(request);
//                weixinDialogInit(getString(R.string.dialog_process));
            }

            @Override
            public void onAfter() {
                super.onAfter();
//                cancelWeiXinDialog();
            }

            @Override
            public void onError(Call call, Exception e) {
                LogUtils.e(e.toString());
                ToastUtil.toast(R.string.text_tips_net_issue);
            }

            @Override
            public void onResponse(HttpResult<SearchResult> response) {
                try {
                    List<String> results = response.data.keyword;
                    for (final String result : results) {
                        TextView view = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.item_hot_search, flowSearch, false);
                        view.setText(result);
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //显示新商品列表
                                enterGoodsList(result);
                            }
                        });
                        flowSearch.addView(view);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void enterGoodsList(String result) {
        //销毁商品列表页
        EventBus.getDefault().post(new NewSearchEvent());

        Intent intent = new Intent(getContext(), GoodsListActivity.class);
        intent.putExtra("keyword", result);
        startActivity(intent);
        getActivity().finish();
    }

    @OnClick({R.id.iv_search_back, R.id.tv_search_search})
    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.iv_search_back:
                getActivity().finish();
                break;
            case R.id.tv_search_search:
                search();
                break;
            case R.id.tv_search_next:
                break;
        }
    }

    private void search() {
        String keywords = etSearch.getText().toString().trim();
        if (CommonUtils.isEmpty(keywords)) {
            return;
        }

        enterGoodsList(keywords);
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(UmengStat.SEARCHPAGE); //统计页面
        CommonUtils.umTongJi(getContext(),getIPAddress(),"3", "0");
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(UmengStat.SEARCHPAGE);
    }
}
