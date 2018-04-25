// Generated code from Butter Knife. Do not modify!
package com.svgouwu.client.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.svgouwu.client.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class OrderCommentGoodsFragment_ViewBinding implements Unbinder {
  private OrderCommentGoodsFragment target;

  private View view2131558637;

  @UiThread
  public OrderCommentGoodsFragment_ViewBinding(final OrderCommentGoodsFragment target,
      View source) {
    this.target = target;

    View view;
    target.iv_goods_comment_pic = Utils.findRequiredViewAsType(source, R.id.iv_goods_comment_pic, "field 'iv_goods_comment_pic'", ImageView.class);
    target.et_goods_comment_content = Utils.findRequiredViewAsType(source, R.id.et_goods_comment_content, "field 'et_goods_comment_content'", EditText.class);
    target.rba_goods_comment_rating = Utils.findRequiredViewAsType(source, R.id.rba_goods_comment_rating, "field 'rba_goods_comment_rating'", RatingBar.class);
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
    OrderCommentGoodsFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.iv_goods_comment_pic = null;
    target.et_goods_comment_content = null;
    target.rba_goods_comment_rating = null;

    view2131558637.setOnClickListener(null);
    view2131558637 = null;
  }
}
