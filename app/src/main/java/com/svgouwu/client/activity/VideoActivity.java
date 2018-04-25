package com.svgouwu.client.activity;

import android.view.View;

import com.svgouwu.client.BaseActivity;
import com.svgouwu.client.R;
import com.svgouwu.client.utils.UmengStat;
import com.umeng.analytics.MobclickAgent;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by topwolf on 2017/6/23.
 */

public class VideoActivity extends BaseActivity {

    private JCVideoPlayerStandard jcVideoPlayerStandard;
    String s1 = "http://2449.vod.myqcloud.com/2449_22ca37a6ea9011e5acaaf51d105342e3.f20.mp4";
    String s2 = "http://player.youku.com/player.php/sid/XMjUyODI2NDc2MA==/v.swf";//不支持swf格式的视频播放

    @Override
    protected int getContentView() {
        return R.layout.activity_video;
    }

    @Override
    public void initViews() {
        jcVideoPlayerStandard = (JCVideoPlayerStandard) findViewById(R.id.mPlayer);
        jcVideoPlayerStandard.setUp(s1, jcVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "视频标题");
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View view) {

    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(UmengStat.LIVEPAGE); //
        MobclickAgent.onPause(this);
        JCVideoPlayer.releaseAllVideos();
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(UmengStat.LIVEPAGE); //统计页面
        MobclickAgent.onResume(this);          //统计时长
    }
}
