// Generated code from Butter Knife. Do not modify!
package com.svgouwu.client.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.svgouwu.client.R;
import com.svgouwu.client.view.LoadPage;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CategoryFragment_ViewBinding implements Unbinder {
  private CategoryFragment target;

  private View view2131558978;

  @UiThread
  public CategoryFragment_ViewBinding(final CategoryFragment target, View source) {
    this.target = target;

    View view;
    target.xrvLeft = Utils.findRequiredViewAsType(source, R.id.xrv_left, "field 'xrvLeft'", XRecyclerView.class);
    target.xrvRight = Utils.findRequiredViewAsType(source, R.id.xrv_right, "field 'xrvRight'", XRecyclerView.class);
    view = Utils.findRequiredView(source, R.id.ll_head, "field 'll_head' and method 'processClick'");
    target.ll_head = Utils.castView(view, R.id.ll_head, "field 'll_head'", LinearLayout.class);
    view2131558978 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    target.bannerView = Utils.findRequiredViewAsType(source, R.id.bannerView, "field 'bannerView'", ConvenientBanner.class);
    target.mLoadPage = Utils.findRequiredViewAsType(source, R.id.mLoadPage, "field 'mLoadPage'", LoadPage.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CategoryFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.xrvLeft = null;
    target.xrvRight = null;
    target.ll_head = null;
    target.bannerView = null;
    target.mLoadPage = null;

    view2131558978.setOnClickListener(null);
    view2131558978 = null;
  }
}
