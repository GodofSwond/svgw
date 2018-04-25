package com.svgouwu.client.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.svgouwu.client.R;

/**
 * Created by whq on 2017/9/14.
 * 自定义toast
 */

public class CustomToast {
    private static TextView mTextView;
    private static ImageView mImageView;

    /**
     *
     * @param context
     * @param message--展示内容
     * @param id--0隐藏图片，非0展示图片
     */
    public static void showToasts(Context context, String message, int id) {
        //加载Toast布局
        View toastRoot = LayoutInflater.from(context).inflate(R.layout.activity_toast, null);
        //初始化布局控件
        mTextView = (TextView) toastRoot.findViewById(R.id.tv_hf_toast);
        mImageView = (ImageView) toastRoot.findViewById(R.id.iv_hf_toast);
        //为控件设置属性
        mTextView.setText(message);
        if(id == 0){
            //隐藏图片
            mImageView.setVisibility(View.GONE);
        }else{
            mImageView.setVisibility(View.VISIBLE);
            mImageView.setImageResource(id);
        }
        //Toast的初始化
        Toast toastStart = new Toast(context);
        //获取屏幕高度
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        //Toast的Y坐标是屏幕高度的1/3，不会出现不适配的问题
        toastStart.setGravity(Gravity.TOP, 0, height / 2);
        toastStart.setDuration(Toast.LENGTH_LONG);
        toastStart.setView(toastRoot);
        toastStart.show();
    }

    //平时用
    public static void toast(Context context, String msg) {
        String oldMsg = null;
        Toast toast = null;
        long firstTime = 0;
        long secondTime = 0;
        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
            toast.show();
            firstTime = System.currentTimeMillis();
        } else {
            secondTime = System.currentTimeMillis();
            if (msg.equals(oldMsg)) {
                if (secondTime - firstTime > Toast.LENGTH_SHORT) {
                    toast.show();
                }
            } else {
                oldMsg = msg;
                toast.setText(msg);
                toast.show();
            }
        }
        firstTime = secondTime;
    }

    //带图的自定义
    public static void ivToast(Context context, String Title, String msg, Integer imageView) {
        TextView mtitle, mmsg;
        ImageView iv;
        Toast toast = null;
        long oneTime = 0;
        long twoTime = 0;
        String oldMsg = null;
        View view = LayoutInflater.from(context).inflate(R.layout.custom_toast, null);
        mtitle = (TextView) view.findViewById(R.id.tv_custom_toast_title);
        mmsg = (TextView) view.findViewById(R.id.tv_custom_toast_msg);
        iv = (ImageView) view.findViewById(R.id.iv_custom_toast);
        if (imageView != null) {
            iv.setVisibility(View.VISIBLE);
            iv.setImageResource(imageView);
        } else {
            iv.setVisibility(View.GONE);
        }
        mtitle.setText(Title);
        mmsg.setText(msg);

        if (toast == null) {
            toast = Toast.makeText(context, mmsg.getText().toString(), Toast.LENGTH_SHORT);
            toast.setView(view);
            toast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (mmsg.getText().toString().equals(oldMsg)) {
                if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                    toast.show();
                }
            } else {
                oldMsg = mmsg.getText().toString();
                toast.setView(view);
                toast.show();
            }
        }
        oneTime = twoTime;

    }
}
