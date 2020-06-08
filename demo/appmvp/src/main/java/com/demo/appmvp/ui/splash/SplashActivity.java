package com.demo.appmvp.ui.splash;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import androidx.annotation.NonNull;
import androidx.viewbinding.ViewBinding;
import com.demo.appmvp.R;
import com.demo.appmvp.databinding.SplashActBinding;
import com.demo.appmvp.ui.base.BaseFragmentActivity;
import com.demo.appmvp.ui.main.MainActivity;
import com.demo.corelib.util.ScreenUtils;
import java.util.List;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 启动页
 */
public class SplashActivity extends BaseFragmentActivity implements EasyPermissions.PermissionCallbacks {

    private static final String TAG = SplashActivity.class.getSimpleName();

    // 定位权限的请求码
    private static final int REQ_CODE_PERMISSION = 100;
    // 启动页面停留时间
    private static final int sleepTime = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenUtils.setFullScreen(this);
        init();
    }

    @Override
    protected int getContentView() {
        return R.layout.splash_act;
    }

    @Override
    protected boolean isDataBindingEnabled() {
        return super.isDataBindingEnabled();
    }

    @Override
    protected ViewBinding getViewBinding() {
        return SplashActBinding.inflate(getLayoutInflater());
    }

    void init() {

        AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
        animation.setDuration(1500);
        ((SplashActBinding) mViewBinding).tvLaunchScreen.startAnimation(animation);
        permissionCheck();
    }

    @AfterPermissionGranted(REQ_CODE_PERMISSION)
    public void permissionCheck() {
        // 权限检查
        if (hasLocationPermissions()) {
            initLocation();
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.app_name), REQ_CODE_PERMISSION,
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        Log.d(TAG, "onPermissionsGranted: requestCode = " + requestCode + " permissions = " + perms);
        // 授权
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Log.d(TAG, "onPermissionsDenied: requestCode = " + requestCode + " permissions = " + perms);
        // 未授权
        next();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull final String[] permissions,
        @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    /**
     * 下一步迁移的逻辑
     */
    private void next() {

        // 当前应用程序的版本号
        new Thread(() -> {
            try {
                Thread.sleep(sleepTime);
                toMain();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * 跳转到下一个画面的方法
     */
    void toMain() {

        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    //================================================================================
    // 位置服务
    //================================================================================

    /**
     * 位置信息初始化
     */
    public void initLocation() {
        // TODO 按照实际业务改造
        next();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private boolean hasLocationPermissions() {
        return EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
    }
}
