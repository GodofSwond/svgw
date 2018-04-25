// Generated code from Butter Knife. Do not modify!
package com.svgouwu.client.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.svgouwu.client.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LoginActivity_ViewBinding implements Unbinder {
  private LoginActivity target;

  private View view2131558637;

  private View view2131558695;

  private View view2131558696;

  private View view2131558698;

  private View view2131558699;

  private View view2131558700;

  private View view2131558694;

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginActivity_ViewBinding(final LoginActivity target, View source) {
    this.target = target;

    View view;
    target.etLoginName = Utils.findRequiredViewAsType(source, R.id.et_login_name, "field 'etLoginName'", EditText.class);
    target.etLoginPwd = Utils.findRequiredViewAsType(source, R.id.et_login_pwd, "field 'etLoginPwd'", EditText.class);
    target.cbLoginSave = Utils.findRequiredViewAsType(source, R.id.cb_login_save, "field 'cbLoginSave'", CheckBox.class);
    view = Utils.findRequiredView(source, R.id.tv_right, "method 'processClick'");
    view2131558637 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_login_register_2, "method 'processClick'");
    view2131558695 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_login_find_pwd, "method 'processClick'");
    view2131558696 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.iv_login_wx, "method 'processClick'");
    view2131558698 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.iv_login_wb, "method 'processClick'");
    view2131558699 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.iv_login_qq, "method 'processClick'");
    view2131558700 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.bt_login_ok, "method 'processClick'");
    view2131558694 = view;
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
    LoginActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.etLoginName = null;
    target.etLoginPwd = null;
    target.cbLoginSave = null;

    view2131558637.setOnClickListener(null);
    view2131558637 = null;
    view2131558695.setOnClickListener(null);
    view2131558695 = null;
    view2131558696.setOnClickListener(null);
    view2131558696 = null;
    view2131558698.setOnClickListener(null);
    view2131558698 = null;
    view2131558699.setOnClickListener(null);
    view2131558699 = null;
    view2131558700.setOnClickListener(null);
    view2131558700 = null;
    view2131558694.setOnClickListener(null);
    view2131558694 = null;
  }
}
