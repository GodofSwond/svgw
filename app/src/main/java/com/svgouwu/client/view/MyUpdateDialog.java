package com.svgouwu.client.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.svgouwu.client.R;
import com.svgouwu.client.bean.VersionInfo;
import com.svgouwu.client.utils.CommonUtils;
import com.svgouwu.client.utils.SpUtil;

/**
 * 等待对话框
 *
 * @author HJGANG
 */
public class MyUpdateDialog extends Dialog {
    public Button btu_on;
    public Button btu_off;
    public LinearLayout ll_operation;
    public ProgressBar progress;
    public TextView shuzhi,tv_update_content;
    private final LinearLayout ll_update_progress;
    private final CheckBox cb_update_ignore;

    public MyUpdateDialog(final Context context, final VersionInfo verInfo) {
        super(context, R.style.MyUpdateProgressDialog);
        this.setContentView(R.layout.dialog_my_update);

        ll_update_progress = (LinearLayout) findViewById(R.id.ll_update_progress);
        btu_on = (Button) findViewById(R.id.btn_update_download);
        btu_off = (Button) findViewById(R.id.btn_update_cancel);
        progress = (ProgressBar) findViewById(R.id.progress);
        shuzhi = (TextView) findViewById(R.id.tv_update_progress_num);
        tv_update_content = (TextView) findViewById(R.id.tv_update_content);
        ll_operation = (LinearLayout) findViewById(R.id.ll_operation);

        cb_update_ignore = (CheckBox) findViewById(R.id.cb_update_ignore);
        cb_update_ignore.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SpUtil.setInt(context,"ignoreVer",verInfo.build);
                if(isChecked){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            MyUpdateDialog.this.dismiss();
                        }
                    }, 700);
                }
            }
        });

        //强制升级不显示忽略
        if(verInfo.isForce == 1){
            cb_update_ignore.setVisibility(View.GONE);
            btu_off.setVisibility(View.GONE);
        }

        String version = "最新版本: "+verInfo.version +"\n\n";
        String updateContent = "更新内容：\n"+verInfo.content.replace("\\","");
        tv_update_content.setText(version + updateContent);
    }

    public void showProgressBar() {
        ll_update_progress.setVisibility(View.VISIBLE);
        ll_operation.setVisibility(View.GONE);
    }

    public void hideIgnore(){
        cb_update_ignore.setVisibility(View.GONE);
    }
}
