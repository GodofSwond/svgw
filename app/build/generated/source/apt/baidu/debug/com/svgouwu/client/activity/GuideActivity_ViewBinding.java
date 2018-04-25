// Generated code from Butter Knife. Do not modify!
package com.svgouwu.client.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.RelativeLayout;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.svgouwu.client.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class GuideActivity_ViewBinding implements Unbinder {
  private GuideActivity target;

  private View view2131558679;

  @UiThread
  public GuideActivity_ViewBinding(GuideActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public GuideActivity_ViewBinding(final GuideActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.activity_guide_ib_start, "field 'start' and method 'processClick'");
    target.start = Utils.castView(view, R.id.activity_guide_ib_start, "field 'start'", RelativeLayout.class);
    view2131558679 = view;
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
    GuideActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.start = null;

    view2131558679.setOnClickListener(null);
    view2131558679 = null;
  }
}
