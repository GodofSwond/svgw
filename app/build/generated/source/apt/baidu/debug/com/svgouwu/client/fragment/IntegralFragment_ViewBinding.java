// Generated code from Butter Knife. Do not modify!
package com.svgouwu.client.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.svgouwu.client.R;
import com.svgouwu.client.view.XListView.XListView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class IntegralFragment_ViewBinding implements Unbinder {
  private IntegralFragment target;

  private View view2131559034;

  private View view2131559035;

  @UiThread
  public IntegralFragment_ViewBinding(final IntegralFragment target, View source) {
    this.target = target;

    View view;
    target.xlv_integral = Utils.findRequiredViewAsType(source, R.id.xlv_integral, "field 'xlv_integral'", XListView.class);
    view = Utils.findRequiredView(source, R.id.ll_integral_plus, "method 'processClick'");
    view2131559034 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ll_integral_minus, "method 'processClick'");
    view2131559035 = view;
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
    IntegralFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.xlv_integral = null;

    view2131559034.setOnClickListener(null);
    view2131559034 = null;
    view2131559035.setOnClickListener(null);
    view2131559035 = null;
  }
}
