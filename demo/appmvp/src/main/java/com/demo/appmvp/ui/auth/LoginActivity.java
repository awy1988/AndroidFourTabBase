package com.demo.appmvp.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import com.demo.appmvp.R;
import com.demo.appmvp.data.model.CaptchaDataModel;
import com.demo.appmvp.databinding.LoginActBinding;
import com.demo.appmvp.di.component.DaggerActivityComponent;
import com.demo.appmvp.di.module.DatabaseModule;
import com.demo.appmvp.mvp.contract.LoginContract;
import com.demo.appmvp.ui.App;
import com.demo.appmvp.ui.base.BaseFragmentActivity;
import com.demo.appmvp.ui.main.MainActivity;
import com.demo.corelib.util.ImageUtils;
import com.demo.corelib.util.KeyboardUtils;
import javax.inject.Inject;

/**
 * 登录页
 */
public class LoginActivity extends BaseFragmentActivity implements LoginContract.View {

    private static final String TAG = LoginActivity.class.getSimpleName();


    private CaptchaDataModel mCaptchaDataModel;

    @Inject
    LoginContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DaggerActivityComponent.builder()
            .databaseModule(new DatabaseModule(App.getContext()))
            .build().inject(this);
        super.onCreate(savedInstanceState);

        initActionBar();

        mPresenter.attachView(this);
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

    @Override
    protected void onDestroy() {

        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
        super.onDestroy();
    }

    /**
     * 登录按钮点击事件
     */
    public void onLoginClick(View view) {
        if (isLoginFormDataLegal()) {

            String userName = ((LoginActBinding) mDataBinding).etUserName.getText().toString().trim();
            String password = ((LoginActBinding) mDataBinding).etPassword.getText().toString().trim();
            String inputCaptcha = ((LoginActBinding) mDataBinding).etLoginVCodeInput.getText().toString().trim();
            String encryptedData = mCaptchaDataModel == null ? null : mCaptchaDataModel.getEncryptedData();

            mPresenter.login(userName, password, inputCaptcha, encryptedData);

        }
    }

    /**
     * 刷新图形验证码按钮点击事件
     */
    public void onRefreshCaptchaClick(View view) {
        mPresenter.refreshCaptcha();
    }

    private void initActionBar() {
        ((LoginActBinding) mDataBinding).toolbar.setTitle(R.string.login_ab_title);
        ((LoginActBinding) mDataBinding).toolbar.setTitleTextColor(getColor(R.color.common_primary_font_color));
    }


    private boolean isLoginFormDataLegal() {

        if (!isUserNameLegal()) {
            return false;
        }

        return isPasswordLegal();
    }

    private boolean isUserNameLegal() {
        // 用户名是否为空
        String userName =  ((LoginActBinding) mDataBinding).etUserName.getText().toString().trim();
        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(this, R.string.login_input_username_toast, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean isPasswordLegal() {
        // 密码是否为空
        String password = ((LoginActBinding) mDataBinding).etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, R.string.login_input_password_toast, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * 显示图形验证码
     * @param captchaDataModel
     */
    @Override
    public void showCaptcha(CaptchaDataModel captchaDataModel) {
        if (captchaDataModel != null) {
            mCaptchaDataModel = captchaDataModel;
            ((LoginActBinding) mDataBinding).rlLoginVCodeContainer.setVisibility(View.VISIBLE);
            ((LoginActBinding) mDataBinding).ivLoginVCode.setImageBitmap(
                ImageUtils.decodeBase64(captchaDataModel.getImageData()));
        } else {
            ((LoginActBinding) mDataBinding).rlLoginVCodeContainer.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void loginSuccess() {
        // 收键盘的逻辑
        KeyboardUtils.hideKeyboard(LoginActivity.this);
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String errorMsg) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
    }
}
