// Generated code from Butter Knife. Do not modify!
package com.svgouwu.client.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.svgouwu.client.R;
import com.svgouwu.client.view.LoadPage;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MyWalletActivity_ViewBinding implements Unbinder {
  private MyWalletActivity target;

  private View view2131558716;

  private View view2131558721;

  private View view2131558722;

  private View view2131558723;

  private View view2131558724;

  @UiThread
  public MyWalletActivity_ViewBinding(MyWalletActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MyWalletActivity_ViewBinding(final MyWalletActivity target, View source) {
    this.target = target;

    View view;
    target.mLoadPage_mywt = Utils.findRequiredViewAsType(source, R.id.mLoadPage_mywt, "field 'mLoadPage_mywt'", LoadPage.class);
    target.tv_mywt_pre_money = Utils.findRequiredViewAsType(source, R.id.tv_mywt_pre_money, "field 'tv_mywt_pre_money'", TextView.class);
    target.tv_mywt_coupon_num = Utils.findRequiredViewAsType(source, R.id.tv_mywt_coupon_num, "field 'tv_mywt_coupon_num'", TextView.class);
    target.tv_mywt_coin_num = Utils.findRequiredViewAsType(source, R.id.tv_mywt_coin_num, "field 'tv_mywt_coin_num'", TextView.class);
    target.rv_mywt = Utils.findRequiredViewAsType(source, R.id.rv_mywt, "field 'rv_mywt'", XRecyclerView.class);
    view = Utils.findRequiredView(source, R.id.iv_mywallet_back, "method 'processClick'");
    view2131558716 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_mywt_coupon, "method 'processClick'");
    view2131558721 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_mywt_wealth, "method 'processClick'");
    view2131558722 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_mywt_coin, "method 'processClick'");
    view2131558723 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_mywt_Recharge, "method 'processClick'");
    view2131558724 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    MyWalletActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLoadPage_mywt = null;
    target.tv_mywt_pre_money = null;
    target.tv_mywt_coupon_num = null;
    target.tv_mywt_coin_num = null;
    target.rv_mywt = null;

    view2131558716.setOnClickListener(null);
    view2131558716 = null;
    view2131558721.setOnClickListener(null);
    view2131558721 = null;
    view2131558722.setOnClickListener(null);
    view2131558722 = null;
    view2131558723.setOnClickListener(null);
    view2131558723 = null;
    view2131558724.setOnClickListener(null);
    view2131558724 = null;
  }
}
