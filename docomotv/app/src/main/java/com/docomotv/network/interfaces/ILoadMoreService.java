package com.docomotv.network.interfaces;

import com.docomotv.model.common.ResponseModel;
import com.docomotv.network.base.RequestCallbackListener;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;


public interface ILoadMoreService<T> {

    @GET
    Call<ResponseModel<T>> loadMore(@Url String nextLinkUrl, RequestCallbackListener<T> listener);

}
