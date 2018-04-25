package com.svgouwu.client.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.kili.okhttp.OkHttpUtils;
import com.svgouwu.client.Api;
import com.svgouwu.client.BaseFragment;
import com.svgouwu.client.CommonFragmentActivity;
import com.svgouwu.client.MyApplication;
import com.svgouwu.client.R;
import com.svgouwu.client.bean.GoodsInfo;
import com.svgouwu.client.bean.HttpResult;
import com.svgouwu.client.bean.OrderDetails;
import com.svgouwu.client.bean.OrderDetailsResult;
import com.svgouwu.client.utils.CommonCallback;
import com.svgouwu.client.utils.CommonUtils;
import com.svgouwu.client.utils.ImageLoader;
import com.svgouwu.client.utils.ToastUtil;
import com.svgouwu.client.utils.UmengStat;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

/**
 * 评价晒单页
 * Created by melon on 2017/8/4.
 * Email 530274554@qq.com
 */

public class OrderCommentFragment extends BaseFragment {
    @BindView(R.id.ll_order_comment_goods)
    LinearLayout ll_order_comment_goods;
    @BindView(R.id.rba_order_comment_pack)
    RatingBar rba_order_comment_pack;
    @BindView(R.id.rba_order_comment_speed)
    RatingBar rba_order_comment_speed;
    @BindView(R.id.rba_order_comment_attr)
    RatingBar rba_order_comment_attr;

    @Override
    protected int getContentView() {
        return R.layout.fragment_order_comment_send;
    }

    @Override
    public void initViews() {
        initTopBar();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        mOrderId = getActivity().getIntent().getStringExtra("orderId");
        if (CommonUtils.isEmpty(mOrderId)) {
            ToastUtil.toast("订单异常");
            getActivity().finish();
            return;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //获取订单详情中的商品
        getGoodsInfo();
        MobclickAgent.onPageStart(UmengStat.EVALUATIONLISTPAGE);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(UmengStat.EVALUATIONLISTPAGE);
    }

    String mOrderId;

    private void getGoodsInfo() {
        String url = Api.URL_ORDER_DETAILS;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", MyApplication.getInstance().getLoginKey());
        params.put("orderId", mOrderId);
        OkHttpUtils.post().url(url).params(params).build().execute(new CommonCallback<OrderDetailsResult>() {
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
//                mLoadPage.switchPage(LoadPage.STATE_NO_NET);
                ToastUtil.toast(R.string.text_tips_net_issue);
            }

            @Override
            public void onResponse(HttpResult<OrderDetailsResult> response) {
                try {
                    if (response.isSuccess()) {
//                        mLoadPage.switchPage(LoadPage.STATE_HIDE);
                        if (response.data != null) {
                            OrderDetails orderinfo = response.data.orderinfo;
                            List<GoodsInfo> goodsz = orderinfo.goods;

                            ll_order_comment_goods.removeAllViews();
                            for (final GoodsInfo goods : goodsz) {
                                View goodsView = LayoutInflater.from(getContext()).inflate(R.layout.item_goods_order_comment, ll_order_comment_goods, false);
                                ImageView iv_item_goods_order_comment_send = (ImageView) goodsView.findViewById(R.id.iv_item_goods_order_comment_send);
                                ImageView iv_item_goods_order_comment_pic = (ImageView) goodsView.findViewById(R.id.iv_item_goods_order_comment_pic);
                                TextView tv_item_goods_order_comment_name = (TextView) goodsView.findViewById(R.id.tv_item_goods_order_comment_name);

                                ImageLoader.with(getContext(), goods.defaultImage, iv_item_goods_order_comment_pic);
                                tv_item_goods_order_comment_name.setText(goods.goodsName);

                                if(goods.evaluation==0){
                                    iv_item_goods_order_comment_send.setEnabled(true);
                                    iv_item_goods_order_comment_send.setImageResource(R.drawable.ic_goods_comment_send);
                                }else {
                                    iv_item_goods_order_comment_send.setEnabled(false);
                                    iv_item_goods_order_comment_send.setImageResource(R.drawable.ic_order_comment_send_alr);
                                }

                                iv_item_goods_order_comment_send.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(getContext(), CommonFragmentActivity.class);
                                        intent.putExtra("target", CommonFragmentActivity.FRAGMENT_ORDER_COMMENT_GOODS);
                                        intent.putExtra("pic", goods.defaultImage);
                                        intent.putExtra("recid", goods.recId+"");
                                        startActivity(intent);
                                    }
                                });
                                ll_order_comment_goods.addView(goodsView);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
//                    mLoadPage.switchPage(LoadPage.STATE_NO_NET);
                    ToastUtil.toast(R.string.text_tips_server_issue);
                }
            }
        });
    }

    @OnClick({R.id.iv_order_comment_submit})
    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.iv_order_comment_submit:
                submitComment();
                break;
        }
    }

    private void submitComment() {
        int packStars = (int)rba_order_comment_pack.getRating();
        int speedStars = (int)rba_order_comment_speed.getRating();
        int attrStars = (int)rba_order_comment_attr.getRating();

        if(packStars == 0){
            ToastUtil.toast("请对商品包装评价");
            return;
        }
        if(speedStars == 0){
            ToastUtil.toast("请对送货速度评价");
            return;
        }
        if(attrStars == 0){
            ToastUtil.toast("请对配送员服务态度评价");
            return;
        }

        String url = Api.URL_ORDER_COMMENT;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", MyApplication.getInstance().getLoginKey());
        params.put("oid", mOrderId);
        params.put("goodspack", +packStars+"");
        params.put("speed", speedStars+"");
        params.put("attitude", attrStars+"");

        OkHttpUtils.post().url(url).params(params).build().execute(new CommonCallback<OrderDetailsResult>() {
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
                ToastUtil.toast(R.string.text_tips_net_issue);
            }

            @Override
            public void onResponse(HttpResult<OrderDetailsResult> response) {
                try {
                    if(!CommonUtils.isEmpty(response.msg)){
                        ToastUtil.toast(response.msg);
                    }
                    if (response.isSuccess()) {
                        getActivity().finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtil.toast(R.string.text_tips_server_issue);
                }
            }
        });
    }

    public void initTopBar() {
        TextView tv_title = findView(R.id.tv_title);
        tv_title.setText("评价晒单");

        findView(R.id.iv_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }
}
