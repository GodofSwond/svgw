package com.svgouwu.client.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.svgouwu.client.BaseFragment;
import com.svgouwu.client.CommonFragmentActivity;
import com.svgouwu.client.R;
import com.svgouwu.client.adapter.CouponsUseAdapter;
import com.svgouwu.client.bean.OrderConfirmResult;
import com.svgouwu.client.utils.CommonUtils;
import com.svgouwu.client.utils.ToastUtil;
import com.svgouwu.client.utils.UmengStat;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/9/11.
 * 优惠券使用规则
 */

public class CouponsUseFragment extends BaseFragment {

    private List<HashMap<String, String>> useList = new ArrayList<>();//可用优惠券列表
    private List<OrderConfirmResult.OrderCoupon> coupon;
    private CouponsUseAdapter useAdapter;
    private String couponValue;

    @BindView(R.id.fragment_coupons_use_Lv)
    ListView useLv;
    private String id;
    private double cValue;
    private CouponsUseAdapter.ViewHolder holder;
    private HashMap<String, String> pos;

    @Override
    protected int getContentView() {
        return R.layout.fragment_coupons_use;
    }

    /**
     * 展示头部
     */
    private void showTopBar() {
        TextView textView = findView(R.id.tv_title);
        final TextView rightTv = findView(R.id.tv_right);
        rightTv.setText("优惠券规则");
        rightTv.setVisibility(View.VISIBLE);
        textView.setText("使用优惠劵");
        findView(R.id.iv_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        //
        rightTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CommonUtils.checkLogin())
                    CommonUtils.startAct(getActivity(), CommonFragmentActivity.FRAGMENT_DO_RULE);
            }
        });
    }

    @Override
    public void initViews() {
        showTopBar();
    }

    @Override
    public void initListener() {
        useLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // 取得ViewHolder对象，这样就省去了通过层层的findViewById去实例化我们需要的cb实例的步骤
                holder = (CouponsUseAdapter.ViewHolder) view.getTag();
                // 改变CheckBox的状态
                holder.useCb.toggle();
                if (holder.useCb.isChecked()) {
                    cValue = Double.parseDouble((useList.get(i).get("couponValue")));
                } else {
                    cValue -= Double.parseDouble((useList.get(i).get("couponValue")));
                }
            }
        });
    }

    @Override
    public void initData() {
        getIntentData();// 获取上一个碎片传递过来的数据
        useAdapter = new CouponsUseAdapter(getActivity(), coupon);
        useLv.setAdapter(useAdapter);
    }

    private void getIntentData() {
        coupon = (List<OrderConfirmResult.OrderCoupon>) getActivity().getIntent().getSerializableExtra("Coupon");
        for (int i = 0; i < coupon.size(); i++) {
            String couponName = coupon.get(i).couponName;
            couponValue = coupon.get(i).couponValue;
            String startTime = coupon.get(i).startTime;
            String endTime = coupon.get(i).endTime;
            id = coupon.get(i).id;
            HashMap<String, String> map = new HashMap<>();
            map.put("couponName", couponName);
            map.put("couponValue", couponValue);
            map.put("startTime", startTime);
            map.put("endTime", endTime);
            useList.add(map);
        }
    }

    @OnClick({R.id.fragment_coupons_use_tvSub})
    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_coupons_use_tvSub:
                for (int i = 0; i < useList.size(); i++) {
                    if (cValue != 0.0) {
                        getActivity().setResult(1, new Intent().putExtra("cValue", cValue + "").putExtra("id", id));// 跳转回原来的activity
                        getActivity().finish();// 一定要结束当前activity
                    } else {
                        Log.i("TAG", "processClick: " + "亲，请选择优惠券");
                        ToastUtil.toast("亲，请选择优惠券");
                    }
                }
                MobclickAgent.onEvent(getActivity(), UmengStat.COUPONSUSECONFIRM);//ym
                break;
        }
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(UmengStat.COUPONSUSEPAGE); //统计页面
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(UmengStat.COUPONSUSEPAGE);
    }
}
