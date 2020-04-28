package com.demo.module.startup;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.demo.R;
import com.demo.module.MainActivity;
import com.demo.util.ScreenUtils;

import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 启动页
 */
public class StartupActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{

    private static final String TAG = StartupActivity.class.getSimpleName();

    @BindView(R.id.iv_launch_screen)
    ImageView mIvLaunchScreen; // 启动图片

    // 定位权限的请求码
    private static final int REQ_CODE_PERMISSION = 100;

    private static final int sleepTime = 1000;// 启动图片停留时间


    //================================================================================
    // 位置服务
    //================================================================================

    private JSONObject mAdData; // 广告数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenUtils.setFullScreen(this);
        setContentView(R.layout.startup_act);
        ButterKnife.bind(this);
        init();
    }

    void init() {

        AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
        animation.setDuration(1500);
        mIvLaunchScreen.startAnimation(animation);
        permissionCheck();

    }



    @AfterPermissionGranted(REQ_CODE_PERMISSION)
    public void permissionCheck() {
        // 权限检查
        if (hasLocationPermissions()) {
            initLocation();
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.app_name), REQ_CODE_PERMISSION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
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
    public void onRequestPermissionsResult(int requestCode, @NonNull final String[] permissions, @NonNull int[] grantResults) {
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

        Intent intent = new Intent(StartupActivity.this, MainActivity.class);
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
        return EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
    }


}
