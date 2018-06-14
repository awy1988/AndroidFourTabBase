package com.docomotv.util;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 类描述：文件路径相关操作
 * @author weiyang.an
 */
public class FileUtils {

    // TODO 改造

    private static final String TAG = FileUtils.class.getSimpleName();

    //用户的图片
    public static String sd_ImgPath = Environment.getExternalStorageDirectory()
            + File.separator + "docomotv" + File.separator + "image";

    //App存储根目录
    public static String sd_AppPath = Environment.getExternalStorageDirectory()
            + File.separator + "docomotv" + File.separator ;

    //压缩的图片
    public static String sd_CompressedImgPath = Environment.getExternalStorageDirectory()
            + File.separator + "docomotv" + File.separator + "compressedImg";

    /**
     * 创建文件夹用于保存缓存的图片 *
     */
    public static final void createPath(String strPath) {

        try {
            File fileName = new File(strPath);
            if (fileName.exists() == false) {
                fileName.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 创建文件夹用于保存缓存的图片 *
     */
    public static final void createImgPath() {

        try {
            File fileName = new File(sd_ImgPath);
            if (fileName.exists() == false) {
                fileName.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String getSDCardImgPath() {

        createImgPath();

        return sd_ImgPath + "/";
    }


    /**
     * 删除文件或目录
     * @param strPath
     */
    public static void deleteFile(String strPath) {
        File file = null;
        try {
            file = new File(strPath);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        if (file.exists()) { // 判断文件是否存在
            if (file.isFile()) { // 判断是否是文件
                file.delete(); // delete()方法 你应该知道 是删除的意思;
            } else if (file.isDirectory()) { // 否则如果它是一个目录
                File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
                for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
                    deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
                }
            }
            file.delete();
        }
    }

    /**
     * 删除文件
     *
     * @param file
     */
    private static void deleteFile(File file) {

        if (file.exists()) { // 判断文件是否存在
            if (file.isFile()) { // 判断是否是文件
                file.delete(); // delete()方法 你应该知道 是删除的意思;
            }
        }
    }

    /**
     * 获取apk下载路径
     * @return
     */
    public static String getApkDownloadDir(){

        String apkDownloadDir = sd_AppPath + "apk" + File.separator;
        createPath(apkDownloadDir);

        return apkDownloadDir;
    }

    /**
     * 在应用的图片文件夹下生成空的图片文件（主要用于上传图片之前的照相或者选择操作）
     * @return
     */
    public static File getEmptyImageFile() {
        File mediaStorageDir = new File(sd_ImgPath);
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "failed to create directory");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator + "M" + timeStamp
                + ".jpg");

        return mediaFile;
    }

    /**
     * 在应用的图片文件夹下生成空的图片文件（主要用于上传图片之前的照相或者选择操作）
     * @return
     */
    public static Uri getEmptyImageFileUri() {
        return Uri.fromFile(getEmptyImageFile());
    }

    /**
     * 获取媒体路径
     *
     * @param contentUri
     * @param activity
     * @return
     */
    public static String getRealPathFromURI(Uri contentUri, Activity activity) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = activity.managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    /**
     * 获取压缩图片存储路径
     * @return
     */
    public static String getSDCompressedImgDir() {

        createPath(sd_CompressedImgPath);

        return sd_CompressedImgPath;
    }


    /**
     * 得到一个文件目录的大小
     * @param dir
     * @return
     * @throws IOException
     */
    private Long getDirectoryFilesSize(File dir) throws IOException {

        long directoryFileSize = 0;
        long childFolderSize = 0;

        File[] files = dir.listFiles();
        if (files == null) {
            throw new IllegalArgumentException("not a directory: " + dir);
        }
        for (File file : files) {
            if (file.isDirectory()) {
                childFolderSize =  getDirectoryFilesSize(file);
            } else {
                childFolderSize = file.length();
            }
            //这里要累加所有的文件长度
            directoryFileSize = directoryFileSize + childFolderSize;
        }
        return directoryFileSize;
    }

}
