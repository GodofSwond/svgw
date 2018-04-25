// Generated code from Butter Knife. Do not modify!
package com.svgouwu.client.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.svgouwu.client.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CartAdapter$GroupViewHolder_ViewBinding implements Unbinder {
  private CartAdapter.GroupViewHolder target;

  @UiThread
  public CartAdapter$GroupViewHolder_ViewBinding(CartAdapter.GroupViewHolder target, View source) {
    this.target = target;

    target.storeCheckBox = Utils.findRequiredViewAsType(source, R.id.store_checkBox, "field 'storeCheckBox'", CheckBox.class);
    target.storeName = Utils.findRequiredViewAsType(source, R.id.store_name, "field 'storeName'", TextView.class);
    target.storeEdit = Utils.findRequiredViewAsType(source, R.id.store_edit, "field 'storeEdit'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CartAdapter.GroupViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.storeCheckBox = null;
    target.storeName = null;
    target.storeEdit = null;
  }
}
