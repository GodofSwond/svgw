// Generated code from Butter Knife. Do not modify!
package com.svgouwu.client.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.FrameLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.svgouwu.client.R;
import com.svgouwu.client.view.MyJsBridgeWebView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DetailsFragment_ViewBinding implements Unbinder {
  private DetailsFragment target;

  @UiThread
  public DetailsFragment_ViewBinding(DetailsFragment target, View source) {
    this.target = target;

    target.fl = Utils.findRequiredViewAsType(source, R.id.fragment_details_fl, "field 'fl'", FrameLayout.class);
    target.webviewID = Utils.findRequiredViewAsType(source, R.id.webviewID, "field 'webviewID'", MyJsBridgeWebView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    DetailsFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.fl = null;
    target.webviewID = null;
  }
}
