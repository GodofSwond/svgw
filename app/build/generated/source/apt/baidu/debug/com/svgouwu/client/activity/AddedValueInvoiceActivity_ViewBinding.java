// Generated code from Butter Knife. Do not modify!
package com.svgouwu.client.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.svgouwu.client.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AddedValueInvoiceActivity_ViewBinding implements Unbinder {
  private AddedValueInvoiceActivity target;

  private View view2131558635;

  private View view2131558585;

  @UiThread
  public AddedValueInvoiceActivity_ViewBinding(AddedValueInvoiceActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AddedValueInvoiceActivity_ViewBinding(final AddedValueInvoiceActivity target,
      View source) {
    this.target = target;

    View view;
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
    target.etCompany = Utils.findRequiredViewAsType(source, R.id.et_company, "field 'etCompany'", EditText.class);
    target.etCode = Utils.findRequiredViewAsType(source, R.id.et_code, "field 'etCode'", EditText.class);
    target.etAddress = Utils.findRequiredViewAsType(source, R.id.et_address, "field 'etAddress'", EditText.class);
    target.etPhone = Utils.findRequiredViewAsType(source, R.id.et_phone, "field 'etPhone'", EditText.class);
    target.etBank = Utils.findRequiredViewAsType(source, R.id.et_bank, "field 'etBank'", EditText.class);
    target.etBankAccount = Utils.findRequiredViewAsType(source, R.id.et_bank_account, "field 'etBankAccount'", EditText.class);
    view = Utils.findRequiredView(source, R.id.btn_submit, "field 'btnSubmit' and method 'processClick'");
    target.btnSubmit = Utils.castView(view, R.id.btn_submit, "field 'btnSubmit'", Button.class);
    view2131558585 = view;
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
    AddedValueInvoiceActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ivLeft = null;
    target.tvTitle = null;
    target.etCompany = null;
    target.etCode = null;
    target.etAddress = null;
    target.etPhone = null;
    target.etBank = null;
    target.etBankAccount = null;
    target.btnSubmit = null;

    view2131558635.setOnClickListener(null);
    view2131558635 = null;
    view2131558585.setOnClickListener(null);
    view2131558585 = null;
  }
}
