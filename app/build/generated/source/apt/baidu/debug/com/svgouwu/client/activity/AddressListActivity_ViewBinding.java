// Generated code from Butter Knife. Do not modify!
package com.svgouwu.client.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.svgouwu.client.R;
import com.svgouwu.client.view.LoadPage;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AddressListActivity_ViewBinding implements Unbinder {
  private AddressListActivity target;

  private View view2131558635;

  private View view2131558597;

  private View view2131558594;

  @UiThread
  public AddressListActivity_ViewBinding(AddressListActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AddressListActivity_ViewBinding(final AddressListActivity target, View source) {
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
    target.ivRight = Utils.findRequiredViewAsType(source, R.id.iv_right, "field 'ivRight'", ImageView.class);
    target.tvRight = Utils.findRequiredViewAsType(source, R.id.tv_right, "field 'tvRight'", TextView.class);
    target.rlTopbarTitle = Utils.findRequiredViewAsType(source, R.id.rl_topbar_title, "field 'rlTopbarTitle'", RelativeLayout.class);
    target.llTopbar = Utils.findRequiredViewAsType(source, R.id.ll_topbar, "field 'llTopbar'", LinearLayout.class);
    target.mXRecyclerView = Utils.findRequiredViewAsType(source, R.id.mXRecyclerView, "field 'mXRecyclerView'", XRecyclerView.class);
    view = Utils.findRequiredView(source, R.id.tv_add, "field 'tv_add' and method 'processClick'");
    target.tv_add = Utils.castView(view, R.id.tv_add, "field 'tv_add'", TextView.class);
    view2131558597 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_add2, "field 'tv_add2' and method 'processClick'");
    target.tv_add2 = Utils.castView(view, R.id.tv_add2, "field 'tv_add2'", TextView.class);
    view2131558594 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    target.ll_no_address = Utils.findRequiredViewAsType(source, R.id.ll_no_address, "field 'll_no_address'", LinearLayout.class);
    target.mLoadPage = Utils.findRequiredViewAsType(source, R.id.mLoadPage, "field 'mLoadPage'", LoadPage.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AddressListActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ivLeft = null;
    target.tvTitle = null;
    target.ivRight = null;
    target.tvRight = null;
    target.rlTopbarTitle = null;
    target.llTopbar = null;
    target.mXRecyclerView = null;
    target.tv_add = null;
    target.tv_add2 = null;
    target.ll_no_address = null;
    target.mLoadPage = null;

    view2131558635.setOnClickListener(null);
    view2131558635 = null;
    view2131558597.setOnClickListener(null);
    view2131558597 = null;
    view2131558594.setOnClickListener(null);
    view2131558594 = null;
  }
}
