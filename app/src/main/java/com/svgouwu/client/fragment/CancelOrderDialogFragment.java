package com.svgouwu.client.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.svgouwu.client.R;
import com.svgouwu.client.bean.ListDialogItem;
import com.svgouwu.client.utils.MyViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by topwolf on 2017/7/23.
 */

@SuppressLint("ValidFragment")
public class CancelOrderDialogFragment extends DialogFragment {

    private List<ListDialogItem> items = new ArrayList<>();
    private ListView mListView;
    private int selectPos = 0;
    public CancelOrderDialogFragment(){
        super();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_cancel_order_dialog, null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view);
        mListView = (ListView) view.findViewById(R.id.mListView);
        Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        Button btn_ok = (Button) view.findViewById(R.id.btn_ok);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                OnConfirmListener listener = (OnConfirmListener) getActivity();
                if(listener!=null){
                    listener.onConfirm(items.get(selectPos));
                }
            }
        });
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        mXRecyclerView.setLayoutManager(layoutManager);
//        mXRecyclerView.setPullRefreshEnabled(false);
////        mXRecyclerView.setNoMore(true);
//        mXRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

//        CommonAdapter<ListDialogItem> mAdapter = new CommonAdapter<ListDialogItem>(getContext(),R.layout.item_list_dialog,items) {
//            @Override
//            protected void convert(ViewHolder holder, ListDialogItem listDialogItem, int position) {
//                holder.setText(R.id.tv_item_name, listDialogItem.name);
//            }
//        };

        //取消原因（1=>改选其他商品 2=>改选其他配送方式 3 =>'改选其他卖家 4=>其他原因
        items.add(new ListDialogItem(1,"改选其他商品"));
        items.add(new ListDialogItem(2,"改选其他配送方式"));
        items.add(new ListDialogItem(3,"改选其他卖家"));
        items.add(new ListDialogItem(4,"其他原因"));

        mListView.setAdapter(new MyAdapter());
        return builder.create();
    }

    private class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return items ==null?0:items.size();
        }

        @Override
        public Object getItem(int position) {
            return items ==null?null:items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_cancel_order_dialog, parent, false);
            }
            final ListDialogItem item = items.get(position);
            TextView tv_item_name = MyViewHolder.get(convertView,R.id.tv_item_name);
            CheckBox cb_select = MyViewHolder.get(convertView,R.id.cb_select);

            tv_item_name.setText(item.name);
            if(position ==selectPos){
                cb_select.setChecked(true);
            }else{
                cb_select.setChecked(false);
            }
//            btn_ok.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dismiss();
//                    OnConfirmListener listener = (OnConfirmListener) getActivity();
//                    if(listener!=null){
//                        listener.onConfirm(item);
//                    }
//                }
//            });

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectPos = position;
                    notifyDataSetChanged();
                }
            });
            return convertView;
        }
    }

    public interface OnConfirmListener
    {
        void onConfirm(ListDialogItem item);
    }
}
