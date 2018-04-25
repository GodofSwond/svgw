// Generated code from Butter Knife. Do not modify!
package com.svgouwu.client.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.svgouwu.client.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MessageSettingFragment_ViewBinding implements Unbinder {
  private MessageSettingFragment target;

  private View view2131559049;

  private View view2131559051;

  private View view2131559053;

  private View view2131558635;

  @UiThread
  public MessageSettingFragment_ViewBinding(final MessageSettingFragment target, View source) {
    this.target = target;

    View view;
    target.tv_title = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tv_title'", TextView.class);
    view = Utils.findRequiredView(source, R.id.frg_msg_setting_ivInform, "field 'ivInform' and method 'processClick'");
    target.ivInform = Utils.castView(view, R.id.frg_msg_setting_ivInform, "field 'ivInform'", ImageView.class);
    view2131559049 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.frg_msg_setting_ivCoupon, "field 'ivCoupon' and method 'processClick'");
    target.ivCoupon = Utils.castView(view, R.id.frg_msg_setting_ivCoupon, "field 'ivCoupon'", ImageView.class);
    view2131559051 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.frg_msg_setting_tvClear, "field 'tvClear' and method 'processClick'");
    target.tvClear = Utils.castView(view, R.id.frg_msg_setting_tvClear, "field 'tvClear'", TextView.class);
    view2131559053 = view;
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
    MessageSettingFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tv_title = null;
    target.ivInform = null;
    target.ivCoupon = null;
    target.tvClear = null;

    view2131559049.setOnClickListener(null);
    view2131559049 = null;
    view2131559051.setOnClickListener(null);
    view2131559051 = null;
    view2131559053.setOnClickListener(null);
    view2131559053 = null;
    view2131558635.setOnClickListener(null);
    view2131558635 = null;
  }
}
