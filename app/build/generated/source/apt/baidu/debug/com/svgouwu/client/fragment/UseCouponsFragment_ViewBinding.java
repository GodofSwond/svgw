// Generated code from Butter Knife. Do not modify!
package com.svgouwu.client.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.svgouwu.client.R;
import com.svgouwu.client.view.MyJsBridgeWebView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class UseCouponsFragment_ViewBinding implements Unbinder {
  private UseCouponsFragment target;

  private View view2131558635;

  @UiThread
  public UseCouponsFragment_ViewBinding(final UseCouponsFragment target, View source) {
    this.target = target;

    View view;
    target.tv_title = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tv_title'", TextView.class);
    target.fl = Utils.findRequiredViewAsType(source, R.id.fragment_details_fl, "field 'fl'", FrameLayout.class);
    target.webviewID = Utils.findRequiredViewAsType(source, R.id.webviewID, "field 'webviewID'", MyJsBridgeWebView.class);
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
    UseCouponsFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tv_title = null;
    target.fl = null;
    target.webviewID = null;

    view2131558635.setOnClickListener(null);
    view2131558635 = null;
  }
}
