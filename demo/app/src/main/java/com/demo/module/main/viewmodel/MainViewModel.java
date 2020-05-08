package com.demo.module.main.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.demo.corelib.constant.ApiConstant;
import com.demo.corelib.model.common.LinksModel;
import com.demo.corelib.model.item.Item;
import com.demo.corelib.network.base.RequestCallbackListener;
import com.demo.module.data.MainRepository;
import java.util.List;
import java.util.Map;

public class MainViewModel extends ViewModel {
    private MutableLiveData<String> title = new MutableLiveData<>();

    private MutableLiveData<Boolean> isDataLoading = new MutableLiveData<>();

    private final MainRepository mMainRepository;

    private String nextLinkUrl;

    public MainViewModel() {
        this.mMainRepository = MainRepository.getInstance();
    }


    public MutableLiveData<String> getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title.setValue(title);
    }

    public MutableLiveData<Boolean> getIsDataLoading() {
        return this.isDataLoading;
    }

    private void setDataLoading(boolean isLoading) {
        this.isDataLoading.setValue(isLoading);
    }

    /**
     * 加载商品
     * @param queryParams
     */
    public void loadItems(Map<String, Object> queryParams) {
        this.mMainRepository.getItems(queryParams, new RequestCallbackListener<List<Item>>() {
            @Override public void onStarted() {
                setDataLoading(true);
            }

            @Override public void onCompleted(List<Item> items, LinksModel links) {
                    if (links != null && links.getNext() != null) {
                        nextLinkUrl = ApiConstant.BASE_URL + links.getNext();
                    } else {
                        nextLinkUrl = null;
                    }
            }

            @Override public void onEndedWithError(String s) {
                setDataLoading(false);
            }
        });
    }

    public void loadMoreItems() {
        this.mMainRepository.loadMoreItems(nextLinkUrl, new RequestCallbackListener<List<Item>>() {
            @Override public void onStarted() {
                setDataLoading(true);
            }

            @Override public void onCompleted(List<Item> items, LinksModel linksModel) {

            }

            @Override public void onEndedWithError(String s) {
                setDataLoading(false);
            }
        });
    }




}
