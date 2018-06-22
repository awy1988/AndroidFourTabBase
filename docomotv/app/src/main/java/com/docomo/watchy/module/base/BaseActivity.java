package com.docomo.watchy.module.base;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.docomo.watchy.module.App;
import com.docomo.watchy.widget.CustomProgressDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 *
 * Base Activity
 *
 * @author weiyang.an
 * @version 1.0 2018/6/11
 */
public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = BaseActivity.class.getSimpleName();

    /** 用来找layout文件夹下的xml布局文件 */
    protected LayoutInflater mInflater;

    /** 进度条对话框对象 */
    CustomProgressDialog mProgressDialog;

    /** 加载中标题 */
    private String mLoaddingTitle;

    /** 应用内部广播管理对象 */
    private LocalBroadcastManager mLocalBroadcastManager;

    private BroadcastReceiver mLocalReceiver;

    /** 用户自定义ActionBar */
    private View mCustomerActionBar;

    private Unbinder unbinder;
    private InputMethodManager imm;
    protected ViewDataBinding binding;

    /**
     * 实例被创建时调用的第一个方法
     *
     * @param savedInstanceState 存储Activity的信息（包括了每个UI的信息和用户自定义在其中存储的信息）
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProgressDialog = new CustomProgressDialog(this, "努力加载中...");
        mProgressDialog.setCancelable(false);
        mInflater = getLayoutInflater();
        mCustomerActionBar = null;


        if (isDataBindingEnabled()) {
           binding = DataBindingUtil.setContentView(this, getContentView());
        } else {
            setContentView(getContentView());
        }

        unbinder = ButterKnife.bind(this);

        if (isImmersionBarEnabled()) {
            // 是否使用沉浸式状态栏
        }
    }


    /**
     * 是否使用沉浸式状态栏
     * Is immersion bar enabled boolean.
     *
     * @return boolean true-使用沉浸式状态栏 false-不使用沉浸式状态栏
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    /**
     * 是否使用 DataBinding
     * @return
     */
    protected boolean isDataBindingEnabled() {
        return false;
    }

//    /**
//     * 设定ActionBar的标题
//     * @param title 要设定的标题
//     */
//    protected void setCustomActionBarTitle(String title){
//        if(title == null || mCustomerActionBar == null){
//            return;
//        }
//
//        TextView tvTitle = (TextView)mCustomerActionBar.findViewById(R.id.tv_common_ab_title);
//
//        if (tvTitle != null) {
//            tvTitle.setText(title);
//        }
//    }
//
//    /**
//     * 设定ActionBar的返回按钮监听器
//     * @param listener 要设定的监听器对象
//     */
//    protected void setActionBarBackButtonListener(View.OnClickListener listener){
//        if(listener == null || mCustomerActionBar == null){
//            return;
//        }
//
//        View btnBack = mCustomerActionBar.findViewById(R.id.ll_common_ab_back_btn);
//        if (btnBack != null) {
//            btnBack.setOnClickListener(listener);
//        }
//    }

//    /**
//     * 自定义工具条（ActionBar）
//     *
//     * @param toolbar 自定义ActionBar视图
//     */
//    protected void setCustomActionbar(View toolbar){
//
//        if(toolbar == null){
//            return;
//        }
//        View btnBack = findViewById(R.id.ll_common_ab_back_btn);
//        if (btnBack != null) {
//            btnBack.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    KeyboardUtils.hideKeyboard(BaseActivity.this);
//                    finish();
//                }
//            });
//        }
//        mCustomerActionBar = toolbar;
//    }

    /**
     * 取得自定义工具条（ActionBar）
     */
    protected View getCustomerActionBar(){
        return mCustomerActionBar;
    }

    public void showLoadingDialog(){
        showProgressBar(true);
    }

    protected void showLoadingDialog(String title){
        this.mLoaddingTitle = title;
        showProgressBar(true);
    }

    /**
     * 隐藏加载中
     */
    public void hideLoadingDialog(){
        showProgressBar(false);
    }

    protected void showButtomToast(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.show();
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
     * 注册广播接收器
     * */
    protected void registerReceiver(String action, BroadcastReceiver receiver){
        setReceiver(receiver);
        registerReceiver(action);
    }

    /**
     * 取消广播
     * */
    protected void unregisterReceiver(){

        if(mLocalBroadcastManager != null && mLocalReceiver != null){
            mLocalBroadcastManager.unregisterReceiver(mLocalReceiver);
        }
    }

    /**
     * get content view layout id
     * @return layout id
     */
    protected abstract int getContentView();

//    /**
//     * 获取列表无数据时显示的view
//     * @return
//     */
//    protected View getListEmptyView() {
//        View view = mInflater.inflate(R.layout.common_exception_hint,null);
//
//        ImageView ivBlank = view.findViewById(R.id.iv_blank);
//        TextView tvTitle = view.findViewById(R.id.tv_blank_title);
//
//        ivBlank.setVisibility(View.VISIBLE);
//        tvTitle.setVisibility(View.VISIBLE);
//        tvTitle.setText("暂无数据");
//
//        return view;
//    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(mProgressDialog != null && mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }

        if(unbinder != null) {
            unbinder.unbind();
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
            if(mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
            if(!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }
        } else {
            if(mProgressDialog.isShowing()){
                mProgressDialog.dismiss();
            }
        }
    }

    private void cancelProgress() {
        if ( mProgressDialog!=null && mProgressDialog.isShowing() ){
            mProgressDialog.cancel();
        }
    }

}