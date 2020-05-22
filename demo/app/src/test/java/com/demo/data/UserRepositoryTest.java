package com.demo.data;

import com.demo.corelib.model.common.LinksModel;
import com.demo.corelib.network.base.HandleResponseHeaderRequestCallbackListener;
import com.demo.data.db.AppDatabase;
import com.demo.data.remote.api.auth.AccountService;
import com.demo.data.remote.api.auth.AuthService;
import okhttp3.Headers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserRepositoryTest {

    @Mock
    AuthService authService;

    @Mock
    AccountService accountService;

    @Mock
    AppDatabase appDatabase;

    @InjectMocks
    UserRepository mUserRepository;

    @Before
    public void initUserRepository(){
        //authService = Mockito.mock(AuthService.class);
        //accountService = Mockito.mock(AccountService.class);
        //appDatabase = Mockito.mock(AppDatabase.class);

        //mUserRepository = new UserRepository(authService, accountService, appDatabase);
        System.out.println("initUserRepository in ");
    }

    /**
     * 测试登录业务，目标：
     * 传入用户名密码
     * 1.可以登录成功
     * 2.返回token
     * 3.token成功保存
     */
    @Test
    public void login() {

        HandleResponseHeaderRequestCallbackListener listener = new HandleResponseHeaderRequestCallbackListener() {
            @Override
            public void onHandleResponseHeaders(Headers headers) {

            }

            @Override
            public void onStarted() {

            }

            @Override
            public void onCompleted(Object data, LinksModel links) {

            }

            @Override
            public void onEndedWithError(String errorInfo) {

            }
        };
        mUserRepository.login("super", "super", null, null, listener);

        Mockito.verify(authService).authorizations("super","super",null, null, listener);
    }

    @Test
    public void captchaNecessaryCheck() {
    }

    @Test
    public void validateCaptcha() {
    }

    @Test
    public void getUserInfo() {
    }
}
