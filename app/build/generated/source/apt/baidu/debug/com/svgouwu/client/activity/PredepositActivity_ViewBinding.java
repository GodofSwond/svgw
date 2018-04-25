// Generated code from Butter Knife. Do not modify!
package com.svgouwu.client.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.svgouwu.client.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PredepositActivity_ViewBinding implements Unbinder {
  private PredepositActivity target;

  private View view2131558802;

  @UiThread
  public PredepositActivity_ViewBinding(PredepositActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public PredepositActivity_ViewBinding(final PredepositActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.iv_pre_left, "method 'processClick'");
    view2131558802 = view;
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
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    target = null;


    view2131558802.setOnClickListener(null);
    view2131558802 = null;
  }
}
