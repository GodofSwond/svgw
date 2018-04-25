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

public class OrderDetailsActivity_ViewBinding implements Unbinder {
  private OrderDetailsActivity target;

  private View view2131558635;

  private View view2131558751;

  private View view2131558772;

  @UiThread
  public OrderDetailsActivity_ViewBinding(OrderDetailsActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public OrderDetailsActivity_ViewBinding(final OrderDetailsActivity target, View source) {
    this.target = target;

    View view;
    target.tv_title = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tv_title'", TextView.class);
    view = Utils.findRequiredView(source, R.id.iv_left, "field 'iv_left' and method 'processClick'");
    target.iv_left = Utils.castView(view, R.id.iv_left, "field 'iv_left'", ImageView.class);
    view2131558635 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    target.mLoadPage = Utils.findRequiredViewAsType(source, R.id.mLoadPage, "field 'mLoadPage'", LoadPage.class);
    view = Utils.findRequiredView(source, R.id.tv_view_logistics, "field 'tv_view_logistics' and method 'processClick'");
    target.tv_view_logistics = Utils.castView(view, R.id.tv_view_logistics, "field 'tv_view_logistics'", TextView.class);
    view2131558751 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    target.tv_order_desc = Utils.findRequiredViewAsType(source, R.id.tv_order_desc, "field 'tv_order_desc'", TextView.class);
    target.ll_delivery = Utils.findRequiredViewAsType(source, R.id.ll_delivery, "field 'll_delivery'", LinearLayout.class);
    target.tv_delivery_company = Utils.findRequiredViewAsType(source, R.id.tv_delivery_company, "field 'tv_delivery_company'", TextView.class);
    target.tv_delivery_order_no = Utils.findRequiredViewAsType(source, R.id.tv_delivery_order_no, "field 'tv_delivery_order_no'", TextView.class);
    target.tv_consignee = Utils.findRequiredViewAsType(source, R.id.tv_consignee, "field 'tv_consignee'", TextView.class);
    target.tv_phone = Utils.findRequiredViewAsType(source, R.id.tv_phone, "field 'tv_phone'", TextView.class);
    target.tv_address = Utils.findRequiredViewAsType(source, R.id.tv_address, "field 'tv_address'", TextView.class);
    target.tv_subtotal = Utils.findRequiredViewAsType(source, R.id.tv_subtotal, "field 'tv_subtotal'", TextView.class);
    target.tv_goods_count = Utils.findRequiredViewAsType(source, R.id.tv_goods_count, "field 'tv_goods_count'", TextView.class);
    target.tv_voucher = Utils.findRequiredViewAsType(source, R.id.tv_voucher, "field 'tv_voucher'", TextView.class);
    target.tv_delivery_type = Utils.findRequiredViewAsType(source, R.id.tv_delivery_type, "field 'tv_delivery_type'", TextView.class);
    target.tv_payed = Utils.findRequiredViewAsType(source, R.id.tv_payed, "field 'tv_payed'", TextView.class);
    target.tv_postage = Utils.findRequiredViewAsType(source, R.id.tv_postage, "field 'tv_postage'", TextView.class);
    target.ll_container = Utils.findRequiredViewAsType(source, R.id.ll_container, "field 'll_container'", LinearLayout.class);
    target.ll_invoice = Utils.findRequiredViewAsType(source, R.id.ll_invoice, "field 'll_invoice'", LinearLayout.class);
    target.tv_invoice_type = Utils.findRequiredViewAsType(source, R.id.tv_invoice_type, "field 'tv_invoice_type'", TextView.class);
    target.tv_invoice_head = Utils.findRequiredViewAsType(source, R.id.tv_invoice_head, "field 'tv_invoice_head'", TextView.class);
    target.tv_invoice_content = Utils.findRequiredViewAsType(source, R.id.tv_invoice_content, "field 'tv_invoice_content'", TextView.class);
    target.tv_order_no = Utils.findRequiredViewAsType(source, R.id.tv_order_no, "field 'tv_order_no'", TextView.class);
    target.tv_transation_no = Utils.findRequiredViewAsType(source, R.id.tv_transation_no, "field 'tv_transation_no'", TextView.class);
    target.tv_pay_type = Utils.findRequiredViewAsType(source, R.id.tv_pay_type, "field 'tv_pay_type'", TextView.class);
    target.tv_create_time = Utils.findRequiredViewAsType(source, R.id.tv_create_time, "field 'tv_create_time'", TextView.class);
    target.tv_pay_time = Utils.findRequiredViewAsType(source, R.id.tv_pay_time, "field 'tv_pay_time'", TextView.class);
    view = Utils.findRequiredView(source, R.id.tv_operation_left, "field 'tv_operation_left' and method 'processClick'");
    target.tv_operation_left = Utils.castView(view, R.id.tv_operation_left, "field 'tv_operation_left'", TextView.class);
    view2131558772 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    target.tv_operation_right = Utils.findRequiredViewAsType(source, R.id.tv_operation_right, "field 'tv_operation_right'", TextView.class);
    target.rl_voucher = Utils.findRequiredViewAsType(source, R.id.rl_voucher, "field 'rl_voucher'", RelativeLayout.class);
    target.rl_transation_no = Utils.findRequiredViewAsType(source, R.id.rl_transation_no, "field 'rl_transation_no'", RelativeLayout.class);
    target.rl_pay_type = Utils.findRequiredViewAsType(source, R.id.rl_pay_type, "field 'rl_pay_type'", RelativeLayout.class);
    target.rl_pay_time = Utils.findRequiredViewAsType(source, R.id.rl_pay_time, "field 'rl_pay_time'", RelativeLayout.class);
    target.rl_orderd_coupon = Utils.findRequiredViewAsType(source, R.id.rl_orderd_coupon, "field 'rl_orderd_coupon'", RelativeLayout.class);
    target.tv_orderd_coupon = Utils.findRequiredViewAsType(source, R.id.tv_orderd_coupon, "field 'tv_orderd_coupon'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    OrderDetailsActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tv_title = null;
    target.iv_left = null;
    target.mLoadPage = null;
    target.tv_view_logistics = null;
    target.tv_order_desc = null;
    target.ll_delivery = null;
    target.tv_delivery_company = null;
    target.tv_delivery_order_no = null;
    target.tv_consignee = null;
    target.tv_phone = null;
    target.tv_address = null;
    target.tv_subtotal = null;
    target.tv_goods_count = null;
    target.tv_voucher = null;
    target.tv_delivery_type = null;
    target.tv_payed = null;
    target.tv_postage = null;
    target.ll_container = null;
    target.ll_invoice = null;
    target.tv_invoice_type = null;
    target.tv_invoice_head = null;
    target.tv_invoice_content = null;
    target.tv_order_no = null;
    target.tv_transation_no = null;
    target.tv_pay_type = null;
    target.tv_create_time = null;
    target.tv_pay_time = null;
    target.tv_operation_left = null;
    target.tv_operation_right = null;
    target.rl_voucher = null;
    target.rl_transation_no = null;
    target.rl_pay_type = null;
    target.rl_pay_time = null;
    target.rl_orderd_coupon = null;
    target.tv_orderd_coupon = null;

    view2131558635.setOnClickListener(null);
    view2131558635 = null;
    view2131558751.setOnClickListener(null);
    view2131558751 = null;
    view2131558772.setOnClickListener(null);
    view2131558772 = null;
  }
}
