package com.svgouwu.client.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;

/**
 * 对应用全局未捕获异常的处理核心类，在这里实现优雅的关闭以及应用程序的重启，获得良好的用户体验！ 还可以收集用户的使用版本信息、机型信息等
 *
 * @author topwolf
 */
public class MyCrashHandler implements UncaughtExceptionHandler {
    private Context context;
    private static MyCrashHandler handler;

    /**
     * 构造方法私有化
     */
    private MyCrashHandler() {
    }

    /**
     * 此处不会出现在多线程场景下
     *
     * @return
     */
    public static MyCrashHandler getMyCrashHandler() {
        if (handler == null) {
            handler = new MyCrashHandler();
        }
        return handler;
    }

    public void init(Context context) {
        this.context = context;
    }

    /**
     * 程序出错的话调用方法！ 实现将用户信息，堆栈错误信息记录；并且实现应用程序的重启！
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        StringBuilder sb = new StringBuilder();
        PackageManager pm = context.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            // 获取错误的堆栈信息:先获取堆栈信息，然后获取手机内存信息
            StringWriter writer = new StringWriter();
            PrintWriter pw = new PrintWriter(writer);
            ex.printStackTrace(pw);
            String string = writer.toString();
            sb.append(string);
            packageInfo = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_UNINSTALLED_PACKAGES
                            | PackageManager.GET_ACTIVITIES);
            sb.append("VersionCode = " + packageInfo.versionName);
            sb.append("\n");
            // 然后获取用户的手机硬件信息
            Field[] fields = Build.class.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                String name = fields[i].getName();
                sb.append(name + " = ");
                String value = fields[i].get(null).toString();
                sb.append(value);
                sb.append("\n");
            }
//            sb.append("Channel="+GlobalConstants.umengChannel).append("\n");
//            MobclickAgent.reportError(context,ex);
            // 接下来是提交友盟服务器！
//            MobclickAgent.reportError(context, sb.toString());
//            SpUtil.putString("error", sb.toString());
            LogUtils.e("CrashInfo:", sb.toString());
            SdcardUtils.save2Sd("CrashInfo:" + sb.toString());// 保存信息到SD卡
        } catch (Exception e) {
            LogUtils.e("in crashHandler has exception too");
            e.printStackTrace();
        }
        // 然后重启应用程序：
//        Intent intent = new Intent(context, StartupActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent);
//		Util.toast("重启");
        // 最后完成自杀的操作
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}
