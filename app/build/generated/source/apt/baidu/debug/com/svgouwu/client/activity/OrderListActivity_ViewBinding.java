// Generated code from Butter Knife. Do not modify!
package com.svgouwu.client.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.svgouwu.client.R;
import com.svgouwu.client.view.LoadPage;
import java.lang.IllegalStateException;
import java.lang.Override;

public class OrderListActivity_ViewBinding implements Unbinder {
  private OrderListActivity target;

  private View view2131558635;

  @UiThread
  public OrderListActivity_ViewBinding(OrderListActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public OrderListActivity_ViewBinding(final OrderListActivity target, View source) {
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
    target.mXRecyclerView = Utils.findRequiredViewAsType(source, R.id.mXRecyclerView, "field 'mXRecyclerView'", XRecyclerView.class);
    target.mLoadPage = Utils.findRequiredViewAsType(source, R.id.mLoadPage, "field 'mLoadPage'", LoadPage.class);
    target.mTabLayout = Utils.findRequiredViewAsType(source, R.id.mTabLayout, "field 'mTabLayout'", TabLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    OrderListActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ivLeft = null;
    target.tvTitle = null;
    target.mXRecyclerView = null;
    target.mLoadPage = null;
    target.mTabLayout = null;

    view2131558635.setOnClickListener(null);
    view2131558635 = null;
  }
}
