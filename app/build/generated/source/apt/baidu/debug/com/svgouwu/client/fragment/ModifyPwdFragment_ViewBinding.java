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

public class ModifyPwdFragment_ViewBinding implements Unbinder {
  private ModifyPwdFragment target;

  private View view2131558951;

  @UiThread
  public ModifyPwdFragment_ViewBinding(final ModifyPwdFragment target, View source) {
    this.target = target;

    View view;
    target.etModifyPwdCur = Utils.findRequiredViewAsType(source, R.id.et_modify_pwd_cur, "field 'etModifyPwdCur'", EditText.class);
    target.etModifyPwdNew = Utils.findRequiredViewAsType(source, R.id.et_modify_pwd_new, "field 'etModifyPwdNew'", EditText.class);
    target.etModifyPwdReNew = Utils.findRequiredViewAsType(source, R.id.et_modify_pwd_re_new, "field 'etModifyPwdReNew'", EditText.class);
    view = Utils.findRequiredView(source, R.id.tv_base_info_save, "method 'processClick'");
    view2131558951 = view;
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
    ModifyPwdFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.etModifyPwdCur = null;
    target.etModifyPwdNew = null;
    target.etModifyPwdReNew = null;

    view2131558951.setOnClickListener(null);
    view2131558951 = null;
  }
}
