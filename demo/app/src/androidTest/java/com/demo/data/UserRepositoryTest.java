package com.demo.data;

import android.content.Context;
import androidx.room.Room;
import com.demo.corelib.model.common.LinksModel;
import com.demo.corelib.network.base.HttpApiHelper;
import com.demo.corelib.network.base.RequestCallbackListener;
import com.demo.data.db.AppDatabase;
import com.demo.data.db.User;
import com.demo.data.remote.api.auth.AccountService;
import com.demo.data.remote.api.auth.AuthService;
import com.demo.data.remote.api.auth.IAccountService;
import com.demo.data.remote.api.auth.IAuthService;
import com.demo.ui.App;
import com.demo.ui.constant.AppModuleApiConstant;
import java.util.concurrent.CountDownLatch;
import org.junit.Before;
import org.junit.Test;

public class UserRepositoryTest {

    Context mContext = App.getContext();;

    AuthService authService;

    AccountService accountService;

    AppDatabase appDatabase;

    RequestCallbackListener listener;

    UserRepository mUserRepository;

    @Before
    public void prepareUserRepository() {

        authService = new AuthService(HttpApiHelper.getRetrofitInstance(AppModuleApiConstant.BASE_URL, AppModuleApiConstant.IS_DEBUG)
            .create(IAuthService.class));
        accountService = new AccountService(HttpApiHelper.getRetrofitInstance(AppModuleApiConstant.BASE_URL, AppModuleApiConstant.IS_DEBUG)
            .create(IAccountService.class));
        appDatabase = Room.databaseBuilder(mContext,
            AppDatabase.class, "test-database-name").build();

        mUserRepository = new UserRepository(authService, accountService, appDatabase);
    }

    @Test
    public void getUserInfo222() throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(1);

        listener = new RequestCallbackListener<User>() {
            @Override
            public void onStarted() {
                System.out.println("listener onStarted");
            }

            @Override
            public void onCompleted(User data, LinksModel links) {
                System.out.println("listener onCompleted");
                latch.countDown();
            }

            @Override
            public void onEndedWithError(String errorInfo) {
                System.out.println("listener onEndedWithError");
            }
        };
        mUserRepository.getUserInfo("token",listener);

        latch.await();

        System.out.println("test ended");


    }
}
