// Generated code from Butter Knife. Do not modify!
package com.svgouwu.client.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.svgouwu.client.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MessageActivity_ViewBinding implements Unbinder {
  private MessageActivity target;

  private View view2131558709;

  private View view2131558710;

  private View view2131558635;

  @UiThread
  public MessageActivity_ViewBinding(MessageActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MessageActivity_ViewBinding(final MessageActivity target, View source) {
    this.target = target;

    View view;
    target.tv_title = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tv_title'", TextView.class);
    target.line = Utils.findRequiredViewAsType(source, R.id.iv_bottom_line, "field 'line'", ImageView.class);
    target.line2 = Utils.findRequiredViewAsType(source, R.id.iv_bottom_line2, "field 'line2'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.aty_msg_rbInform, "field 'rbInform' and method 'processClick'");
    target.rbInform = Utils.castView(view, R.id.aty_msg_rbInform, "field 'rbInform'", RadioButton.class);
    view2131558709 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.aty_msg_rbCoupon, "field 'rbCoupon' and method 'processClick'");
    target.rbCoupon = Utils.castView(view, R.id.aty_msg_rbCoupon, "field 'rbCoupon'", RadioButton.class);
    view2131558710 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
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
    MessageActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tv_title = null;
    target.line = null;
    target.line2 = null;
    target.rbInform = null;
    target.rbCoupon = null;

    view2131558709.setOnClickListener(null);
    view2131558709 = null;
    view2131558710.setOnClickListener(null);
    view2131558710 = null;
    view2131558635.setOnClickListener(null);
    view2131558635 = null;
  }
}
