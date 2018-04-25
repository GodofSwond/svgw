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

public class BindPhoneFragment_ViewBinding implements Unbinder {
  private BindPhoneFragment target;

  private View view2131558956;

  private View view2131558959;

  @UiThread
  public BindPhoneFragment_ViewBinding(final BindPhoneFragment target, View source) {
    this.target = target;

    View view;
    target.et_bind_phone_mobile = Utils.findRequiredViewAsType(source, R.id.et_bind_phone_mobile, "field 'et_bind_phone_mobile'", EditText.class);
    target.et_bind_phone_code = Utils.findRequiredViewAsType(source, R.id.et_bind_phone_code, "field 'et_bind_phone_code'", EditText.class);
    view = Utils.findRequiredView(source, R.id.tv_bind_phone_get_code, "field 'tv_bind_phone_get_code' and method 'processClick'");
    target.tv_bind_phone_get_code = Utils.castView(view, R.id.tv_bind_phone_get_code, "field 'tv_bind_phone_get_code'", TextView.class);
    view2131558956 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    target.et_bindphone_excode = Utils.findRequiredViewAsType(source, R.id.et_bindphone_excode, "field 'et_bindphone_excode'", EditText.class);
    view = Utils.findRequiredView(source, R.id.tv_bind_phone_save, "method 'processClick'");
    view2131558959 = view;
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
    BindPhoneFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.et_bind_phone_mobile = null;
    target.et_bind_phone_code = null;
    target.tv_bind_phone_get_code = null;
    target.et_bindphone_excode = null;

    view2131558956.setOnClickListener(null);
    view2131558956 = null;
    view2131558959.setOnClickListener(null);
    view2131558959 = null;
  }
}
