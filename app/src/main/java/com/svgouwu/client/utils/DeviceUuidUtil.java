package com.svgouwu.client.utils;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.util.UUID;

public class DeviceUuidUtil {
	private static final String TAG = "DeviceUuidUtil";

	/**
	 * 让uuid和app绑定，卸载之后，重新生成uuid
	 */
	public static synchronized String getRandomUuid(Context ctx) {
		String uuid = SpUtil.getString(ctx,"uuid");
		LogUtils.d(TAG, "uudi: " + uuid);
		if (CommonUtils.isEmpty(uuid)) {
			// 4、随机生成
			uuid = UUID.randomUUID().toString();
			uuid = "88" + uuid.replace("-", "").substring(2);
			LogUtils.e(TAG, "uuid随机生成: " + uuid);
			saveUuid(ctx, uuid);
		}
		return uuid;
	}

	public static String getDeviceId(Context ctx){
		try {
			String deviceId = ((TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();

			if(!CommonUtils.isEmpty(deviceId)){
				return  deviceId;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	public static String getAndroidId(Context ctx){
		try {
			String androidId = Secure.getString(ctx.getContentResolver(), Secure.ANDROID_ID);
			if(!CommonUtils.isEmpty(androidId)){
				return androidId;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 获取手机的mac
	 */
	public static String getPhoneMac(Context ctx) {
		try {
			WifiManager wifiManager = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
			String macAddress = wifiManager.getConnectionInfo().getMacAddress();
			if(!TextUtils.isEmpty(macAddress)){
				return macAddress;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	//获得独一无二的Psuedo ID
	//最终会得到这样的一串ID：00000000-28ee-3eab-ffff-ffffe9374e72
	public static String getUniquePsuedoID() {
		String serial = null;

		String m_szDevIDShort = "35" +
				Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +

				Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +

				Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +

				Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +

				Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +

				Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +

				Build.USER.length() % 10; //13 位

		try {
			serial = android.os.Build.class.getField("SERIAL").get(null).toString();
			//API>=9 使用serial号
			return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
		} catch (Exception exception) {
			//serial需要一个初始化
			serial = "serial"; // 随便一个初始化
		}
		//使用硬件信息拼凑出来的15位号码
		return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
	}

	private static void saveUuid(Context ctx, String uuid) {
		SpUtil.setString(ctx,"uuid", uuid);
	}
}
