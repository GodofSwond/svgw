// Generated code from Butter Knife. Do not modify!
package com.svgouwu.client.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.svgouwu.client.R;
import com.svgouwu.client.view.LoadPage;
import com.svgouwu.client.view.MyRadioGroup;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PayActivity_ViewBinding implements Unbinder {
  private PayActivity target;

  private View view2131558635;

  private View view2131558791;

  private View view2131558793;

  private View view2131558788;

  private View view2131558795;

  @UiThread
  public PayActivity_ViewBinding(PayActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public PayActivity_ViewBinding(final PayActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.iv_left, "field 'iv_left' and method 'processClick'");
    target.iv_left = Utils.castView(view, R.id.iv_left, "field 'iv_left'", ImageView.class);
    view2131558635 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    target.tv_title = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tv_title'", TextView.class);
    target.rg_payment = Utils.findRequiredViewAsType(source, R.id.rg_payment, "field 'rg_payment'", MyRadioGroup.class);
    view = Utils.findRequiredView(source, R.id.rl_ali, "field 'rl_ali' and method 'processClick'");
    target.rl_ali = Utils.castView(view, R.id.rl_ali, "field 'rl_ali'", RelativeLayout.class);
    view2131558791 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    target.rl_wx = Utils.findRequiredViewAsType(source, R.id.rl_wx, "field 'rl_wx'", RelativeLayout.class);
    target.tv_money = Utils.findRequiredViewAsType(source, R.id.tv_money, "field 'tv_money'", TextView.class);
    target.tv_order_desc = Utils.findRequiredViewAsType(source, R.id.tv_order_desc, "field 'tv_order_desc'", TextView.class);
    view = Utils.findRequiredView(source, R.id.rb_ali, "field 'rb_ali' and method 'processClick'");
    target.rb_ali = Utils.castView(view, R.id.rb_ali, "field 'rb_ali'", RadioButton.class);
    view2131558793 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rb_wx, "field 'rb_wx' and method 'processClick'");
    target.rb_wx = Utils.castView(view, R.id.rb_wx, "field 'rb_wx'", RadioButton.class);
    view2131558788 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_pay, "field 'btn_pay' and method 'processClick'");
    target.btn_pay = Utils.castView(view, R.id.btn_pay, "field 'btn_pay'", Button.class);
    view2131558795 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    target.mLoadPage = Utils.findRequiredViewAsType(source, R.id.mLoadPage, "field 'mLoadPage'", LoadPage.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PayActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.iv_left = null;
    target.tv_title = null;
    target.rg_payment = null;
    target.rl_ali = null;
    target.rl_wx = null;
    target.tv_money = null;
    target.tv_order_desc = null;
    target.rb_ali = null;
    target.rb_wx = null;
    target.btn_pay = null;
    target.mLoadPage = null;

    view2131558635.setOnClickListener(null);
    view2131558635 = null;
    view2131558791.setOnClickListener(null);
    view2131558791 = null;
    view2131558793.setOnClickListener(null);
    view2131558793 = null;
    view2131558788.setOnClickListener(null);
    view2131558788 = null;
    view2131558795.setOnClickListener(null);
    view2131558795 = null;
  }
}
