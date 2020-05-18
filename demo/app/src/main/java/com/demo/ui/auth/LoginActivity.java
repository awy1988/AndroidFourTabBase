package com.demo.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import com.demo.R;
import com.demo.corelib.util.ImageUtils;
import com.demo.corelib.util.KeyboardUtils;
import com.demo.databinding.LoginActBinding;
import com.demo.di.component.DaggerActivityComponent;
import com.demo.di.module.ViewModelProviderModule;
import com.demo.ui.MainActivity;
import com.demo.ui.auth.viewmodel.LoginViewModel;
import com.demo.ui.base.BaseFragmentActivity;
import javax.inject.Inject;

/**
 * 登录页
 */
public class LoginActivity extends BaseFragmentActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    @Inject
    LoginViewModel mLoginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        DaggerActivityComponent.builder().viewModelProviderModule(new ViewModelProviderModule(this)).build().inject(this);

        super.onCreate(savedInstanceState);
        initActionBar();
        initViewModel();
        initObserver();
        mDataBinding.setLifecycleOwner(LoginActivity.this);
        ((LoginActBinding) mDataBinding).setViewModel(mLoginViewModel);
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

            if (mLoginViewModel.getCaptchaData().getValue() == null) {
                mLoginViewModel.captchaCheck();
                return;
            }

            mLoginViewModel.validateCaptcha();
        }
    }

    /**
     * 刷新图形验证码按钮点击事件
     */
    public void onRefreshCaptchaClick(View view) {
        mLoginViewModel.refreshCaptcha();
    }

    private void initActionBar() {
        ((LoginActBinding) mDataBinding).toolbar.setTitle(R.string.login_ab_title);
        ((LoginActBinding) mDataBinding).toolbar.setTitleTextColor(getColor(R.color.common_primary_font_color));
    }

    private void initViewModel() {
        //mLoginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
    }

    private void initObserver() {
        mLoginViewModel.isLoading().observe(this, isLoading -> {
            if (isLoading) {
                showLoadingDialog();
            } else {
                hideLoadingDialog();
            }
        });

        mLoginViewModel.getCaptchaData().observe(this, captchaDataModel -> {
            // 需要显示图形验证码
            if (captchaDataModel != null) {
                ((LoginActBinding) mDataBinding).ivLoginVCode.setImageBitmap(
                    ImageUtils.decodeBase64(captchaDataModel.getImageData()));
            }
        });

        mLoginViewModel.getErrorMessage().observe(this, s -> {
            Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        });

        mLoginViewModel.getLoginSuccess().observe(this, isLoginSuccess -> {
            if (isLoginSuccess) {
                // 收键盘的逻辑
                KeyboardUtils.hideKeyboard(LoginActivity.this);
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        });
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
        if (TextUtils.isEmpty(mLoginViewModel.getUserName().getValue()) || TextUtils.isEmpty(
            mLoginViewModel.getUserName().getValue().trim())) {
            Toast.makeText(this, R.string.login_input_username_toast, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean isPasswordLegal() {
        // 密码是否为空
        if (TextUtils.isEmpty(mLoginViewModel.getPassword().getValue()) || TextUtils.isEmpty(
            mLoginViewModel.getPassword().getValue().trim())) {
            Toast.makeText(this, R.string.login_input_password_toast, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
