package com.svgouwu.client.utils;


import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.svgouwu.client.R;

/***
 * 、
 *
 * @author Liu_Dongxu
 *         Activity PopWin
 */

public class PopwindowUtils {

    PopupWindow window;

    /**
     * 顶部弹窗
     */
    public static PopupWindow popWinTop(View view, Context context, int width, int height, View parent) {
        PopupWindow window = new PopupWindow(view, width, height);
        window.setFocusable(true);
        window.setOutsideTouchable(true);
        window.setBackgroundDrawable(new BitmapDrawable());
        window.showAtLocation(parent, Gravity.TOP, 0, 0);
        return window;
    }

    /**
     * 居中弹窗
     */
    public static PopupWindow popWinCenter(View view, Context context, int width, int height, View parent) {
        PopupWindow window = new PopupWindow(view, width, height);
        window.setFocusable(true);
        window.setOutsideTouchable(true);
        window.setBackgroundDrawable(new BitmapDrawable());
        window.showAtLocation(parent, Gravity.CENTER, 0, 0);
        return window;
    }


    /**
     * 底部弹窗
     */
    public static PopupWindow popWinBottom(View view, Context context, int width, int height, View parent) {
        PopupWindow window = new PopupWindow(view, width, height);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);
        window.setOutsideTouchable(true);
        window.setBackgroundDrawable(new BitmapDrawable());
        // 设置popWindow的显示和消失动画
//        window.setAnimationStyle(R.style.popwin_pop_anim_style);
        window.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        return window;
    }

    /**
     * 左边弹窗
     */
    public static PopupWindow popWinleft(View view, Context context, int width, int height, View parent) {
        PopupWindow window = new PopupWindow(view, width, height);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);
        window.setOutsideTouchable(true);
        window.setBackgroundDrawable(new BitmapDrawable());
        // 设置popWindow的显示和消失动画
//        window.setAnimationStyle(R.style.popwin_pop_anim_style_translate);

        int[] location = new int[2];
        parent.getLocationOnScreen(location);

        window.showAtLocation(parent, Gravity.NO_GRAVITY, location[0] - parent.getWidth() * 2 - 18, location[1] - 18);
        return window;
    }


    /**
     * 底部弹窗
     */
    public static PopupWindow popWinCustom(View view, Context context, int width, int height, View parent) {
        PopupWindow window = new PopupWindow(view, width, height);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);
        window.setOutsideTouchable(true);
        window.setBackgroundDrawable(new BitmapDrawable());
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        @SuppressWarnings("deprecation")
        int xpos = manager.getDefaultDisplay().getWidth() / 2 - window.getWidth() / 2;
        //xoff,yoff基于anchor的左下角进行偏移。
        window.showAsDropDown(parent, 0, 10);
//        window.showAtLocation(parent, Gravity.NO_GRAVITY, 0, 0);
        return window;
    }


}
