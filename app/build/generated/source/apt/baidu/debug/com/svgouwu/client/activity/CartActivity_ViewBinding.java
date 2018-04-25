// Generated code from Butter Knife. Do not modify!
package com.svgouwu.client.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.svgouwu.client.R;
import in.srain.cube.views.ptr.PtrFrameLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CartActivity_ViewBinding implements Unbinder {
  private CartActivity target;

  private View view2131558968;

  private View view2131558972;

  private View view2131558974;

  private View view2131558975;

  private View view2131558976;

  @UiThread
  public CartActivity_ViewBinding(CartActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public CartActivity_ViewBinding(final CartActivity target, View source) {
    this.target = target;

    View view;
    target.listView = Utils.findRequiredViewAsType(source, R.id.listView, "field 'listView'", ExpandableListView.class);
    view = Utils.findRequiredView(source, R.id.all_checkBox, "field 'allCheckBox' and method 'onClick'");
    target.allCheckBox = Utils.castView(view, R.id.all_checkBox, "field 'allCheckBox'", CheckBox.class);
    view2131558968 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.totalPrice = Utils.findRequiredViewAsType(source, R.id.total_price, "field 'totalPrice'", TextView.class);
    view = Utils.findRequiredView(source, R.id.go_pay, "field 'goPay' and method 'onClick'");
    target.goPay = Utils.castView(view, R.id.go_pay, "field 'goPay'", TextView.class);
    view2131558972 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.orderInfo = Utils.findRequiredViewAsType(source, R.id.order_info, "field 'orderInfo'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.share_goods, "field 'shareGoods' and method 'onClick'");
    target.shareGoods = Utils.castView(view, R.id.share_goods, "field 'shareGoods'", TextView.class);
    view2131558974 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.collect_goods, "field 'collectGoods' and method 'onClick'");
    target.collectGoods = Utils.castView(view, R.id.collect_goods, "field 'collectGoods'", TextView.class);
    view2131558975 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.del_goods, "field 'delGoods' and method 'onClick'");
    target.delGoods = Utils.castView(view, R.id.del_goods, "field 'delGoods'", TextView.class);
    view2131558976 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.shareInfo = Utils.findRequiredViewAsType(source, R.id.share_info, "field 'shareInfo'", LinearLayout.class);
    target.llCart = Utils.findRequiredViewAsType(source, R.id.ll_cart, "field 'llCart'", LinearLayout.class);
    target.mPtrFrame = Utils.findRequiredViewAsType(source, R.id.mPtrframe, "field 'mPtrFrame'", PtrFrameLayout.class);
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvTitle'", TextView.class);
    target.tvRight = Utils.findRequiredViewAsType(source, R.id.tv_right, "field 'tvRight'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CartActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.listView = null;
    target.allCheckBox = null;
    target.totalPrice = null;
    target.goPay = null;
    target.orderInfo = null;
    target.shareGoods = null;
    target.collectGoods = null;
    target.delGoods = null;
    target.shareInfo = null;
    target.llCart = null;
    target.mPtrFrame = null;
    target.tvTitle = null;
    target.tvRight = null;

    view2131558968.setOnClickListener(null);
    view2131558968 = null;
    view2131558972.setOnClickListener(null);
    view2131558972 = null;
    view2131558974.setOnClickListener(null);
    view2131558974 = null;
    view2131558975.setOnClickListener(null);
    view2131558975 = null;
    view2131558976.setOnClickListener(null);
    view2131558976 = null;
  }
}
