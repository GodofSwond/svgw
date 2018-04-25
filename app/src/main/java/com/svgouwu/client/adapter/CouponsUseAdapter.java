package com.svgouwu.client.adapter;

import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.svgouwu.client.R;
import com.svgouwu.client.bean.OrderConfirmResult;

import java.util.List;

/**
 * Created by Administrator on 2017/9/11.
 */
public class CouponsUseAdapter extends BaseAdapter {

    private FragmentActivity disListActivity;
    private List<OrderConfirmResult.OrderCoupon> useList;
    private int temp = -1;

    public CouponsUseAdapter(FragmentActivity disListActivity, List<OrderConfirmResult.OrderCoupon> useList) {
        this.disListActivity = disListActivity;
        this.useList = useList;
    }


    @Override
    public int getCount() {
        return useList.size();
    }

    @Override
    public Object getItem(int i) {
        return useList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(disListActivity).inflate(R.layout.item_coupons_use, viewGroup, false);
            viewHolder.tv_useName = (TextView) view.findViewById(R.id.item_coupons_use_tvName);
            viewHolder.tv_useMoney = (TextView) view.findViewById(R.id.item_coupons_use_tvMoney);
            viewHolder.tv_useTime = (TextView) view.findViewById(R.id.item_coupons_use_tvTime);
            viewHolder.useCb = (CheckBox) view.findViewById(R.id.item_coupons_cb);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tv_useName.setText(useList.get(position).couponName);
        Float f = Float.parseFloat(useList.get(position).couponValue);//去掉小数点
        int fValue = Math.round(f);
        viewHolder.tv_useMoney.setText(fValue + "");
        viewHolder.tv_useTime.setText(useList.get(position).startTime + " 至 " + useList.get(position).endTime);

        // 根据isSelected来设置checkbox的选中状况
        viewHolder.useCb.setId(position);//对checkbox的id进行重新设置为当前的position
        viewHolder.useCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isSelected) {
                if (isSelected) {
                    //实现checkbox的单选功能,同样适用于radiobutton
                    if (temp != -1) {
                        //找到上次点击的checkbox,并把它设置为false,对重新选择时可以将以前的关掉
                        CheckBox tempCheckBox = (CheckBox) disListActivity.findViewById(temp);
                        if (tempCheckBox != null) {
                            tempCheckBox.setChecked(false);
                        }
                    }
                    temp = compoundButton.getId();//保存当前选中的checkbox的id值
                    viewHolder.useCb.setVisibility(View.VISIBLE);
                } else {
                    temp = -1;
                    viewHolder.useCb.setVisibility(View.GONE);
                }
            }
        });
        return view;
    }

    public class ViewHolder {
        public TextView tv_useName;
        public TextView tv_useMoney;
        public TextView tv_useTime;
        public CheckBox useCb;
    }
}
