// Generated code from Butter Knife. Do not modify!
package com.svgouwu.client.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
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

public class CouponsCenterActivity_ViewBinding implements Unbinder {
  private CouponsCenterActivity target;

  private View view2131558635;

  @UiThread
  public CouponsCenterActivity_ViewBinding(CouponsCenterActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public CouponsCenterActivity_ViewBinding(final CouponsCenterActivity target, View source) {
    this.target = target;

    View view;
    target.tv_title = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tv_title'", TextView.class);
    target.llNodata = Utils.findRequiredViewAsType(source, R.id.tv_nodata_ll, "field 'llNodata'", LinearLayout.class);
    target.mLoadPage = Utils.findRequiredViewAsType(source, R.id.mLoadPage, "field 'mLoadPage'", LoadPage.class);
    target.xrvUnreceived = Utils.findRequiredViewAsType(source, R.id.activity_couCenter_xrvUnreceived, "field 'xrvUnreceived'", XRecyclerView.class);
    target.xrvReceived = Utils.findRequiredViewAsType(source, R.id.activity_couCenter_xrvReceived, "field 'xrvReceived'", XRecyclerView.class);
    target.xrvOutofnum = Utils.findRequiredViewAsType(source, R.id.activity_couCenter_xrvOutofnum, "field 'xrvOutofnum'", XRecyclerView.class);
    view = Utils.findRequiredView(source, R.id.iv_left, "method 'processClick'");
    view2131558635 = view;
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
    CouponsCenterActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tv_title = null;
    target.llNodata = null;
    target.mLoadPage = null;
    target.xrvUnreceived = null;
    target.xrvReceived = null;
    target.xrvOutofnum = null;

    view2131558635.setOnClickListener(null);
    view2131558635 = null;
  }
}
