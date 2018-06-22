package com.docomo.watchy.module.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.docomo.watchy.module.base.BaseActivity;
import com.docomo.watchy.widget.ProgressWebView;
import com.docomo.watchy.R;

import butterknife.BindView;


/**
 * webview共同类
 * Created by weiyang.an on 17/1/4.
 */
public class WebViewActivity extends BaseActivity {

    private static final String TAG = WebViewActivity.class.getSimpleName();

    /** 获取WebView控件*/
    @BindView(R.id.webView)
    ProgressWebView webView;

    String defaultTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected int getContentView() {
        return R.layout.common_webview;
    }

    void init(){

        /** 获取标题和地址*/
        Intent intent = this.getIntent();
        defaultTitle = intent.getStringExtra("title");
        if (TextUtils.isEmpty(defaultTitle)) {
            defaultTitle = getString(R.string.app_name);
        }
        String url = intent.getStringExtra("url");

        findViewById(R.id.ll_common_ab_back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });

        //设置title
        final TextView actionBarTitle = (TextView) findViewById(R.id.tv_common_ab_title);

        // 启用支持javascript
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true); // 可以启用LocalStorage
        Log.d(TAG,"url:" + url);
        if(url != null){
            webView.loadUrl(url);
            webView.setWebChromeClient(webView.new WebChromeClient(){
                @Override
                public void onReceivedTitle(WebView view, String title) {
                    super.onReceivedTitle(view, title);
                    if(TextUtils.isEmpty(title)){
                        actionBarTitle.setText(defaultTitle);
                    } else {
                        actionBarTitle.setText(title);
                    }

                }
            });

            webView.setWebViewClient(new WebViewClient(){

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {

                    if(URLUtil.isNetworkUrl(url)){
                        view.loadUrl(url);
                        return super.shouldOverrideUrlLoading(view,url);
                    }

                    return true;
                }
            });
        } else {
            actionBarTitle.setText(defaultTitle);
            webView.loadData(intent.getStringExtra("data"), "text/html; charset=UTF-8", null);
        }
    }

    @Override
    public void onBackPressed() {

        back();
    }

    /**
     * 后退操作
     */
    private void back(){
        if(webView.canGoBack()){
            webView.goBack();
        } else {
            finish();
        }
    }

    /**
     * 启动 WebViewActivity
     * @param context 启动 WebActivity 的画面上下文
     * @param defaultTitle 默认标题
     * @param url 加载url
     */
    public static void start(Context context, String defaultTitle, String url) {

        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("title", defaultTitle)
                .putExtra("url", url);
        context.startActivity(intent);

    }
}
