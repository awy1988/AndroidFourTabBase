package com.docomotv.module;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Environment;

import com.docomotv.constant.ApiConstant;

import java.util.List;

/**
 * @author weiyang.an
 * @version 1.0 2018/6/11
 */
public class App extends Application {

    private static final String TAG = App.class.getSimpleName();

    //================================================================================
    // 应用程序基本信息
    //================================================================================
    private static Context _context;// 应用程序的抽象基类对象
    private static App _application;// 入口启动类对象

    //================================================================================
    // 设备屏幕信息
    //================================================================================
    public static float sScale;// 显示密度（表示每英寸有多少个显示点(逻辑值)，通常屏幕大时，density就大，屏幕小时，density就小）
    public static int sWidthDp;// 屏幕宽度(dp)
    public static int sWidthPix;// 屏幕宽度(像素)
    public static int sHeightPix;// 屏幕高度(像素)

    // SD卡是否可用（true:手机已经绑定SD卡, false:手机没有SD卡）
    public static Boolean sSDCardIsMounted;

    // 状态栏的高度
    public static int sStatusBarHeight;

    /**
     * 启动时调用的第一个方法
     * */
    @Override
    public void onCreate() {
        super.onCreate();

        // 进程名
        String procName = getCurProcessName(getApplicationContext());
        if (this.getPackageName().equals(procName)) {
            // 设置应用程序的基本的实例（任意位置都可以参照）
            App._context = getApplicationContext();
            App._application = this;

            // 检测手机是否挂载SD卡
            final String storageState = Environment.getExternalStorageState();

            // true:手机已经绑定SD卡, false:手机没有SD卡
            sSDCardIsMounted = Environment.MEDIA_MOUNTED.equals(storageState);

            sScale = getResources().getDisplayMetrics().density;
            sWidthPix = getResources().getDisplayMetrics().widthPixels;
            sHeightPix = getResources().getDisplayMetrics().heightPixels;
            sWidthDp = (int) (sWidthPix / sScale);
        }

        setStatusBarHeight();

        initThirdPartyAuthorization();


        // 异常系页面初始化
        initLoadSir();
    }

    /**
     * 获得状态栏的高度
     */
    public static void setStatusBarHeight(){
        int statusHeight = -1;
        try {
            Class clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = _context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
        }

        sStatusBarHeight = statusHeight;
    }


    /**
     * 获取入口启动类对象的方法
     * */
    public static App getApp(){
        return App._application;
    }

    /**
     * 获取应用程序的抽象基类对象的方法
     */
    public static Context getContext(){
        return App._context;
    }

    /**
     * 获取当前版本
     * */
    public String getVersion() {
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            return pInfo.versionName;
        } catch (Exception e) {
            return "";
        }
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }



    /**
     * 获得当前进程的名字
     *
     * @param context
     * @return 进程号
     */
    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }

    /**
     * 获取UserAgent，用于Http请求头中添加UserAgent字段
     * @return userAgent字符串
     */
    public String getUserAgent() {
        return ApiConstant.BASE_UA_APP_NAME +"/"+App.getApp().getVersion() + " "+ getDeviceUserAgent();
    }


    /**
     * 获取设备User-Agent的后半部分
     * @return
     */
    private String getDeviceUserAgent(){
        String defaultUserAgent = System.getProperty("http.agent");
        String[] uaSplit = defaultUserAgent.split("\\(");
        return "(" + uaSplit[1];
    }


    /**
     * 初始化第三方授权控件
     * */
    public void initThirdPartyAuthorization() {

    }

    /**
     * 初始化异常系页面配置
     */
    private void initLoadSir() {
//        ProgressCallback loadingCallback = new ProgressCallback.Builder()
//                .setTitle("Loading")
//                .build();
//        LoadSir.beginBuilder()
//                .addCallback(loadingCallback)
//                .addCallback(new NetworkErrorCallback())
//                .commit();
    }

}
