package com.demo.util;

import android.content.Context;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import android.text.TextUtils;

import com.demo.R;


/**
 * 弹出框工具类
 *
 * @author weiyang.an
 * @version 1.0 2018/6/13
 */
public class DialogUtils {
    private static final String TAG = DialogUtils.class.getSimpleName();

    //================================================================================
    // 显示提示对话框
    //================================================================================
    public static void showAlertDialog(Context context, String alertStr, final OnAlertOperationCallbackListener listener){
        showAlertDialog(context, null, "确定", "取消", alertStr, listener);
    }

    /**
     * 显示警告对话框
     * @param context 上下文对象
     * @param title 标题
     * @param positiveBtnStr 确定按钮文言（如果不设置的话，默认为：确定）
     * @param negativeBtnStr 取消按钮文言（如果不设置的话，则不显示取消按钮）
     * @param alertStr 警告内容
     * @param listener 回调方法
     */
    public static void showAlertDialog(Context context, String title, String positiveBtnStr, String negativeBtnStr, String alertStr, final OnAlertOperationCallbackListener listener){
        AlertDialog dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(alertStr);

        if(!TextUtils.isEmpty(title)){
            builder.setTitle(title);
        }

        builder.setPositiveButton((TextUtils.isEmpty(positiveBtnStr)?"确定":positiveBtnStr), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(listener != null){
                    listener.onConfirm(dialog, which);
                }
            }
        });

        if(!TextUtils.isEmpty(negativeBtnStr)){

            builder.setNegativeButton(negativeBtnStr, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(listener != null){
                        listener.onCancel(dialog, which);
                    }
                }
            });
        }

        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.common_app_theme_color));
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.common_app_theme_color));
    }

    public interface OnAlertOperationCallbackListener {
        void onConfirm(DialogInterface dialog, int which);
        void onCancel(DialogInterface dialog, int which);
    }


}
