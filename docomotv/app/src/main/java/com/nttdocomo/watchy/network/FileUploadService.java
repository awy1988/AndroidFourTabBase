package com.nttdocomo.watchy.network;

import com.nttdocomo.watchy.model.common.FileUploadResultModel;
import com.nttdocomo.watchy.model.common.ResponseModel;
import com.nttdocomo.watchy.network.common.HttpApiHelper;
import com.nttdocomo.watchy.network.interfaces.IFileUploadService;
import com.nttdocomo.watchy.util.ImageUtils;
import com.nttdocomo.watchy.model.common.FileUploadResultModel;
import com.nttdocomo.watchy.model.common.ResponseModel;
import com.nttdocomo.watchy.network.common.HttpApiHelper;
import com.nttdocomo.watchy.network.interfaces.IFileUploadService;
import com.nttdocomo.watchy.util.ImageUtils;

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
        IFileUploadService uploadService = HttpApiHelper.getRetrofitInstance().create(IFileUploadService.class);
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
