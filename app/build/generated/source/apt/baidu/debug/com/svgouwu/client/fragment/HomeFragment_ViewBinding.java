// Generated code from Butter Knife. Do not modify!
package com.svgouwu.client.fragment;

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
import com.svgouwu.client.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class HomeFragment_ViewBinding implements Unbinder {
  private HomeFragment target;

  private View view2131559031;

  private View view2131559026;

  private View view2131559029;

  private View view2131559025;

  private View view2131559024;

  private View view2131558635;

  @UiThread
  public HomeFragment_ViewBinding(final HomeFragment target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.ll_hf_coupon, "field 'll_hf_coupon' and method 'processClick'");
    target.ll_hf_coupon = Utils.castView(view, R.id.ll_hf_coupon, "field 'll_hf_coupon'", LinearLayout.class);
    view2131559031 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    target.tv_hf_coupon = Utils.findRequiredViewAsType(source, R.id.tv_hf_coupon, "field 'tv_hf_coupon'", TextView.class);
    target.rl_home_title = Utils.findRequiredViewAsType(source, R.id.rl_home_title, "field 'rl_home_title'", RelativeLayout.class);
    view = Utils.findRequiredView(source, R.id.tv_home_news, "field 'tv_home_news' and method 'processClick'");
    target.tv_home_news = Utils.castView(view, R.id.tv_home_news, "field 'tv_home_news'", TextView.class);
    view2131559026 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    target.rl_second_sharetop = Utils.findRequiredViewAsType(source, R.id.rl_second_sharetop, "field 'rl_second_sharetop'", RelativeLayout.class);
    target.tv_second_title = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tv_second_title'", TextView.class);
    view = Utils.findRequiredView(source, R.id.iv_top_right2, "field 'iv_top_right2' and method 'processClick'");
    target.iv_top_right2 = Utils.castView(view, R.id.iv_top_right2, "field 'iv_top_right2'", ImageView.class);
    view2131559029 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_home_search, "method 'processClick'");
    view2131559025 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_home_scan, "method 'processClick'");
    view2131559024 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
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
    HomeFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ll_hf_coupon = null;
    target.tv_hf_coupon = null;
    target.rl_home_title = null;
    target.tv_home_news = null;
    target.rl_second_sharetop = null;
    target.tv_second_title = null;
    target.iv_top_right2 = null;

    view2131559031.setOnClickListener(null);
    view2131559031 = null;
    view2131559026.setOnClickListener(null);
    view2131559026 = null;
    view2131559029.setOnClickListener(null);
    view2131559029 = null;
    view2131559025.setOnClickListener(null);
    view2131559025 = null;
    view2131559024.setOnClickListener(null);
    view2131559024 = null;
    view2131558635.setOnClickListener(null);
    view2131558635 = null;
  }
}
