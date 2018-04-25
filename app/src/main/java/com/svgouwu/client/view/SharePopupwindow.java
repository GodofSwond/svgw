package com.svgouwu.client.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.svgouwu.client.R;

/**
 * Created by whq on 2017/11/8.
 * 顶部菜单弹窗
 */

public class SharePopupwindow extends PopupWindow {
    private View view;
    private LinearLayout ll_share_menu_news,ll_share_menu_home,ll_share_menu_share;

    public SharePopupwindow(Activity context, OnClickListener itemsOnClick) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.sharepopwindow, null);

        ll_share_menu_share = (LinearLayout) view.findViewById(R.id.ll_share_menu_share);//分享
        ll_share_menu_home = (LinearLayout) view.findViewById(R.id.ll_share_menu_home);//首页
        ll_share_menu_news = (LinearLayout) view.findViewById(R.id.ll_share_menu_news);//消息

        int h = context.getWindowManager().getDefaultDisplay().getHeight();
        int w = context.getWindowManager().getDefaultDisplay().getWidth();
        this.setContentView(view);
        //设置宽、高
        this.setWidth(w / 3 );
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.update();
        ColorDrawable dw = new ColorDrawable(00000000);
        this.setBackgroundDrawable(dw);
        ll_share_menu_news.setOnClickListener(itemsOnClick);
        ll_share_menu_home.setOnClickListener(itemsOnClick);
        ll_share_menu_share.setOnClickListener(itemsOnClick);
        this.setAnimationStyle(R.style.AnimationPreview);
    }

    public void showPopupWindow(View parent, String type) {
        if(type.equals("0")){
            ll_share_menu_share.setVisibility(View.GONE);
        }else{
            ll_share_menu_share.setVisibility(View.VISIBLE);
        }
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
         //   this.showAsDropDown(parent, parent.getLayoutParams().width / 4, 0);
            this.showAsDropDown(parent, -255, 0);
        } else {
            this.dismiss();
        }
    }
}
