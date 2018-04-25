// Generated code from Butter Knife. Do not modify!
package com.svgouwu.client.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.svgouwu.client.R;
import com.svgouwu.client.view.FlowLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SearchFragment_ViewBinding implements Unbinder {
  private SearchFragment target;

  private View view2131559119;

  private View view2131559121;

  @UiThread
  public SearchFragment_ViewBinding(final SearchFragment target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.iv_search_back, "field 'ivSearchBack' and method 'processClick'");
    target.ivSearchBack = Utils.castView(view, R.id.iv_search_back, "field 'ivSearchBack'", ImageView.class);
    view2131559119 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    target.etSearch = Utils.findRequiredViewAsType(source, R.id.et_search, "field 'etSearch'", EditText.class);
    view = Utils.findRequiredView(source, R.id.tv_search_search, "field 'tvSearchSearch' and method 'processClick'");
    target.tvSearchSearch = Utils.castView(view, R.id.tv_search_search, "field 'tvSearchSearch'", TextView.class);
    view2131559121 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    target.flowSearch = Utils.findRequiredViewAsType(source, R.id.flow_search, "field 'flowSearch'", FlowLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SearchFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ivSearchBack = null;
    target.etSearch = null;
    target.tvSearchSearch = null;
    target.flowSearch = null;

    view2131559119.setOnClickListener(null);
    view2131559119 = null;
    view2131559121.setOnClickListener(null);
    view2131559121 = null;
  }
}
