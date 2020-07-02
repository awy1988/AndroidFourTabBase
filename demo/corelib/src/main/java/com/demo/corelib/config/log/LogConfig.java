package com.demo.corelib.config.log;

import android.os.Handler;
import android.os.HandlerThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.demo.corelib.util.Utils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.CsvFormatStrategy;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.LogStrategy;
import com.orhanobut.logger.LogcatLogStrategy;
import com.orhanobut.logger.Logger;
import java.io.File;

/**
 * Log 输出配置类
 */
public class LogConfig {

    private static final int MAX_BYTES = 500 * 1024; // 500K averages to a 4000 lines per file

    public void init() {
        // 默认采用格式化输出方式
        pretty();
    }

    /**
     * 采用普通模式输出 log
     */
    public LogConfig normal() {
        Logger.addLogAdapter(new AndroidLogAdapter(new FormatStrategy() {

            LogStrategy logStrategy = new LogcatLogStrategy();

            @Override
            public void log(int priority, @Nullable String tag, @NonNull String message) {
                logStrategy.log(priority, tag, message);
            }
        }));
        return this;
    }

    /**
     * 采用 pretty 模式输出 log
     */
    public LogConfig pretty() {
        Logger.addLogAdapter(new AndroidLogAdapter());
        return this;
    }

    /**
     * 将日志输出到硬盘。
     * 注意：使用此方法时，需要申请系统写外部存储的权限。（<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>）
     */
    public LogConfig disk(String folder) {
        CsvFormatStrategy csvFormatStrategy = CsvFormatStrategy.newBuilder()
            .logStrategy(getDiskLogStrategy(folder))
            .build();
        Logger.addLogAdapter(new DiskLogAdapter(csvFormatStrategy));
        return this;
    }

    /**
     * 清除日志适配器
     */
    public LogConfig clearLogAdapter() {
        Logger.clearLogAdapters();
        return this;
    }

    /**
     * 自定义磁盘输出策略
     * @param folder 磁盘输出目录
     */
    private LogStrategy getDiskLogStrategy(String folder) {

        if (folder == null) {
            // 默认存储地址为：/storage/emulated/0/Android/data/packagename/files , 不同的机型可能路径会有差异。
            String diskPath = Utils.getApp().getExternalFilesDir(null).getAbsolutePath();
            folder = diskPath + File.separatorChar + "logger";
        }

        HandlerThread ht = new HandlerThread("AndroidFileLogger." + folder);
        ht.start();
        Handler handler = new DiskLogStrategy.WriteHandler(ht.getLooper(), folder, MAX_BYTES);
        return new DiskLogStrategy(handler);
    }

}
