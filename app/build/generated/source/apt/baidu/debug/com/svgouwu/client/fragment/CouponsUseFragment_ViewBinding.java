// Generated code from Butter Knife. Do not modify!
package com.svgouwu.client.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ListView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.svgouwu.client.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CouponsUseFragment_ViewBinding implements Unbinder {
  private CouponsUseFragment target;

  private View view2131558993;

  @UiThread
  public CouponsUseFragment_ViewBinding(final CouponsUseFragment target, View source) {
    this.target = target;

    View view;
    target.useLv = Utils.findRequiredViewAsType(source, R.id.fragment_coupons_use_Lv, "field 'useLv'", ListView.class);
    view = Utils.findRequiredView(source, R.id.fragment_coupons_use_tvSub, "method 'processClick'");
    view2131558993 = view;
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
    CouponsUseFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.useLv = null;

    view2131558993.setOnClickListener(null);
    view2131558993 = null;
  }
}
