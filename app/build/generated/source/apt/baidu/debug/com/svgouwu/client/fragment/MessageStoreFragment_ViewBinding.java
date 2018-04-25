// Generated code from Butter Knife. Do not modify!
package com.svgouwu.client.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.svgouwu.client.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MessageStoreFragment_ViewBinding implements Unbinder {
  private MessageStoreFragment target;

  private View view2131558635;

  private View view2131559055;

  private View view2131559059;

  @UiThread
  public MessageStoreFragment_ViewBinding(final MessageStoreFragment target, View source) {
    this.target = target;

    View view;
    target.tv_title = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tv_title'", TextView.class);
    target.iv_right = Utils.findRequiredViewAsType(source, R.id.iv_right, "field 'iv_right'", ImageView.class);
    target.iv_right2 = Utils.findRequiredViewAsType(source, R.id.iv_top_right2, "field 'iv_right2'", ImageView.class);
    target.refreshLayout = Utils.findRequiredViewAsType(source, R.id.frg_msg_store_refreshLayout, "field 'refreshLayout'", SwipeRefreshLayout.class);
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.frg_msg_store_rvStore, "field 'recyclerView'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.iv_left, "method 'processClick'");
    view2131558635 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.frg_msg_store_rlInform, "method 'processClick'");
    view2131559055 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.frg_msg_store_rlCoupon, "method 'processClick'");
    view2131559059 = view;
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
    MessageStoreFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tv_title = null;
    target.iv_right = null;
    target.iv_right2 = null;
    target.refreshLayout = null;
    target.recyclerView = null;

    view2131558635.setOnClickListener(null);
    view2131558635 = null;
    view2131559055.setOnClickListener(null);
    view2131559055 = null;
    view2131559059.setOnClickListener(null);
    view2131559059 = null;
  }
}
