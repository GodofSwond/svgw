// Generated code from Butter Knife. Do not modify!
package com.svgouwu.client.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.svgouwu.client.R;
import com.svgouwu.client.view.LoadPage;
import java.lang.IllegalStateException;
import java.lang.Override;

public class WealthRebateFragment_ViewBinding implements Unbinder {
  private WealthRebateFragment target;

  private View view2131558635;

  private View view2131559183;

  @UiThread
  public WealthRebateFragment_ViewBinding(final WealthRebateFragment target, View source) {
    this.target = target;

    View view;
    target.rlTopbar = Utils.findRequiredViewAsType(source, R.id.rl_topbar_title, "field 'rlTopbar'", RelativeLayout.class);
    target.vXian = Utils.findRequiredView(source, R.id.vXian, "field 'vXian'");
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvTitle'", TextView.class);
    view = Utils.findRequiredView(source, R.id.iv_left, "field 'ivLeft' and method 'processClick'");
    target.ivLeft = Utils.castView(view, R.id.iv_left, "field 'ivLeft'", ImageView.class);
    view2131558635 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    target.linearLayout = Utils.findRequiredViewAsType(source, R.id.fragment_money_rebate_rl, "field 'linearLayout'", LinearLayout.class);
    target.tvAlreadyMoney = Utils.findRequiredViewAsType(source, R.id.fragment_rebate_tvAlreadyMoney, "field 'tvAlreadyMoney'", TextView.class);
    target.tvCanMoney = Utils.findRequiredViewAsType(source, R.id.fragment_rebate_tvCanMoney, "field 'tvCanMoney'", TextView.class);
    target.tvFreezeMoney = Utils.findRequiredViewAsType(source, R.id.fragment_rebate_tvFreezeMoney, "field 'tvFreezeMoney'", TextView.class);
    target.mLoadPage = Utils.findRequiredViewAsType(source, R.id.mLoadPage, "field 'mLoadPage'", LoadPage.class);
    target.xrecycleView = Utils.findRequiredViewAsType(source, R.id.fragment_money_rebate_xrecycleView, "field 'xrecycleView'", XRecyclerView.class);
    view = Utils.findRequiredView(source, R.id.fragment_money_rebate_tvApply, "method 'processClick'");
    view2131559183 = view;
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
    WealthRebateFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rlTopbar = null;
    target.vXian = null;
    target.tvTitle = null;
    target.ivLeft = null;
    target.linearLayout = null;
    target.tvAlreadyMoney = null;
    target.tvCanMoney = null;
    target.tvFreezeMoney = null;
    target.mLoadPage = null;
    target.xrecycleView = null;

    view2131558635.setOnClickListener(null);
    view2131558635 = null;
    view2131559183.setOnClickListener(null);
    view2131559183 = null;
  }
}
