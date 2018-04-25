package com.svgouwu.client.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.view.View;

import com.svgouwu.client.Constant;
import com.svgouwu.client.R;
import com.svgouwu.client.bean.VersionInfo;
import com.svgouwu.client.utils.CommonUtils;
import com.svgouwu.client.utils.SystemHelper;
import com.svgouwu.client.utils.ToastUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@SuppressLint("HandlerLeak")
public class UpdateManager {

    private Context mContext;
    /* 下载包安装路径 */
    // private static final String savePath = "/sdcard/VeryNCShop/";
    private static final String savePath = Constant.CACHE_DIR;

    private static final String saveFileName = savePath + "/" + "android_verync_shop.apk";

    private static final int DOWN_UPDATE = 1;

    private static final int DOWN_OVER = 2;

    private int progress;

    private Thread downLoadThread;
    private VersionInfo verInfo;
    private boolean interceptFlag = false;

    private MyUpdateDialog mpDialog;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWN_UPDATE:
                    mpDialog.shuzhi.setText(mContext.getResources().getString(R.string.text_download) + ":" + (progress + 1) + "%");
                    mpDialog.progress.setProgress(progress);
                    break;
                case DOWN_OVER:
                    ToastUtil.toast(R.string.text_load_ok_install);
                    mpDialog.dismiss();
                    installApk();
                    break;
                default:
                    break;
            }
        }

        ;
    };

    public UpdateManager(Context context, VersionInfo verInfo) {
        this.mContext = context;
        this.verInfo = verInfo;
    }


    /**
     * 外部接口让主Activity调用
     */
    public void checkUpdateInfo() {
        mpDialog = new MyUpdateDialog(UpdateManager.this.mContext, verInfo);
        mpDialog.setCanceledOnTouchOutside(false);//设置点击Dialog外部任意区域关闭Dialog

        final int curVer = SystemHelper.getAppVersionCode(mContext);

        if (verInfo.isForce == 1) {//<=mobile_apk_forced_version
            mpDialog.setCancelable(false);
        }
        mpDialog.show();
        mpDialog.btu_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                    mpDialog.showProgressBar();
                    downLoadThread = new Thread(mdownApkRunnable);
                    downLoadThread.start();
                    mpDialog.btu_on.setVisibility(View.GONE);
                    mpDialog.hideIgnore();
                } else {
                    ToastUtil.toast(R.string.text_no_sdcard);
                }
            }
        });
        mpDialog.btu_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interceptFlag = true;
                mpDialog.dismiss();
                if (verInfo.isForce == 1) {
                    Process.killProcess(Process.myPid());
                }
            }
        });
    }

    private Runnable mdownApkRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                URL url = new URL(verInfo.downloadUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();
                int length = conn.getContentLength();
                InputStream is = conn.getInputStream();

                File file = new File(savePath);
                if (!file.exists()) {
                    file.mkdir();
                }
                String apkFile = saveFileName;
                File ApkFile = new File(apkFile);
                FileOutputStream fos = new FileOutputStream(ApkFile);

                int count = 0;
                byte buf[] = new byte[1024];
                do {
                    int numread = is.read(buf);
                    count += numread;
                    progress = (int) (((float) count / length) * 100);

                    if (progress < 0) progress = 0;

                    // 更新进度
                    mHandler.sendEmptyMessage(DOWN_UPDATE);
                    if (numread <= 0) {
                        // 下载完成通知安装
                        mHandler.sendEmptyMessage(DOWN_OVER);
                        break;
                    }
                    fos.write(buf, 0, numread);
                } while (!interceptFlag);// 点击取消就停止下载.

                fos.close();
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };

    /**
     * 安装apk
     */
    private void installApk() {
        File apkfile = new File(saveFileName);
        if (!apkfile.exists()) {
            return;
        }
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        mContext.startActivity(i);

    }
}
