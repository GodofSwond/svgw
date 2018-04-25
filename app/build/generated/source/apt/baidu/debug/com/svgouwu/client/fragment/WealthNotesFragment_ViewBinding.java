// Generated code from Butter Knife. Do not modify!
package com.svgouwu.client.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.svgouwu.client.R;
import com.svgouwu.client.view.LoadPage;
import java.lang.IllegalStateException;
import java.lang.Override;

public class WealthNotesFragment_ViewBinding implements Unbinder {
  private WealthNotesFragment target;

  @UiThread
  public WealthNotesFragment_ViewBinding(WealthNotesFragment target, View source) {
    this.target = target;

    target.noData = Utils.findRequiredView(source, R.id.tv_nodata_ll, "field 'noData'");
    target.mLoadPage = Utils.findRequiredViewAsType(source, R.id.mLoadPage, "field 'mLoadPage'", LoadPage.class);
    target.xrecyclerView = Utils.findRequiredViewAsType(source, R.id.fragment_money_notes_xrecyclerView, "field 'xrecyclerView'", XRecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    WealthNotesFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.noData = null;
    target.mLoadPage = null;
    target.xrecyclerView = null;
  }
}
