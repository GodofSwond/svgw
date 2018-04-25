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

public class ModifyPhoneFragment_ViewBinding implements Unbinder {
  private ModifyPhoneFragment target;

  private View view2131559072;

  private View view2131559073;

  @UiThread
  public ModifyPhoneFragment_ViewBinding(final ModifyPhoneFragment target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.tv_modify_phone_get_code, "field 'tv_modify_phone_get_code' and method 'processClick'");
    target.tv_modify_phone_get_code = Utils.castView(view, R.id.tv_modify_phone_get_code, "field 'tv_modify_phone_get_code'", TextView.class);
    view2131559072 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    target.etModifyPhonePwd = Utils.findRequiredViewAsType(source, R.id.et_modify_phone_pwd, "field 'etModifyPhonePwd'", EditText.class);
    target.etModifyPhoneMobile = Utils.findRequiredViewAsType(source, R.id.et_modify_phone_mobile, "field 'etModifyPhoneMobile'", EditText.class);
    target.et_modify_phone_code = Utils.findRequiredViewAsType(source, R.id.et_modify_phone_code, "field 'et_modify_phone_code'", EditText.class);
    view = Utils.findRequiredView(source, R.id.tv_modify_phone_save, "method 'processClick'");
    view2131559073 = view;
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
    ModifyPhoneFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tv_modify_phone_get_code = null;
    target.etModifyPhonePwd = null;
    target.etModifyPhoneMobile = null;
    target.et_modify_phone_code = null;

    view2131559072.setOnClickListener(null);
    view2131559072 = null;
    view2131559073.setOnClickListener(null);
    view2131559073 = null;
  }
}
