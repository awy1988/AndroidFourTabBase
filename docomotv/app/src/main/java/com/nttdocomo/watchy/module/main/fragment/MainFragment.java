package com.nttdocomo.watchy.module.main.fragment;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nttdocomo.watchy.constant.ApiConstant;
import com.nttdocomo.watchy.model.api.PageModel;
import com.nttdocomo.watchy.model.api.SortModel;
import com.nttdocomo.watchy.model.api.UpdatePasswordRequestBodyModel;
import com.nttdocomo.watchy.model.api.UpdateProfileRequestBodyModel;
import com.nttdocomo.watchy.model.auth.UserInfoModel;
import com.nttdocomo.watchy.model.common.LinksModel;
import com.nttdocomo.watchy.model.item.Item;
import com.nttdocomo.watchy.module.base.BaseFragment;
import com.nttdocomo.watchy.network.AccountService;
import com.nttdocomo.watchy.network.FileUploadService;
import com.nttdocomo.watchy.network.ItemService;
import com.nttdocomo.watchy.network.common.RequestCallbackListener;
import com.nttdocomo.watchy.util.ImageUtils;
import com.nttdocomo.watchy.util.SPCacheUtils;
import com.nttdocomo.watchy.R;

import java.util.HashMap;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * @author weiyang.an
 * @version 1.0 2018/6/12
 */
public class MainFragment extends BaseFragment implements EasyPermissions.PermissionCallbacks {

    private static final String TAG = MainFragment.class.getSimpleName();
    private static final int REQ_CODE_CAMERA_PERMISSION = 1;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.main_frag, container, false);
        testRequest();
        return view;
    }

    private String testUploadImageFileName = "/storage/emulated/0/proding/image/M20180614101231.jpg";
    private String testUploadImageFileName2 = "/storage/emulated/0/proding/image/M20180614141404.jpg";
    private String nextLinkUrl = null;

    private void testRequest() {

//        testGet();
//        testPost();
//        testDelete();
//        testPut();
//        testPatch();

//        testUploadImage();
    }

    public void onButtonClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                testPost();
                break;
            case R.id.btn_get_items:
                testGet();
                break;
            case R.id.btn_logout:
                testDelete();
                break;
            case R.id.btn_qrcode:

                if (EasyPermissions.hasPermissions(getContext(), Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                    startActivity(new Intent(getContext(), CaptureActivity.class));
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
        queryParams.put("page",new PageModel(1, 1));
        SortModel sort = new SortModel();
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
        AccountService.authorization("15840891377","rd123456", null, null, new RequestCallbackListener<UserInfoModel>() {
            @Override
            public void onStarted() {
                Log.d("testPost", "onStarted: ");
            }

            @Override
            public void onCompleted(UserInfoModel data, LinksModel links) {
                Log.d("testPost", "onCompleted: ");
                Log.d("testPost", "onCompleted: data = " + data.getName());

                // 保存用户信息
                SPCacheUtils.saveAccessToken(data.getAccessToken());

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

        UpdatePasswordRequestBodyModel requestBody = new UpdatePasswordRequestBodyModel();
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
        UpdateProfileRequestBodyModel requestBody = new UpdateProfileRequestBodyModel();
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
        ImageUtils.uploadImage(getContext(), testUploadImageFileName2, FileUploadService.Category.USER_LOGO, new ImageUtils.OnFileUploadResultListener() {
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
//        startActivity(new Intent(getContext(), CaptureActivity.class));
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
