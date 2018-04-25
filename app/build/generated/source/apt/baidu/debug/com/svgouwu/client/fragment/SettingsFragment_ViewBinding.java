// Generated code from Butter Knife. Do not modify!
package com.svgouwu.client.fragment;

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

public class SettingsFragment_ViewBinding implements Unbinder {
  private SettingsFragment target;

  private View view2131559128;

  private View view2131559124;

  private View view2131559126;

  private View view2131559127;

  private View view2131559125;

  @UiThread
  public SettingsFragment_ViewBinding(final SettingsFragment target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.tv_settings_logout, "field 'tvSettingsLogout' and method 'processClick'");
    target.tvSettingsLogout = Utils.castView(view, R.id.tv_settings_logout, "field 'tvSettingsLogout'", TextView.class);
    view2131559128 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_settings_base, "method 'processClick'");
    view2131559124 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_settings_modify_email, "method 'processClick'");
    view2131559126 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_settings_modify_phone, "method 'processClick'");
    view2131559127 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_settings_modify_pwd, "method 'processClick'");
    view2131559125 = view;
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
    SettingsFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvSettingsLogout = null;

    view2131559128.setOnClickListener(null);
    view2131559128 = null;
    view2131559124.setOnClickListener(null);
    view2131559124 = null;
    view2131559126.setOnClickListener(null);
    view2131559126 = null;
    view2131559127.setOnClickListener(null);
    view2131559127 = null;
    view2131559125.setOnClickListener(null);
    view2131559125 = null;
  }
}
