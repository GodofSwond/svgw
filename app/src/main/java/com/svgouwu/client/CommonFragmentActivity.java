package com.svgouwu.client;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.svgouwu.client.fragment.BaseInfoFragment;
import com.svgouwu.client.fragment.BindPhoneFragment;
import com.svgouwu.client.fragment.CouponsUseFragment;
import com.svgouwu.client.fragment.DetailsFragment;
import com.svgouwu.client.fragment.DoRuleFragment;
import com.svgouwu.client.fragment.ForgotPasswordFragment;
import com.svgouwu.client.fragment.ForgotPasswordSetFragment;
import com.svgouwu.client.fragment.GoodsFavoriteFragment;
import com.svgouwu.client.fragment.IntegralFragment;
import com.svgouwu.client.fragment.MessageSettingFragment;
import com.svgouwu.client.fragment.MessageStoreFragment;
import com.svgouwu.client.fragment.ModifyEmailFragment;
import com.svgouwu.client.fragment.ModifyPhoneFragment;
import com.svgouwu.client.fragment.ModifyPwdFragment;
import com.svgouwu.client.fragment.WealthApplyFragment;
import com.svgouwu.client.fragment.WealthLookFragment;
import com.svgouwu.client.fragment.WealthNotesFragment;
import com.svgouwu.client.fragment.WealthRebateFragment;
import com.svgouwu.client.fragment.OrderCommentFragment;
import com.svgouwu.client.fragment.OrderCommentGoodsFragment;
import com.svgouwu.client.fragment.SearchFragment;
import com.svgouwu.client.fragment.SettingsFragment;
import com.svgouwu.client.fragment.SpreedFragment;
import com.svgouwu.client.fragment.UseCouponsFragment;

/**
 * 单个fragment页面 可以统一走这里
 * Created by admin on 2017/3/28.
 * Email 530274554@qq.com
 */

public class CommonFragmentActivity extends BaseActivity {
    public static final String TARGET = "target";

    public static final int FRAGMENT_SETTINGS = 1; //设置
    public static final int FRAGMENT_SETTINGS_MODIFY_EMAIL = 2; //修改邮箱
    public static final int FRAGMENT_SETTINGS_MODIFY_PHONE = 3; // 修改手机
    public static final int FRAGMENT_SETTINGS_MODIFY_PWD = 4; //修改密码
    public static final int FRAGMENT_SETTINGS_BASE_INFO = 5; //基本信息
    public static final int FRAGMENT_FAVORITE_GOODS = 6; //商品收藏
    public static final int FRAGMENT_INTEGRAL = 7; //会员积分
    public static final int FRAGMENT_FORGOT_PWD = 8; //忘记密码
    public static final int FRAGMENT_FORGOT_PWD_SET = 9; //忘记密码 设置密码
    public static final int FRAGMENT_BIND_PHONE = 10; //绑定手机号
    public static final int FRAGMENT_SEARCH = 11; //搜索
    public static final int FRAGMENT_ORDER_COMMENT = 12; //订单评价
    public static final int FRAGMENT_ORDER_COMMENT_GOODS = 13; //商品评价
    public static final int FRAGMENT_SPREED = 14; //优惠大礼包
    public static final int FRAGMENT_DO_RULE = 15; //使用规则
    public static final int FRAGMENT_USE_DIS = 16; //优惠券使用
    public static final int FRAGMENT_MYHOME = 20;
    public static final int FRAGMENT_USE_COUPONS = 21;
    public static final int FRAGMENT_USE = 22; //使用优惠券
    public static final int FRAGMENT_MESSAGE_SETTING = 24; //消息设置
    public static final int FRAGMENT_STORE = 25; //店铺
    public static final int FRAGMENT_REBATE = 26; //我的返利
    public static final int FRAGMENT_LOOK = 27; //查看佣金
    public static final int FRAGMENT_APPLY = 28; //提现申请
    public static final int FRAGMENT_NOTES = 29; //提现记录

    private FragmentManager fragmentManager;

    @Override
    protected int getContentView() {
        return R.layout.activity_common_fragment;
    }

    @Override
    public void initViews() {
        fragmentManager = getSupportFragmentManager();
        int target = getIntent().getIntExtra(TARGET, -1);

        if (target < 0) finish();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragment = null;
        switch (target) {
            case FRAGMENT_SETTINGS:
                fragment = new SettingsFragment();
                break;
            case FRAGMENT_SETTINGS_MODIFY_PHONE:
                fragment = new ModifyPhoneFragment();
                break;
            case FRAGMENT_SETTINGS_MODIFY_PWD:
                fragment = new ModifyPwdFragment();
                break;
            case FRAGMENT_SETTINGS_MODIFY_EMAIL:
                fragment = new ModifyEmailFragment();
                break;
            case FRAGMENT_SETTINGS_BASE_INFO:
                fragment = new BaseInfoFragment();
                break;
            case FRAGMENT_FAVORITE_GOODS:
                fragment = new GoodsFavoriteFragment();
                break;
            case FRAGMENT_INTEGRAL:
                fragment = new IntegralFragment();
                break;
            case FRAGMENT_FORGOT_PWD:
                fragment = new ForgotPasswordFragment();
                break;
            case FRAGMENT_FORGOT_PWD_SET:
                fragment = new ForgotPasswordSetFragment();
                break;
            case FRAGMENT_BIND_PHONE:
                fragment = new BindPhoneFragment();
                break;
            case FRAGMENT_SEARCH:
                fragment = new SearchFragment();
                break;
            case FRAGMENT_ORDER_COMMENT:
                fragment = new OrderCommentFragment();
                break;
            case FRAGMENT_ORDER_COMMENT_GOODS:
                fragment = new OrderCommentGoodsFragment();
                break;
            case FRAGMENT_SPREED:
                fragment = new SpreedFragment();
                break;
            case FRAGMENT_DO_RULE:
                fragment = new DoRuleFragment();
                break;
            case FRAGMENT_USE_DIS:
                fragment = new CouponsUseFragment();
                break;
            case FRAGMENT_MYHOME:
                //            fragment = new Home2Fragment();
                break;
            case FRAGMENT_USE_COUPONS:
                fragment = new UseCouponsFragment();
                break;
            case FRAGMENT_USE:
                fragment = new DetailsFragment();
                break;
            case FRAGMENT_MESSAGE_SETTING:
                fragment = new MessageSettingFragment();
                break;
            case FRAGMENT_STORE:
                fragment = new MessageStoreFragment();
                break;
            case FRAGMENT_REBATE:
                fragment = new WealthRebateFragment();
                break;
            case FRAGMENT_LOOK:
                fragment = new WealthLookFragment();
                break;
            case FRAGMENT_APPLY:
                fragment = new WealthApplyFragment();
                break;
            case FRAGMENT_NOTES:
                fragment = new WealthNotesFragment();
                break;
        }
        transaction.add(R.id.fl_common_fragment, fragment);
        transaction.commit();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View view) {

    }

    @Override
    public void onClick(View view) {

    }
}
