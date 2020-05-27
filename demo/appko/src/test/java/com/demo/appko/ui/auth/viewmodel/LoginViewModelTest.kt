package com.demo.appko.ui.auth.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.demo.appko.data.UserRepository
import com.demo.appko.data.model.CaptchaDataModel
import com.demo.corelib.network.base.HandleResponseHeaderRequestCallbackListener
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class LoginViewModelTest {

    private lateinit var viewModel: LoginViewModel
    private lateinit var userRepository: UserRepository
    private lateinit var loginCallbackListener: HandleResponseHeaderRequestCallbackListener<Any>

    @get:Rule
    public var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        userRepository = mock()
        loginCallbackListener = mock()
        viewModel = LoginViewModel(userRepository)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun login_success() {
        viewModel.userName.value = "super"
        viewModel.passWord.value = "super"
        viewModel.inputCaptcha.value = ""
        viewModel.captchaData.value = CaptchaDataModel("","",1234L)
        viewModel.login()
        verify(userRepository).login("super", "super","","", viewModel.loginCallbackListener)

    }

    @Test
    fun login_failed() {

    }



}
