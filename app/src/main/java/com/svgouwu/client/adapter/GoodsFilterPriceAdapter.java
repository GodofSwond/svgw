package com.svgouwu.client.adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.svgouwu.client.MyApplication;
import com.svgouwu.client.R;
import com.svgouwu.client.bean.Classify;
import com.svgouwu.client.bean.PriceFilter;
import com.svgouwu.client.utils.LogUtils;

import java.util.List;

/**
 * Created by melon on 2017/7/20.
 */

public class GoodsFilterPriceAdapter extends BaseAdapter {
    private List<PriceFilter> priceFilters;
    private boolean isShowAll;
    private int checkedPos = -1;
    private int preCheckedPos = -1;
    private boolean isClickAll; //true是点击了全部。false点击了item

    public GoodsFilterPriceAdapter(List<PriceFilter> priceFilters) {
        this.priceFilters = priceFilters;
    }

    @Override
    public int getCount() {
        if (priceFilters == null) return 0;
        if (priceFilters.size() > 4 && !isShowAll) {
            return 4;
        }
        return priceFilters.size();
    }

    public void showAll(TextView textView) {
        isShowAll = !isShowAll;
        isClickAll = true;

        if (isShowAll) {
            Drawable drawable = textView.getContext().getResources().getDrawable(R.drawable.ic_arrow_up);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            textView.setCompoundDrawables(null, null, drawable, null);
        } else {
            Drawable drawable = textView.getContext().getResources().getDrawable(R.drawable.ic_arrow_down);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            textView.setCompoundDrawables(null, null, drawable, null);
        }
        notifyDataSetChanged();

        MyApplication.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isClickAll = false;
            }
        }, 500);
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
        PriceFilter priceFilter = priceFilters.get(position);
        TextView view = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goods_filter_cate, parent, false);
        view.setText(priceFilter.minprice+" - "+priceFilter.maxprice+"("+priceFilter.num+")");
        LogUtils.e("position: "+position+"--checkedPos: "+checkedPos+"--preCheckedPos: "+preCheckedPos);
        if (position == checkedPos) {
            if (preCheckedPos == checkedPos &&!isClickAll) {
                //取消选中
                view.setTextColor(parent.getResources().getColor(R.color.grey_color2));
                view.setBackgroundResource(R.drawable.shape_gray_corner_bg);
                preCheckedPos = -1;
            } else {
                // 被选中
                view.setTextColor(parent.getResources().getColor(R.color.color_money));
                view.setBackgroundResource(R.drawable.shape_red_corner_bg);
                preCheckedPos = position;
                view.setText("√ "+view.getText());
            }
        } else {
            // 未选中
            view.setTextColor(parent.getResources().getColor(R.color.grey_color2));
            view.setBackgroundResource(R.drawable.shape_gray_corner_bg);
        }
        return view;
    }

    public void check(int position) {
        if(position == checkedPos){
            checkedPos = -1;
            preCheckedPos = -1;
        }else {
            checkedPos = position;
        }
        notifyDataSetChanged();
    }

    public PriceFilter getSelectedPriceArea() {
        if(checkedPos != -1) {
            return priceFilters.get(checkedPos);
        }
        return null;
    }
}
