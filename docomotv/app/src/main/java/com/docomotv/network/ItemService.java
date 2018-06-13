package com.docomotv.network;

import com.docomotv.model.item.Item;
import com.docomotv.network.base.HttpApiHelper;
import com.docomotv.network.base.RequestCallbackListener;
import com.docomotv.network.interfaces.IItemService;

import java.util.List;
import java.util.Map;

public class ItemService {

    /**
     * 获取商品列表
     * @param queryParams 查询参数
     * @param listener
     */
    public static void getItems(Map<String, String> queryParams, final RequestCallbackListener<List<Item>> listener) {
        HttpApiHelper.executeRequest(HttpApiHelper.getRetrofitInstance().create(IItemService.class).getItems(queryParams), listener);
    }


}
