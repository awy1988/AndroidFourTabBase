package com.demo.appmvp.ui.base;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewbinding.ViewBinding;
import com.demo.appmvp.ui.App;
import com.demo.widget.CustomProgressDialog;

/**
 * Base Activity
 */
public abstract class BaseFragmentActivity extends AppCompatActivity {

    private static final String TAG = BaseFragmentActivity.class.getSimpleName();

    /** 用来找layout文件夹下的xml布局文件 */
    protected LayoutInflater mInflater;

    /** 进度条对话框对象 */
    CustomProgressDialog mProgressDialog;

    /** 加载中标题 */
    private String mLoadingTitle;

    /** 应用内部广播管理对象 */
    private LocalBroadcastManager mLocalBroadcastManager;

    private BroadcastReceiver mLocalReceiver;

    /** 用户自定义ActionBar */
    private View mCustomerActionBar;

    private InputMethodManager imm;
    protected ViewDataBinding mDataBinding;
    protected ViewBinding mViewBinding;

    /**
     * 实例被创建时调用的第一个方法
     *
     * @param savedInstanceState 存储Activity的信息（包括了每个UI的信息和用户自定义在其中存储的信息）
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //        DaggerInjectHelper.inject(this);

        super.onCreate(savedInstanceState);
        mProgressDialog = new CustomProgressDialog(this, "努力加载中...");
        mProgressDialog.setCancelable(false);
        mInflater = getLayoutInflater();
        mCustomerActionBar = null;

        mViewBinding = getViewBinding();

        if (isDataBindingEnabled()) {
            mDataBinding = DataBindingUtil.setContentView(this, getContentView());
            mDataBinding.setLifecycleOwner(this);
        } else if (mViewBinding != null) {
            // viewBinding
            setContentView(mViewBinding.getRoot());
        } else {
            setContentView(getContentView());
        }
    }

    /**
     * 是否使用 DataBinding
     */
    protected boolean isDataBindingEnabled() {
        return false;
    }

    /**
     * 使用ViewBinding时需要复写此方法
     */
    protected ViewBinding getViewBinding() {
        return null;
    }

    /**
     * 取得自定义工具条（ActionBar）
     */
    protected View getCustomerActionBar() {
        return mCustomerActionBar;
    }

    public void showLoadingDialog() {
        showProgressBar(true);
    }

    protected void showLoadingDialog(String title) {
        this.mLoadingTitle = title;
        showProgressBar(true);
    }

    /**
     * 隐藏加载中
     */
    public void hideLoadingDialog() {
        showProgressBar(false);
    }

    protected void showBottomToast(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * 发送应用内广播
     */
    protected void sendLocalBroadcast(String action) {
        Intent intent = new Intent();
        intent.setAction(action);
        LocalBroadcastManager.getInstance(App.getContext()).sendBroadcast(intent);
    }

    /**
     * 发送应用内广播
     */
    protected void sendLocalBroadcast(Intent intent) {
        LocalBroadcastManager.getInstance(App.getContext()).sendBroadcast(intent);
    }

    public void setReceiver(BroadcastReceiver receiver) {
        mLocalReceiver = receiver;
    }

    /**
     * 注册广播接收器
     */
    protected void registerReceiver(String action) {

        mLocalBroadcastManager = LocalBroadcastManager.getInstance(App.getContext());

        if (mLocalReceiver != null) {
            final IntentFilter filter = new IntentFilter();
            filter.addAction(action);
            mLocalBroadcastManager.registerReceiver(mLocalReceiver, filter);
        }
    }

    /**
     * 注册广播接收器
     */
    protected void registerReceiver(String action, BroadcastReceiver receiver) {
        setReceiver(receiver);
        registerReceiver(action);
    }

    /**
     * 取消广播
     */
    protected void unregisterReceiver() {

        if (mLocalBroadcastManager != null && mLocalReceiver != null) {
            mLocalBroadcastManager.unregisterReceiver(mLocalReceiver);
        }
    }

    /**
     * get content view layout id
     *
     * @return layout id
     */
    protected abstract int getContentView();

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }

        imm = null;
        unregisterReceiver();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    private void showProgressBar(boolean show) {
        if (show) {
            if (mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }
        } else {
            if (mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        }
    }

    private void cancelProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }
}
