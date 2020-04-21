package com.demo.module.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.module.base.BaseFragment;
import com.demo.R;

/**
 * @author weiyang.an
 * @version 1.0 2018/6/12
 */
public class ProfileFragment extends BaseFragment {

    private static final String TAG = ProfileFragment.class.getSimpleName();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.main_frag, container, false);

        return view;
    }

}
