// Generated code from Butter Knife. Do not modify!
package com.svgouwu.client.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.svgouwu.client.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ForgotPasswordSetFragment_ViewBinding implements Unbinder {
  private ForgotPasswordSetFragment target;

  private View view2131558637;

  private View view2131559002;

  @UiThread
  public ForgotPasswordSetFragment_ViewBinding(final ForgotPasswordSetFragment target,
      View source) {
    this.target = target;

    View view;
    target.etForgotPwdSetPwd = Utils.findRequiredViewAsType(source, R.id.et_forgot_pwd_set_pwd, "field 'etForgotPwdSetPwd'", EditText.class);
    target.etForgetPwdSetRePwd = Utils.findRequiredViewAsType(source, R.id.et_forgot_pwd_set_re_pwd, "field 'etForgetPwdSetRePwd'", EditText.class);
    view = Utils.findRequiredView(source, R.id.tv_right, "method 'processClick'");
    view2131558637 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.bt_forget_pwd_set_ok, "method 'processClick'");
    view2131559002 = view;
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
    ForgotPasswordSetFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.etForgotPwdSetPwd = null;
    target.etForgetPwdSetRePwd = null;

    view2131558637.setOnClickListener(null);
    view2131558637 = null;
    view2131559002.setOnClickListener(null);
    view2131559002 = null;
  }
}
