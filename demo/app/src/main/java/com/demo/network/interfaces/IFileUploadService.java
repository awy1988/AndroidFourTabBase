package com.demo.network.interfaces;

import com.demo.constant.ApiConstant;
import com.demo.model.common.FileUploadResultModel;
import com.demo.model.common.ResponseModel;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * 图片上传
 */
public interface IFileUploadService {

    @Multipart
    @POST(ApiConstant.API_UPLOAD_USER_LOGO)
    Call<ResponseModel<FileUploadResultModel>> uploadUserLogo(@Part MultipartBody.Part imagePart);

}
