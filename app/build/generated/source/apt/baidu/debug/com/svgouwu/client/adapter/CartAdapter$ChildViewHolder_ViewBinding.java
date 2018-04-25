// Generated code from Butter Knife. Do not modify!
package com.svgouwu.client.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.svgouwu.client.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CartAdapter$ChildViewHolder_ViewBinding implements Unbinder {
  private CartAdapter.ChildViewHolder target;

  @UiThread
  public CartAdapter$ChildViewHolder_ViewBinding(CartAdapter.ChildViewHolder target, View source) {
    this.target = target;

    target.singleCheckBox = Utils.findRequiredViewAsType(source, R.id.single_checkBox, "field 'singleCheckBox'", CheckBox.class);
    target.iv_pic = Utils.findRequiredViewAsType(source, R.id.iv_pic, "field 'iv_pic'", ImageView.class);
    target.goodsName = Utils.findRequiredViewAsType(source, R.id.tv_goods_name, "field 'goodsName'", TextView.class);
    target.tv_specification = Utils.findRequiredViewAsType(source, R.id.tv_specification, "field 'tv_specification'", TextView.class);
    target.goodsPrice = Utils.findRequiredViewAsType(source, R.id.tv_goods_price, "field 'goodsPrice'", TextView.class);
    target.goodsPrimePrice = Utils.findRequiredViewAsType(source, R.id.goods_prime_price, "field 'goodsPrimePrice'", TextView.class);
    target.goodsBuyNum = Utils.findRequiredViewAsType(source, R.id.goods_buyNum, "field 'goodsBuyNum'", TextView.class);
    target.tv_minus = Utils.findRequiredViewAsType(source, R.id.tv_minus, "field 'tv_minus'", TextView.class);
    target.tv_plus = Utils.findRequiredViewAsType(source, R.id.tv_plus, "field 'tv_plus'", TextView.class);
    target.goodsData = Utils.findRequiredViewAsType(source, R.id.goods_data, "field 'goodsData'", RelativeLayout.class);
    target.reduceGoodsNum = Utils.findRequiredViewAsType(source, R.id.reduce_goodsNum, "field 'reduceGoodsNum'", TextView.class);
    target.goodsNum = Utils.findRequiredViewAsType(source, R.id.goods_Num, "field 'goodsNum'", TextView.class);
    target.increaseGoodsNum = Utils.findRequiredViewAsType(source, R.id.increase_goods_Num, "field 'increaseGoodsNum'", TextView.class);
    target.goodsSize = Utils.findRequiredViewAsType(source, R.id.goodsSize, "field 'goodsSize'", TextView.class);
    target.delGoods = Utils.findRequiredViewAsType(source, R.id.del_goods, "field 'delGoods'", TextView.class);
    target.editGoodsData = Utils.findRequiredViewAsType(source, R.id.edit_goods_data, "field 'editGoodsData'", LinearLayout.class);
    target.ll_goods_buyNum = Utils.findRequiredViewAsType(source, R.id.ll_goods_buyNum, "field 'll_goods_buyNum'", LinearLayout.class);
    target.ll_reduce_goodsNum = Utils.findRequiredViewAsType(source, R.id.ll_reduce_goodsNum, "field 'll_reduce_goodsNum'", LinearLayout.class);
    target.tv_car_rebate = Utils.findRequiredViewAsType(source, R.id.tv_car_rebate, "field 'tv_car_rebate'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CartAdapter.ChildViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.singleCheckBox = null;
    target.iv_pic = null;
    target.goodsName = null;
    target.tv_specification = null;
    target.goodsPrice = null;
    target.goodsPrimePrice = null;
    target.goodsBuyNum = null;
    target.tv_minus = null;
    target.tv_plus = null;
    target.goodsData = null;
    target.reduceGoodsNum = null;
    target.goodsNum = null;
    target.increaseGoodsNum = null;
    target.goodsSize = null;
    target.delGoods = null;
    target.editGoodsData = null;
    target.ll_goods_buyNum = null;
    target.ll_reduce_goodsNum = null;
    target.tv_car_rebate = null;
  }
}
