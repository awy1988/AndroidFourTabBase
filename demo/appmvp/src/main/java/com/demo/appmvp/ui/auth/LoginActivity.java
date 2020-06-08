package com.demo.appmvp.ui.auth;

import android.os.Bundle;
import android.view.View;
import com.demo.appmvp.R;
import com.demo.appmvp.databinding.LoginActBinding;
import com.demo.appmvp.ui.base.BaseFragmentActivity;


/**
 * 登录页
 */
public class LoginActivity extends BaseFragmentActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        initActionBar();
        initViewModel();
        initObserver();
        ((LoginActBinding) mDataBinding).setLoginActivity(LoginActivity.this);
    }

    @Override
    protected int getContentView() {
        return R.layout.login_act;
    }

    @Override
    protected boolean isDataBindingEnabled() {
        return true;
    }

    /**
     * 登录按钮点击事件
     */
    public void onLoginClick(View view) {
        if (isLoginFormDataLegal()) {

        }
    }

    /**
     * 刷新图形验证码按钮点击事件
     */
    public void onRefreshCaptchaClick(View view) {
    }

    private void initActionBar() {
        ((LoginActBinding) mDataBinding).toolbar.setTitle(R.string.login_ab_title);
        ((LoginActBinding) mDataBinding).toolbar.setTitleTextColor(getColor(R.color.common_primary_font_color));
    }

    private void initViewModel() {
        //mLoginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
    }

    private void initObserver() {

    }

    private boolean isLoginFormDataLegal() {

        if (!isUserNameLegal()) {
            return false;
        }

        if (!isPasswordLegal()) {
            return false;
        }
        return true;
    }

    private boolean isUserNameLegal() {
        // 用户名是否为空

        return true;
    }

    private boolean isPasswordLegal() {
        // 密码是否为空

        return true;
    }
}
