// Generated code from Butter Knife. Do not modify!
package com.svgouwu.client.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.svgouwu.client.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ApplyCashActivity_ViewBinding implements Unbinder {
  private ApplyCashActivity target;

  private View view2131558599;

  private View view2131558609;

  @UiThread
  public ApplyCashActivity_ViewBinding(ApplyCashActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ApplyCashActivity_ViewBinding(final ApplyCashActivity target, View source) {
    this.target = target;

    View view;
    target.tv_apply_name = Utils.findRequiredViewAsType(source, R.id.tv_apply_name, "field 'tv_apply_name'", TextView.class);
    target.et_apply_pwd = Utils.findRequiredViewAsType(source, R.id.et_apply_pwd, "field 'et_apply_pwd'", EditText.class);
    target.et_apply_apwd = Utils.findRequiredViewAsType(source, R.id.et_apply_apwd, "field 'et_apply_apwd'", EditText.class);
    target.tv_apply_zhuan = Utils.findRequiredViewAsType(source, R.id.tv_apply_zhuan, "field 'tv_apply_zhuan'", TextView.class);
    view = Utils.findRequiredView(source, R.id.iv_cash_left, "method 'processClick'");
    view2131558599 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_apply_sure, "method 'processClick'");
    view2131558609 = view;
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
    ApplyCashActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tv_apply_name = null;
    target.et_apply_pwd = null;
    target.et_apply_apwd = null;
    target.tv_apply_zhuan = null;

    view2131558599.setOnClickListener(null);
    view2131558599 = null;
    view2131558609.setOnClickListener(null);
    view2131558609 = null;
  }
}
