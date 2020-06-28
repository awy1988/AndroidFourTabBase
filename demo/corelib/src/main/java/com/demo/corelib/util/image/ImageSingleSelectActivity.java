package com.demo.corelib.util.image;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.demo.corelib.constant.AppConstant;
import com.demo.corelib.util.FileUtils;
import com.yalantis.ucrop.UCrop;
import java.io.File;
import java.util.List;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


/**
 * 调用照相机和拍照的工具类的辅助Activity
 */
public class ImageSingleSelectActivity extends Activity implements EasyPermissions.PermissionCallbacks {

    private static final String TAG = ImageSingleSelectActivity.class.getSimpleName();

    private Activity mActivity;

    /**
     * 用户上传头像相关
     */
    private Uri fileUri;
    private Uri fileCropUri;
    private File file;

    private String mType;

    /**
     * 照相机回调代码
     */
    private final int REQ_CODE_PHOTO = 1005;

    /**
     * 相册回调代码
     */
    private final int REQ_CODE_PHOTO_CROP = 1006;

    private final int REQ_CODE_CAMERA_PERMISSION = 101;


    private boolean mIsCrop = false;

    private int aspectX = -1; // 横向宽的比例
    private int aspectY = -1; // 横向高的比例
    private int outputX = -1; // 裁剪图片宽
    private int outputY = -1; // 裁剪图片高


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        mType = getIntent().getStringExtra("type");
        mIsCrop = getIntent().getBooleanExtra("isCrop", false);
        aspectX = getIntent().getIntExtra("aspectX", -1);
        aspectY = getIntent().getIntExtra("aspectY", -1);
        outputX = getIntent().getIntExtra("outputX", -1);
        outputY = getIntent().getIntExtra("outputY", -1);

        if (mType.equals(ImageSingleSelector.CAMERA_EXTRA)) {
            camera();
        } else if (mType.equals(ImageSingleSelector.PHOTO_EXTRA)) {
            photo();
        }
    }


    public void camera() {

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            fileUri = FileUtils.getEmptyImageFileUri();
        } else {
            //7.0 调用系统相机拍照不再允许使用Uri方式，应该替换为FileProvider
            //并且这样可以解决MIUI系统上拍照返回size为0的情况
            file = FileUtils.getEmptyImageFile();
            // TODO 这里的provider名称需要斟酌
            fileUri = FileProvider.getUriForFile(mActivity, "com.demo.fileprovider", file);
        }

        if(EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            onCameraPermissionGranted();
        } else {  // TODO 这里将demo动态获取App的名称
            EasyPermissions.requestPermissions(this, "demo", REQ_CODE_CAMERA_PERMISSION, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE);
        }

    }

    public void photo() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        startActivityForResult(intent, REQ_CODE_PHOTO);
    }

    // 图片剪切
    private void cropImageUri(Uri uri, Uri outputUri, int aspectX, int aspectY, int outputX, int outputY, int requestCode) {
        //cropImageByAndroidCropAction(uri, outputUri, aspectX, aspectY, outputX, outputY, requestCode);
        cropImageByUCrop(uri, outputUri, aspectX, aspectY, outputX, outputY, requestCode);
    }

    // 使用UCrop进行图片剪切
    private void cropImageByUCrop(Uri inputFileUri, Uri outputUri, int aspectX, int aspectY, int outputX, int outputY, int requestCode) {

        UCrop uCrop = UCrop.of(inputFileUri, outputUri);

        if (outputX != -1 && outputY != -1) {
            uCrop.withMaxResultSize(outputX, outputY);
        }

        if (aspectX != -1 && aspectY != -1) {
            uCrop.withAspectRatio(aspectX, aspectY);
        }

        uCrop.start(this);

    }

    /**
     * 原生方式裁剪图片
     * @param uri
     * @param outputUri
     * @param outputX
     * @param outputY
     * @param requestCode
     */
    private void cropImageByAndroidCropAction(Uri uri, Uri outputUri, int aspectX, int aspectY, int outputX, int outputY, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");// crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        if (aspectX != -1) {
            intent.putExtra("aspectX", aspectX);// 宽的比例
        }

        if (aspectY != -1) {
            intent.putExtra("aspectY", aspectY);// 高的比例
        }

        if (outputX != -1) {
            intent.putExtra("outputX", outputX);// 裁剪图片宽
        }

        if (outputY != -1) {
            intent.putExtra("outputY", outputY);// 裁剪图片高
        }

        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection

        // 通过Intent调用系统相机拍照，如果本机版本大于等于anroid7.0需要临时授权Uri的访问权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }

        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CODE_PHOTO) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    fileUri = data.getData();
                }
                if (!mIsCrop) {

                    String filePath = null;
                    // Android N 以后，在这里执行到 CommonUtils.getPath(mActivity,fileUri) 时会报错：column '_data' does not exist
                    // 问题可以参见：https://stackoverflow.com/questions/42508383/illegalargumentexception-column-data-does-not-exist
                    // 不过这里并没有用那篇文章中的方案解决问题，而是采取在内存中保存照片的文件对象的方法。
                    if (file != null) {
                        filePath = file.getPath();
                    } else {
                        filePath = FileUtils.getRealPathFromURI(fileUri, mActivity);
                    }

                    Intent intent = new Intent();
                    intent.setAction(AppConstant.BROADCAST_ACTION_SINGLE_IMAGE_SELECT);
                    intent.putExtra("filePath", filePath);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                    finish();
                }

                fileCropUri = FileUtils.getEmptyImageFileUri();

                if (mIsCrop) {
                    cropImageUri(fileUri, fileCropUri, aspectX, aspectY, outputX, outputY, REQ_CODE_PHOTO_CROP);
                }

            }else {
                finish();
            }


        } else if (requestCode == REQ_CODE_PHOTO_CROP) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    sendCropFileName();
                    finish();
                } catch (Exception e) {
                    finish();
                }
            }else {
                finish();
            }
        } else if (requestCode == UCrop.REQUEST_CROP) {
            if (resultCode == RESULT_OK) {
                sendCropFileName();
                finish();
            } else if (resultCode == UCrop.RESULT_ERROR) {
                finish();
            }
        }

    }

    /**
     * 获取裁剪的图片名称，并返回给 ImageSingleSelector 类。
     */
    private void sendCropFileName() {
        String filePath = FileUtils.getRealPathFromURI(fileCropUri, mActivity);
        Intent intent = new Intent();
        intent.setAction(AppConstant.BROADCAST_ACTION_SINGLE_IMAGE_SELECT);
        intent.putExtra("filePath", filePath);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @AfterPermissionGranted(REQ_CODE_CAMERA_PERMISSION)
    public void onCameraPermissionGranted() {

        Intent intent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);

        // 下面这句指定调用相机拍照后的照片存储的路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, REQ_CODE_PHOTO);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
