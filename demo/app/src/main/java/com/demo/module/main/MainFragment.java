package com.demo.module.main;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.demo.R;
import com.demo.corelib.constant.ApiConstant;
import com.demo.corelib.model.api.AuthorizationRequestBody;
import com.demo.corelib.model.api.Page;
import com.demo.corelib.model.api.Sort;
import com.demo.corelib.model.api.UpdatePasswordRequestBody;
import com.demo.corelib.model.api.UpdateProfileRequestBody;
import com.demo.corelib.model.auth.UserInfoModel;
import com.demo.corelib.model.common.LinksModel;
import com.demo.corelib.model.item.Item;
import com.demo.corelib.network.AccountService;
import com.demo.corelib.network.FileUploadService;
import com.demo.corelib.network.ItemService;
import com.demo.corelib.network.base.RequestCallbackListener;
import com.demo.corelib.utils.ImageUtils;
import com.demo.corelib.utils.SPUtils;
import com.demo.corelib.utils.zxing.qrcode.CaptureActivity;
import com.demo.module.base.BaseFragment;
import com.demo.module.main.viewmodel.MainViewModel;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;

public class MainFragment extends BaseFragment implements EasyPermissions.PermissionCallbacks {

    private static final String TAG = MainFragment.class.getSimpleName();
    private static final int REQ_CODE_CAMERA_PERMISSION = 1;

    MainViewModel viewModel;

    @BindView(R.id.tv_title)
    TextView tvTitle;

//    @Inject
//    AccountService AccountService;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        viewModel.getTitle().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tvTitle.setText(s);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.main_frag, container, false);
//        ButterKnife.bind(view);
        return view;
    }

    private String testUploadImageFileName = "/storage/emulated/0/proding/image/M20180614101231.jpg";
    private String nextLinkUrl = null;


    @OnClick({R.id.btn_login, R.id.btn_logout, R.id.btn_get_items, R.id.btn_qrcode, R.id.btn_take_photo, R.id.btn_take_camera})
    public void onButtonClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                testPost();
                viewModel.setTitle("hahaha");
                break;
            case R.id.btn_get_items:
                testGet();
                break;
            case R.id.btn_logout:
                testDelete();
                break;
            case R.id.btn_qrcode:

                if (EasyPermissions.hasPermissions(getContext(), Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    startActivity(new Intent(getContext(), CaptureActivity.class));
                } else {
                    EasyPermissions.requestPermissions(MainFragment.this,getString(R.string.app_name), REQ_CODE_CAMERA_PERMISSION, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }

                break;
            case R.id.btn_take_photo:
                ImageUtils.selectSingleImageByPhoto(getActivity(), new ImageUtils.IPathCallBack() {
                    @Override
                    public void callBackPath(String filePath) {
                        Toast.makeText(getContext(), "file path = " + filePath, Toast.LENGTH_SHORT).show();
                    }
                }, true);
                break;
            case R.id.btn_take_camera:
                ImageUtils.selectSingleImageByCamera(getActivity(), new ImageUtils.IPathCallBack() {
                    @Override
                    public void callBackPath(String filePath) {
                        Toast.makeText(getContext(), "file path = " + filePath, Toast.LENGTH_SHORT).show();
                    }
                }, true);
                break;
            default:
                break;
        }
    }



    private void testGet() {

        HashMap<String, Object> queryParams = new HashMap<>();
        queryParams.put("page",new Page(1, 1));
        Sort sort = new Sort();
        sort.setCreateAt(ApiConstant.Sort.DESC);
        queryParams.put("sort", sort);

        ItemService.getItems(queryParams, new RequestCallbackListener<List<Item>>() {
            @Override
            public void onStarted() {
                System.out.println("onStarted: ");
            }

            @Override
            public void onCompleted(List<Item> data, LinksModel links) {
                System.out.println("onCompleted: ");
                System.out.println("onCompleted: data[0] = " + data.get(0).getName() );

                if (links != null && links.getNext() != null) {
                    nextLinkUrl = ApiConstant.BASE_URL + links.getNext();
                } else {
                    nextLinkUrl = null;
                }
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
            public void onCompleted(UserInfoModel data, LinksModel links) {
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
            public void onCompleted(String data, LinksModel links) {
                Log.d("testDelete", "onCompleted: ");
            }

            @Override
            public void onEndedWithError(String errorInfo) {
                Log.d("testDelete", "onEndedWithError: errorinfo = " + errorInfo);
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
            public void onCompleted(String data, LinksModel links) {
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
            public void onCompleted(String data, LinksModel links) {
                Log.d("testPatch", "onCompleted: ");
            }

            @Override
            public void onEndedWithError(String errorInfo) {
                Log.d("testPatch", "onEndedWithError: " + errorInfo);
            }
        });
    }

    @OnClick(R.id.btn_loadmore)
    public void testLoadMore() {

        ItemService.getItems(nextLinkUrl, new RequestCallbackListener<List<Item>>() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onCompleted(List<Item> data, LinksModel links) {

            }

            @Override
            public void onEndedWithError(String errorInfo) {
                Log.d("testLoadMore", "onEndedWithError: " + errorInfo);
            }
        });
    }

    private void testUploadImage() {
        ImageUtils.uploadImage(getContext(), testUploadImageFileName, FileUploadService.Category.USER_LOGO, new ImageUtils.OnFileUploadResultListener() {
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

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        // 授权成功，由于本页面只有一个授权的请求，所以可以直接将迁移到二维码画面的逻辑写在这里
        startActivity(new Intent(getContext(), CaptureActivity.class));
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        // 授权失败
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this);
    }
}
