// Generated code from Butter Knife. Do not modify!
package com.svgouwu.client.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.svgouwu.client.R;
import com.svgouwu.client.view.MyGridView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class GoodsListActivity_ViewBinding implements Unbinder {
  private GoodsListActivity target;

  private View view2131558642;

  private View view2131558645;

  private View view2131558649;

  private View view2131558655;

  private View view2131558652;

  private View view2131558636;

  private View view2131558658;

  private View view2131558657;

  private View view2131558637;

  @UiThread
  public GoodsListActivity_ViewBinding(GoodsListActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public GoodsListActivity_ViewBinding(final GoodsListActivity target, View source) {
    this.target = target;

    View view;
    target.rv_goods_list = Utils.findRequiredViewAsType(source, R.id.rv_goods_list, "field 'rv_goods_list'", XRecyclerView.class);
    target.cb_goods_list_show_way = Utils.findRequiredViewAsType(source, R.id.cb_goods_list_show_way, "field 'cb_goods_list_show_way'", CheckBox.class);
    view = Utils.findRequiredView(source, R.id.rb_goods_list_sort_price, "field 'rb_goods_list_sort_price' and method 'processClick'");
    target.rb_goods_list_sort_price = Utils.castView(view, R.id.rb_goods_list_sort_price, "field 'rb_goods_list_sort_price'", RadioButton.class);
    view2131558642 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.iv_goods_list_go_top, "field 'iv_goods_list_go_top' and method 'processClick'");
    target.iv_goods_list_go_top = Utils.castView(view, R.id.iv_goods_list_go_top, "field 'iv_goods_list_go_top'", ImageView.class);
    view2131558645 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    target.rg_goods_list_tab = Utils.findRequiredViewAsType(source, R.id.rg_goods_list_tab, "field 'rg_goods_list_tab'", RadioGroup.class);
    target.mgv_goods_filter_cate = Utils.findRequiredViewAsType(source, R.id.mgv_goods_filter_cate, "field 'mgv_goods_filter_cate'", MyGridView.class);
    target.mgv_goods_filter_price = Utils.findRequiredViewAsType(source, R.id.mgv_goods_filter_price, "field 'mgv_goods_filter_price'", MyGridView.class);
    target.mgv_goods_filter_brand = Utils.findRequiredViewAsType(source, R.id.mgv_goods_filter_brand, "field 'mgv_goods_filter_brand'", MyGridView.class);
    view = Utils.findRequiredView(source, R.id.tv_goods_list_filter_cate_more, "field 'tv_goods_list_filter_cate_more' and method 'processClick'");
    target.tv_goods_list_filter_cate_more = Utils.castView(view, R.id.tv_goods_list_filter_cate_more, "field 'tv_goods_list_filter_cate_more'", TextView.class);
    view2131558649 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_goods_list_filter_price_more, "field 'tv_goods_list_filter_price_more' and method 'processClick'");
    target.tv_goods_list_filter_price_more = Utils.castView(view, R.id.tv_goods_list_filter_price_more, "field 'tv_goods_list_filter_price_more'", TextView.class);
    view2131558655 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_goods_list_filter_brand_more, "field 'tv_goods_list_filter_brand_more' and method 'processClick'");
    target.tv_goods_list_filter_brand_more = Utils.castView(view, R.id.tv_goods_list_filter_brand_more, "field 'tv_goods_list_filter_brand_more'", TextView.class);
    view2131558652 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    target.tv_goods_list_no_content_tips = Utils.findRequiredViewAsType(source, R.id.tv_goods_list_no_content_tips, "field 'tv_goods_list_no_content_tips'", TextView.class);
    target.rl_goods_list_filter_cate = Utils.findRequiredViewAsType(source, R.id.rl_goods_list_filter_cate, "field 'rl_goods_list_filter_cate'", RelativeLayout.class);
    target.rl_goods_list_filter_price = Utils.findRequiredViewAsType(source, R.id.rl_goods_list_filter_price, "field 'rl_goods_list_filter_price'", RelativeLayout.class);
    target.rl_goods_list_filter_brand = Utils.findRequiredViewAsType(source, R.id.rl_goods_list_filter_brand, "field 'rl_goods_list_filter_brand'", RelativeLayout.class);
    target.dl_goods_list = Utils.findRequiredViewAsType(source, R.id.dl_goods_list, "field 'dl_goods_list'", DrawerLayout.class);
    view = Utils.findRequiredView(source, R.id.et_goods_list_search, "field 'et_goods_list_search' and method 'processClick'");
    target.et_goods_list_search = Utils.castView(view, R.id.et_goods_list_search, "field 'et_goods_list_search'", TextView.class);
    view2131558636 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_goods_list_filter_ok, "method 'processClick'");
    view2131558658 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_goods_list_filter_reset, "method 'processClick'");
    view2131558657 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_right, "method 'processClick'");
    view2131558637 = view;
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
    GoodsListActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rv_goods_list = null;
    target.cb_goods_list_show_way = null;
    target.rb_goods_list_sort_price = null;
    target.iv_goods_list_go_top = null;
    target.rg_goods_list_tab = null;
    target.mgv_goods_filter_cate = null;
    target.mgv_goods_filter_price = null;
    target.mgv_goods_filter_brand = null;
    target.tv_goods_list_filter_cate_more = null;
    target.tv_goods_list_filter_price_more = null;
    target.tv_goods_list_filter_brand_more = null;
    target.tv_goods_list_no_content_tips = null;
    target.rl_goods_list_filter_cate = null;
    target.rl_goods_list_filter_price = null;
    target.rl_goods_list_filter_brand = null;
    target.dl_goods_list = null;
    target.et_goods_list_search = null;

    view2131558642.setOnClickListener(null);
    view2131558642 = null;
    view2131558645.setOnClickListener(null);
    view2131558645 = null;
    view2131558649.setOnClickListener(null);
    view2131558649 = null;
    view2131558655.setOnClickListener(null);
    view2131558655 = null;
    view2131558652.setOnClickListener(null);
    view2131558652 = null;
    view2131558636.setOnClickListener(null);
    view2131558636 = null;
    view2131558658.setOnClickListener(null);
    view2131558658 = null;
    view2131558657.setOnClickListener(null);
    view2131558657 = null;
    view2131558637.setOnClickListener(null);
    view2131558637 = null;
  }
}
