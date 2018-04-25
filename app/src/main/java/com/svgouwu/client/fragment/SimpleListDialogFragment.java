package com.svgouwu.client.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.svgouwu.client.R;
import com.svgouwu.client.activity.AddressListActivity;
import com.svgouwu.client.bean.Address;
import com.svgouwu.client.bean.ListDialogItem;
import com.svgouwu.client.utils.MyViewHolder;
import com.svgouwu.client.utils.ToastUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by topwolf on 2017/7/23.
 */

@SuppressLint("ValidFragment")
public class SimpleListDialogFragment extends DialogFragment {

    private SureListener mSureListener;
    private List<ListDialogItem> items ;
    private ListView mListView;
    public SimpleListDialogFragment(){
        super();
    }

    public SimpleListDialogFragment(List<ListDialogItem> items ){
        this.items = items;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_simple_list_dialog, null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view);
        mListView = (ListView) view.findViewById(R.id.mListView);
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
        mListView.setAdapter(new MyAdapter());
        return builder.create();
    }

    public interface SureListener
    {
        void onSureListener();
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
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list_dialog, parent, false);
            }
            final ListDialogItem item = items.get(position);
            TextView tv_item_name = MyViewHolder.get(convertView,R.id.tv_item_name);
            tv_item_name.setText(item.name);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    DialogItemClickListener listener = (DialogItemClickListener) getActivity();
                    if(listener!=null){
                        listener.onItemClick(item);
                    }
                }
            });
            return convertView;
        }
    }

    public interface DialogItemClickListener
    {
        void onItemClick(ListDialogItem item);
    }
}
