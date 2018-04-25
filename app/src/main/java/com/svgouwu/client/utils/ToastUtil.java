package com.svgouwu.client.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import com.svgouwu.client.MyApplication;
import com.svgouwu.client.R;

/**
 * singleton Toast, or it will dismiss at a long time when toast.show() call a lot of times
 *
 * @author ChenDanyang
 *
 */
@SuppressLint("InflateParams")
public class ToastUtil {

    private static final int LONG_DELAY = 3500; // 3.5 seconds
    private static final int SHORT_DELAY = 2000; // 2 seconds

    private static final int MESSAGE_TIMEOUT = 2;

    private static final boolean DEBUG = true;

    private static android.widget.Toast toast = null;

    private static Handler mUiHandler;

    public static final int LENGTH_SHORT = 0;

    public static final int LENGTH_LONG = 1;

    private static TextView mText;
    private static View mView;

    private static WindowManager mWindowManager;

    private static boolean USE_SYSTEM_TOAST = false;

    private static WindowManager.LayoutParams mWindowManagerParams;

    private static boolean mIsShowing = false;
    /**
     * can be run in not ui thread
     *
     * @param context
     * @param text
     * @param durationLong
     *            {@link ToastUtil#LENGTH_LONG} if true
     */
    @SuppressLint("ShowToast")
    public static void toast(final Context context, final CharSequence text, final boolean durationLong) {
        toast(context, text, durationLong ? ToastUtil.LENGTH_LONG : ToastUtil.LENGTH_SHORT);
    }

    /**
     * can be run in not ui thread
     *
     * @param context
     * @param text
     * @param durationLong
     *            {@link ToastUtil#LENGTH_LONG} if true
     */
    @SuppressLint("ShowToast")
    public static void show(final Context context, final CharSequence text, final boolean durationLong) {
        toast(context, text, durationLong ? ToastUtil.LENGTH_LONG : ToastUtil.LENGTH_SHORT);
    }

    public static void toast(final Context context, final CharSequence text, final int duration) {

        if (mUiHandler == null) {
            mUiHandler = new Handler(Looper.getMainLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    if(mWindowManager != null && mView != null) {
                        mWindowManager.removeView(mView);
                        mIsShowing = false;
                    }
                }
            };
        }

        if(mWindowManager == null) {
            mWindowManager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        }

        mUiHandler.post(new Runnable() {

            @Override
            public void run() {
                if (toast == null) {
//					LayoutInflater inflater = null;//context.getApplicationContext().get;
                    mView = (View) LayoutInflater.from(context).inflate(R.layout.toast, null);
                    mText = (TextView) mView.findViewById(R.id.tv_toast);
                    toast = new android.widget.Toast(context);
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.setView(mView);
                }
                toast.setDuration(duration);
                mText.setText(text);
                if(USE_SYSTEM_TOAST) {
                    toast.show();
                }else {
                    mUiHandler.removeMessages(MESSAGE_TIMEOUT);
                    if(!mIsShowing) {
                        mWindowManager.addView(mView, getLayoutParams());
                        mIsShowing = true;
                    }
                    mUiHandler.sendEmptyMessageDelayed(MESSAGE_TIMEOUT, duration == ToastUtil.LENGTH_LONG ? LONG_DELAY : SHORT_DELAY);
                }
            }
        });
    }

    private static WindowManager.LayoutParams getLayoutParams() {
        if(mWindowManagerParams == null) {
            mWindowManagerParams = new WindowManager.LayoutParams();

            mWindowManagerParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            mWindowManagerParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
            mWindowManagerParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                    | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
            mWindowManagerParams.format = PixelFormat.TRANSLUCENT;
            mWindowManagerParams.windowAnimations = android.R.style.Animation_Toast;
            mWindowManagerParams.type = WindowManager.LayoutParams.TYPE_TOAST;
            mWindowManagerParams.gravity = Gravity.BOTTOM;
        }
        return mWindowManagerParams;
    }


    /**
     * toast with short duration, can be run in not ui thread
     *
     * @param context
     * @param text
     */
    public static void toast(Context context, CharSequence text) {
        toast(context, text, false);
    }

    /**
     * toast with short duration, can be run in not ui thread
     *
     * @param context
     * @param text
     */
    public static void show(Context context, CharSequence text) {
        toast(context, text, false);
    }

    public static void toast(CharSequence text) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            boolean canAlert = Settings.canDrawOverlays(MyApplication.getInstance());
            if(!canAlert){
                //系统悬浮窗不能使用时，用系统Toast
                ToastUtil2.toast(text.toString());
                return;
            }
        }
        //正常情况 用系统悬浮窗
        toast(MyApplication.getInstance(), text, false);
    }
    public static void toast(int id) {
        toast(MyApplication.getInstance().getString(id));
    }
    /**
     * toast with short duration, can be run in not ui thread
     *
     * @param context
     * @param textId
     */
    public static void toast(Context context, int textId) {
        toast(context, context.getString(textId), false);
    }

    /**
     * toast with short duration, can be run in not ui thread
     *
     * @param context
     * @param text
     */
    public static void toastDebug(Context context, CharSequence text) {
        if (DEBUG) {
            toast(context, text, false);
        }
    }

    public static ToastObject makeText(Context context, CharSequence text, int duration) {
        return new ToastObject(context, text, duration);
    }

    public static ToastObject makeText(Context context, int textId, int duration) {
        return new ToastObject(context, context.getString(textId), duration);
    }

    public static class ToastObject {

        Context context;
        CharSequence text;
        int duration;

        public ToastObject(Context context, CharSequence text, int duration) {
            this.context = context;
            this.text = text;
            this.duration = duration;
        }

        public void show() {
            toast(context, text, duration);
        }
    }

}
