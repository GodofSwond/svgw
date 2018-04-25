// Generated code from Butter Knife. Do not modify!
package com.svgouwu.client.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.svgouwu.client.R;
import com.svgouwu.client.view.LoadPage;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MysvCoinActivity_ViewBinding implements Unbinder {
  private MysvCoinActivity target;

  private View view2131558829;

  private View view2131558822;

  @UiThread
  public MysvCoinActivity_ViewBinding(MysvCoinActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MysvCoinActivity_ViewBinding(final MysvCoinActivity target, View source) {
    this.target = target;

    View view;
    target.tv_mycoin_money = Utils.findRequiredViewAsType(source, R.id.tv_mycoin_money, "field 'tv_mycoin_money'", TextView.class);
    target.tv_mycoin_take = Utils.findRequiredViewAsType(source, R.id.tv_mycoin_take, "field 'tv_mycoin_take'", TextView.class);
    target.tv_mycoin_frozen = Utils.findRequiredViewAsType(source, R.id.tv_mycoin_frozen, "field 'tv_mycoin_frozen'", TextView.class);
    target.rv_mycoin = Utils.findRequiredViewAsType(source, R.id.rv_mycoin, "field 'rv_mycoin'", XRecyclerView.class);
    target.mLoadPage_mycoin = Utils.findRequiredViewAsType(source, R.id.mLoadPage_mycoin, "field 'mLoadPage_mycoin'", LoadPage.class);
    view = Utils.findRequiredView(source, R.id.tv_mycoin_apply, "field 'tv_mycoin_apply' and method 'processClick'");
    target.tv_mycoin_apply = Utils.castView(view, R.id.tv_mycoin_apply, "field 'tv_mycoin_apply'", TextView.class);
    view2131558829 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.iv_mycoin_back, "method 'processClick'");
    view2131558822 = view;
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
    MysvCoinActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tv_mycoin_money = null;
    target.tv_mycoin_take = null;
    target.tv_mycoin_frozen = null;
    target.rv_mycoin = null;
    target.mLoadPage_mycoin = null;
    target.tv_mycoin_apply = null;

    view2131558829.setOnClickListener(null);
    view2131558829 = null;
    view2131558822.setOnClickListener(null);
    view2131558822 = null;
  }
}
