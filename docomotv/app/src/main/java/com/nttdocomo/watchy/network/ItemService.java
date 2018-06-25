package com.nttdocomo.watchy.network;

import com.nttdocomo.watchy.model.item.Item;
import com.nttdocomo.watchy.network.common.HttpApiHelper;
import com.nttdocomo.watchy.network.common.RequestCallbackListener;
import com.nttdocomo.watchy.network.interfaces.IItemService;

import java.util.List;
import java.util.Map;

public class ItemService {

    /**
     * 获取商品列表
     * @param queryParams 查询参数
     * @param listener
     */
    public static void getItems(Map<String, Object> queryParams, final RequestCallbackListener<List<Item>> listener) {
        HttpApiHelper.executeRequest(getItemService().getItems(HttpApiHelper.handleQueryParams(queryParams)), listener);
    }

    public static void getItems(String nextLinkUrl, final RequestCallbackListener<List<Item>> listener) {
        HttpApiHelper.executeRequest(getItemService().getItems(nextLinkUrl), listener);
    }

    private static IItemService getItemService() {
        return HttpApiHelper.getRetrofitInstance().create(IItemService.class);
    }

}
