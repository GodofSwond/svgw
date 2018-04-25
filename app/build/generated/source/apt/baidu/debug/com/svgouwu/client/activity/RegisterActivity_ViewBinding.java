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

public class RegisterActivity_ViewBinding implements Unbinder {
  private RegisterActivity target;

  private View view2131558806;

  private View view2131558637;

  private View view2131558698;

  private View view2131558699;

  private View view2131558700;

  private View view2131558814;

  @UiThread
  public RegisterActivity_ViewBinding(RegisterActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public RegisterActivity_ViewBinding(final RegisterActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.tv_register_get_code, "field 'tvRegisterGetCode' and method 'processClick'");
    target.tvRegisterGetCode = Utils.castView(view, R.id.tv_register_get_code, "field 'tvRegisterGetCode'", TextView.class);
    view2131558806 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    target.etRegisterPhone = Utils.findRequiredViewAsType(source, R.id.et_register_phone, "field 'etRegisterPhone'", EditText.class);
    target.etRegisterCode = Utils.findRequiredViewAsType(source, R.id.et_register_code, "field 'etRegisterCode'", EditText.class);
    target.etRegisterPwd = Utils.findRequiredViewAsType(source, R.id.et_register_pwd, "field 'etRegisterPwd'", EditText.class);
    target.etRegisterPwdAgain = Utils.findRequiredViewAsType(source, R.id.et_register_pwd_again, "field 'etRegisterPwdAgain'", EditText.class);
    target.et_register_excode = Utils.findRequiredViewAsType(source, R.id.et_register_excode, "field 'et_register_excode'", EditText.class);
    view = Utils.findRequiredView(source, R.id.tv_right, "method 'processClick'");
    view2131558637 = view;
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
    view = Utils.findRequiredView(source, R.id.bt_register_ok, "method 'processClick'");
    view2131558814 = view;
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
    RegisterActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvRegisterGetCode = null;
    target.etRegisterPhone = null;
    target.etRegisterCode = null;
    target.etRegisterPwd = null;
    target.etRegisterPwdAgain = null;
    target.et_register_excode = null;

    view2131558806.setOnClickListener(null);
    view2131558806 = null;
    view2131558637.setOnClickListener(null);
    view2131558637 = null;
    view2131558698.setOnClickListener(null);
    view2131558698 = null;
    view2131558699.setOnClickListener(null);
    view2131558699 = null;
    view2131558700.setOnClickListener(null);
    view2131558700 = null;
    view2131558814.setOnClickListener(null);
    view2131558814 = null;
  }
}
