package com.svgouwu.client.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.svgouwu.client.BaseActivity;
import com.svgouwu.client.CommonFragmentActivity;
import com.svgouwu.client.R;
import com.svgouwu.client.fragment.MessageCouponFragment;
import com.svgouwu.client.fragment.MessageInformFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/3.
 * 消息首页
 */

public class MessageActivity extends BaseActivity {

    private FragmentManager fragmentManager;
    public static final int FRAGMENT_INFORM = 1;
    public static final int FRAGMENT_COUPON = 2;
    public int current_page = 1;

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_bottom_line)
    ImageView line;
    @BindView(R.id.iv_bottom_line2)
    ImageView line2;
    @BindView(R.id.aty_msg_rbInform)
    RadioButton rbInform;
    @BindView(R.id.aty_msg_rbCoupon)
    RadioButton rbCoupon;
    private Fragment informFragment;
    private Fragment couponFragment;

    @Override
    protected int getContentView() {
        return R.layout.activity_message;
    }

    @Override
    public void initViews() {
        tv_title.setText("消息");

        fragmentManager = getSupportFragmentManager();
    }

    @Override
    public void initListener() {
        rbInform.setOnClickListener(this);
        rbCoupon.setOnClickListener(this);
    }

    @Override
    public void initData() {
        showFragment(FRAGMENT_INFORM);
    }

    private void showFragment(int fragmentInform) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        current_page = fragmentInform;
        switch (fragmentInform) {
            case FRAGMENT_INFORM:
                if (informFragment == null) {
                    informFragment = MessageInformFragment.newInstance();
                    transaction.add(R.id.aty_msg_fl, informFragment);
                } else {
                    transaction.show(informFragment);
                }
                rbInform.setChecked(true);
                break;
            case FRAGMENT_COUPON:
                if (couponFragment == null) {
                    couponFragment = MessageCouponFragment.newInstance();
                    transaction.add(R.id.aty_msg_fl, couponFragment);
                } else {
                    transaction.show(couponFragment);
                }
                rbCoupon.setChecked(true);
                break;
        }
        transaction.commitAllowingStateLoss();
    }

    /**
     * 隐藏所有的FRAGMENT
     *
     * @param transaction 用于对FRAGMENT进行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (informFragment != null) {
            transaction.hide(informFragment);
        }
        if (couponFragment != null) {
            transaction.hide(couponFragment);
        }
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        if (informFragment == null && fragment instanceof MessageInformFragment)
            informFragment = fragment;
        if (couponFragment == null && fragment instanceof MessageCouponFragment)
            couponFragment = fragment;
        super.onAttachFragment(fragment);
    }

    @OnClick({R.id.iv_left, R.id.aty_msg_rbInform, R.id.aty_msg_rbCoupon})

    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.aty_msg_rbInform:
                showFragment(FRAGMENT_INFORM);
                line.setVisibility(View.VISIBLE);
                line2.setVisibility(View.GONE);
                rbInform.setTextColor(this.getResources().getColor(R.color.color_red));
                rbCoupon.setTextColor(this.getResources().getColor(R.color.color_text_title));
                break;
            case R.id.aty_msg_rbCoupon:
                showFragment(FRAGMENT_COUPON);
                line2.setVisibility(View.VISIBLE);
                line.setVisibility(View.GONE);
                rbCoupon.setTextColor(this.getResources().getColor(R.color.color_red));
                rbInform.setTextColor(this.getResources().getColor(R.color.color_text_title));
                break;
        }
    }
}