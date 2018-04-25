// Generated code from Butter Knife. Do not modify!
package com.svgouwu.client.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.svgouwu.client.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CouponListActivity_ViewBinding implements Unbinder {
  private CouponListActivity target;

  private View view2131558637;

  private View view2131558635;

  @UiThread
  public CouponListActivity_ViewBinding(CouponListActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public CouponListActivity_ViewBinding(final CouponListActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.tv_right, "field 'tv_right' and method 'processClick'");
    target.tv_right = Utils.castView(view, R.id.tv_right, "field 'tv_right'", TextView.class);
    view2131558637 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    target.tv_coupons_center = Utils.findRequiredViewAsType(source, R.id.tv_coupons_center, "field 'tv_coupons_center'", TextView.class);
    target.tv_coupons_exchange = Utils.findRequiredViewAsType(source, R.id.tv_coupons_exchange, "field 'tv_coupons_exchange'", TextView.class);
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvTitle'", TextView.class);
    target.mTabLayout = Utils.findRequiredViewAsType(source, R.id.fragment_coupon_list_mTabLayout, "field 'mTabLayout'", TabLayout.class);
    target.mXRecyclerView = Utils.findRequiredViewAsType(source, R.id.fragment_coupon_list_mXRecyclerView, "field 'mXRecyclerView'", XRecyclerView.class);
    view = Utils.findRequiredView(source, R.id.iv_left, "method 'processClick'");
    view2131558635 = view;
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
    CouponListActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tv_right = null;
    target.tv_coupons_center = null;
    target.tv_coupons_exchange = null;
    target.tvTitle = null;
    target.mTabLayout = null;
    target.mXRecyclerView = null;

    view2131558637.setOnClickListener(null);
    view2131558637 = null;
    view2131558635.setOnClickListener(null);
    view2131558635 = null;
  }
}
