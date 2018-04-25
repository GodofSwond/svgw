package com.svgouwu.client.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.svgouwu.client.R;

/**
 * Created by topwolf on 2017/7/23.
 */

@SuppressLint("ValidFragment")
public class CommonSureDialogFragment extends DialogFragment {

    private SureListener mSureListener;
    private TextView tv_title;
    private String title ;

    public CommonSureDialogFragment(){
        super();
    }

    public CommonSureDialogFragment(String title ,SureListener mSureListener){
        this.title = title;
        this.mSureListener = mSureListener;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_common_sure_dialog, null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_title.setText(title);
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        TextView tv_sure = (TextView) view.findViewById(R.id.tv_sure);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSureListener !=null){
                    mSureListener.onSureListener();
                    dismiss();
                }
            }
        });
        return builder.create();
    }

    public interface SureListener
    {
        void onSureListener();
    }

}
