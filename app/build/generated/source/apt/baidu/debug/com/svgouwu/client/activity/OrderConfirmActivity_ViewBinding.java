// Generated code from Butter Knife. Do not modify!
package com.svgouwu.client.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.svgouwu.client.R;
import com.svgouwu.client.view.LoadPage;
import java.lang.IllegalStateException;
import java.lang.Override;

public class OrderConfirmActivity_ViewBinding implements Unbinder {
  private OrderConfirmActivity target;

  private View view2131558635;

  private View view2131558736;

  private View view2131558747;

  private View view2131558728;

  private View view2131558738;

  private View view2131558739;

  @UiThread
  public OrderConfirmActivity_ViewBinding(OrderConfirmActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public OrderConfirmActivity_ViewBinding(final OrderConfirmActivity target, View source) {
    this.target = target;

    View view;
    target.tv_orderGold = Utils.findRequiredViewAsType(source, R.id.tv_order_gold, "field 'tv_orderGold'", TextView.class);
    view = Utils.findRequiredView(source, R.id.iv_left, "field 'iv_left' and method 'processClick'");
    target.iv_left = Utils.castView(view, R.id.iv_left, "field 'iv_left'", ImageView.class);
    view2131558635 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    target.tv_title = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tv_title'", TextView.class);
    target.mLoadPage = Utils.findRequiredViewAsType(source, R.id.mLoadPage, "field 'mLoadPage'", LoadPage.class);
    target.tv_consignee = Utils.findRequiredViewAsType(source, R.id.tv_consignee, "field 'tv_consignee'", TextView.class);
    target.tv_phone = Utils.findRequiredViewAsType(source, R.id.tv_phone, "field 'tv_phone'", TextView.class);
    target.tv_address = Utils.findRequiredViewAsType(source, R.id.tv_address, "field 'tv_address'", TextView.class);
    target.ll_container = Utils.findRequiredViewAsType(source, R.id.ll_container, "field 'll_container'", LinearLayout.class);
    target.rl_delivery = Utils.findRequiredViewAsType(source, R.id.rl_delivery, "field 'rl_delivery'", RelativeLayout.class);
    target.tv_delivery_type = Utils.findRequiredViewAsType(source, R.id.tv_delivery_type, "field 'tv_delivery_type'", TextView.class);
    view = Utils.findRequiredView(source, R.id.rl_invoice, "field 'rl_invoice' and method 'processClick'");
    target.rl_invoice = Utils.castView(view, R.id.rl_invoice, "field 'rl_invoice'", RelativeLayout.class);
    view2131558736 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    target.tv_invoice_type = Utils.findRequiredViewAsType(source, R.id.tv_invoice_type, "field 'tv_invoice_type'", TextView.class);
    target.tv_total_price = Utils.findRequiredViewAsType(source, R.id.tv_total_price, "field 'tv_total_price'", TextView.class);
    target.tv_count = Utils.findRequiredViewAsType(source, R.id.tv_count, "field 'tv_count'", TextView.class);
    target.tv_total = Utils.findRequiredViewAsType(source, R.id.tv_total, "field 'tv_total'", TextView.class);
    view = Utils.findRequiredView(source, R.id.tv_submit, "field 'tv_submit' and method 'processClick'");
    target.tv_submit = Utils.castView(view, R.id.tv_submit, "field 'tv_submit'", TextView.class);
    view2131558747 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ll_address, "field 'll_address' and method 'processClick'");
    target.ll_address = Utils.castView(view, R.id.ll_address, "field 'll_address'", LinearLayout.class);
    view2131558728 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_order_coupon, "field 'tv_order_coupon' and method 'processClick'");
    target.tv_order_coupon = Utils.castView(view, R.id.tv_order_coupon, "field 'tv_order_coupon'", TextView.class);
    view2131558738 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    target.rl_order_coupon = Utils.findRequiredViewAsType(source, R.id.rl_order_coupon, "field 'rl_order_coupon'", RelativeLayout.class);
    target.tv_coupon_money = Utils.findRequiredViewAsType(source, R.id.tv_coupon_money, "field 'tv_coupon_money'", TextView.class);
    target.tv_order_all_rebate = Utils.findRequiredViewAsType(source, R.id.tv_order_all_rebate, "field 'tv_order_all_rebate'", TextView.class);
    view = Utils.findRequiredView(source, R.id.rl_order_gold, "method 'processClick'");
    view2131558739 = view;
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
    OrderConfirmActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tv_orderGold = null;
    target.iv_left = null;
    target.tv_title = null;
    target.mLoadPage = null;
    target.tv_consignee = null;
    target.tv_phone = null;
    target.tv_address = null;
    target.ll_container = null;
    target.rl_delivery = null;
    target.tv_delivery_type = null;
    target.rl_invoice = null;
    target.tv_invoice_type = null;
    target.tv_total_price = null;
    target.tv_count = null;
    target.tv_total = null;
    target.tv_submit = null;
    target.ll_address = null;
    target.tv_order_coupon = null;
    target.rl_order_coupon = null;
    target.tv_coupon_money = null;
    target.tv_order_all_rebate = null;

    view2131558635.setOnClickListener(null);
    view2131558635 = null;
    view2131558736.setOnClickListener(null);
    view2131558736 = null;
    view2131558747.setOnClickListener(null);
    view2131558747 = null;
    view2131558728.setOnClickListener(null);
    view2131558728 = null;
    view2131558738.setOnClickListener(null);
    view2131558738 = null;
    view2131558739.setOnClickListener(null);
    view2131558739 = null;
  }
}
