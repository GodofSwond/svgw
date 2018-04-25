package com.svgouwu.client.utils;

import android.os.Environment;
import android.text.TextUtils;

import com.svgouwu.client.Constant;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * Created by topwolf on 2017/4/25.
 */

public class SdcardUtils {
    private static final String TAG = "SdcardUtils";
    private static FileOutputStream outputStream;

    static {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            String crash_log_path = Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/svshop/";
            File file_Path = new File(crash_log_path);
            file_Path.mkdirs();
            File file = new File(crash_log_path, "log.txt");
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                outputStream = new FileOutputStream(file, true);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    // 将错误信息保存到SD卡中去！可选的操作！
    @SuppressWarnings("deprecation")
    public static void save2Sd(String msg) {
        if (TextUtils.isEmpty(msg) || !Constant.DEBUG) {
            return;
        }
        Date date = new Date();
        String time = date.toLocaleString();
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            if (outputStream != null) {
                try {
                    outputStream.write(time.getBytes());
                    outputStream.write(msg.getBytes());
                    outputStream.write("\r\n".getBytes());
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 创建SD卡缓存目录
     */
    private static void createCacheDir() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File f = new File(Constant.CACHE_DIR);
            if (f.exists()) {
                LogUtils.d(TAG, "SD卡缓存目录:已存在!");
            } else {
                if (f.mkdirs()) {
                    LogUtils.d(TAG, "SD卡缓存目录:" + f.getAbsolutePath() + "已创建!");
                } else {
                    LogUtils.d(TAG, "SD卡缓存目录:创建失败!");
                }
            }

            File ff = new File(Constant.CACHE_DIR_IMG);
            if (ff.exists()) {
                LogUtils.d(TAG, "SD卡照片缓存目录:已存在!");
            } else {
                if (ff.mkdirs()) {
                    LogUtils.d(TAG, "SD卡照片缓存目录:" + ff.getAbsolutePath() + "已创建!");
                } else {
                    LogUtils.d(TAG, "SD卡照片缓存目录:创建失败!");
                }
            }

            File fff = new File(Constant.CACHE_DIR_IMG_UPLOADING);
            if (fff.exists()) {
                LogUtils.d(TAG, "SD卡待上传照片缓存目录:已存在!");
            } else {
                if (fff.mkdirs()) {
                    LogUtils.d(TAG, "SD卡待上传照片缓存目录:" + fff.getAbsolutePath() + "已创建!");
                } else {
                    LogUtils.d(TAG, "SD卡待上传照片缓存目录:创建失败!");
                }
            }
            File patchDir = new File(Constant.CACHE_DIR_PATCH);
            if (patchDir.exists()) {
                LogUtils.d(TAG, "patch目录:已存在!");
            } else {
                if (patchDir.mkdirs()) {
                    LogUtils.d(TAG, "patch目录:" + patchDir.getAbsolutePath() + "已创建!");
                } else {
                    LogUtils.d(TAG, "patch:创建失败!");
                }
            }
        }
    }

}
