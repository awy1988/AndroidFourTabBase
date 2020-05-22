package com.demo.appko.ui.auth.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.demo.appko.data.CaptchaDataModel

class LoginViewModel : ViewModel(){
    val userName: MutableLiveData<String> = MutableLiveData()
    val passWord: MutableLiveData<String> = MutableLiveData()
    val inputCaptcha: MutableLiveData<String> = MutableLiveData()
    val loginSuccess: MutableLiveData<Boolean> = MutableLiveData()
    val captchaData: MutableLiveData<CaptchaDataModel> = MutableLiveData()
}
