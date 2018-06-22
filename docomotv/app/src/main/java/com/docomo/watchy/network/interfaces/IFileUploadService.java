package com.docomo.watchy.network.interfaces;

import com.docomo.watchy.constant.ApiConstant;
import com.docomo.watchy.model.common.ResponseModel;
import com.docomo.watchy.constant.ApiConstant;
import com.docomo.watchy.model.common.FileUploadResultModel;
import com.docomo.watchy.model.common.ResponseModel;

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
