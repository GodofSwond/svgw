// Generated code from Butter Knife. Do not modify!
package com.svgouwu.client.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.svgouwu.client.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PayCallBackActivity_ViewBinding implements Unbinder {
  private PayCallBackActivity target;

  private View view2131558635;

  @UiThread
  public PayCallBackActivity_ViewBinding(PayCallBackActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public PayCallBackActivity_ViewBinding(final PayCallBackActivity target, View source) {
    this.target = target;

    View view;
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
    target.tv_order_status = Utils.findRequiredViewAsType(source, R.id.tv_order_status, "field 'tv_order_status'", TextView.class);
    target.iv_pay_status = Utils.findRequiredViewAsType(source, R.id.iv_pay_status, "field 'iv_pay_status'", ImageView.class);
    target.rl_ps_coupon = Utils.findRequiredViewAsType(source, R.id.rl_ps_coupon, "field 'rl_ps_coupon'", RelativeLayout.class);
    target.tv_ps_cp_title = Utils.findRequiredViewAsType(source, R.id.tv_ps_cp_title, "field 'tv_ps_cp_title'", TextView.class);
    target.tv_sp_cp_content = Utils.findRequiredViewAsType(source, R.id.tv_sp_cp_content, "field 'tv_sp_cp_content'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PayCallBackActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.iv_left = null;
    target.tv_title = null;
    target.tv_order_status = null;
    target.iv_pay_status = null;
    target.rl_ps_coupon = null;
    target.tv_ps_cp_title = null;
    target.tv_sp_cp_content = null;

    view2131558635.setOnClickListener(null);
    view2131558635 = null;
  }
}
