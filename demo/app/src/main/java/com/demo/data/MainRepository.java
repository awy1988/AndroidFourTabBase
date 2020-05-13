package com.demo.data;

import com.demo.corelib.model.item.Item;
import com.demo.corelib.network.base.RequestCallbackListener;
import java.util.List;
import java.util.Map;

public class MainRepository {

    private static MainRepository INSTANCE = null;


    // 数据来源分为两部分，一部分是本地数据库，一部分是网络
    private MainRepository() {
    }

    /**
     * 获取数据仓库示例
     */
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
     * @param queryParams 查询参数
     * @param listener 回调接口
     */
    public void getItems(Map<String, Object> queryParams, RequestCallbackListener<List<Item>> listener) {
    }

    /**
     * TODO 改造该方法，加入本地缓存逻辑。
     * 从远程加载更多商品数据
     * @param nextLinkUrl 请求url
     * @param listener 回调接口
     */
    public void loadMoreItems(String nextLinkUrl, RequestCallbackListener<List<Item>> listener) {
    }


}
