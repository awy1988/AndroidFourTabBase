package com.demo.appko
import android.app.Application
import android.util.Log
import com.demo.corelib.CoreLib

class App : Application() {
  override fun onCreate() {
    super.onCreate()
    CoreLib.init(this)
    Log.e("hello","Appko's onCreate is in")
  }
}
