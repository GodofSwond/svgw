package com.svgouwu.client.fragment;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.kili.okhttp.OkHttpUtils;
import com.svgouwu.client.Api;
import com.svgouwu.client.BaseFragment;
import com.svgouwu.client.MyApplication;
import com.svgouwu.client.R;
import com.svgouwu.client.bean.HttpResult;
import com.svgouwu.client.bean.OrderDetailsResult;
import com.svgouwu.client.utils.CommonCallback;
import com.svgouwu.client.utils.CommonUtils;
import com.svgouwu.client.utils.ImageLoader;
import com.svgouwu.client.utils.ToastUtil;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

/**
 * 商品评价
 * Created by melon on 2017/8/4.
 * Email 530274554@qq.com
 */

public class OrderCommentGoodsFragment extends BaseFragment {
    @BindView(R.id.iv_goods_comment_pic)
    ImageView iv_goods_comment_pic;
    @BindView(R.id.et_goods_comment_content)
    EditText et_goods_comment_content;
    @BindView(R.id.rba_goods_comment_rating)
    RatingBar rba_goods_comment_rating;
    private String recid;

    @Override
    protected int getContentView() {
        return R.layout.fragment_order_comment_goods;
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
        String pic = getActivity().getIntent().getStringExtra("pic");
        recid = getActivity().getIntent().getStringExtra("recid");
        if(CommonUtils.isEmpty(pic)){
            ToastUtil.toast("商品异常");
            getActivity().finish();
            return;
        }
        if (!CommonUtils.isEmpty(pic)) {
            ImageLoader.with(getContext(),pic,iv_goods_comment_pic);
        }
    }

    @OnClick({R.id.tv_right})
    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
                submit();
                break;
        }
    }

    private void submit() {
        String comment = et_goods_comment_content.getText().toString().trim();
//        if(CommonUtils.isEmpty(comment)){
//            ToastUtil.toast("请输入评论内容");
//            return;
//        }

        String url = Api.URL_GOODS_COMMENT;
        HashMap<String, String> params = new HashMap<>();
        params.put("token", MyApplication.getInstance().getLoginKey());
        params.put("recid", recid);
        params.put("score", (int)rba_goods_comment_rating.getRating()+"");
        params.put("comment", comment);

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
        TextView tv_right = findView(R.id.tv_right);
        tv_title.setText("评价晒单");
        tv_right.setText("发表");
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setTextColor(getResources().getColor(R.color.color_money));

        findView(R.id.iv_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }
}
