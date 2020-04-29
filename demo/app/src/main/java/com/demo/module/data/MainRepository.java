package com.demo.module.data;

import com.demo.corelib.model.item.Item;
import com.demo.corelib.network.base.RequestCallbackListener;
import com.demo.module.data.remote.api.item.ItemService;

import java.util.List;
import java.util.Map;

public class MainRepository {

    private static MainRepository INSTANCE = null;

    // 数据来源分为两部分，一部分是本地数据库，一部分是网络
    private MainRepository() {

    }

    public static MainRepository getInstance() {
        if (INSTANCE == null) {
            synchronized (MainRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MainRepository();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * TODO 改造该方法，加入本地缓存逻辑。
     * 获取远程商品数据
     * @param queryParams
     * @param listener
     */
    public void getItems(Map<String, Object> queryParams, RequestCallbackListener<List<Item>> listener) {
        ItemService.getItems(queryParams, listener);
    }

    /**
     * TODO 改造该方法，加入本地缓存逻辑。
     * 从远程加载更多商品数据
     * @param nextLinkUrl
     * @param listener
     */
    public void loadMoreItems(String nextLinkUrl, RequestCallbackListener<List<Item>> listener) {
        ItemService.getItems(nextLinkUrl, listener);
    }


}
