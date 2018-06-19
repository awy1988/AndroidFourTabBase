package com.docomotv.util.image;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;

import com.docomotv.constant.AppConstant;


/**
 * 拍照选择照片的工具类
 * 调用的页面可省去ActivityResult
 *
 * @author weiyang.an
 * @version 1.0 2018/6/19
 */

public class ImageSingleSelect {


    public static final String CAMERA_EXTRA = "camera";
    public static final String PHOTO_EXTRA = "photo";

    private Activity mActivity;

    public ImageSingleSelect(Activity activity) {
        mActivity = activity;
    }


    /**
     * @param callPathBack 回调路径的接口
     * @param isCrop       是否剪裁 true剪裁 false 不剪裁
     */
    public void camera(final IPathCallBack callPathBack, boolean isCrop) {

        camera(callPathBack, isCrop, null, null, null, null);

    }

    /**
     * @param callPathBack 回调路径的接口
     * @param isCrop       是否剪裁 true剪裁 false 不剪裁
     */
    public void camera(final IPathCallBack callPathBack, boolean isCrop, Integer aspectX, Integer aspectY, Integer outputX, Integer outputY) {
        initReceiver(callPathBack);
        Intent intent = new Intent(mActivity, ImageSingleSelectActivity.class);
        intent.putExtra("type", CAMERA_EXTRA)
            .putExtra("isCrop", isCrop);

        if (aspectX != null) {
            intent.putExtra("aspectX", aspectX);
        }

        if (aspectY != null) {
            intent.putExtra("aspectY", aspectY);
        }

        if (outputX != null) {
            intent.putExtra("outputX", outputX);
        }

        if (outputY != null) {
            intent.putExtra("outputY", outputY);
        }

        mActivity.startActivity(intent);

    }

    public void photo(final IPathCallBack onPathCallBack, boolean isCrop) {

        camera(onPathCallBack, isCrop, null, null, null, null);
    }

    public void photo(final IPathCallBack onPathCallBack, boolean isCrop, Integer aspectX, Integer aspectY, Integer outputX, Integer outputY) {
        initReceiver(onPathCallBack);
        Intent intent = new Intent(mActivity, ImageSingleSelectActivity.class);
        intent.putExtra("type", PHOTO_EXTRA)
            .putExtra("isCrop", isCrop);

        if (aspectX != null) {
            intent.putExtra("aspectX", aspectX);
        }

        if (aspectY != null) {
            intent.putExtra("aspectY", aspectY);
        }

        if (outputX != null) {
            intent.putExtra("outputX", outputX);
        }

        if (outputY != null) {
            intent.putExtra("outputY", outputY);
        }

        mActivity.startActivity(intent);
    }

    /**
     * 回调过来的是文件路径
     */
    public interface IPathCallBack {
        void callBackPath(String filePath);
    }

    private void initReceiver(final IPathCallBack callPathBack) {
        final IntentFilter filter = new IntentFilter();
        filter.addAction(AppConstant.BROADCAST_ACTION_SINGLE_IMAGE_SELECT);
        LocalBroadcastManager.getInstance(mActivity).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action != null) {
                    String filePath = intent.getStringExtra("filePath");
                    switch (action) {
                        case AppConstant.BROADCAST_ACTION_SINGLE_IMAGE_SELECT:
                            if (!TextUtils.isEmpty(filePath)) {
                                if (callPathBack != null) {
                                    callPathBack.callBackPath(filePath);
                                }
                                // 图片获取成功后，需要注销本广播接收器
                                LocalBroadcastManager.getInstance(mActivity).unregisterReceiver(this);
                            }
                            break;
                        default:
                            break;
                    }
                }

            }
        },filter);
    }
}
