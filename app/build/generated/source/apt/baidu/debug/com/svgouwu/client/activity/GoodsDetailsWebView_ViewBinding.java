// Generated code from Butter Knife. Do not modify!
package com.svgouwu.client.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.svgouwu.client.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class GoodsDetailsWebView_ViewBinding implements Unbinder {
  private GoodsDetailsWebView target;

  private View view2131558673;

  private View view2131558661;

  private View view2131558662;

  private View view2131558663;

  private View view2131558635;

  private View view2131559028;

  private View view2131559029;

  @UiThread
  public GoodsDetailsWebView_ViewBinding(GoodsDetailsWebView target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public GoodsDetailsWebView_ViewBinding(final GoodsDetailsWebView target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.iv_web_view_back, "method 'processClick'");
    view2131558673 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.cb_goods_fav, "method 'processClick'");
    view2131558661 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_goods_detail_add_cart, "method 'processClick'");
    view2131558662 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_goods_detail_buy, "method 'processClick'");
    view2131558663 = view;
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
    view = Utils.findRequiredView(source, R.id.iv_right, "method 'processClick'");
    view2131559028 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.iv_top_right2, "method 'processClick'");
    view2131559029 = view;
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
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    target = null;


    view2131558673.setOnClickListener(null);
    view2131558673 = null;
    view2131558661.setOnClickListener(null);
    view2131558661 = null;
    view2131558662.setOnClickListener(null);
    view2131558662 = null;
    view2131558663.setOnClickListener(null);
    view2131558663 = null;
    view2131558635.setOnClickListener(null);
    view2131558635 = null;
    view2131559028.setOnClickListener(null);
    view2131559028 = null;
    view2131559029.setOnClickListener(null);
    view2131559029 = null;
  }
}
