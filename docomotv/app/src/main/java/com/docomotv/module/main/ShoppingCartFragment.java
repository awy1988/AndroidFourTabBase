package com.docomotv.module.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.docomotv.R;
import com.docomotv.module.base.BaseFragment;

/**
 * @author weiyang.an
 * @version 1.0 2018/6/12
 */
public class ShoppingCartFragment extends BaseFragment {

    private static final String TAG = ShoppingCartFragment.class.getSimpleName();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.main_frag, container, false);
        return view;
    }

}
