package com.docomotv.module.startup;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import com.docomotv.R;
import com.docomotv.model.api.AuthorizationRequestBody;
import com.docomotv.model.api.UpdatePasswordRequestBody;
import com.docomotv.model.api.UpdateProfileRequestBody;
import com.docomotv.model.auth.UserInfoModel;
import com.docomotv.model.item.Item;
import com.docomotv.module.App;
import com.docomotv.module.MainActivity;
import com.docomotv.network.AccountService;
import com.docomotv.network.FileUploadService;
import com.docomotv.network.ItemService;
import com.docomotv.network.base.RequestCallbackListener;
import com.docomotv.util.ImageUtils;
import com.docomotv.util.SPUtils;
import com.docomotv.util.ScreenUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 启动页
 *
 * @author weiyang.an
 * @version 1.0 2017/10/24
 */
public class StartupActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{

    private static final String TAG = StartupActivity.class.getSimpleName();

    @BindView(R.id.iv_launch_screen)
    ImageView mIvLaunchScreen; // 启动图片

    // 定位权限的请求码
    private static final int REQ_CODE_PERMISSION = 100;

    private static final int sleepTime = 1000;// 启动图片停留时间

    private String testUploadImageFileName = "/storage/emulated/0/proding/image/M20180614101231.jpg";
    private String testUploadImageFileName2 = "/storage/emulated/0/proding/image/M20180614141404.jpg";

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

//        testGet();
//        testPost();
//        testDelete();
//        testPut();
//        testPatch();
        testUploadImage();
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
        final String strCurrentVersion = App.getApp().getVersion();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    toMain();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private boolean hasLocationPermissions() {
        return EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    private void testGet() {
        ItemService.getItems(new HashMap<String, String>(), new RequestCallbackListener<List<Item>>() {
            @Override
            public void onStarted() {
                System.out.println("onStarted: ");
            }

            @Override
            public void onCompleted(List<Item> data) {
                System.out.println("onCompleted: ");
                System.out.println("onCompleted: data[0] = " + data.get(0).getName() );
            }

            @Override
            public void onEndedWithError(String errorInfo) {
                System.out.println("onEndedWithError: " + errorInfo);
            }
        });
    }


    private void testPost() {
        AuthorizationRequestBody requestBody = new AuthorizationRequestBody();
        requestBody.setUsername("15840891377");
        requestBody.setPassword("rd123456");
        AccountService.authorization(requestBody, new RequestCallbackListener<UserInfoModel>() {
            @Override
            public void onStarted() {
                Log.d("testPost", "onStarted: ");
            }

            @Override
            public void onCompleted(UserInfoModel data) {
                Log.d("testPost", "onCompleted: ");
                Log.d("testPost", "onCompleted: data = " + data.getName());

                // 保存用户信息
                SPUtils.saveAccessToken(data.getAccessToken());

            }

            @Override
            public void onEndedWithError(String errorInfo) {
                Log.d("testPost", "onEndedWithError: ");
            }
        });
    }

    private void testDelete() {
        AccountService.logout(new RequestCallbackListener<String>() {
            @Override
            public void onStarted() {
                Log.d("testDelete", "onStarted: ");
            }

            @Override
            public void onCompleted(String data) {
                Log.d("testDelete", "onCompleted: ");
            }

            @Override
            public void onEndedWithError(String errorInfo) {
                Log.d("testDelete", "onEndedWithError: ");
            }
        });
    }

    private void testPut() {

        UpdatePasswordRequestBody requestBody = new UpdatePasswordRequestBody();
        requestBody.setPassword("rd1234");
        requestBody.setNewPassword("rd123456");

        AccountService.updatePassword(requestBody, new RequestCallbackListener<String>() {
            @Override
            public void onStarted() {
                Log.d("testPut", "onStarted: ");
            }

            @Override
            public void onCompleted(String data) {
                Log.d("testPut", "onCompleted: ");
            }

            @Override
            public void onEndedWithError(String errorInfo) {
                Log.d("testPut", "onEndedWithError: " + errorInfo);
            }
        });
    }

    private void testPatch() {
        UpdateProfileRequestBody requestBody = new UpdateProfileRequestBody();
        requestBody.setName("helloLaoTie");
        AccountService.updateProfile(requestBody, new RequestCallbackListener<String>() {
            @Override
            public void onStarted() {
                Log.d("testPatch", "onStarted: ");
            }

            @Override
            public void onCompleted(String data) {
                Log.d("testPatch", "onCompleted: ");
            }

            @Override
            public void onEndedWithError(String errorInfo) {
                Log.d("testPatch", "onEndedWithError: " + errorInfo);
            }
        });
    }

    private void testUploadImage() {
        ImageUtils.uploadImage(this, testUploadImageFileName2, FileUploadService.Category.USER_LOGO, new ImageUtils.OnFileUploadResultListener() {
            @Override
            public void onUploadSuccess(String data) {

            }

            @Override
            public void onUploadFailure(String message) {

            }

            @Override
            public void onUploadError(String error) {

            }
        });
    }

}
