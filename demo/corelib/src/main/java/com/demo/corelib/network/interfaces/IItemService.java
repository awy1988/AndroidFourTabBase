package com.demo.corelib.network.interfaces;


import com.demo.corelib.constant.ApiConstant;
import com.demo.corelib.model.common.ResponseModel;
import com.demo.corelib.model.item.Item;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;


public interface IItemService {

    @GET(ApiConstant.API_ITEMS)
    Call<ResponseModel<List<Item>>> getItems(@QueryMap Map<String, String> options);

    @GET
    Call<ResponseModel<List<Item>>> getItems(@Url String nextLinkUrl);

}
