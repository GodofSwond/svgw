// Generated code from Butter Knife. Do not modify!
package com.svgouwu.client.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.svgouwu.client.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PersonalFragment_ViewBinding implements Unbinder {
  private PersonalFragment target;

  private View view2131559116;

  private View view2131559113;

  private View view2131559114;

  private View view2131559115;

  private View view2131559112;

  private View view2131559096;

  private View view2131559111;

  private View view2131559098;

  private View view2131559099;

  private View view2131559093;

  private View view2131559109;

  private View view2131559107;

  private View view2131559105;

  private View view2131559102;

  private View view2131559100;

  @UiThread
  public PersonalFragment_ViewBinding(final PersonalFragment target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.rl_personal_rlClear, "field 'rlClear' and method 'processClick'");
    target.rlClear = Utils.castView(view, R.id.rl_personal_rlClear, "field 'rlClear'", RelativeLayout.class);
    view2131559116 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    target.tvCacheSize = Utils.findRequiredViewAsType(source, R.id.rl_personal_tvCacheSize, "field 'tvCacheSize'", TextView.class);
    target.ivPersonalAvatar = Utils.findRequiredViewAsType(source, R.id.iv_personal_avatar, "field 'ivPersonalAvatar'", ImageView.class);
    target.tvPersonalName = Utils.findRequiredViewAsType(source, R.id.tv_personal_name, "field 'tvPersonalName'", TextView.class);
    target.tv_personal_order_num_1 = Utils.findRequiredViewAsType(source, R.id.tv_personal_order_num_1, "field 'tv_personal_order_num_1'", TextView.class);
    target.tv_personal_order_num_2 = Utils.findRequiredViewAsType(source, R.id.tv_personal_order_num_2, "field 'tv_personal_order_num_2'", TextView.class);
    target.tv_personal_order_num_3 = Utils.findRequiredViewAsType(source, R.id.tv_personal_order_num_3, "field 'tv_personal_order_num_3'", TextView.class);
    target.tv_personal_order_num_4 = Utils.findRequiredViewAsType(source, R.id.tv_personal_order_num_4, "field 'tv_personal_order_num_4'", TextView.class);
    target.tv_personal_order_num_5 = Utils.findRequiredViewAsType(source, R.id.tv_personal_order_num_5, "field 'tv_personal_order_num_5'", TextView.class);
    view = Utils.findRequiredView(source, R.id.rl_address, "field 'rl_address' and method 'processClick'");
    target.rl_address = Utils.castView(view, R.id.rl_address, "field 'rl_address'", RelativeLayout.class);
    view2131559113 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rl_personal_MyMoney, "method 'processClick'");
    view2131559114 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rl_personal_myMessage, "method 'processClick'");
    view2131559115 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rl_Discounts, "method 'processClick'");
    view2131559112 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.iv_personal_settings, "method 'processClick'");
    view2131559096 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rl_personal_center_fav, "method 'processClick'");
    view2131559111 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ll_personal_integral, "method 'processClick'");
    view2131559098 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rl_personal_all_order, "method 'processClick'");
    view2131559099 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ll_personal_login, "method 'processClick'");
    view2131559093 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rl_personal_order_5, "method 'processClick'");
    view2131559109 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rl_personal_order_4, "method 'processClick'");
    view2131559107 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rl_personal_order_3, "method 'processClick'");
    view2131559105 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rl_personal_order_2, "method 'processClick'");
    view2131559102 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.processClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rl_personal_order_1, "method 'processClick'");
    view2131559100 = view;
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
    PersonalFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rlClear = null;
    target.tvCacheSize = null;
    target.ivPersonalAvatar = null;
    target.tvPersonalName = null;
    target.tv_personal_order_num_1 = null;
    target.tv_personal_order_num_2 = null;
    target.tv_personal_order_num_3 = null;
    target.tv_personal_order_num_4 = null;
    target.tv_personal_order_num_5 = null;
    target.rl_address = null;

    view2131559116.setOnClickListener(null);
    view2131559116 = null;
    view2131559113.setOnClickListener(null);
    view2131559113 = null;
    view2131559114.setOnClickListener(null);
    view2131559114 = null;
    view2131559115.setOnClickListener(null);
    view2131559115 = null;
    view2131559112.setOnClickListener(null);
    view2131559112 = null;
    view2131559096.setOnClickListener(null);
    view2131559096 = null;
    view2131559111.setOnClickListener(null);
    view2131559111 = null;
    view2131559098.setOnClickListener(null);
    view2131559098 = null;
    view2131559099.setOnClickListener(null);
    view2131559099 = null;
    view2131559093.setOnClickListener(null);
    view2131559093 = null;
    view2131559109.setOnClickListener(null);
    view2131559109 = null;
    view2131559107.setOnClickListener(null);
    view2131559107 = null;
    view2131559105.setOnClickListener(null);
    view2131559105 = null;
    view2131559102.setOnClickListener(null);
    view2131559102 = null;
    view2131559100.setOnClickListener(null);
    view2131559100 = null;
  }
}
