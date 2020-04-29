package com.demo.module.data.remote.api.item;

import com.demo.corelib.model.item.Item;
import com.demo.corelib.network.base.HttpApiHelper;
import com.demo.corelib.network.base.RequestCallbackListener;

import java.util.List;
import java.util.Map;

public class ItemService {

    /**
     * TODO 优化逻辑，这个类的使用最好使用依赖注入的方式。
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
