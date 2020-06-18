package com.demo.corelib.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.core.breakpoint.BreakpointInfo;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.cause.ResumeFailedCause;
import com.liulishuo.okdownload.core.listener.DownloadListener2;
import java.io.File;
import java.util.concurrent.atomic.AtomicLong;

public class DownLoadUtils {
    public static void downloadFile(String fileUrl, File storeDirectory,
        String downloadFileName, final DownloadCallbackListener downloadCallbackListener) {

        DownloadTask task = new DownloadTask.Builder(fileUrl, storeDirectory)
            .setFilename(downloadFileName)
            .setPassIfAlreadyCompleted(false)
            .setMinIntervalMillisCallbackProcess(80)
            .setAutoCallbackToUIThread(true)
            .build();

        task.enqueue(new DownloadListener2() {

            private final AtomicLong downloadedSize = new AtomicLong();
            Long totalSize = 0L;

            @Override
            public void taskStart(@NonNull DownloadTask task) {
                if (downloadCallbackListener != null) {
                    downloadCallbackListener.onDownloadStart();
                }
            }

            @Override
            public void downloadFromBeginning(@NonNull DownloadTask task, @NonNull BreakpointInfo info,
                @NonNull ResumeFailedCause cause) {
                super.downloadFromBeginning(task, info, cause);
                downloadedSize.set(0);
                totalSize = info.getTotalLength();
            }

            @Override
            public void fetchProgress(@NonNull DownloadTask task, int blockIndex, long increaseBytes) {
                super.fetchProgress(task, blockIndex, increaseBytes);

                Long offset = downloadedSize.addAndGet(increaseBytes);
                int downloadedProgress = 0;

                if (totalSize != null && totalSize != 0) {
                    downloadedProgress = (int) Math.round((offset.doubleValue() / totalSize.doubleValue()) * 100);
                }

                downloadCallbackListener.onDownloading(downloadedProgress);
            }

            @Override
            public void taskEnd(@NonNull DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause) {

                if (downloadCallbackListener != null) {
                    downloadCallbackListener.onDownloadEnd(realCause);
                }
            }
        });
    }

    public interface DownloadCallbackListener {
        void onDownloadStart();

        void onDownloadEnd(Exception endException);

        /**
         * 下载中回调方法
         * @param progress progress取值为0-100，表示加载的百分比
         */
        void onDownloading(int progress);
    }
}
