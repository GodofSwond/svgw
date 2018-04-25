// Generated code from Butter Knife. Do not modify!
package com.svgouwu.client.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.svgouwu.client.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AddressActivity_ViewBinding implements Unbinder {
  private AddressActivity target;

  private View view2131558587;

  private View view2131558635;

  private View view2131558637;

  @UiThread
  public AddressActivity_ViewBinding(AddressActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AddressActivity_ViewBinding(final AddressActivity target, View source) {
    this.target = target;

    View view;
    target.etConsignee = Utils.findRequiredViewAsType(source, R.id.et_consignee, "field 'etConsignee'", EditText.class);
    target.etPhone = Utils.findRequiredViewAsType(source, R.id.et_phone, "field 'etPhone'", EditText.class);
    target.tvRegion = Utils.findRequiredViewAsType(source, R.id.tv_region, "field 'tvRegion'", TextView.class);
    view = Utils.findRequiredView(source, R.id.rl_region, "field 'rlRegion' and method 'processClick'");
    target.rlRegion = Utils.castView(view, R.id.rl_region, "field 'rlRegion'", RelativeLayout.class);
    view2131558587 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    target.etZipCode = Utils.findRequiredViewAsType(source, R.id.et_zip_code, "field 'etZipCode'", EditText.class);
    target.etAddress = Utils.findRequiredViewAsType(source, R.id.et_address, "field 'etAddress'", EditText.class);
    target.etName = Utils.findRequiredViewAsType(source, R.id.et_name, "field 'etName'", EditText.class);
    target.etSfzCode = Utils.findRequiredViewAsType(source, R.id.et_sfz_code, "field 'etSfzCode'", EditText.class);
    target.cbDefault = Utils.findRequiredViewAsType(source, R.id.cb_default, "field 'cbDefault'", CheckBox.class);
    view = Utils.findRequiredView(source, R.id.iv_left, "field 'ivLeft' and method 'processClick'");
    target.ivLeft = Utils.castView(view, R.id.iv_left, "field 'ivLeft'", ImageView.class);
    view2131558635 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvTitle'", TextView.class);
    target.ivRight = Utils.findRequiredViewAsType(source, R.id.iv_right, "field 'ivRight'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.tv_right, "field 'tvRight' and method 'processClick'");
    target.tvRight = Utils.castView(view, R.id.tv_right, "field 'tvRight'", TextView.class);
    view2131558637 = view;
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
    AddressActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.etConsignee = null;
    target.etPhone = null;
    target.tvRegion = null;
    target.rlRegion = null;
    target.etZipCode = null;
    target.etAddress = null;
    target.etName = null;
    target.etSfzCode = null;
    target.cbDefault = null;
    target.ivLeft = null;
    target.tvTitle = null;
    target.ivRight = null;
    target.tvRight = null;

    view2131558587.setOnClickListener(null);
    view2131558587 = null;
    view2131558635.setOnClickListener(null);
    view2131558635 = null;
    view2131558637.setOnClickListener(null);
    view2131558637 = null;
  }
}
