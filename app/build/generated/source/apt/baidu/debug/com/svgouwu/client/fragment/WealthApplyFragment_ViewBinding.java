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

public class WealthApplyFragment_ViewBinding implements Unbinder {
  private WealthApplyFragment target;

  private View view2131559147;

  @UiThread
  public WealthApplyFragment_ViewBinding(final WealthApplyFragment target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.frg_money_apply_tvSubmit, "field 'tvSubmit' and method 'processClick'");
    target.tvSubmit = Utils.castView(view, R.id.frg_money_apply_tvSubmit, "field 'tvSubmit'", TextView.class);
    view2131559147 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    target.etName = Utils.findRequiredViewAsType(source, R.id.frg_money_apply_etName, "field 'etName'", EditText.class);
    target.etBank = Utils.findRequiredViewAsType(source, R.id.frg_money_apply_etBank, "field 'etBank'", EditText.class);
    target.tvFee = Utils.findRequiredViewAsType(source, R.id.frg_money_apply_etFee, "field 'tvFee'", TextView.class);
    target.tvMoney = Utils.findRequiredViewAsType(source, R.id.frg_money_apply_etMoney, "field 'tvMoney'", TextView.class);
    target.etCard = Utils.findRequiredViewAsType(source, R.id.frg_money_apply_etCard, "field 'etCard'", EditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    WealthApplyFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvSubmit = null;
    target.etName = null;
    target.etBank = null;
    target.tvFee = null;
    target.tvMoney = null;
    target.etCard = null;

    view2131559147.setOnClickListener(null);
    view2131559147 = null;
  }
}
