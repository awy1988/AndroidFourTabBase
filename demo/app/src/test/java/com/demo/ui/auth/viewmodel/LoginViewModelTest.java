package com.demo.ui.auth.viewmodel;

import android.text.TextUtils;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import com.demo.corelib.util.SPUtils;
import com.demo.data.UserRepository;
import com.demo.data.model.CaptchaDataModel;
import okhttp3.Headers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ SPUtils.class, TextUtils.class })
public class LoginViewModelTest {

    @Mock
    private UserRepository userRepository;

    @Captor
    private ArgumentCaptor<LoginViewModel.LoginCallbackListener> loginCallbackListenerArgumentCaptor;

    @Captor
    private ArgumentCaptor<LoginViewModel.ValidateCaptchaCallbackListener> validateCaptchaCallbackListenerArgumentCaptor;

    @Captor
    private ArgumentCaptor<LoginViewModel.CaptchaCheckListener> captchaCheckListenerArgumentCaptor;

    @Captor
    private ArgumentCaptor<LoginViewModel.RefreshCaptchaCallbackListener> refreshCaptchaCallbackListenerArgumentCaptor;

    private LoginViewModel loginViewModel;

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() throws Exception {
        loginViewModel = new LoginViewModel(userRepository);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void login_success() {
        PowerMockito.mockStatic(TextUtils.class);
        PowerMockito.mockStatic(SPUtils.class);
        PowerMockito.when(TextUtils.isEmpty(Mockito.anyString())).thenReturn(false);

        doLogin();
        verify(userRepository).login(eq("super"), eq("super"), eq("2m4n"), eq("encryptedData"), loginCallbackListenerArgumentCaptor.capture());


        loginCallbackListenerArgumentCaptor.getValue().onStarted();
        assertEquals(true, loginViewModel.isLoading().getValue());

        Headers headers = new Headers.Builder().add("x-access-token", "accessToken").build();
        loginCallbackListenerArgumentCaptor.getValue().onHandleResponseHeaders(headers);
        PowerMockito.verifyStatic(SPUtils.class);
        SPUtils.saveAccessToken("accessToken");
        assertEquals(true, loginViewModel.getLoginSuccess().getValue());

        loginCallbackListenerArgumentCaptor.getValue().onCompleted(null, null);
        assertEquals(false, loginViewModel.isLoading().getValue());

    }



    @Test
    public void login_failed() {

        doLogin();
        verify(userRepository).login(eq("super"), eq("super"), eq("2m4n"), eq("encryptedData"), loginCallbackListenerArgumentCaptor.capture());


        loginCallbackListenerArgumentCaptor.getValue().onStarted();
        assertEquals(true, loginViewModel.isLoading().getValue());

        String errorMessage = "errorMessage";
        loginCallbackListenerArgumentCaptor.getValue().onEndedWithError(errorMessage);
        assertEquals(false, loginViewModel.isLoading().getValue());
        assertEquals("errorMessage", loginViewModel.getErrorMessage().getValue());
    }

    @Test
    public void validateCaptcha_success() {

        doValidateCaptcha();

        verify(userRepository).validateCaptcha(eq("2m4n"), eq("encryptedData"), validateCaptchaCallbackListenerArgumentCaptor.capture());

        // 确认请求成功的情况下，login方法被调用，因框架无法直接判断 loginViewModel.login 被调用，所以采用确认 UserRepository 对象行为的方法。
        validateCaptchaCallbackListenerArgumentCaptor.getValue().onCompleted(null, null);
        verify(userRepository).login(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.any());

    }

    @Test
    public void validateCaptcha_failed() {

        doValidateCaptcha();

        verify(userRepository).validateCaptcha(eq("2m4n"), eq("encryptedData"), validateCaptchaCallbackListenerArgumentCaptor.capture());

        // 验证请求失败的情况下，refreshCaptcha方法被调用，因框架无法直接判断 loginViewModel.refreshCaptcha 被调用，所以采用确认 userRepository 对象行为的方法。
        validateCaptchaCallbackListenerArgumentCaptor.getValue().onEndedWithError("errorMessage");
        assertThat(loginViewModel.getErrorMessage().getValue(), is("errorMessage"));
        verify(userRepository).captchaNecessaryCheck(Mockito.isNull(), Mockito.any());

    }

    /**
     * 不需要验证码的情况
     */
    @Test
    public void captchaCheck_success_captchaIsUnnecessary() {

        doCaptchaCheck();
        verify(userRepository).captchaNecessaryCheck(eq("super"), captchaCheckListenerArgumentCaptor.capture());

        captchaCheckListenerArgumentCaptor.getValue().onCompleted(null, null);
        assertNull(loginViewModel.getCaptchaData().getValue());
        verify(userRepository).login(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());

    }

    /**
     * 需要验证码的情况
     */
    @Test
    public void captchaCheck_success_captchaIsNecessary() {

        doCaptchaCheck();
        verify(userRepository).captchaNecessaryCheck(eq("super"), captchaCheckListenerArgumentCaptor.capture());

        CaptchaDataModel data = new CaptchaDataModel();
        captchaCheckListenerArgumentCaptor.getValue().onCompleted(data, null);
        assertEquals(data, loginViewModel.getCaptchaData().getValue());
        // 确认某个方法没有被调用，使用 verify()
        verify(userRepository, never()).login(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());
    }

    @Test
    public void captchaCheck_failed() {
        doCaptchaCheck();
        verify(userRepository).captchaNecessaryCheck(eq("super"), captchaCheckListenerArgumentCaptor.capture());
        String errorMessage = "errorMessage";
        captchaCheckListenerArgumentCaptor.getValue().onEndedWithError(errorMessage);
        assertEquals(errorMessage, loginViewModel.getErrorMessage().getValue());
    }

    @Test
    public void refreshCaptcha_success() {
        loginViewModel.refreshCaptcha();
        verify(userRepository).captchaNecessaryCheck(Mockito.isNull(), refreshCaptchaCallbackListenerArgumentCaptor.capture());
        CaptchaDataModel dataModel = new CaptchaDataModel();
        refreshCaptchaCallbackListenerArgumentCaptor.getValue().onCompleted(dataModel, null);
        assertEquals(dataModel, loginViewModel.getCaptchaData().getValue());
    }

    @Test
    public void refreshCaptcha_failed() {
        loginViewModel.refreshCaptcha();
        verify(userRepository).captchaNecessaryCheck(Mockito.isNull(), refreshCaptchaCallbackListenerArgumentCaptor.capture());
        String errorMessage = "refreshCaptchaErrorMessage";
        refreshCaptchaCallbackListenerArgumentCaptor.getValue().onEndedWithError(errorMessage);
        assertEquals("refreshCaptchaErrorMessage", loginViewModel.getErrorMessage().getValue());
    }

    private void doLogin() {
        loginViewModel.getUserName().setValue("super");
        loginViewModel.getPassword().setValue("super");
        loginViewModel.getInputCaptchaText().setValue("2m4n");
        CaptchaDataModel captchaDataModel = new CaptchaDataModel();
        captchaDataModel.setEncryptedData("encryptedData");
        loginViewModel.getCaptchaData().setValue(captchaDataModel);
        loginViewModel.login();
    }


    private void doValidateCaptcha() {
        loginViewModel.getInputCaptchaText().setValue("2m4n");
        CaptchaDataModel captchaDataModel = new CaptchaDataModel();
        captchaDataModel.setEncryptedData("encryptedData");
        loginViewModel.getCaptchaData().setValue(captchaDataModel);
        loginViewModel.validateCaptcha();
    }

    private void doCaptchaCheck() {
        loginViewModel.getUserName().setValue("super");
        loginViewModel.captchaCheck();
    }
}
