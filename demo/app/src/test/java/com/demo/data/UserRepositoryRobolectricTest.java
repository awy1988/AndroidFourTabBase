package com.demo.data;

import android.os.Build;
import com.demo.corelib.network.base.RequestCallbackListener;
import com.demo.data.db.AppDatabase;
import com.demo.data.remote.api.auth.AccountService;
import com.demo.data.remote.api.auth.AuthService;
import com.demo.ui.App;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@Config(sdk = Build.VERSION_CODES.O_MR1)
@RunWith(RobolectricTestRunner.class)
public class UserRepositoryRobolectricTest {

    AuthService authService;

    AccountService accountService;

    AppDatabase appDatabase;

    App app;

    RequestCallbackListener listener;

    UserRepository mUserRepository;

    @Before
    public void initUserRepository(){
        authService = Mockito.mock(AuthService.class);
        accountService = Mockito.mock(AccountService.class);
        appDatabase = Mockito.mock(AppDatabase.class);
        app = Mockito.mock(App.class);
        mUserRepository = new UserRepository(authService, accountService, appDatabase);
        System.out.println("initUserRepository in ");
    }

    @Test
    public void getUserInfo() {
        // 在getUserInfo中用到了App对象，所以无法实施Junit单体测试
        mUserRepository.getUserInfo("token",listener);
        Mockito.verify(accountService).getUserInfo(listener);
    }
}
