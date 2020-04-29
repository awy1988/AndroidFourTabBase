package com.demo.corelib.util;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 类描述：文件路径相关操作
 */
public class FileUtils {

    // TODO 改造

    private static final String TAG = FileUtils.class.getSimpleName();

    //用户的图片
    public static String sd_ImgPath = Environment.getExternalStorageDirectory()
            + File.separator + "demo" + File.separator + "image";

    //App存储根目录
    public static String sd_AppPath = Environment.getExternalStorageDirectory()
            + File.separator + "demo" + File.separator ;

    //压缩的图片
    public static String sd_CompressedImgPath = Environment.getExternalStorageDirectory()
            + File.separator + "demo" + File.separator + "compressedImg";

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
     * @param uri
     * @param context
     * @return
     */
    public static String getRealPathFromURI(Uri uri, Context context) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
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

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    private static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    private static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }


}
