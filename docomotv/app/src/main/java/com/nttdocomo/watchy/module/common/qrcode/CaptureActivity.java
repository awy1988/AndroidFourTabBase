/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.nttdocomo.watchy.module.common.qrcode;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.nttdocomo.watchy.module.common.WebViewActivity;
import com.nttdocomo.watchy.util.FileUtils;
import com.nttdocomo.watchy.util.zxing.camera.CameraManager;
import com.nttdocomo.watchy.util.zxing.core.QRCodeReader;
import com.nttdocomo.watchy.util.zxing.core.RGBLuminanceSource;
import com.nttdocomo.watchy.util.zxing.decode.DecodeThread;
import com.nttdocomo.watchy.util.zxing.utils.BeepManager;
import com.nttdocomo.watchy.util.zxing.utils.CaptureActivityHandler;
import com.nttdocomo.watchy.util.zxing.utils.InactivityTimer;
import com.nttdocomo.watchy.R;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.EncodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Hashtable;

/**
 * This activity opens the camera and does the actual scanning on a background
 * thread. It draws a viewfinder to help the user place the barcode correctly,
 * shows feedback as the image processing is happening, and then overlays the
 * results when a scan is successful.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 * @author Sean Owen
 */
public final class CaptureActivity extends Activity implements SurfaceHolder.Callback {

    private static final String TAG = CaptureActivity.class.getSimpleName();

    private CameraManager cameraManager;
    private CaptureActivityHandler handler;
    private InactivityTimer inactivityTimer;
    private BeepManager beepManager;

    private RelativeLayout rlActionBar;
    private SurfaceView scanPreview = null;
    private RelativeLayout scanContainer;
    private RelativeLayout scanCropView;
    private ImageView mQrLineView;
    private ProgressDialog mProgress;
    String photoPath;

    private Rect mCropRect = null;

    public Handler getHandler() {
        return handler;
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    private static final int CHOOSE_PIC = 0x2131;
    static final int PARSE_BARCODE_SUC = 3035;
    static final int PARSE_BARCODE_FAIL = 3036;
    private boolean isHasSurface = false;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.capture_act);

        scanPreview = (SurfaceView) findViewById(R.id.capture_preview);
        scanContainer = (RelativeLayout) findViewById(R.id.capture_container);
        scanCropView = (RelativeLayout) findViewById(R.id.capture_crop_view);
        mQrLineView = (ImageView) findViewById(R.id.capture_scan_line);
        rlActionBar = (RelativeLayout) findViewById(R.id.rl_action_bar);

        inactivityTimer = new InactivityTimer(this);
        beepManager = new BeepManager(this);

        // 初始化标题栏
        initActionBar();
//		TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT,
//				0.9f);
//		animation.setDuration(4500);
//		animation.setRepeatCount(-1);
//		animation.setRepeatMode(Animation.RESTART);
//		scanLine.startAnimation(animation);
        initAnima();
    }

    /**
     * 初始化标题栏
     */
    private void initActionBar() {

        // 导航栏左侧[返回]按钮
        LinearLayout llLeftBackButton = (LinearLayout) rlActionBar.findViewById(R.id.ll_ab_back_button);
        llLeftBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 导航栏右侧[从相册找]按钮
        LinearLayout llRightOpenAlbumButton = (LinearLayout) rlActionBar.findViewById(R.id.ll_ab_right_button);
        llRightOpenAlbumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickPhoto();
            }
        });
    }

    private void initAnima() {
        ScaleAnimation animation = new ScaleAnimation(1.0f, 1.0f, 0.0f, 1.0f);
        animation.setRepeatCount(-1);
        animation.setRepeatMode(Animation.RESTART);
        //动画变化速率
        animation.setInterpolator(new LinearInterpolator());
        animation.setDuration(3000);
        mQrLineView.startAnimation(animation);
    }

    /**
     * 调用本地相册
     */
    private void pickPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, CHOOSE_PIC);
    }

    Handler barHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case PARSE_BARCODE_SUC:
                    //viewfinderView.setRun(false);
                    //Util.toastMsg("扫描结果家校邦:"+(String) msg.obj);
                    if (mProgress != null && mProgress.isShowing()) {
                        mProgress.dismiss();
                    }
                    parsePicCallback((String) msg.obj);

                    break;
                case PARSE_BARCODE_FAIL:
                    //showDialog((String) msg.obj);
                    if (mProgress != null && mProgress.isShowing()) {
                        mProgress.dismiss();
                    }
                    new AlertDialog.Builder(CaptureActivity.this).setTitle("提示").setMessage("扫描失败！").setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
                    break;
            }
            super.handleMessage(msg);
        }

    };

    /**
     * 图片解析回调
     *
     * @param result 解析二维码的结果
     */
    private void parsePicCallback(String result) {

        Log.d(TAG,"parsePicCallback result = " + result);

//        String category = "";
//        if(result.contains("couponDetail")){
//            category = "couponQRCode"; // 优惠券(邦学币)
//        }
//
//        if(getIntent().hasExtra("category")){
//            category = getIntent().getStringExtra("category"); // 获取种类
//        }

//        switch (category){
//            case "couponQRCode": // 邦学币优惠券
//                String couponId = result.substring(result.indexOf("=") + 1, result.length());
//                startActivity(new Intent(CaptureActivity.this, CouponDetailActivity_.class).putExtra("couponId", couponId));
//                CaptureActivity.this.finish();
//                break;
//            case "contestEntryCodeQRCode": // 扫描比赛参赛码
//                String[] temp = result.split("cr/");
//                String entryCode = temp[temp.length - 1];
//                Log.d(TAG,"entryCode : " + entryCode);
//                Intent data = new Intent();
//                data.putExtra("entryCode",entryCode);
//                setResult(RESULT_OK,data);
//                CaptureActivity.this.finish();
//                break;
//            case "webJSInterface": // webView调用原生扫一扫，直接将解析结果返回
//                Intent intent = new Intent();
//                intent.putExtra("result",result);
//                setResult(RESULT_OK,intent);
//                CaptureActivity.this.finish();
//                break;
//            default:
//                // 从主页面扫一扫进入，直接解析结果，打开需要访问的页面
//
//                WebViewActivity_.intent(CaptureActivity.this)
//                    .extra("url", result)
//                    .start();
//                CaptureActivity.this.finish();
//                break;
//        }

        // 从主页面扫一扫进入，直接解析结果，打开需要访问的页面
        if (URLUtil.isNetworkUrl(result)) {
            WebViewActivity.start(CaptureActivity.this,"",result);
        }
        CaptureActivity.this.finish();

    }

    /**
     * 图片解析
     *
     * @param path
     * @return
     */
    public String parsLocalPic(String path) {
        String parseResult = null;

        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF8");
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 先获取原大小
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        options.inJustDecodeBounds = false; // 获取新的大小
        // 缩放比
        int be = (int) (options.outHeight / (float) 200);
        if (be <= 0){
            be = 1;
        }

        options.inSampleSize = be;
        bitmap = BitmapFactory.decodeFile(path, options);
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        RGBLuminanceSource source = new RGBLuminanceSource(bitmap);
        BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
        QRCodeReader reader2 = new QRCodeReader();
        Result result;
        try {
            result = reader2.decode(bitmap1, hints);
            parseResult = result.getText();

        } catch (NotFoundException e) {
            parseResult = null;
        } catch (ChecksumException e) {
            parseResult = null;
        } catch (FormatException e) {
            parseResult = null;
        }
        return parseResult;
    }

    /**
     * 图片解析的回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("steven", "data.getData()" + data);
        if (data != null) {
            mProgress = new ProgressDialog(CaptureActivity.this);
            mProgress.setMessage("正在扫描...");
            mProgress.setCancelable(false);
            mProgress.show();
            final ContentResolver resolver = getContentResolver();


            if (resultCode == RESULT_OK) {
                switch (requestCode) {
                    // 如果是直接从相册获取
                    case CHOOSE_PIC:
                        try {
                            if (data != null) {
                                photoPath = FileUtils.getRealPathFromURI(data.getData(), this);
                            } else {
                                finish();
                            }

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Looper.prepare();
                                    String result = parsLocalPic(photoPath);
                                    if (result != null) {
                                        Message m = Message.obtain();
                                        m.what = PARSE_BARCODE_SUC;
                                        m.obj = result;
                                        barHandler.sendMessage(m);
                                    } else {
                                        Message m = Message.obtain();
                                        m.what = PARSE_BARCODE_FAIL;
                                        m.obj = "扫描失败！";
                                        barHandler.sendMessage(m);
                                    }
                                    Looper.loop();
                                }
                            }).start();


                        } catch (Exception e) {
                            e.printStackTrace();
                            finish();
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // CameraManager must be initialized here, not in onCreate(). This is
        // necessary because we don't
        // want to open the camera driver and measure the screen size if we're
        // going to show the help on
        // first launch. That led to bugs where the scanning rectangle was the
        // wrong size and partially
        // off screen.
        cameraManager = new CameraManager(getApplication());

        handler = null;

        if (isHasSurface) {
            // The activity was paused but not stopped, so the surface still
            // exists. Therefore
            // surfaceCreated() won't be called, so init the camera here.
            initCamera(scanPreview.getHolder());
        } else {
            // Install the callback and wait for surfaceCreated() to init the
            // camera.
            scanPreview.getHolder().addCallback(this);
        }

        inactivityTimer.onResume();
    }

    @Override
    protected void onPause() {
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        inactivityTimer.onPause();
        beepManager.close();
        cameraManager.closeDriver();
        if (!isHasSurface) {
            scanPreview.getHolder().removeCallback(this);
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (holder == null) {
            Log.e(TAG, "*** WARNING *** surfaceCreated() gave us a null surface!");
        }
        if (!isHasSurface) {
            isHasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isHasSurface = false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    /**
     * A valid barcode has been found, so give an indication of success and show
     * the results.
     *
     * @param rawResult The contents of the barcode.
     * @param bundle    The extras
     */
    public void handleDecode(Result rawResult, Bundle bundle) {
        inactivityTimer.onActivity();
        beepManager.playBeepSoundAndVibrate();

//        lastResult = rawResult;
//
//        ResultHandler resultHandler = new ResultHandler(parseResult(rawResult));
//
////		Util.toastMsg(resultHandler.getDisplayContents().toString());
//
//        boolean fromLiveScan = barcode != null;
//        if (barcode == null) {
//            Log.i("steven", "rawResult.getBarcodeFormat().toString():" + rawResult.getBarcodeFormat().toString());
//            Log.i("steven", "resultHandler.getType().toString():" + resultHandler.getType().toString());
//            Log.i("steven", "resultHandler.getDisplayContents():" + resultHandler.getDisplayContents());
//        } else {
//
//            //Util.toastMsg("扫描结果家校邦:"+resultHandler.getDisplayContents().toString());
//            ok(resultHandler.getDisplayContents().toString());
//        }

        parsePicCallback(rawResult.getText());
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }
        if (cameraManager.isOpen()) {
            Log.w(TAG, "initCamera() while already open -- late SurfaceView callback?");
            return;
        }
        try {
            cameraManager.openDriver(surfaceHolder);
            // Creating the handler starts the preview, which can also throw a
            // RuntimeException.
            if (handler == null) {
                handler = new CaptureActivityHandler(this, cameraManager, DecodeThread.ALL_MODE);
            }

            initCrop();
        } catch (IOException ioe) {
            Log.w(TAG, ioe);
            displayFrameworkBugMessageAndExit();
        } catch (RuntimeException e) {
            // Barcode Scanner has seen crashes in the wild of this variety:
            // java.?lang.?RuntimeException: Fail to connect to camera service
            Log.w(TAG, "Unexpected error initializing camera", e);
            displayFrameworkBugMessageAndExit();
        }
    }

    private void displayFrameworkBugMessageAndExit() {
        // camera error
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.app_name));
        builder.setMessage("相机打开出错，请稍后重试");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }

        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });
        builder.show();
    }

    public void restartPreviewAfterDelay(long delayMS) {
        if (handler != null) {
            handler.sendEmptyMessageDelayed(R.id.restart_preview, delayMS);
        }
    }

    public Rect getCropRect() {
        return mCropRect;
    }

    /**
     * 初始化截取的矩形区域
     */
    private void initCrop() {
        int cameraWidth = cameraManager.getCameraResolution().y;
        int cameraHeight = cameraManager.getCameraResolution().x;

        /** 获取布局中扫描框的位置信息 */
        int[] location = new int[2];
        scanCropView.getLocationInWindow(location);

        int cropLeft = location[0];
        int cropTop = location[1] - getStatusBarHeight();

        int cropWidth = scanCropView.getWidth();
        int cropHeight = scanCropView.getHeight();

        /** 获取布局容器的宽高 */
        int containerWidth = scanContainer.getWidth();
        int containerHeight = scanContainer.getHeight();

        /** 计算最终截取的矩形的左上角顶点x坐标 */
        int x = cropLeft * cameraWidth / containerWidth;
        /** 计算最终截取的矩形的左上角顶点y坐标 */
        int y = cropTop * cameraHeight / containerHeight;

        /** 计算最终截取的矩形的宽度 */
        int width = cropWidth * cameraWidth / containerWidth;
        /** 计算最终截取的矩形的高度 */
        int height = cropHeight * cameraHeight / containerHeight;

        /** 生成最终的截取的矩形 */
        mCropRect = new Rect(x, y, width + x, height + y);
    }

    private int getStatusBarHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}