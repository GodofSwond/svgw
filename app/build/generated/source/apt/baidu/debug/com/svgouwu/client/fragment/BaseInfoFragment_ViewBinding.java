// Generated code from Butter Knife. Do not modify!
package com.svgouwu.client.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.svgouwu.client.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class BaseInfoFragment_ViewBinding implements Unbinder {
  private BaseInfoFragment target;

  private View view2131558951;

  private View view2131558949;

  @UiThread
  public BaseInfoFragment_ViewBinding(final BaseInfoFragment target, View source) {
    this.target = target;

    View view;
    target.etBaseInfoName = Utils.findRequiredViewAsType(source, R.id.et_base_info_name, "field 'etBaseInfoName'", EditText.class);
    target.tvBaseInfoBirthday = Utils.findRequiredViewAsType(source, R.id.tv_base_info_birthday, "field 'tvBaseInfoBirthday'", TextView.class);
    target.rb_base_info_man = Utils.findRequiredViewAsType(source, R.id.rb_base_info_man, "field 'rb_base_info_man'", RadioButton.class);
    target.rb_base_info_woman = Utils.findRequiredViewAsType(source, R.id.rb_base_info_woman, "field 'rb_base_info_woman'", RadioButton.class);
    view = Utils.findRequiredView(source, R.id.tv_base_info_save, "method 'processClick'");
    view2131558951 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rl_base_info_birthday, "method 'processClick'");
    view2131558949 = view;
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
    BaseInfoFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.etBaseInfoName = null;
    target.tvBaseInfoBirthday = null;
    target.rb_base_info_man = null;
    target.rb_base_info_woman = null;

    view2131558951.setOnClickListener(null);
    view2131558951 = null;
    view2131558949.setOnClickListener(null);
    view2131558949 = null;
  }
}
