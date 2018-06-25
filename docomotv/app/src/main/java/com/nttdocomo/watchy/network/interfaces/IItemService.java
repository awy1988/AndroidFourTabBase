package com.nttdocomo.watchy.network.interfaces;

import com.nttdocomo.watchy.constant.ApiConstant;
import com.nttdocomo.watchy.model.common.ResponseModel;
import com.nttdocomo.watchy.model.item.Item;

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
