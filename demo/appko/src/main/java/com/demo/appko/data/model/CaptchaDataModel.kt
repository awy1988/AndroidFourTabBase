package com.demo.appko.data.model

import com.demo.corelib.util.ScreenUtils

data class CaptchaDataModel(val encryptedData: String, val imageData:String, val validUntil: Long) {
}
