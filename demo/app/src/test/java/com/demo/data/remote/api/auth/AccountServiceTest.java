package com.demo.data.remote.api.auth;

import com.demo.corelib.model.common.LinksModel;
import com.demo.corelib.network.base.HttpApiHelper;
import com.demo.corelib.network.base.RequestCallbackListener;
import com.demo.data.db.User;
import com.demo.ui.constant.AppModuleApiConstant;
import java.util.concurrent.CountDownLatch;
import org.junit.Before;
import org.junit.Test;

public class AccountServiceTest {

    AccountService accountService;

    @Before
    public void prepareAccountService() {
        accountService = new AccountService(
            HttpApiHelper.getRetrofitInstance(AppModuleApiConstant.BASE_URL)
                .create(IAccountService.class));
    }

    @Test
    public void getUserInfo() throws InterruptedException {
        CountDownLatch countDown = new CountDownLatch(1);

        accountService.getUserInfo(new RequestCallbackListener<User>() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onCompleted(User data, LinksModel links) {
                countDown.countDown();
            }

            @Override
            public void onEndedWithError(String errorInfo) {

            }
        });
        countDown.await();


    }
}
