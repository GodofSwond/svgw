package com.svgouwu.client.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.svgouwu.client.R;


public class DialogUtil {

    public static void show(Activity act, String msg, DialogInterface.OnClickListener onClickListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(act);
        builder.setMessage(msg);
        builder.setPositiveButton("确定", onClickListener);
        builder.setNegativeButton("取消",null);
        builder.show();
    }

    public static Dialog getAlertDialog(Activity act, int layout) {
        Dialog dialog = new Dialog(act, R.style.AlertDialogStyle);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(layout);
        return dialog;
    }

    /**
     * 从下而上弹出对话框
     */
    public static Dialog getDown2UpDialog(Activity act, int contentView) {
        Dialog dialog = new Dialog(act, R.style.Down2UpDialogStyle);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(contentView);

        // 动画弹出
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.dimAmount = 0.8f;
        lp.width = act.getWindowManager().getDefaultDisplay().getWidth();
        window.setWindowAnimations(R.style.Down2UpDialogAnimStyle);

        return dialog;
    }
}
