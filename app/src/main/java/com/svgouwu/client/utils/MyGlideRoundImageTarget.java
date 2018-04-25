package com.svgouwu.client.utils;

import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.request.target.BitmapImageViewTarget;

/**
 * Created by melon on 2017/6/29.
 * Email 530274554@qq.com
 */

public class MyGlideRoundImageTarget extends BitmapImageViewTarget {
    private ImageView imageView;

    public MyGlideRoundImageTarget(ImageView view) {
        super(view);
        this.imageView = view;
    }

    @Override
    protected void setResource(Bitmap resource) {
        RoundedBitmapDrawable circularBitmapDrawable =
                RoundedBitmapDrawableFactory.create(imageView.getContext().getResources(), resource);
        circularBitmapDrawable.setCircular(true);
        imageView.setImageDrawable(circularBitmapDrawable);
    }
}
