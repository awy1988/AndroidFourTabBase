package com.demo.appko.ui.splash

import android.content.Intent
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import com.demo.appko.databinding.AppkoSplashActBinding
import com.demo.appko.ui.auth.LoginActivity
import com.demo.appko.ui.base.BaseFragmentActivity
import com.demo.corelib.util.ScreenUtils

class SplashActivity : BaseFragmentActivity() {

    private lateinit var binding: AppkoSplashActBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ScreenUtils.setFullScreen(this)
        binding = AppkoSplashActBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        val animation = AlphaAnimation(0.3f, 1.0f)
        animation.duration = 4000
        animation.setAnimationListener(object : AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                toLogin()
            }

            override fun onAnimationStart(animation: Animation?) {

            }
        })
        binding.tvLaunchScreen.startAnimation(animation)

    }

    private fun toLogin() {
        startActivity(Intent(this,LoginActivity::class.java))
    }
}
