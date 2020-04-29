package com.demo.corelib.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.demo.corelib.network.FileUploadService;
import com.demo.corelib.util.image.GlideApp;
import com.demo.corelib.util.image.ImageSingleSelect;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 图片操作共同类
 */
public class ImageUtils {

    private static final String TAG = ImageUtils.class.getSimpleName();

    public static final int IMAGE_COMPRESS_QUALITY_60 = 60; // 图片质量压缩参数 取值1-100，100为不压缩
    public static final int IMAGE_COMPRESS_QUALITY_70 = 70; // 图片质量压缩参数 取值1-100，100为不压缩
    public static final int IMAGE_COMPRESS_QUALITY_80 = 80; // 图片质量压缩参数 取值1-100，100为不压缩
    public static final int IMAGE_COMPRESS_QUALITY_90 = 90; // 图片质量压缩参数 取值1-100，100为不压缩
    public static final int IMAGE_COMPRESS_QUALITY_100 = 100; // 图片质量压缩参数 取值1-100，100为不压缩

    /**
     * 加载网络图片
     * 不指定图片的显示策略
     *
     * @param imageView
     * @param defaultImageResource
     * @param imgUrl               A file path, or a uri or url handled by {@link com.bumptech.glide.load.model.UriLoader}
     * @param context
     */
    public static void showImageByGlideWithoutScaleType(ImageView imageView, int defaultImageResource, String imgUrl, Context context) {

        GlideApp.with(context)
                .load(imgUrl)
                // 如果占位图片与加载的图片不一样大，会出现加载的图片加载完成后先被放大（或缩小）然后再正常显示的问题（此问题见于课程封面系统图库页面）。所以此处注释掉设置占位图片的代码
//                .placeholder(defaultImageResource)
                .error(defaultImageResource)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);

    }


    /**
     * 加载网络图片
     *
     * @param imageView
     * @param defaultImageResource
     * @param imgUrl               A file path, or a uri or url handled by {@link com.bumptech.glide.load.model.UriLoader}
     * @param context
     */
    public static void showImageByGlide(ImageView imageView, int defaultImageResource, String imgUrl, Context context) {

        if (imageView instanceof RoundedImageView) {
            showRoundImageByGlide(imageView, defaultImageResource, imgUrl, context);
            return;
        }

        GlideApp.with(context)
                .load(imgUrl)
                .placeholder(defaultImageResource)
                .error(defaultImageResource)
//                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

    /**
     * 加载网络图片
     *
     * @param imageView
     * @param defaultImageResource
     * @param imgUrl               A file path, or a uri or url handled by {@link com.bumptech.glide.load.model.UriLoader}
     * @param context
     */
    public static void showImageByGlide(ImageView imageView, Drawable defaultImageResource, String imgUrl, Context context) {

        if (imageView instanceof RoundedImageView) {
            GlideApp.with(context)
                    .asBitmap()
                    .load(imgUrl)
                    .placeholder(defaultImageResource)
                    .error(defaultImageResource)
                    .into(imageView);
            return;
        }

        GlideApp.with(context)
                .load(imgUrl)
                .placeholder(defaultImageResource)
                .error(defaultImageResource)
//                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

    /**
     * 加载网络图片
     *
     * @param imageView
     * @param imgUrl               A file path, or a uri or url handled by {@link com.bumptech.glide.load.model.UriLoader}
     * @param context
     */
    public static void showImageByGlide(ImageView imageView, String imgUrl, Context context) {

        if (imageView instanceof RoundedImageView) {
            GlideApp.with(context)
                    .asBitmap()
                    .load(imgUrl)
                    .into(imageView);
            return;
        }

        GlideApp.with(context)
                .load(imgUrl)
//                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

    /**
     * 加载图片文件
     *
     * @param imageView
     * @param defaultImageResource
     * @param file
     * @param context
     */
    public static void showImageByGlide(ImageView imageView, int defaultImageResource, File file, Context context) {

        if (file != null && file.exists()) {

            GlideApp.with(context)
                    .load(file)
                    .placeholder(defaultImageResource)
                    .error(defaultImageResource)
//                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageView);
        }

    }

    /**
     * 从URI加载图片
     *
     * @param imageView
     * @param defaultImageResource
     * @param uri
     * @param context
     */
    public static void showImageByGlide(ImageView imageView, int defaultImageResource, Uri uri, Context context) {

        GlideApp.with(context)
                .load(uri)
                .placeholder(defaultImageResource)
                .error(defaultImageResource)
//                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);

    }


    /**
     * 加载资源图片
     *
     * @param imageView
     * @param context
     */
    public static void showImageByGlide(ImageView imageView, int imageResId, Context context) {

        GlideApp.with(context)
                .load(imageResId)
                .into(imageView);

    }

    /**
     * 加载圆形图片用（与既存的RoundImageView、RoundedImageView连用）
     *
     * @param imageView
     * @param defaultImageResource
     * @param imgUrl
     * @param context
     */
    public static void showRoundImageByGlide(final ImageView imageView, int defaultImageResource, String imgUrl, Context context) {

        // 解决glide加载圆形图片的问题
        GlideApp.with(context)
                .asBitmap()
                .load(imgUrl)
                .placeholder(defaultImageResource)
                .error(defaultImageResource)
                .into(imageView);

    }

    /**
     * 旋转图片bitmap用函数
     * @param angle
     * @param bitmap
     * @return
     */
    public static Bitmap rotatingImageView(int angle , Bitmap bitmap) {
        //旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);

        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }

    /**
     * 读取图片的旋转角度
     * @param path
     * @return
     */
    public static int readPictureDegree(String path) {
        int degree  = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
//            exifInterface
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }


    //================================================================================
    // 上传图片
    //================================================================================
    public static void uploadImage(Context context, final String filePath, FileUploadService.Category category, final OnFileUploadResultListener listener) {

        //  这里加入压缩的逻辑，先读文件，然后压缩，上传
        File file = new File(filePath);
        String compressedImagePath = null;
        if(file.exists()){
            long fileSize = file.length() / 1024; // 得到文件的大小，单位为kb
            Log.d(TAG,"fileSize = " + fileSize);

            if(fileSize < 200){
                compressedImagePath = filePath;
            } else if(fileSize < 500){
                // 200kb-500kb
                compressedImagePath = transformPicture(context, filePath, IMAGE_COMPRESS_QUALITY_100, null);
            } else if (fileSize < 1000){
                // 500kb-1000kb
                compressedImagePath = transformPicture(context, filePath, IMAGE_COMPRESS_QUALITY_90, null);
            } else if (fileSize < 2000){
                // 1000kb-2000kb
                compressedImagePath = transformPicture(context, filePath, IMAGE_COMPRESS_QUALITY_80, null);
            } else if (fileSize < 3000){
                // 2000kb-3000kb
                compressedImagePath = transformPicture(context, filePath, IMAGE_COMPRESS_QUALITY_70, null);
            } else {
                // >3000kb
                compressedImagePath = transformPicture(context, filePath, IMAGE_COMPRESS_QUALITY_60, null);
            }

            Log.d(TAG, "uploadImage: compressedImagePath = " + compressedImagePath);
            FileUploadService.uploadFile(compressedImagePath, category, listener);
        } else {
            Log.e(TAG, "uploadImage: file not exit");
        }

    }

    /**
     * 上传一组图片
     * @param context
     * @param filePaths
     * @param upLoadUrl
     * @param category
     * @param listener
     */
    public static void uploadImages(Context context, final List<String> filePaths, String upLoadUrl , String category, final OnFilesUploadResultListener listener) {
//        UploadHelper.getInstance().uploadImages(context, filePaths, upLoadUrl,category,listener);
    }

    /**
     * 文件上传回调接口
     */
    public interface OnFileUploadResultListener {
        void onUploadSuccess(String data); // 上传成功

        void onUploadFailure(String message); // 上传失败

        void onUploadError(String error); // 上传出错
    }

    /**
     * 多文件上传回调接口
     */
    public interface OnFilesUploadResultListener {
        /**
         * 上传成功
         * @param data 上传成功后的临时文件 id 数组
         */
        void onUploadSuccess(List<String> data); // 上传成功

        /**
         * 上传出错或失败
         * @param error
         */
        void onUploadError(String error); // 上传出错
    }

    /**
     * 缩放图片的处理函数
     *
     * @param filePath  文件路径
     * @param scaleRate 压缩率（取值1-100，传入的值是多少就压缩为原来的百分之多少）
     */
    public static String transformPicture(Context context, String filePath, Integer scaleRate, Integer identifier){

        // 注意，这里的图片，宽高不压缩，只压缩图片的质量，质量压缩为原来的1/10.
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
//        Log.d(TAG,"getSDCompressedImgDir = " + MyPath.getSDCompressedImgDir());
        File imageFileSource = null;
        if(identifier != null){
            // 这里之所以把文件名中也加入identifier索引，是为了解决多线程情况下，时间戳可能相同，导致临时文件重复的问题
            imageFileSource = new File(FileUtils.getSDCompressedImgDir(), timeStamp + identifier + "_source.jpg");
        } else {
            imageFileSource = new File(FileUtils.getSDCompressedImgDir(), timeStamp + "_source.jpg");
        }

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        BitmapFactory.Options options = new BitmapFactory.Options();

        options.inJustDecodeBounds = true; //只获取图像的大小
        Bitmap bmpSizeSource = BitmapFactory.decodeFile(filePath, options);
        // 引入新的压缩策略对应图片不清晰的问题
        options.inSampleSize = calculateInSampleSize(options, 1080, 1440); // 上传图片尺寸如果大于1080*1440则按照1080*1440比例压缩

        options.inJustDecodeBounds = false;

        Bitmap bmpSource = BitmapFactory.decodeFile(filePath, options);

//        // 读取图片的旋转角度
//        int degree = ImageUtils.readPictureDegree(filePath);
//        Log.d(TAG, "degree ==== " + degree);
//        Bitmap bmpRotateSource = null;
//        if (degree == 0) {
//            bmpRotateSource = bmpSource;
//        } else {
//            bmpRotateSource = ImageUtils.rotatingImageView(degree, bmpSource);
//        }
        Bitmap bmpRotateSource = bmpSource;

//        Bitmap bmpSource = BitmapFactory.decodeFile(filePath);
        try {
            imageFileSource.createNewFile();
            FileOutputStream fosSource = new FileOutputStream(imageFileSource);
//            bmpSource.compress(Bitmap.CompressFormat.JPEG, 100, fosSource);// 保存原图
            Log.e(TAG,"scaleRate = " + scaleRate);
            if (scaleRate != null) {//0-100
                bmpRotateSource.compress(Bitmap.CompressFormat.JPEG, scaleRate, fosSource);// 将图片质量压缩为设定的压缩比率再写入文件
            } else {
                bmpRotateSource.compress(Bitmap.CompressFormat.JPEG, 10, fosSource);// 将图片质量压缩为原来的1/10再写入文件
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (bmpSource != null && !bmpSource.isRecycled()) {
            bmpSource.recycle();
        }
        if (bmpRotateSource != null && !bmpRotateSource.isRecycled()) {
            bmpRotateSource.recycle();
        }

        Log.e(TAG, "imageFileSource.getPath() = " + imageFileSource.getPath());
        try {
            saveExif(filePath, imageFileSource.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageFileSource.getPath();
    }

    /**
     * 计算缩放比例
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int width = options.outWidth;
        final int height = options.outHeight;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            //计算图片高度和我们需要高度的最接近比例值
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            //宽度比例值
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            //取比例值中的较大值作为inSampleSize
            inSampleSize = heightRatio > widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }


    /**
     * 从相册选择照片
     * @param activity
     * @param callBack 回调文件路径
     * @param isCrop 是否剪裁 true 剪裁 false 不剪裁
     */
    public static void selectSingleImageByPhoto(Activity activity, final IPathCallBack callBack , boolean isCrop){
        selectSingleImageByPhoto(activity, callBack, isCrop, null, null, null, null);
    }

    /**
     * 从相册选择照片
     *
     *  注意：裁剪图片宽与高如果设定的情况下，裁剪图片的宽高比与裁剪图片的横纵向比例必须一致。
     *
     * @param activity
     * @param callBack 回调文件路径
     * @param isCrop 是否剪裁 true 剪裁 false 不剪裁
     * @param aspectX 裁剪横向比例
     * @param aspectY 裁剪纵向比例
     * @param outputX 裁剪图片宽
     * @param outputY 裁剪图片高
     */
    public static void selectSingleImageByPhoto(Activity activity, final IPathCallBack callBack , boolean isCrop, Integer aspectX, Integer aspectY, Integer outputX, Integer outputY){
        ImageSingleSelect imageSingleSelect = new ImageSingleSelect(activity);
        imageSingleSelect.photo(new ImageSingleSelect.IPathCallBack() {
            @Override
            public void callBackPath(String filePath) {
                if (callBack!=null){
                    callBack.callBackPath(filePath);
                }
            }
        }, isCrop, aspectX, aspectY, outputX, outputY);
    }

    /**
     * 相机拍照
     * @param activity
     * @param callBack 回调文件路径
     * @param isCrop 是否剪裁 true 剪裁 false 不剪裁
     */
    public static void selectSingleImageByCamera(Activity activity, final IPathCallBack callBack , boolean isCrop){
        selectSingleImageByCamera(activity, callBack, isCrop, null, null, null, null);
    }

    /**
     * 相机拍照
     *
     *  注意：裁剪图片宽与高如果设定的情况下，裁剪图片的宽高比与裁剪图片的横纵向比例必须一致。
     *
     * @param activity
     * @param callBack 回调文件路径
     * @param isCrop 是否剪裁 true 剪裁 false 不剪裁
     * @param aspectX 裁剪横向比例
     * @param aspectY 裁剪纵向比例
     * @param outputX 裁剪图片宽
     * @param outputY 裁剪图片高
     */
    public static void selectSingleImageByCamera(Activity activity, final IPathCallBack callBack , boolean isCrop, Integer aspectX, Integer aspectY, Integer outputX, Integer outputY){
        ImageSingleSelect imageSingleSelect = new ImageSingleSelect(activity);
        imageSingleSelect.camera(new ImageSingleSelect.IPathCallBack() {
            @Override
            public void callBackPath(String filePath) {
                if (callBack!=null){
                    callBack.callBackPath(filePath);
                }
            }
        }, isCrop, aspectX, aspectY, outputX, outputY);
    }

    public interface IPathCallBack{
        void callBackPath(String filePath);
    }

    /**
     * copy exif 信息
     * @param srcFilePath
     * @param destFilePath
     * @throws Exception
     */
    private static void saveExif(String srcFilePath, String destFilePath) throws Exception {
        ExifInterface srcExif = new ExifInterface(srcFilePath);
        ExifInterface destExif = new ExifInterface(destFilePath);
        Class<ExifInterface> cls = ExifInterface.class;
        Field[] fields = cls.getFields();
        for (int i = 0; i < fields.length; i++) {
            String fieldName = fields[i].getName();
            if (!TextUtils.isEmpty(fieldName) && fieldName.startsWith("TAG")) {
                String fieldValue = fields[i].get(cls).toString();
                String attribute = srcExif.getAttribute(fieldValue);
                if (attribute != null) {
                    destExif.setAttribute(fieldValue, attribute);
                }
            }
        }
        destExif.saveAttributes();
    }

    /**
     * 下载图片回调接口
     */
    public interface IOnImageLoadListener {
        /**
         * 下载成功
         */
        void onSuccess();

        /**
         * 下载失败
         */
        void onFailed();
    }




}
