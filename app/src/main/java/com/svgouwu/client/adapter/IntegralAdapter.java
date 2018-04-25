package com.svgouwu.client.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.svgouwu.client.MyApplication;
import com.svgouwu.client.R;

/**
 * Created by melon on 2017/6/25.
 */

public class IntegralAdapter extends BaseAdapter {
    private Context ctx;
    public IntegralAdapter(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_integral, parent, false);
        }
        return convertView;
    }
}
