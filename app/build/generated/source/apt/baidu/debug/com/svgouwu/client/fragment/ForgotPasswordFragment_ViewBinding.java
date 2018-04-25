// Generated code from Butter Knife. Do not modify!
package com.svgouwu.client.fragment;

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

public class ForgotPasswordFragment_ViewBinding implements Unbinder {
  private ForgotPasswordFragment target;

  private View view2131558997;

  private View view2131558637;

  private View view2131558999;

  @UiThread
  public ForgotPasswordFragment_ViewBinding(final ForgotPasswordFragment target, View source) {
    this.target = target;

    View view;
    target.etForgotPwdPhone = Utils.findRequiredViewAsType(source, R.id.et_forgot_pwd_phone, "field 'etForgotPwdPhone'", EditText.class);
    view = Utils.findRequiredView(source, R.id.tv_forget_pwd_get_code, "field 'tvForgetPwdGetCode' and method 'processClick'");
    target.tvForgetPwdGetCode = Utils.castView(view, R.id.tv_forget_pwd_get_code, "field 'tvForgetPwdGetCode'", TextView.class);
    view2131558997 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    target.etForgotPwdCode = Utils.findRequiredViewAsType(source, R.id.et_forgot_pwd_code, "field 'etForgotPwdCode'", EditText.class);
    view = Utils.findRequiredView(source, R.id.tv_right, "method 'processClick'");
    view2131558637 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.bt_forget_pwd_ok, "method 'processClick'");
    view2131558999 = view;
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
    ForgotPasswordFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.etForgotPwdPhone = null;
    target.tvForgetPwdGetCode = null;
    target.etForgotPwdCode = null;

    view2131558997.setOnClickListener(null);
    view2131558997 = null;
    view2131558637.setOnClickListener(null);
    view2131558637 = null;
    view2131558999.setOnClickListener(null);
    view2131558999 = null;
  }
}
