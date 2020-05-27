package com.demo.appko.ui.auth.viewmodel

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.demo.appko.data.UserRepository
import com.demo.appko.data.model.CaptchaDataModel
import com.demo.appko.data.remote.api.AuthService
import com.demo.corelib.model.common.LinksModel
import com.demo.corelib.network.base.HandleResponseHeaderRequestCallbackListener
import com.demo.corelib.util.SPUtils
import okhttp3.Headers
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
    val userName: MutableLiveData<String> = MutableLiveData("super")
    val passWord: MutableLiveData<String> = MutableLiveData("super")
    val inputCaptcha: MutableLiveData<String> = MutableLiveData()
    val loginSuccess: MutableLiveData<Boolean> = MutableLiveData()
    val captchaData: MutableLiveData<CaptchaDataModel> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()

    fun login() {

        userRepository.login(userName.value.toString(), passWord.value.toString(), inputCaptcha.value.toString(),
            this.captchaData.value?.encryptedData.toString(),
            loginCallbackListener
        )
    }

    val loginCallbackListener = object : HandleResponseHeaderRequestCallbackListener<Any> {
        override fun onStarted() {
        }

        override fun onEndedWithError(errorInfo: String?) {
            errorMessage.value = errorInfo
        }

        override fun onHandleResponseHeaders(headers: Headers?) {
            val token = headers?.get("x-access-token")
            if (!TextUtils.isEmpty(token)) {
                SPUtils.saveAccessToken(token)
            }
        }

        override fun onCompleted(
            data: Any?,
            links: LinksModel?
        ) {
            loginSuccess.value = true
        }

    }

}
