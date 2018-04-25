// Generated code from Butter Knife. Do not modify!
package com.svgouwu.client.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.svgouwu.client.R;
import com.svgouwu.client.view.LoadPage;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RegionFragment_ViewBinding implements Unbinder {
  private RegionFragment target;

  private View view2131558635;

  @UiThread
  public RegionFragment_ViewBinding(final RegionFragment target, View source) {
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
    target.mLoadPage = Utils.findRequiredViewAsType(source, R.id.mLoadPage, "field 'mLoadPage'", LoadPage.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    RegionFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ivLeft = null;
    target.mLoadPage = null;

    view2131558635.setOnClickListener(null);
    view2131558635 = null;
  }
}
