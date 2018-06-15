package com.docomotv.module.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.docomotv.R;
import com.docomotv.constant.ApiConstant;
import com.docomotv.model.api.AuthorizationRequestBody;
import com.docomotv.model.api.UpdatePasswordRequestBody;
import com.docomotv.model.api.UpdateProfileRequestBody;
import com.docomotv.model.auth.UserInfoModel;
import com.docomotv.model.common.LinksModel;
import com.docomotv.model.item.Item;
import com.docomotv.module.base.BaseFragment;
import com.docomotv.network.AccountService;
import com.docomotv.network.FileUploadService;
import com.docomotv.network.ItemService;
import com.docomotv.network.base.RequestCallbackListener;
import com.docomotv.util.ImageUtils;
import com.docomotv.util.SPUtils;

import java.util.HashMap;
import java.util.List;

import butterknife.OnClick;

/**
 * @author weiyang.an
 * @version 1.0 2018/6/12
 */
public class MainFragment extends BaseFragment {

    private static final String TAG = MainFragment.class.getSimpleName();



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

        testGet();
//        testPost();
//        testDelete();
//        testPut();
//        testPatch();

//        testUploadImage();
    }



    private void testGet() {

        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("page[no]","1");
        queryParams.put("page[size]","1");

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

}
