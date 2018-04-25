// Generated code from Butter Knife. Do not modify!
package com.svgouwu.client.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.svgouwu.client.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class OrderListFragment_ViewBinding implements Unbinder {
  private OrderListFragment target;

  @UiThread
  public OrderListFragment_ViewBinding(OrderListFragment target, View source) {
    this.target = target;

    target.recycleView = Utils.findRequiredViewAsType(source, R.id.recycleView, "field 'recycleView'", XRecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    OrderListFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recycleView = null;
  }
}
