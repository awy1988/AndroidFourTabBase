package com.demo.ui.main;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import com.demo.R;
import com.demo.corelib.constant.ApiConstant;
import com.demo.corelib.model.api.Page;
import com.demo.corelib.model.api.Sort;
import com.demo.corelib.network.FileUploadService;
import com.demo.corelib.util.DownLoadUtils;
import com.demo.corelib.util.ImageUtils;
import com.demo.corelib.util.image.ImagePagerActivity;
import com.demo.corelib.util.zxing.qrcode.CaptureActivity;
import com.demo.databinding.MainFragBinding;
import com.demo.ui.base.BaseFragment;
import com.demo.ui.main.viewmodel.MainViewModel;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;

public class MainFragment extends BaseFragment implements EasyPermissions.PermissionCallbacks {

    private static final String TAG = MainFragment.class.getSimpleName();
    private static final int REQ_CODE_CAMERA_PERMISSION = 1;
    private static final int REQ_CODE_QR_CODE_SCAN = 2;

    private MainViewModel mViewModel;

    private MainFragBinding mBinding;
    //    @Inject
    //    AccountService accountService;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

        mViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);

        mViewModel.getIsDataLoading().observe(this, isLoading -> {
            if (isLoading) {
                showLoadingDialog();
                return;
            }
            hideLoadingDialog();
        });



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.main_frag, container, false);
        //        ButterKnife.bind(view);
        mBinding = DataBindingUtil.bind(view);
        mBinding.setFragment(MainFragment.this);

        return mBinding.getRoot();
    }

    public void onButtonClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                break;
            case R.id.btn_get_items:
                testGet();
                break;
            case R.id.btn_logout:
                break;
            case R.id.btn_qrcode:

                if (EasyPermissions.hasPermissions(getContext(), Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    startActivityForResult(new Intent(getContext(), CaptureActivity.class), REQ_CODE_QR_CODE_SCAN);
                } else {
                    EasyPermissions.requestPermissions(MainFragment.this, getString(R.string.app_name),
                        REQ_CODE_CAMERA_PERMISSION, Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }

                break;
            case R.id.btn_take_photo:
                ImageUtils.selectSingleImageByPhoto(getActivity(), new ImageUtils.IPathCallBack() {
                    @Override
                    public void callBackPath(String filePath) {
                        Log.d(TAG, filePath);
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
            case R.id.btn_download_file:
                downloadFile();
                break;
            case R.id.btn_upload_file:
                testUploadImage();
                break;
            case R.id.btn_image_pager:
                testImagePager();
                break;
            default:
                break;
        }
    }

    private void downloadFile() {
        String apkUrl = "https://cdn.llscdn.com/yy/files/tkzpx40x-lls-LLS-5.7-785-20171108-111118.apk";
        String parentFilePath = getParentFile(getActivity()).getPath();
        Log.e("test", "parentFilePath = " + parentFilePath);
        DownLoadUtils.downloadFile(apkUrl, getParentFile(getActivity()), "1.apk",
            new DownLoadUtils.DownloadCallbackListener() {
                @Override
                public void onDownloadStart() {
                    Toast.makeText(getActivity(), "onDownloadStart", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onDownloadEnd(Exception realCause) {
                    Toast.makeText(getActivity(), "onDownloadEnd", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onDownloading(int progress) {
                    mBinding.pbProgress.setProgress(progress);
                }
            });
        mBinding.pbProgress.setMax(100);
    }

    private void testGet() {

        HashMap<String, Object> queryParams = new HashMap<>();
        queryParams.put("page", new Page(1, 1));
        Sort sort = new Sort();
        sort.setCreateAt(ApiConstant.Sort.DESC);
        queryParams.put("sort", sort);

        mViewModel.loadItems(queryParams);
    }



    public void testLoadMore() {
        mViewModel.loadMoreItems();
    }

    private void testUploadImage() {

        ImageUtils.selectSingleImageByPhoto(getActivity(), new ImageUtils.IPathCallBack() {
            @Override
            public void callBackPath(String filePath) {
                ImageUtils.uploadImage(getContext(), ApiConstant.BASE_URL, ApiConstant.IS_DEBUG, filePath, FileUploadService.Category.USER_LOGO,
                    new ImageUtils.OnFileUploadResultListener() {
                        @Override
                        public void onUploadSuccess(String data) {
                            Toast.makeText(getActivity(), "onUploadSuccess: " + data, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onUploadFailure(String message) {
                            Log.e(TAG, message);
                        }

                        @Override
                        public void onUploadError(String error) {
                            Log.e(TAG, error);
                        }
                    });
            }
        }, true);

    }

    private void testImagePager() {

        ArrayList<String> imagePaths = new ArrayList<>();
        imagePaths.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1593317940124&di=aa00a41b4aa6f29a02ab1817f8574fbb&imgtype=0&src=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D403949589%2C2257383715%26fm%3D214%26gp%3D0.jpg");
        imagePaths.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1593317940124&di=aa00a41b4aa6f29a02ab1817f8574fbb&imgtype=0&src=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D403949589%2C2257383715%26fm%3D214%26gp%3D0.jpg");
        imagePaths.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1593317940124&di=aa00a41b4aa6f29a02ab1817f8574fbb&imgtype=0&src=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D403949589%2C2257383715%26fm%3D214%26gp%3D0.jpg");
        imagePaths.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1593317940124&di=aa00a41b4aa6f29a02ab1817f8574fbb&imgtype=0&src=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D403949589%2C2257383715%26fm%3D214%26gp%3D0.jpg");
        imagePaths.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1593317940124&di=aa00a41b4aa6f29a02ab1817f8574fbb&imgtype=0&src=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D403949589%2C2257383715%26fm%3D214%26gp%3D0.jpg");
        imagePaths.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1593317940124&di=aa00a41b4aa6f29a02ab1817f8574fbb&imgtype=0&src=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D403949589%2C2257383715%26fm%3D214%26gp%3D0.jpg");
        imagePaths.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1593317940124&di=aa00a41b4aa6f29a02ab1817f8574fbb&imgtype=0&src=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D403949589%2C2257383715%26fm%3D214%26gp%3D0.jpg");
        imagePaths.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1593317940124&di=aa00a41b4aa6f29a02ab1817f8574fbb&imgtype=0&src=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D403949589%2C2257383715%26fm%3D214%26gp%3D0.jpg");
        imagePaths.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1593317940124&di=aa00a41b4aa6f29a02ab1817f8574fbb&imgtype=0&src=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D403949589%2C2257383715%26fm%3D214%26gp%3D0.jpg");


        Intent intent = new Intent();
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, 0);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_PATHS, imagePaths);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_SHOW_DELETE_BUTTON, true);
        intent.setClass(getActivity(), ImagePagerActivity.class);
        startActivity(intent);
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
        @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQ_CODE_QR_CODE_SCAN) {
                Toast.makeText(getActivity(), data.getStringExtra("result"), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private File getParentFile(@NonNull Context context) {
        final File externalSaveDir = context.getExternalCacheDir();
        if (externalSaveDir == null) {
            return context.getCacheDir();
        } else {
            return externalSaveDir;
        }
    }
}
