// Generated code from Butter Knife. Do not modify!
package com.bracode.ui;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.svgouwu.client.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ScanResultActivity_ViewBinding implements Unbinder {
  private ScanResultActivity target;

  private View view2131558635;

  private View view2131558818;

  private View view2131558610;

  @UiThread
  public ScanResultActivity_ViewBinding(ScanResultActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ScanResultActivity_ViewBinding(final ScanResultActivity target, View source) {
    this.target = target;

    View view;
    target.tv_title = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tv_title'", TextView.class);
    view = Utils.findRequiredView(source, R.id.iv_left, "method 'processClick'");
    view2131558635 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_scan_login, "method 'processClick'");
    view2131558818 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_scan_cancel, "method 'processClick'");
    view2131558610 = view;
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
    ScanResultActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tv_title = null;

    view2131558635.setOnClickListener(null);
    view2131558635 = null;
    view2131558818.setOnClickListener(null);
    view2131558818 = null;
    view2131558610.setOnClickListener(null);
    view2131558610 = null;
  }
}
