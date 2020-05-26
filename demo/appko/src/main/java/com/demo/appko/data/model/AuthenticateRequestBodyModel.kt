package com.demo.appko.data.model

import com.demo.corelib.model.api.Captcha

class AuthenticateRequestBodyModel {
    var username: String? = null
    var password: String? = null
    var captcha: Captcha? = null

}
