// Generated code from Butter Knife. Do not modify!
package com.svgouwu.client.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.svgouwu.client.R;
import com.svgouwu.client.view.LoadPage;
import java.lang.IllegalStateException;
import java.lang.Override;

public class WealthLookFragment_ViewBinding implements Unbinder {
  private WealthLookFragment target;

  private View view2131559149;

  private View view2131559165;

  private View view2131559166;

  @UiThread
  public WealthLookFragment_ViewBinding(final WealthLookFragment target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.fragment_look_ivLeft, "field 'ivLeft' and method 'processClick'");
    target.ivLeft = Utils.castView(view, R.id.fragment_look_ivLeft, "field 'ivLeft'", ImageView.class);
    view2131559149 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.fragment_look_tvTitle, "field 'tvTitle'", TextView.class);
    target.linearLayout = Utils.findRequiredViewAsType(source, R.id.fragment_money_look_rl, "field 'linearLayout'", LinearLayout.class);
    target.tvAlreadyMoney = Utils.findRequiredViewAsType(source, R.id.fragment_look_tvAlreadyMoney, "field 'tvAlreadyMoney'", TextView.class);
    target.tvCanMoney = Utils.findRequiredViewAsType(source, R.id.fragment_look_tvCanMoney, "field 'tvCanMoney'", TextView.class);
    target.tvFreezeMoney = Utils.findRequiredViewAsType(source, R.id.fragment_look_tvFreezeMoney, "field 'tvFreezeMoney'", TextView.class);
    target.mLoadPage = Utils.findRequiredViewAsType(source, R.id.mLoadPage, "field 'mLoadPage'", LoadPage.class);
    target.xrecyclerView = Utils.findRequiredViewAsType(source, R.id.fragment_money_look_xrecycleView, "field 'xrecyclerView'", XRecyclerView.class);
    view = Utils.findRequiredView(source, R.id.fragment_money_look_tvApply, "method 'processClick'");
    view2131559165 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.fragment_money_look_tvPrestore, "method 'processClick'");
    view2131559166 = view;
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
    WealthLookFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ivLeft = null;
    target.tvTitle = null;
    target.linearLayout = null;
    target.tvAlreadyMoney = null;
    target.tvCanMoney = null;
    target.tvFreezeMoney = null;
    target.mLoadPage = null;
    target.xrecyclerView = null;

    view2131559149.setOnClickListener(null);
    view2131559149 = null;
    view2131559165.setOnClickListener(null);
    view2131559165 = null;
    view2131559166.setOnClickListener(null);
    view2131559166 = null;
  }
}
