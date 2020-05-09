package com.demo.corelib.network;


import com.demo.corelib.constant.ApiConstant;
import com.demo.corelib.model.common.FileUploadResultModel;
import com.demo.corelib.model.common.ResponseModel;
import com.demo.corelib.network.base.HttpApiHelper;
import com.demo.corelib.network.interfaces.IFileUploadService;
import com.demo.corelib.util.ImageUtils;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FileUploadService {

    private static final String FORM_DATA_NAME_LOGO = "logo";

    /**
     * 上传文件类型
     */
    public enum Category {
        USER_LOGO // 用户logo
        // TODO 添加上传文件类型
    }

    /**
     * 上传单个文件
     */
    public static void uploadFile(String filePath, Category category, final ImageUtils.OnFileUploadResultListener listener) {
        File file =  new File(filePath);
        RequestBody requestFile = RequestBody.create( HttpApiHelper.guessMimeType(filePath), file);
        // TODO 改造这里的逻辑，将上传路径与是否需要debug的值传入到此方法中。
        IFileUploadService uploadService = HttpApiHelper.getRetrofitInstance(ApiConstant.BASE_URL, ApiConstant.IS_DEBUG).create(IFileUploadService.class);
        MultipartBody.Part part = null;
        Call<ResponseModel<FileUploadResultModel>> call = null;
        switch (category) {
            case USER_LOGO:
                part = MultipartBody.Part.createFormData(FORM_DATA_NAME_LOGO, file.getName(), requestFile );
                call = uploadService.uploadUserLogo(part);
                break;
            default:
                break;
        }

        // 这里的代码可能会根据实际需求进行修改
        call.enqueue(new Callback<ResponseModel<FileUploadResultModel>>() {
            @Override
            public void onResponse(Call<ResponseModel<FileUploadResultModel>> call, Response<ResponseModel<FileUploadResultModel>> response) {
                if (response != null && response.body() != null) {

                    if (listener != null) {

                        if (response.body().getError() != null) {
                            listener.onUploadError(response.body().getError().getMessage() == null ? "upload error" : response.body().getError().getMessage());
                            return;
                        }

                        listener.onUploadSuccess(response.body().getData().getId());
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseModel<FileUploadResultModel>> call, Throwable t) {
                if (listener != null) {
                    listener.onUploadFailure(t.getMessage());
                }
            }
        });
    }

}
