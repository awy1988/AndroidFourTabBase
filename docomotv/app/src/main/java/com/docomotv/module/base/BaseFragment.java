package com.docomotv.module.base;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.docomotv.module.App;
import com.docomotv.widget.CustomProgressDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Fragmen扩展用基类
 *
 * @author weiyang.an
 * @version 1.0 2018/6/11
 */
public class BaseFragment extends Fragment {

    public static final String TAG = BaseFragment.class.getSimpleName();

    protected LayoutInflater mInflater;

    /** 进度条对话框对象 */
    CustomProgressDialog mProgressDialog;

    private LocalBroadcastManager mLocalBroadcastManager;// 应用内部广播管理对象

    private BroadcastReceiver mLocalReceiver;

    public View mRlToolbar;

    private Unbinder unbinder;

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

        if (isImmersionBarEnabled()) {
        }

    }

    /**
     * 是否在Fragment使用沉浸式
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
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

    /**
     * 空数据
     * @param viewGroup 
     */
    protected void showNoDataPage(ViewGroup viewGroup) {
//        BlankPageView mBlankPageView = new BlankPageView(viewGroup, false, BlankPageView.BlankPageType.BlankPageTypeNoData);
//        mBlankPageView.show();
    }


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
