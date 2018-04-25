package com.svgouwu.client;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.kili.okhttp.OkHttpUtils;
import com.svgouwu.client.bean.CouponsCenterBean;
import com.svgouwu.client.bean.CouponsItemBean;
import com.svgouwu.client.bean.OutofnumBean;
import com.svgouwu.client.bean.ReceivedBean;
import com.svgouwu.client.bean.UnreceivedBean;
import com.svgouwu.client.utils.ApiManager;
import com.svgouwu.client.utils.MyCrashHandler;
import com.svgouwu.client.utils.SpUtil;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;
import cn.magicwindow.Session;

/**
 * 全局变量
 */
public class MyApplication extends MultiDexApplication {

    protected static final String TAG = "MyApplication";
    private static int mainTid;
    private static Handler handler;
    public static boolean hfCancel;//大礼包点叉关闭
//    public static RefWatcher getRefWatcher(Context context) {
//        MyApplication application = (MyApplication) context.getApplicationContext();
//        return application.refWatcher;
//    }
//
//    private RefWatcher refWatcher;

    public static String phone;//用户手机号
    /**
     * 消息通知
     */
    private Notification mNotification;
    private NotificationManager mNotificationManager;

    private static MyApplication instance;//= new MyApplication();

    //优惠券列表
    public List<CouponsItemBean> coulist = new ArrayList<>();//优惠券列表
    public List<UnreceivedBean> unreceived;
    public List<ReceivedBean> received;
    public List<OutofnumBean> outofnum;


    public static MyApplication getInstance() {
        return instance;
    }


    public MyApplication() {
        instance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        /* 极光推送*/
        JPushInterface.setDebugMode(false);//如果是正式版就改成false
        JPushInterface.init(this);

        mainTid = android.os.Process.myTid();
        handler = new Handler();

        UMShareAPI.get(this);
        initThirdKey();
//        RxRetrofitApp.init(this,true);
//		Stetho.initializeWithDefaults(this);
        String environment;
        if (Constant.DEBUG) {
//            refWatcher = LeakCanary.install(this);
            //       OkHttpUtils.getInstance().addCommonHeaders(ApiManager.simpleHeader).debug("OkHttpUtils").setConnectTimeout(100000, TimeUnit.MILLISECONDS);
        } else {
//            refWatcher = RefWatcher.DISABLED;
            //        OkHttpUtils.getInstance().addCommonHeaders(ApiManager.simpleHeader).setConnectTimeout(100000, TimeUnit.MILLISECONDS);
        }

        initCrashHandler();

        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotification = new Notification(R.mipmap.ic_launcher, getString(R.string.app_name), System.currentTimeMillis());
        getIPAddress();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    private void initCrashHandler() {
        if (Constant.DEBUG) {
//        if(false){
            MyCrashHandler crashHandler = MyCrashHandler.getMyCrashHandler();
            crashHandler.init(MyApplication.this);
            Thread.setDefaultUncaughtExceptionHandler(crashHandler);
        } else {

        }
    }


    public void setPhone(String phone) {
        SpUtil.setString(this, "mobilePhone", phone);
    }

    public String getPhone() {
        String loginKey = SpUtil.getString(this, "mobilePhone");
        return loginKey;
    }


    public void setLoginKey(String loginKey) {
        SpUtil.setString(this, "loginKey", loginKey);
    }

    public String getLoginKey() {
        String loginKey = SpUtil.getString(this, "loginKey");
        return loginKey;
    }


    public NotificationManager getmNotificationManager() {
        return mNotificationManager;
    }

    public void setmNotificationManager(NotificationManager mNotificationManager) {
        this.mNotificationManager = mNotificationManager;
    }

    public Notification getmNotification() {
        return mNotification;
    }

    public void setmNotification(Notification mNotification) {
        this.mNotification = mNotification;
    }


    public static int getMainTid() {
        return mainTid;
    }

    public static Handler getHandler() {
        return handler;
    }

    public void logout() {
        setLoginKey("");
    }

    public void initThirdKey() {
//        PlatformConfig.setWeixin("wx583b78e9ffee5bcf","f4c1f46667bcc0b59ccbed92359ed3a8");
        PlatformConfig.setWeixin(Constant.APP_ID_WX, Constant.SECRET_WX);
        PlatformConfig.setQQZone(Constant.APP_ID_QQ, Constant.SECRET_QQ);
        //   PlatformConfig.setQQZone("101366980", "2071d4a2e4d5d6c7c3b4f26c3ebb24a9");
        PlatformConfig.setSinaWeibo("1515533922", " 564becb645c9a316303f8f10021f92a9", "http://sns.whalecloud.com");
    }

    public static String getIPAddress() {
        NetworkInfo info = ((ConnectivityManager) instance
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }

            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                WifiManager wifiManager = (WifiManager) instance.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
                return ipAddress;
            }
        } else {
            //当前无网络连接,请在设置中打开网络
        }
        return null;
    }

    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip
     * @return
     */
    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }
}
