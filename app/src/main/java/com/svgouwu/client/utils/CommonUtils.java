package com.svgouwu.client.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.kili.okhttp.OkHttpUtils;
import com.lzy.imagepicker.view.SystemBarTintManager;
import com.svgouwu.client.Api;
import com.svgouwu.client.CommonFragmentActivity;
import com.svgouwu.client.MyApplication;
import com.svgouwu.client.R;
import com.svgouwu.client.activity.GoodsDetailsWebView;
import com.svgouwu.client.activity.LoginActivity;
import com.svgouwu.client.activity.PayActivity;
import com.svgouwu.client.bean.HttpResult;
import com.svgouwu.client.bean.SpreedData;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;

import static com.svgouwu.client.Api.URL_GOODS_DETAILS;

/**
 * Created by topwolf on 2017/4/25.
 */

public class CommonUtils {
    /**
     * 空判断
     */
    public static boolean isEmpty(CharSequence str) {
        if (str == "" || str == null || str.length() == 0 || "null".equalsIgnoreCase(str.toString()))
            return true;
        else
            return false;
    }

    /**
     * 启动对应的功能模块
     */
    public static void startAct(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        if (!(context instanceof Activity)) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    /**
     * 进入单个Fragment
     *
     * @param target 目标Fragment
     */
    public static void startAct(Context context, int target) {
        Intent intent = new Intent(context, CommonFragmentActivity.class);
        if (!(context instanceof Activity)) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        intent.putExtra(CommonFragmentActivity.TARGET, target);
        context.startActivity(intent);
    }

    /**
     * 应用是否安装
     */
    public static boolean isAppInstalled(Context mActivity, String packageName) {
        try {
            PackageManager manager = mActivity.getPackageManager();
            PackageInfo info = manager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            if (info != null) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 隐藏软键盘
     *
     * @param activity
     * @param flag     true    隐藏
     */
    public static void hideInputMode(Activity activity, boolean flag) {
        if (flag) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            View view = activity.getCurrentFocus();
            if (view != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } else {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            View view = activity.getCurrentFocus();
            if (view != null) {
                imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
            }
        }
    }

    public static void showKeyboard(Context mcontext, View view) {
        InputMethodManager im = (InputMethodManager) mcontext.getSystemService(Context.INPUT_METHOD_SERVICE);
        im.showSoftInput(view, 0);
    }


    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static String getChannel() {
        String ret = "";
        try {
            ret = MyApplication.getInstance().getPackageManager()
                    .getApplicationInfo(MyApplication.getInstance().getPackageName(),
                            PackageManager.GET_META_DATA).metaData.getString("UMENG_CHANNEL");
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtils.e("M_CHANNEL: " + ret);
        return ret;
    }

    //当前登录状态
    public static boolean isLogin() {
        boolean result = false;

        String loginKey = MyApplication.getInstance().getLoginKey();
        if (!isEmpty(loginKey)) {
            return true;
        }

        return result;
    }

    //未登录，直接跳转登录
    public static boolean checkLogin() {
        boolean result = false;

        String loginKey = MyApplication.getInstance().getLoginKey();
        if (!isEmpty(loginKey)) {
            return true;
        } else {
            CommonUtils.startAct(MyApplication.getInstance(), LoginActivity.class);
        }

        return result;
    }

    public static boolean isEmail(String email) {
        Pattern p = Pattern.compile("(\\w|((\\w+.)+\\w+))+@(\\w+.)+\\w+"); //简单匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public static int getScreenWidth(Context ctx) {
        WindowManager windowManager = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * EditText获取焦点并显示软键盘
     */
    public static void showSoftInputFromWindow(Activity activity, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    /**
     * 获取货币符号
     */
    public static String getCurrencySign() {
        return MyApplication.getInstance().getString(R.string.text_rmb);
    }

    public static void enterGoodsDetails(String goodsId) {
        if (isEmpty(goodsId)) return;

        Intent intent = new Intent(MyApplication.getInstance(), GoodsDetailsWebView.class);
        intent.putExtra("url", URL_GOODS_DETAILS + goodsId);
        intent.putExtra("goodsId", goodsId);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MyApplication.getInstance().startActivity(intent);
    }

    public static void go2pay(String orderId, String orderSn, String amount) {
        Intent intent = new Intent(MyApplication.getInstance(), PayActivity.class);
        intent.putExtra("orderIds", orderId);
        intent.putExtra("orderSns", orderSn);
        intent.putExtra("amount", amount);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MyApplication.getInstance().startActivity(intent);
    }

    public static String numRound(String num, int decimal) {
        if (CommonUtils.isEmpty(num) || !Pattern.compile("-?[0-9]+.?[0-9]+").matcher(num).matches()) {
            num = "0";
        }
        BigDecimal b = new BigDecimal(num);
        num = b.setScale(decimal, RoundingMode.HALF_UP).toString();
        return num;
    }

    public static void copy(String content, Context context) {
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
        Toast.makeText(context, "复制成功", Toast.LENGTH_SHORT).show();
    }

    /**
     * 实现粘贴功能
     * add by wangqianzhou
     *
     * @param context
     * @return
     */
    public static String paste(Context context) {
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        return cmb.getText().toString().trim();
    }

    /**
     * 数据卖点统计传给接口
     * ipstr：来访者IP
     * typestr：访问的页面
     * fromstr：请求接口的系统（Android：0，iOS：1）
     *
     * @param context
     */
    public static void umTongJi(Context context, String ipstr, String typestr, String fromstr) {
        HashMap<String, String> map = new HashMap<>();
        map.put("ipstr", ipstr);
        map.put("typestr", typestr);
        map.put("fromstr", fromstr);
        OkHttpUtils.post().url(Api.URL_TONGJI).params(map).build().execute(new CommonCallback<SpreedData>() {
            @Override
            public void onError(Call call, Exception e) {
            }

            @Override
            public void onResponse(HttpResult<SpreedData> response) {
                try {
                    if (response.isSuccess()) {
                        Log.d("whq", "数据卖点统计");

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        });
    }

    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    public static String add(String v1, String v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "保留的小数位数必须大于零");
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.add(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 带边框的圆形头像
     *
     * @param bitmap
     * @param context
     * @return
     */
    public static Drawable createRoundImageWithBorder(Bitmap bitmap, Context context) {
        //原图宽度
        int bitmapWidth = bitmap.getWidth();
        //原图高度
        int bitmapHeight = bitmap.getHeight();
        //边框宽度 pixel
        int borderWidthHalf = 20;

        //转换为正方形后的宽高
        int bitmapSquareWidth = Math.min(bitmapWidth, bitmapHeight);

        //最终图像的宽高
        int newBitmapSquareWidth = bitmapSquareWidth + borderWidthHalf;

        Bitmap roundedBitmap = Bitmap.createBitmap(newBitmapSquareWidth, newBitmapSquareWidth, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(roundedBitmap);
        int x = borderWidthHalf + bitmapSquareWidth - bitmapWidth;
        int y = borderWidthHalf + bitmapSquareWidth - bitmapHeight;

        //裁剪后图像,注意X,Y要除以2 来进行一个中心裁剪
        canvas.drawBitmap(bitmap, x / 2, y / 2, null);
        Paint borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(borderWidthHalf);
        borderPaint.setColor(Color.WHITE);

        //添加边框
        canvas.drawCircle(canvas.getWidth() / 2, canvas.getWidth() / 2, newBitmapSquareWidth / 2, borderPaint);

        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), roundedBitmap);
        roundedBitmapDrawable.setGravity(Gravity.CENTER);
        roundedBitmapDrawable.setCircular(true);
        return roundedBitmapDrawable;
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 修改当前Activity状态栏颜色，适合单个Acitivity
     * 一个Activity多个Fragment不适用
     *
     * @param activity
     * @param res--    状态栏需要的颜色
     */
    public static void myTopBar(Activity activity, int res) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true, activity);
            SystemBarTintManager tintManager = new SystemBarTintManager(activity);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(res);

        }
    }

    @TargetApi(19)
    private static void setTranslucentStatus(boolean on, Activity activity) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

}
