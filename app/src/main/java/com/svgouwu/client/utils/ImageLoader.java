package com.svgouwu.client.utils;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.svgouwu.client.R;

import java.io.File;

/**
 * Created by topwolf on 2017/6/27.
 */

public class ImageLoader {
    static RequestOptions options = new RequestOptions()
            .fitCenter()
            .placeholder(R.drawable.ic_default_bg)
            .error(R.drawable.ic_default_bg)
            .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存全尺寸
            .priority(Priority.HIGH);

    public static void with(Context context, String imageUrl, View view) {
        with(context, imageUrl, view, options);
    }

    public static void with(Context context, String imageUrl, View view, RequestOptions requestOptions) {
        ImageView iv;
        if (view instanceof ImageView) {
            iv = (ImageView) view;

            Glide.with(context)                             //配置上下文
                    .load(imageUrl)      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                    .apply(requestOptions)
                    .into(iv);
        } else {
            throw new RuntimeException("Not ImageView");
        }
    }
}
