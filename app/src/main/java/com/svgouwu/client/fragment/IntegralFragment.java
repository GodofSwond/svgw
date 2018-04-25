package com.svgouwu.client.fragment;

import android.view.View;
import android.widget.TextView;

import com.svgouwu.client.BaseFragment;
import com.svgouwu.client.R;
import com.svgouwu.client.adapter.IntegralAdapter;
import com.svgouwu.client.utils.DateTimePickDialogUtils;
import com.svgouwu.client.utils.ToastUtil;
import com.svgouwu.client.view.XListView.XListView;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 会员积分
 * Created by melon on 2017/6/20.
 */

public class IntegralFragment extends BaseFragment {
    @BindView(R.id.xlv_integral)
    XListView xlv_integral;

    @Override
    protected int getContentView() {
        return R.layout.fragment_integral;
    }

    @Override
    public void initViews() {
        initTopBar();

        xlv_integral.setEmptyView(findView(R.id.tv_integral_empty_tips));
    }

    @Override
    public void initListener() {

    }

    public void initTopBar() {
        TextView tv_title = findView(R.id.tv_title);
        tv_title.setText("会员积分");

        findView(R.id.iv_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    @Override
    public void initData() {
        xlv_integral.setAdapter(new IntegralAdapter(getContext()));
    }

    @OnClick({R.id.ll_integral_plus, R.id.ll_integral_minus})
    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.ll_integral_plus:
                xlv_integral.setAdapter(new IntegralAdapter(getContext()));
                break;
            case R.id.ll_integral_minus:
                xlv_integral.setAdapter(null);
                break;
        }
    }

}
