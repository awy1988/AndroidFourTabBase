package com.docomotv.network.interfaces;

import com.docomotv.model.common.ResponseModel;
import com.docomotv.model.item.Item;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

import static com.docomotv.constant.ApiConstant.API_ITEMS;


public interface IItemService {

    @GET(API_ITEMS)
    Call<ResponseModel<List<Item>>> getItems(@QueryMap Map<String, String> options);

}
