package com.demo.module.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.demo.module.App;
import com.demo.widget.CustomProgressDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Fragmen扩展用基类
 */
public class BaseFragment extends Fragment {

    public static final String TAG = BaseFragment.class.getSimpleName();

    protected LayoutInflater mInflater;

    /** 进度条对话框对象 */
    CustomProgressDialog mProgressDialog;

    private LocalBroadcastManager mLocalBroadcastManager;// 应用内部广播管理对象

    private BroadcastReceiver mLocalReceiver;

    private Unbinder unbinder;

    @Override
    public void onAttach(@NonNull Context context) {
//        DaggerInjectHelper.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        mInflater = LayoutInflater.from(getActivity());

        mProgressDialog = new CustomProgressDialog(getActivity(), "努力加载中...");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
    }

    /**
     * 显示加载中
     */
    protected void showLoadingDialog(){
        if (getActivity() instanceof BaseFragmentActivity) {
            ((BaseFragmentActivity) getActivity()).showLoadingDialog();
        }
    }

    protected void showLoadingDialog(String title){

        if (getActivity() instanceof BaseFragmentActivity) {
            ((BaseFragmentActivity) getActivity()).showLoadingDialog(title);
        }
    }


    /**
     * 隐藏加载中
     */
    protected void hideLoadingDialog(){
        if (getActivity() instanceof BaseFragmentActivity) {
            ((BaseFragmentActivity) getActivity()).hideLoadingDialog();
        }
    }

    public void reload() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 为了解决 activity关闭时抛出 android.view.WindowLeaked（窗体内存泄露） 错误
        if(mProgressDialog != null && mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }

        if(unbinder != null) {
            unbinder.unbind();
        }
    }


    public void setReceiver(BroadcastReceiver receiver){
        mLocalReceiver = receiver;
    }

    /**
     * 注册广播接收器
     * */
    protected void registerReceiver(String action){

        mLocalBroadcastManager = LocalBroadcastManager.getInstance(App.getContext());

        if(mLocalReceiver != null){
            final IntentFilter filter = new IntentFilter();
            filter.addAction(action);
            mLocalBroadcastManager.registerReceiver(mLocalReceiver, filter);
        }
    }

    /**
     * 发送应用内广播
     * */
    protected void sendLocalBroadcast(String action){
        Intent intent = new Intent();
        intent.setAction(action);
        LocalBroadcastManager.getInstance(App.getContext()).sendBroadcast(intent);
    }

    /**
     * 发送应用内广播
     * */
    protected void sendLocalBroadcast(Intent intent){
        LocalBroadcastManager.getInstance(App.getContext()).sendBroadcast(intent);
    }

    /**
     * 注册广播接收器
     * */
    protected void registerReceiver(String action, BroadcastReceiver receiver){
        setReceiver(receiver);
        registerReceiver(action);
    }

    public interface OnReloadListener {
        void onReload();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        unregisterReceiver();
    }

    /**
     * 取消广播
     * */
    protected void unregisterReceiver(){
        if(mLocalReceiver != null) {
            LocalBroadcastManager.getInstance(App.getContext()).unregisterReceiver(mLocalReceiver);
        }
    }
}
