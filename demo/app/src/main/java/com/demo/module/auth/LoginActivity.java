package com.demo.module.auth;

import android.os.Bundle;
import com.demo.R;
import com.demo.module.base.BaseFragmentActivity;

/**
 * 登录页
 */
public class LoginActivity extends BaseFragmentActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override protected int getContentView() {
        return R.layout.login_act;
    }

    @Override protected boolean isDataBindingEnabled() {
        return true;
    }
}
