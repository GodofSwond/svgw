package com.svgouwu.client.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.svgouwu.client.R;

/**
 * 图片轮播适配器
 */
public class NetworkImageHolderView implements Holder<String> {
    private View rootview;
    private ImageView imageView;

    @Override
    public View createView(Context context) {
        rootview = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.goods_item_head_img, null);
        imageView = (ImageView) rootview.findViewById(R.id.sdv_item_head_img);
        return rootview;
    }

    @Override
    public void UpdateUI(Context context, int position, String data) {
        imageView.setImageURI(Uri.parse(data));
    }
}
