package com.svgouwu.client;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by topwolf on 2017/5/10.
 */

public class SimpleFragment extends Fragment {


    public static final String ARGUMENT = "args";
    public static final String RESPONSE = "response";
    private String mArgument;
    public SimpleFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mArgument = bundle.getString(ARGUMENT);
        }
    }

    public static Fragment getInstance(String args){
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT,args);
        Fragment fragment = new SimpleFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    //Fragment.setTargetFragment ，这个方法，一般就是用于当前fragment由别的fragment启动，在完成操作后返回数据的，
}
