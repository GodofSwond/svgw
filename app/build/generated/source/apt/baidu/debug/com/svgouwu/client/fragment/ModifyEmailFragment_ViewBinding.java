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

public class ModifyEmailFragment_ViewBinding implements Unbinder {
  private ModifyEmailFragment target;

  private View view2131558951;

  @UiThread
  public ModifyEmailFragment_ViewBinding(final ModifyEmailFragment target, View source) {
    this.target = target;

    View view;
    target.etModifyEmailPwd = Utils.findRequiredViewAsType(source, R.id.et_modify_email_pwd, "field 'etModifyEmailPwd'", EditText.class);
    target.etModifyEmailEmail = Utils.findRequiredViewAsType(source, R.id.et_modify_email_email, "field 'etModifyEmailEmail'", EditText.class);
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
    ModifyEmailFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.etModifyEmailPwd = null;
    target.etModifyEmailEmail = null;

    view2131558951.setOnClickListener(null);
    view2131558951 = null;
  }
}
