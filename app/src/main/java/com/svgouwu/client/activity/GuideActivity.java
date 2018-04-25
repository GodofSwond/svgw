package com.svgouwu.client.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.svgouwu.client.BaseActivity;
import com.svgouwu.client.R;
import com.svgouwu.client.adapter.GuidePageAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.svgouwu.client.Constant.H5_BASE_URL;

/**
 * Created by Administrator on 2017/10/24.
 */

public class GuideActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private ViewPager vp;
    private int[] imageIdArray;//图片资源的数组
    private List<View> viewList;//图片资源的集合
    private ViewGroup vg;//放置圆点

    //实例化原点View
    private ImageView iv_point;
    private ImageView[] ivPointArray;


    @BindView(R.id.activity_guide_ib_start)
    RelativeLayout start;
    @Override
    protected int getContentView() {
        return R.layout.activity_guide;
    }

    @Override
    public void initViews() {

        //加载ViewPager
        initViewPager();
        //加载底部圆点
        initPoint();
    }

    private void initPoint() {
        //这里实例化LinearLayout
        vg = (ViewGroup) findViewById(R.id.activity_guide_ll_point);
        //根据ViewPager的item数量实例化数组
        ivPointArray = new ImageView[viewList.size()];
        //循环新建底部圆点ImageView，将生成的ImageView保存到数组中
        int size = viewList.size();
        for (int i = 0; i < size; i++) {
            iv_point = new ImageView(this);
            iv_point.setLayoutParams(new ViewGroup.LayoutParams(20, 20));
            iv_point.setPadding(30, 0, 30, 0);//left,top,right,bottom
            ivPointArray[i] = iv_point;
            //第一个页面需要设置为选中状态，这里采用两张不同的图片
            if (i == 0) {
                iv_point.setBackgroundResource(R.color.color_touming);
            } else {
                iv_point.setBackgroundResource(R.color.color_touming);
            }
            //将数组中的ImageView加入到ViewGroup
            vg.addView(ivPointArray[i]);
        }
    }


    /**
     * 加载ViewPager
     */

    private void initViewPager() {
        vp = (ViewPager) findViewById(R.id.activity_guide_vp);
        //实例化图片资源
        imageIdArray = new int[]{R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3};
        viewList = new ArrayList<>();
        //获取一个Layout参数，设置为全屏
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        //循环创建View并加入到集合中
        int len = imageIdArray.length;
        for (int i = 0; i < len; i++) {
            //new ImageView并设置全屏和图片资源
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(params);
            imageView.setBackgroundResource(imageIdArray[i]);

            //将ImageView加入到集合中
            viewList.add(imageView);
        }

        //View集合初始化好后，设置Adapter
        vp.setAdapter(new GuidePageAdapter(viewList));
        //设置滑动监听
        vp.setOnPageChangeListener(this);
    }

    @Override
    public void initListener() {
    }

    @Override
    public void initData() {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        //判断是否是最后一页，若是则显示按钮
        if (position == imageIdArray.length - 1) {
            start.setVisibility(View.VISIBLE);
        } else {
            start.setVisibility(View.GONE);
        }
    }


    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @OnClick({R.id.activity_guide_ib_start})
    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.activity_guide_ib_start:
                Intent it = new Intent();
//        it.setClass(SplashActivity.this, WebViewActivity.class);
                it.setClass(GuideActivity.this, MainActivity.class);
//        it.putExtra("url", "https://wxn.qq.com/cmsid/WXN2017062803464500");
                it.putExtra("url", H5_BASE_URL);
                startActivity(it);
                finish();
                break;
        }
    }
}
