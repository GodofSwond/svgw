// Generated code from Butter Knife. Do not modify!
package com.svgouwu.client.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.svgouwu.client.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class OrderCommentFragment_ViewBinding implements Unbinder {
  private OrderCommentFragment target;

  private View view2131559088;

  @UiThread
  public OrderCommentFragment_ViewBinding(final OrderCommentFragment target, View source) {
    this.target = target;

    View view;
    target.ll_order_comment_goods = Utils.findRequiredViewAsType(source, R.id.ll_order_comment_goods, "field 'll_order_comment_goods'", LinearLayout.class);
    target.rba_order_comment_pack = Utils.findRequiredViewAsType(source, R.id.rba_order_comment_pack, "field 'rba_order_comment_pack'", RatingBar.class);
    target.rba_order_comment_speed = Utils.findRequiredViewAsType(source, R.id.rba_order_comment_speed, "field 'rba_order_comment_speed'", RatingBar.class);
    target.rba_order_comment_attr = Utils.findRequiredViewAsType(source, R.id.rba_order_comment_attr, "field 'rba_order_comment_attr'", RatingBar.class);
    view = Utils.findRequiredView(source, R.id.iv_order_comment_submit, "method 'processClick'");
    view2131559088 = view;
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
    OrderCommentFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ll_order_comment_goods = null;
    target.rba_order_comment_pack = null;
    target.rba_order_comment_speed = null;
    target.rba_order_comment_attr = null;

    view2131559088.setOnClickListener(null);
    view2131559088 = null;
  }
}
