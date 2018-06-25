package com.nttdocomo.watchy.widget;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import com.nttdocomo.watchy.R;


/**
 *
 * 加载中对话框自定义类
 *
 * @author weiyang.an
 * @version 1.0 2017/10/24
 */
public class CustomProgressDialog extends ProgressDialog {

    public CustomProgressDialog(Context context, String content) {
        super(context, R.style.CommonProgressDialog);
        // 按对话框以外的地方不起作用。按返回键还起作用
        setCanceledOnTouchOutside(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setContentView(R.layout.common_progress_dialog);
    }

    private void stop() {
        this.dismiss();
    }
}