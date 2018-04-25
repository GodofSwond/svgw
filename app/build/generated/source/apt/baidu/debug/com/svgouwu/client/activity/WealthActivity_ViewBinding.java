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

public class WealthActivity_ViewBinding implements Unbinder {
  private WealthActivity target;

  private View view2131558833;

  private View view2131558838;

  private View view2131558843;

  private View view2131558848;

  @UiThread
  public WealthActivity_ViewBinding(WealthActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public WealthActivity_ViewBinding(final WealthActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.fragment_money_rebate_rl, "method 'processClick'");
    view2131558833 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.fragment_money_look_rl, "method 'processClick'");
    view2131558838 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.fragment_money_apply_rl, "method 'processClick'");
    view2131558843 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.fragment_money_notes_rl, "method 'processClick'");
    view2131558848 = view;
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


    view2131558833.setOnClickListener(null);
    view2131558833 = null;
    view2131558838.setOnClickListener(null);
    view2131558838 = null;
    view2131558843.setOnClickListener(null);
    view2131558843 = null;
    view2131558848.setOnClickListener(null);
    view2131558848 = null;
  }
}
